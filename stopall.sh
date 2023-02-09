#!/bin/sh

cd deploy
kubectl delete --ignore-not-found=true -f server.yml
kubectl delete --ignore-not-found=true -f client.yml
kubectl delete --ignore-not-found=true -f istio-route/virtual-service.yml
kubectl delete --ignore-not-found=true -f istio-route/destination-rule.yml
kubectl delete --ignore-not-found=true -f istio-route/gateway.yml
cd ..

echo "
#############################################

stopped

#############################################
"
