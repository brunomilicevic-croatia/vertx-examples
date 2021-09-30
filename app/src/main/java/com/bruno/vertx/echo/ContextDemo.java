package com.bruno.vertx.echo;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextDemo extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.setTimer(1000, id -> {
            logger.info("Started Demo Verticle");
            throw new IllegalStateException("Exception from Context verticle");
        });
        super.start();
    }

    private static Logger logger = LoggerFactory.getLogger(ContextDemo.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        vertx.getOrCreateContext()
                .runOnContext(v -> logger.info("ABC"));

        vertx.getOrCreateContext()
                .runOnContext(v -> logger.info("123"));

        Context context = vertx.getOrCreateContext();
        context.put("foo", "bar");

        context.exceptionHandler(e -> {
            if (e instanceof IllegalStateException) {
                logger.info("Got an illegal state exp", e);
            } else {
                logger.error("Got unexpected exception", e);
            }
        });

        context.runOnContext(v -> {
            throw new IllegalStateException("No state is the best state");
        });

        context.runOnContext(v -> {
            logger.info("foo = {}", (String) context.get("foo"));
        });

        vertx.deployVerticle(new ContextDemo());
    }
}
