# apigee-migration-demo

This repo demonstrates how a typical Apigee migration is performed from an existing gateway to Apigee Edge.  

# Summary
This project has a GCP project with:
1. GCP TCP load Balancer
2. VM with Node.js express-gateway installed
3. VM with Node.js hello world application
4. VM with Edge AIO installation

A Gatling script is used to generate the traffic. A DNS cutover is performed on the GCP load balancer to route traffic from the express-gateway to Apigee Edge.  

# Load Balancer Cutover

# Apigee Edge Routing with Proxy Dials
