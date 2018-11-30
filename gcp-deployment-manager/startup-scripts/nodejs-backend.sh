#! /bin/bash

sudo mkdir /opt/nodejs
sudo curl https://nodejs.org/dist/v10.14.1/node-v10.14.1-linux-x64.tar.gz | sudo tar xvzf - -C /opt/nodejs --strip-components=1
sudo ln -s /opt/nodejs/bin/node /usr/bin/node
sudo ln -s /opt/nodejs/bin/npm /usr/bin/npm

#install monitoring agent and stackdriver agent
sudo curl -sSO https://dl.google.com/cloudagents/install-monitoring-agent.sh
sudo bash install-monitoring-agent.sh

sudo curl -sSO https://dl.google.com/cloudagents/install-logging-agent.sh
sudo bash install-logging-agent.sh

cd ~
mkdir helloworld

#fetch files from github
sudo curl https://raw.githubusercontent.com/swilliams11/apigee-migration-demo/master/gcp-deployment-manager/backend-services/helloworld/app.js -o ~/hello-world/app.js
sudo curl https://raw.githubusercontent.com/swilliams11/apigee-migration-demo/master/gcp-deployment-manager/backend-services/helloworld/package.json -o ~/hello-world/package.json

cd helloworld
npm install

#start service
nohup npm start &
