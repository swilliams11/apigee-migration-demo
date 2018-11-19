/**
Copyright 2018 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**/

package migration

import java.lang.Integer

import scala.util.Random
import java.util.UUID

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._
import java.util.concurrent.ConcurrentHashMap

import scala.util.parsing.json._
import org.apache.commons._
import org.apache.http._
import org.apache.http.client._
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import java.util.ArrayList

import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.entity.StringEntity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuffer

import com.typesafe.config.ConfigFactory;

class BasicSimulation extends Simulation {
  val conf = ConfigFactory.load()
  val baseUrl = conf.getString("baseUrl")
  val org = conf.getString("apigeeOrg")
  val env = conf.getString("apigeeEnv")
  val clientId = conf.getString("clientId")
  val secret = conf.getString("clientSecret")

  //No need to update these values
  val credentialsPayload = """ {"client_id":"""" + clientId + """","client_secret":"""" + secret + """","grant_type":"client_credentials"} """
  val tokenCache : ConcurrentHashMap[String, String] = new ConcurrentHashMap()
  val tokenURL = "https://" + org + "-" + env + ".apigee.net/edgemicro-auth/token"
  //var feeder = Iterator.continually(Map("test" -> ("test")))
  //var feeder = Array(Map("test" -> "test")).circular
  val authorization_header = Map("Content-Type" -> """application/x-www-form-urlencoded""")
  val content_type_request_headers = Map("Content-Type" -> """application/json""")

  /*
  This is a hook that Gatling executes before the simulation executes.
  This sends the request to the Apigee Edgemicro OAuth proxy to retrieve an access token.
  */

  /*
  before {
    val url = tokenURL
    val post = new HttpPost(url)
    post.addHeader("Content-type","application/json")

    val client = new DefaultHttpClient
    val stringEntity =
      new StringEntity(credentialsPayload)
    post.setEntity(stringEntity)

    // send the post request
    val response = client.execute(post)
    println("--- HEADERS ---")
    response.getAllHeaders.foreach(arg => println(arg))
    val rd = new BufferedReader(
      new InputStreamReader(response.getEntity().getContent()));

    val str = Stream.continually(rd.readLine()).takeWhile(_ != null).mkString("\n")
    val json = JSON.parseFull(str)
    println(str);
    println(json.get.asInstanceOf[Map[String, Any]]("token").asInstanceOf[String])

    tokenCache.put("jwtcache", json.get.asInstanceOf[Map[String, Any]]("token").asInstanceOf[String])
    println("concurrentHashmap.size() " + tokenCache.size)

    //Not sure if this is the proper way to use the feeder
    //feeder = Iterator.continually(Map("jwtcache" -> (tokenCache.get("jwtcache"))))
    //feeder = Array(Map("jwtcache2" -> tokenCache.get("jwtcache"))).circular
  }
*/

  /*
  Setup the http object that will be used by all the gatling scenarios.
  */
  val host = org + "-" + env
  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("application/json") // Here are the common headers
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")

  val feeder = Iterator.continually(Map("email" -> (Random.alphanumeric.take(20).mkString + "@foo.com"),
      "nonce" -> new Integer(Random.nextInt(Integer.MAX_VALUE)).toString(),
      "sid" -> UUID.randomUUID().toString()))

  def nonce() : String = { return new Integer(Random.nextInt(Integer.MAX_VALUE)).toString() }
  def sid() : String = { return UUID.randomUUID().toString() }

  /*
  Setup the first scenario that will send an authorization request for each user
  and then send a request to the Edge Microgateway with the JWT.
  */
  val scn = scenario("Migration Demo")
    .feed(feeder)
      .exec(http("hello world!")
        .get("/")
          .header("userid", "${email}")
        .header("Sid", "${sid}")
        .header("Nonce", "${nonce}")
        .header("Host", host)
        //.body(RawFileBody("user-files/request-bodies/authorization_request.json")).asJSON //example of how to use predefined payloads
        //.check(
        //  header("BizToken").saveAs("bizToken")
        //)
      ).pause(250 milliseconds)

    /*
    This scenario uses the access token retrieved from the Before hook.
    It shares that token across all the user scenarios.
    */
    /*
    val scn2 = scenario("Edgemicro/Spring Boot Test - shared token")
      .exec( session => {
        val newsession = (session.set("jwtcache", tokenCache.get("jwtcache")).set("tokenCache", tokenCache))
        //println("this is the token from the cache -> " + tokenCache.get("jwtcache"))
        newsession
      })
      .exec(http("Greeting - shared token")
        .get("/")
        .header("Authorization", "Bearer ${jwtcache}")
      )
      .pause(100 milliseconds)
*/
  //setup the scenarios
  setUp(
    scn.inject(
      //atOnceUsers(5)
      constantUsersPerSec(2) during (5 minutes)
    ).protocols(httpConf)
/*
    scn2.inject(
      constantUsersPerSec(1) during (30 seconds)
    ).protocols(httpConf)
    */
  )
}
