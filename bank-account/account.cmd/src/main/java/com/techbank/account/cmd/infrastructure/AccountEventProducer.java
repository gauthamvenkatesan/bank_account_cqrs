package com.techbank.account.cmd.infrastructure;

import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountEventProducer implements EventProducer {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        try {
            this.kafkaTemplate.send(topic, event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
