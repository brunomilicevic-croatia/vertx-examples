package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class BlockVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.setTimer(1000, id -> {
            while(true);
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new BlockVerticle());
    }
}
