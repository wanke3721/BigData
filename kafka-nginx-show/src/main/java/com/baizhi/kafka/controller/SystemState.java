package com.baizhi.kafka.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SystemState {


    @KafkaListener(topics = "topic-result")
    public Map<String,Long> receive(ConsumerRecord<String,Long> record) throws Exception{
        Long PV = null;
        Long UV = 1L;
        Long STATUS_304 = null;
        if ("PV".equals(record.key())){
            PV = record.value();
        }
        if ("304".equals(record.key())){
            STATUS_304 = record.value();
        }
        Map<String,Long> map = new HashMap<>();
        map.put("pv",PV);
        map.put("uv",UV);
        map.put("304",STATUS_304);
        return map;
    }

}
