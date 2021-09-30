package com.bruno.vertx.tempsensor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;

import java.util.Random;
import java.util.UUID;

/**
 * This sensor randomly send temperature updates
 */
public class HeatSensorVerticle extends AbstractVerticle {

    private final String sensorId = UUID.randomUUID().toString();
    private final Random random = new Random();
    private double temp = 21.0;

    @Override
    public void start() throws Exception {
        scheduleNextUpdate();
    }

    private void scheduleNextUpdate() {
        vertx.setTimer(random.nextInt(5000) + 1000, this::update);
    }

    private void update(long aLong) {
        temp = temp + (delta() / 10);
        JsonObject payload = new JsonObject()
                .put("id", sensorId)
                .put("temp", temp);
        vertx.eventBus().publish("sensor.updates", payload);
        scheduleNextUpdate();
    }

    private double delta() {
        if (random.nextInt() > 0) {
            return random.nextGaussian();
        } else {
            return -random.nextGaussian();
        }
    }
}
