BUCKET_NAME="apigee-migration-demo2"
gsutil rm gs://$BUCKET_NAME/express-gateway.sh
gsutil rm -r gs://$BUCKET_NAME

gcloud deployment-manager deployments delete migration-deployment
