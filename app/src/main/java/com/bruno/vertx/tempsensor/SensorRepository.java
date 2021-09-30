package com.bruno.vertx.tempsensor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Stores sensor data and send an average temperature
 */
public class SensorRepository extends AbstractVerticle {

    private final HashMap<String, Double> lastValues = new HashMap<>();

    @Override
    public void start() throws Exception {
        EventBus bus = vertx.eventBus();
        bus.consumer("sensor.updates", this::update);
        bus.consumer("sensor.average", this::calculateAverage);
    }

    private void calculateAverage(Message<JsonObject> tMessage) {
        Double avg = lastValues.values().stream().collect(Collectors.averagingDouble(Double::doubleValue));
        JsonObject response = new JsonObject().put("average", avg);
        tMessage.reply(response);
    }

    private void update(Message<JsonObject> message) {
        String id = message.body().getString("id");
        Double temp = message.body().getDouble("temp");
        lastValues.put(id, temp);
    }

}
