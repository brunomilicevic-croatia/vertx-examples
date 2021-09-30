package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloVerticle extends AbstractVerticle {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private long counter;

    @Override
    public void start() throws Exception {
        vertx.setPeriodic(5000, id -> {
            logger.info("tick");
        });

        vertx.createHttpServer()
                .requestHandler(req -> {
                    logger.info("Request #{} from {}", counter++, req.remoteAddress().host());
                    req.response().end("Hello!");
                })
                .listen(8080);
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HelloVerticle(), ar -> {
            System.out.println("Deployed verticle");
        });
    }
}
