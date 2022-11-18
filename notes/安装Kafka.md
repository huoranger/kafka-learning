## 拉取镜像
Zookeeper
```shell
docker pull zookeeper
```

Kafka
```shell
docker pull wurstmeister/kafka
```

## 启动 Zookeeper
```shell
docker run -d --restart=always --log-driver json-file --log-opt max-size=100m --log-opt max-file=2  --name zookeeper -p 2181:2181 wurstmeister/zookeeper
```

## 启动 Kafka

```shell
docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=192.168.31.235:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.31.235:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -t wurstmeister/kafka
```