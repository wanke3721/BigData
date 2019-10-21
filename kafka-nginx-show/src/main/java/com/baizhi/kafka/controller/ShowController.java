package com.baizhi.kafka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.*;

@RestController
public class ShowController {

    private static Jedis jedis = new Jedis("192.168.5.11", 6379);

    @GetMapping("/status")
    public List<Map<String, Object>> status() throws Exception{

        Integer status_304 = Integer.parseInt(jedis.get("kafka-304"));

        Map<Integer,Integer> status = new HashMap<>();
        status.put(304, status_304);
        status.put(200, 10);
        status.put(400, 10);
        status.put(404, 10);
        status.put(405, 5);
        status.put(500, 10);

        Set<Map.Entry<Integer, Integer>> entries = status.entrySet();
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : entries) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name",entry.getKey());
            map.put("y",entry.getValue());
            // 如果饼图的扇区为304，默认选中
            if(entry.getKey() == 304) {
                map.put("selected",true);
                map.put("sliced",true);
            }
            result.add(map);
        }
        return result;
    }

    @GetMapping("/PVAndUV")
    public List<Map<String, Object>> PVAndUV() throws Exception{

        Integer PV = Integer.parseInt(jedis.get("kafka-pv"));
        Integer UV = Integer.parseInt(jedis.get("kafka-uv"));

        Map<String,Integer> PVAndUV = new HashMap<>();
        PVAndUV.put("pv", PV);
        PVAndUV.put("uv", UV);

        Set<Map.Entry<String,Integer>> entries = PVAndUV.entrySet();
        List<Map<String,Object>> result = new ArrayList<>();
        for (Map.Entry<String,Integer> entry : entries) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", entry.getKey());
            map.put("y", entry.getValue());
            map.put("drilldown", entry.getKey());
            result.add(map);
        }
        return result;
    }

}
