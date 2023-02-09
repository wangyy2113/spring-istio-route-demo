# SpringBoot Application & Istio route Practice
---------------
将SpringBoot应用服务部署到Istio构建service-mesh, 并做一些路由实践

理论参考 https://istio.io/latest/zh/docs/concepts/traffic-management/

环境准备

docker & minikube & istio & jdk1.8 & maven

Getting Started
---------------
0. 环境准备
- minikube安装参考 https://minikube.sigs.k8s.io/docs/start/
- istio安装参考https://istio.io/latest/zh/docs/setup/install/istioctl/
- 开启istio sidecar自动注入(将k8s default命名空间标记为 istio-injection=enabled)
```sh
kubectl label namespace default istio-injection=enabled --overwrite
```
- clone
```sh
git clone https://github.com/wangyy2113/spring-istio-route-demo.git
cd spring-istio-route-demo
```


1. 启动minikube
```sh
minikube start
```

2. start demo
```sh
sh bootstrap.sh
```

3. 开启minikube tunnel便于外部请求访问服务
```sh
minikube tunnel
```

4. 检查服务运行状态, READY=2/2, STATUS=Running
```sh
kubectl get pod|grep istio-route-demo
```

5. HTTP请求路由验证

发送http请求访问gateway，gateway会将请求转发到client, client再构造HTTP请求访问server(server有两个版本server-v1 & server-v2)

```sh
curl '127.0.0.1:80/greet/http?featureTag=v2'
curl '127.0.0.1:80/greet/http?featureTag=v1'
curl '127.0.0.1:80/greet/http?featureTag=v3'
curl '127.0.0.1:80/greet/http?featureTag=v4'
```
- featureTag=v2的请求会打到server-v2, 其余的都会打到server-v1
- 即curl '127.0.0.1:80/greet/http?featureTag=v2' 会得到Response "Client receive Server msg: Http Greeting from server-v2"
- 其他请求会得到Response "Client receive Server msg: Http Greeting from server-v1"

6. 关闭服务
```sh
sh stopall.sh
```

对于minikube tunnel 命令行 control + C 即可

关闭minikube

```sh
minikube stop
```