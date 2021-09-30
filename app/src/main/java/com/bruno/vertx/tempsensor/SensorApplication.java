package com.bruno.vertx.tempsensor;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class SensorApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(HeatSensorVerticle.class.getCanonicalName(), new DeploymentOptions().setInstances(10000));
        vertx.deployVerticle(SensorUpdateLogger.class.getCanonicalName());
        vertx.deployVerticle(SensorRepository.class.getCanonicalName());
        vertx.deployVerticle(HttpServer.class.getCanonicalName());
    }
}
