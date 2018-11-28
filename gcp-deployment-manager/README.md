# Google Cloud Platform Deployment Manager

This directory contains all the resources necessary to deploy this demo to GCP.

## Resources
1. 3 VMs
  * Apigee Edge AIO
  * Express gateway
  * NodeJS backend
2. HTTP load balancer
3. Two instance groups
  * NodeJS instance group, which includes the Express Gateway
  * Edge instance group, which includes Apigee Edge aio.
4. Firewall rules


## Adding/removing instances to an unmanaged instance group

[gcloud command documentation](https://cloud.google.com/compute/docs/instance-groups/creating-groups-of-unmanaged-instances#addinstances)

```
gcloud compute instance-groups unmanaged add-instances [INSTANCE_GROUP] \
  --instances [INSTANCE_NAME],[ANOTHER_INSTANCE_NAME] --zone us-central1-c
```

```
gcloud compute instance-groups unmanaged remove-instances [INSTANCE_GROUP] \
  --instances [INSTANCE_NAME],[ANOTHER_INSTANCE_NAME],[INSTANCES ...] --zone us-central1-f
```

## Deployment

```
cd gcp-deployment-manager
chmod 777 *.sh
```

### Create the deployment
```
./deploy-all.sh
```

### Delete the deployment
```
./delete-deployment.sh
```
