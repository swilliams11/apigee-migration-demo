#! /bin/bash

sudo mkdir /opt/nodejs
sudo curl https://nodejs.org/dist/v10.14.1/node-v10.14.1-linux-x64.tar.gz | sudo tar xvzf - -C /opt/nodejs --strip-components=1
sudo ln -s /opt/nodejs/bin/node /usr/bin/node
sudo ln -s /opt/nodejs/bin/npm /usr/bin/npm

sudo npm install -g express-gateway
#create the symlink
sudo ln -s /opt/nodejs/lib/node_modules/express-gateway/bin/index.js /usr/bin/eg

#install monitoring agent and stackdriver agent
sudo curl -sSO https://dl.google.com/cloudagents/install-monitoring-agent.sh
sudo bash install-monitoring-agent.sh

sudo curl -sSO https://dl.google.com/cloudagents/install-logging-agent.sh
sudo bash install-logging-agent.sh

#init express-gateway
cd ~
eg gateway create -n my-gateway -d my-gateway -t basic
# remove the current config file
sudo rm -rf ~/my-gateway/config/gateway.config.yml
#fetch the config file from github
sudo curl https://raw.githubusercontent.com/swilliams11/apigee-migration-demo/master/gcp-deployment-manager/config/gateway.config.yaml -o ~/my-gateway/config/gateway.config.yml

#start express gateway in the background
cd my-gateway
nohup npm start &
