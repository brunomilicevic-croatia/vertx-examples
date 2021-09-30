package com.bruno.vertx.echo;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ExecuteBlockingVerticle extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(ExecuteBlockingVerticle.class);

    @Override
    public void start() throws Exception {
        Random random = new Random();
        vertx.setTimer(random.nextInt(500), id -> {
            logger.info("Tick");
            vertx.executeBlocking(this::blockingCode, this::resultHandler);
        });
    }

    private void resultHandler(AsyncResult<String> tAsyncResult) {
        if (tAsyncResult.succeeded()) {
            logger.info("Result: {}", tAsyncResult.result());
        } else {
            logger.error("Blocking code failed", tAsyncResult.cause());
        }

    }

    private void blockingCode(Promise<String> promise) {
        logger.info("Running blocking code");
        try {
            Thread.sleep(4000);
            logger.info("Done");
            promise.complete("Ok");
        } catch (InterruptedException e) {
            promise.fail(e);
        }
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(ExecuteBlockingVerticle.class.getName());
    }
}
