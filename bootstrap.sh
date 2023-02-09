#!/bin/sh

echo "
#############################################

mvn build

#############################################
"
mvn clean install -DskipTests

echo "
#############################################

build minikube images

#############################################
"
cd istio-route-demo-server-v1
## docker build --no-cache -t istio-route-demo-server-v1 . && minikube image load istio-route-demo-server-v1
minikube image build -t istio-route-demo-server-v1 .

cd .. && cd istio-route-demo-server-v2
## docker build --no-cache -t istio-route-demo-server-v2 . && minikube image load istio-route-demo-server-v2
minikube image build -t istio-route-demo-server-v2 .

cd .. && cd istio-route-demo-client
## docker build --no-cache -t istio-route-demo-client . && minikube image load istio-route-demo-client
minikube image build -t istio-route-demo-client .

cd ..


echo "
#############################################

applying

#############################################
"
cd deploy

kubectl apply -f server.yml
kubectl apply -f client.yml
kubectl apply -f istio-route/gateway.yml
kubectl apply -f istio-route/destination-rule.yml
kubectl apply -f istio-route/virtual-service.yml
cd ..


echo "
#############################################

success

#############################################
"
