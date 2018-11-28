#!/bin/bash

#gcloud compute instance-groups unmanaged create \
#      nodejs-grouptemp --zone us-central1-c

#gcloud compute instance-groups unmanaged create \
#      edge-grouptemp --zone us-central1-c

#gcloud compute instance-groups unmanaged set-named-ports \
#      nodejs-group --named-ports "http:9001"

#gcloud compute instance-groups unmanaged set-named-ports \
#      edge-grouptemp --named-ports "http:9001"

#gcloud compute health-checks create tcp NAME [--check-interval=CHECK_INTERVAL; default="5s"] [--description=DESCRIPTION] [--healthy-threshold=HEALTHY_THRESHOLD; default=2] [--proxy-header=PROXY_HEADER; default="NONE"] [--request=REQUEST] [--response=RESPONSE] [--timeout=TIMEOUT; default="5s"] [--unhealthy-threshold=UNHEALTHY_THRESHOLD; default=2] [--port=PORT; default=80 --port-name=PORT_NAME] [GCLOUD_WIDE_FLAG …]

gcloud deployment-manager deployments create migration-deployment --config migration-demo.yaml


gcloud compute instance-groups unmanaged add-instances nodejs-grouptemp \
  --instances express-gatewaytemp --zone us-central1-c

  gcloud compute instance-groups unmanaged add-instances edge-grouptemp \
    --instances edgeaiotemp --zone us-central1-c
