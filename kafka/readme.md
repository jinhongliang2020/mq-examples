
# 1. 说明关键接口区别 ;
Kafka消费者，实现有两种方式：client客户端和listener监听接口，这里因业务需要，采用监听接口的方式实现，Spring提供了四种接口，如下所示：

使用MessageListener接口实现时，当消费者拉取消息之后，消费完成会自动提交offset，即enable.auto.commit为true时，适合使用此接口 
```
public interface MessageListener<K, V> {} 
   void onMessage(ConsumerRecord<K, V> data);
}
```
 
使用AcknowledgeMessageListener时，当消费者消费一条消息之后，不会自动提交offset，需要手动ack，即enable.auto.commit为false时，适合使用此接口  
```
public interface AcknowledgingMessageListener<K, V> {} 
 void onMessage(ConsumerRecord<K, V> data, Acknowledgment acknowledgment);
}
```

BatchMessageListener和BatchAcknowledgingMessageListener接口作用与上述两个接口大体类似，只是适合批量消费消息决定是否自动提交offset

由于业务较重，且offset自动提交时，出现消费异常或者消费失败的情况，消费者容易丢失消息，所以需要采用手动提交offset的方式，因此，这里实现了AcknowledgeMessageListener接口。
```
public interface BatchMessageListener<K, V> {} 
 void onMessage(List<ConsumerRecord<K, V>> data);
}
```
```
 public interface BatchAcknowledgingMessageListener<K, V> {} 
     void onMessage(List<ConsumerRecord<K, V>> data, Acknowledgment acknowledgment);
 }
``` 


# 2.kafka 消息的存放机制.

物理上把 topic 分成一个或多个 patition（对应 server.properties 中的 num.partitions=3 配置），每个 patition 物理上对应一个文件夹（该文件夹存储该 patition 的所有消息和索引文件）


# 5.kafka consumer group

发布订阅模式下，能否实现订阅者负载均衡消费呢？当发布者消息量很大时，显然单个订阅者的处理能力是不足的。实际上现实场景中是多个订阅者节点组成一个订阅组负载均衡消费topic消息即分组订阅， 
这样订阅者很容易实现消费能力线性扩展。 

kafka 的分配单位是 patition,每个 consumer 都属于一个 group，一个 partition 只能被同一个 group 内的一个 consumer 所消费（也就保障了一个消息只能被 group 内的一个 consuemr 所消费），但是多个 group 可以同时消费这个 partition。


# 6.为什么说Kafka使用磁盘比内存快 
  其实Kafka最核心的思想是使用磁盘，而不是使用内存，可能所有人都会认为，内存的速度一定比磁盘快，我也不例外。在看了Kafka的设计思想，查阅了相应资料再加上自己的测试后，发现磁盘的顺序读写速度和内存持平。
  而且Linux对于磁盘的读写优化也比较多，包括read-ahead和write-behind，磁盘缓存等。如果在内存做这些操作的时候，一个是JAVA对象的内存开销很大，另一个是随着堆内存数据的增多，JAVA的GC时间会变得很长，使用磁盘操作有以下几个好处：
  
  - 磁盘缓存由Linux系统维护，减少了程序员的不少工作。
  - 磁盘顺序读写速度超过内存随机读写。
  - JVM的GC效率低，内存占用大。使用磁盘可以避免这一问题。
  - 系统冷启动后，磁盘缓存依然可用。
  



 

