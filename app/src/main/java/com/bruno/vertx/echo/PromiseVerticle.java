package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromiseVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(PromiseVerticle.class.getName());

    @Override
    public void start(Promise<Void> promise) throws Exception {
        vertx.createHttpServer()
                .requestHandler(req -> {
                    logger.info("Got a request");
                    req.response().end("Ok");
                })
                .listen(8080, ar -> {
                    if (ar.succeeded()) {
                        promise.complete();
                    } else {
                        logger.error("Failed to listen to port because: {}", ar.cause());
                        promise.fail(ar.cause());
                    }
                });
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new PromiseVerticle(), ar -> {
            logger.info("Deployed vertex");
        });
    }
}
