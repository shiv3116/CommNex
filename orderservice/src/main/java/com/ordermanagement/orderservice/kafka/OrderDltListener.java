package com.ordermanagement.orderservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDltListener {

    @KafkaListener(topics = "order-dlt", groupId = "order-group")
    public void handleDlt(ConsumerRecord<String, String> record) {
        log.error("DLT message received. Topic={}, Partition={}, Offset={}, Key={}, Value={}, Headers={}",
                record.topic(), record.partition(), record.offset(),
                record.key(), record.value(), record.headers());
    }
}
