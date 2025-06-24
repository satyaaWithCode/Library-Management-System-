package com.springBoot.reactive.config;

import com.springBoot.reactive.kafkaTopic.AppConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
//  step-3
   //kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic new-book-topic --from-beginning > bin>windows
    // step-1 is server.properties after creating kafka-logs this is the 1st step otherwise step 1 is kafka-logs- bin\windows\kafka-storage.bat random-uuid
// kafka-server-start.bat ..\..\config\kraft\kafka-server.properties - inside bin>windows but inside config>kraft> this is one kafka-server.properties should present
    //step-3 - using uuid code-b1c232c6-dcc4-4c61-bbfc-8fdaf90bda45  - .\kafka-storage.bat format -t b1c232c6-dcc4-4c61-bbfc-8fdaf90bda45 -c ..\..\config\kraft\kraft-server.properties

   public NewTopic createNewTopic()
   {
       return TopicBuilder
               .name(AppConstant.NEW_BOOK_TOPIC)
               .partitions(1)
               .replicas(1)
               .build();
   }
}
