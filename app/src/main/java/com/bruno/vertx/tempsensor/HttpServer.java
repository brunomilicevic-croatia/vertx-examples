package com.bruno.vertx.tempsensor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;

public class HttpServer extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(this::handler)
                .listen(8080);
    }

    private void handler(HttpServerRequest httpServerRequest) {
        if ("/".equals(httpServerRequest.path())) {
            httpServerRequest.response().sendFile("temperatureSensor.html");
        } else if ("/sse".equals(httpServerRequest.path())) {
            sse(httpServerRequest);
        } else {
            httpServerRequest.response().setStatusCode(404);
        }
    }

    private void sse(HttpServerRequest httpServerRequest) {
        var response = httpServerRequest.response();
        response.putHeader("Content-Type", "text/event-stream")
                .putHeader("Cache-Control", "no-cache")
                .setChunked(true);

        var consumer = vertx.eventBus().<JsonObject>consumer("sensor.updates");
        consumer.handler(msg -> {
            response.write("event: update\n");
            response.write("data: " + msg.body().encode() + "\n\n");
        });

        var ticks = vertx.periodicStream(1000);
        ticks.handler(id -> {
            vertx.eventBus().<JsonObject>request("sensor.average", "",
                    reply -> {
                        if (reply.succeeded()) {
                            response.write("event: average\n");
                            response.write("data: " + reply.result().body().encode() + "\n\n");
                        }
                    });
        });
        response.endHandler(v -> {
            consumer.unregister();
            ticks.cancel();
        });
    }
}
