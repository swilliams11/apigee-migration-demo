# apigee-migration-demo

This repo demonstrates how a typical Apigee migration is performed from an existing gateway to Apigee Edge.  

# Summary
This project has a GCP project with:
1. GCP TCP load Balancer
2. VM with Node.js express-gateway installed
3. VM with Node.js hello world application
4. VM with Edge AIO installation

A Gatling script is used to generate the traffic. A DNS cutover is performed on the GCP load balancer to route traffic from the express-gateway to Apigee Edge.  

# Cutover
This phase represents switching traffic from your current platform to Apigee Edge.  There are two ways to accomplish this:
1. Cutover 100% of your traffic at your load balancer from your current platform to Apigee Edge.
2. Gradually route traffic from your current provider to Apigee Edge.

## 100% Cutover
This can be accomplished by updating your load balancer to route all traffic from your current provider to Apigee Edge.  You can monitor the results of the traffic using Apigee Edge analytics and if your are a SaaS customer, then you can use Apigee API monitoring.  

## Gradual Cutover
A gradual cutover is when you ramp traffic up over a set interval (e.g. 10% per hour) for a set period of time (e.g. 10 hours) and monitor the results.  This can be completed with a full featured load balancer such as Nginx.  

# Migration Demo Setup
## HTTP Load balancer
An HTTP load balancer is configured to cutover traffic from Express Gateway to Apigee Edge.

### Frontend service
The frontend service is listening on port 8080.

### Health check
The health check sends an HTTP request to port 9001.

### Backend Service
There are two backend services:
1. is an instance group with the Express Gateway included.
2. is an instance group with Apigee Edge.

## Express gateway
* listening on port 9001
* configuration is located in `~/my-gateway/config/gateway.config.yml`
* Start the gateway with the following command:
  * `cd ~/my-gateway`
  * `nohup npm start &`
* Exit from GCP terminal by typing `exit` and then push the `enter` key.

## Hello World Node.js backend
This is a sample backend that is used as the Apigee Edge target server.  

You can run the Node.js server with the following commands.
```
cd ~

nohup node app.js &

exit
```


### Server resources
This server has 1 resource listening on the root and only returns hello world.
```
http://ip:8080/
```

# Apigee Edge Routing with Proxy Dials


# Tasks
### Completed Tasks
1. Reviewed [GCP TCP load balancer](https://cloud.google.com/load-balancing/docs/tcp/)
2. Reviewed [GCP HTTP/S load balancer](https://cloud.google.com/load-balancing/docs/https/)
3. Reviewed [GCP Network load balancer](https://cloud.google.com/load-balancing/docs/network/)
  * allows load balancing based on CPU load and other rules, but not based on percentage based routing.
4. Hello World Node.js backend created.  
5. Used [Express Gateway](https://www.express-gateway.io/getting-started/) as the API gateway.
6. Installed Apigee Edge AIO on VM.
7. Created Postman unit tests.
8. Created Gatling test script for load testing.

### Pending Tasks
1. Nginx server for gradual load balancing
2. Increase complexity of Node.js backend application
3. Demo how to migration access tokens
4. demo how to migration refresh tokens
5. Automate deployment of backend server
6. Automate deployment of express-js server
7. Automate deployment of Apigee Edge aio
8. Automate wiring everything together (instance groups, load balancers, firewall rules, etc.)
