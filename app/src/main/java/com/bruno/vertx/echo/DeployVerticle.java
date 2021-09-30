package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeployVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start() {
        long delay = 5;
        for (var i = 50; i > 0; i--) {
            vertx.setTimer(delay, id -> deploy());
            delay += 5;
        }
    }

    private void deploy() {
        vertx.deployVerticle(new EmptyVerticle(), ar -> {
            if (ar.succeeded()) {
                String id = ar.result();
                vertx.setTimer(5000, tid -> undeployLater(id));
            } else {
                logger.error("Failed to deploy verticle", ar.cause());
            }
        });
    }

    private void undeployLater(String id) {
        vertx.undeploy(id, ar -> {
            if (ar.succeeded()) {
                logger.info("{} was undeployed", id);
            } else {
                logger.error("{} could not be undeployed", id);
            }
        });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new DeployVerticle());
    }
}
