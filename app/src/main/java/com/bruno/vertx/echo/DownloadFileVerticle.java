package com.bruno.vertx.echo;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;

import java.net.URI;

public class DownloadFileVerticle {

    public static URI fileSource = URI.create("https://www.iskon.hr/download/2834/40493");

    public static void main(String[] args) {
        Vertx.vertx().createHttpServer()
                .requestHandler(DownloadFileVerticle::handleRequest)
                .listen(3000);
    }

    private static void handleRequest(HttpServerRequest request) {
        request.response().write(getBufferedData(fileSource));
    }

    private static Buffer getBufferedData(URI fileSource) {
        return null;
    }
}
