package com.bruno.vertx.tempsensor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorUpdateLogger extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(SensorUpdateLogger.class);
    //private final DecimalFormat format = new DecimalFormat("##.#");

    @Override
    public void start() throws Exception {
        vertx.eventBus().<JsonObject>consumer("sensor.updates", msg -> {
            JsonObject body = msg.body();
            logger.info("{} reports a temp ~{}C", body.getString("id"), body.getDouble("temp"));
        });
    }
}
