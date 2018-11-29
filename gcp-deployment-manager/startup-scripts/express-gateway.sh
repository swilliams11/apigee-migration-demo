#! /bin/bash

sudo mkdir /opt/nodejs
sudo curl https://nodejs.org/dist/v10.14.1/node-v10.14.1-linux-x64.tar.gz | sudo tar xvzf - -C /opt/nodejs --strip-components=1
sudo ln -s /opt/nodejs/bin/node /usr/bin/node
sudo ln -s /opt/nodejs/bin/npm /usr/bin/npm

sudo npm install -g express-gateway
#create the symlink
sudo ln -s /opt/nodejs/lib/node_modules/express-gateway/bin/index.js /usr/bin/eg

#init express-gateway
cd ~
eg gateway create -n my-gateway -d my-gateway -t basic

#fetch the config file
sudo curl https://github.com
#start express gateway in the background
