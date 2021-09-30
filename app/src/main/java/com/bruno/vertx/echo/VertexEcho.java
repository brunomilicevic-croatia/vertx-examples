package com.bruno.vertx.echo;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

public class VertexEcho {

    private static int numberOfConnections;

    public static void setup() {
        Vertx vertx = Vertx.vertx();
        vertx.createNetServer()
                .connectHandler(VertexEcho::handleNewClient)
                .listen(3000);

        vertx.setPeriodic(5000, id -> System.out.println(howMany()));

        vertx.createHttpServer()
                .requestHandler(request -> request.response().end(howMany()))
                .listen(8080);
    }

    private static String howMany() {
        return Thread.currentThread().getName() + ": We now have " + numberOfConnections + " connections.";
    }

    private static void handleNewClient(NetSocket netSocket) {
        numberOfConnections++;
        netSocket.handler(buffer -> {
            buffer.appendString("[" + Thread.currentThread().getName() + "]");
            netSocket.write(buffer);
            if (buffer.toString().endsWith("/quit\n")) {
                netSocket.close();
            }
        });
        netSocket.closeHandler(v -> numberOfConnections--);
    }
}
