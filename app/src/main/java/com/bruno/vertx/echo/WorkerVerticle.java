package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start() throws Exception {
        vertx.setTimer(10000, id -> {
           try {
               logger.info("{}: ZZZ", id);
               Thread.sleep(8000);
               logger.info("{}: Up", id);
           } catch (InterruptedException e) {
               logger.error("Worker interupted", e);
           }
        });
    }

    public static void main(String[] args) {
        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true)
                .setInstances(2000);
        Vertx.vertx().deployVerticle(WorkerVerticle.class.getName(), options);
    }
}
