package com.bruno.vertx.echo;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyVerticle extends AbstractVerticle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void start() throws Exception {
        logger.info("Started empty verticle.");
    }

    @Override
    public void stop() throws Exception {
        logger.info("Stopped empty verticle.");
    }
}
