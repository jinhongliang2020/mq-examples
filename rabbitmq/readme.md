
# RabbitMQ 

## 1.消息消费确认

 为了确保消息或者任务不会丢失，RabbitMQ支持消息确认–ACK。
 ACK机制是消费者端从RabbitMQ收到消息并处理完成后，反馈给RabbitMQ，RabbitMQ收到反馈后才将此消息从队列中删除。
 那么他就不会有ACK反馈，RabbitMQ会认为这个消息没有正常消费，会将此消息重新放入队列中。
 
 
## 2.消息持久化 

 消息确认可以防止消息丢失，那么RabbitMQ 服务挂了呢？
 这时候就需要消息持久化
 
 如果你想让RabbitMQ记住她当前的状态和内容，就需要通过2件事来确保消息和任务不会丢失。 
 
 - [x] 在队列声明时，告诉RabbitMQ，这个队列需要持久化
 
   ```
   boolean durable = true;
   channel.queueDeclare("hello", durable, false, false, null);
   ```
  
     关于消息持久化的说明： 
     
     标记为持久化后的消息也不能完全保证不会丢失。虽然已经告诉RabbitMQ消息要保存到磁盘上，但是理论上，RabbitMQ已经接收到生产者的消息，
     但是还没有来得及保存到磁盘上，服务器就挂了（比如机房断电），那么重启后，RabbitMQ中的这条未及时保存的消息就会丢失。因为RabbitMQ不做实时立即的磁盘同步（fsync）。
     这种情况下，对于持久化要求不是特别高的简单任务队列来说，还是可以满足的。如果需要更强大的保证，那么你可以考虑使用生产者确认反馈机制。
     
## 3.RabbitMQ Exchange(路由规则)类型详解
 在rabbitmq中，exchange有4个类型：direct，topic，fanout，header     
 - [x] direct exchange  
 
    消费端exchange在和queue进行binding时会设置routingkey
    ```
       //根据路由关键字进行绑定
       for (String routingKey:routingKeys){
           channel.queueBind(queueName,EXCHANGE_NAME,routingKey);
           System.out.println("ReceiveDirect exchange:"+EXCHANGE_NAME+"," +
           queue:"+queueName+", BindRoutingKey:" + routingKey);
       }
    ```  
   生产端将消息发送到exchange时会设置对应的routingkey      
   
    ```
        //发送信息
        for (String routingKey:routingKeys){
            String message = "RoutingSendDirect Send the message level:" + routingKey;
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
            System.out.println("RoutingSendDirect Send"+routingKey +"':'" + message);
        }
    ```            
     在direct类型的exchange中，只有这两个routingkey完全相同，exchange才会选择对应的binging进行消息路由。
     
- [x] topic exchange
   
   此类型exchange和上面的direct类型差不多，但direct类型要求routingkey完全相等，这里的routingkey可以有通配符：'*','#'.
   其中'*'表示匹配一个单词， '#'则表示匹配没有或者多个单词
   
   消费端exchange在和queue进行binding时会设置routingkey
   ```
       //声明一个匹配模式的交换机
       channel.exchangeDeclare(EXCHANGE_NAME, "topic");
       String queueName = channel.queueDeclare().getQueue();
       //路由关键字
       String[] routingKeys = new String[]{"*.orange.*"};
       //绑定路由
       for (String routingKey : routingKeys) {
           channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
           System.out.println("ReceiveLogsTopic1 exchange:" + EXCHANGE_NAME + ", queue:" + queueName + ", BindRoutingKey:" + routingKey);
       }
    ```
    生产端将消息发送到exchange时会设置对应的routingkey  
    ```
        //声明一个匹配模式的交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //待发送的消息
        String[] routingKeys = new String[]{
                "quick.orange.rabbit",
                "lazy.orange.elephant",
                "quick.orange.fox",
                "lazy.brown.fox",
                "quick.brown.fox",
                "quick.orange.male.rabbit",
                "lazy.orange.male.rabbit"
        };
        //发送消息
        for (String severity : routingKeys) {
            String message = "From " + severity + " routingKey' s message!";
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println("TopicSend Sent '" + severity + "':'" + message + "'");
        }
    ``` 
    
- [x]  fanout exchange
   此exchange的路由规则很简单直接将消息路由到所有绑定的队列中，无须对消息的routingkey进行匹配操作。
   
   消费端  
   ```
       channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
       //产生一个随机的队列名称
       String queueName = channel.queueDeclare().getQueue();
       channel.queueBind(queueName, EXCHANGE_NAME, "");//对队列进行绑定
   ```  
   生产端
   ```
       //fanout表示分发，所有的消费者得到同样的队列信息
       channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
       //分发信息
       for (int i=0;i<5;i++){
           String message="Hello World"+i;
           channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
           System.out.println("EmitLog Sent '" + message + "'");
       }
   ```
- [x] header exchange
  此类型的exchange和以上三个都不一样，其路由的规则是根据header来判断，其中的header就是以下方法的arguments参数：






