package com.bruno.vertx.echo;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SyncEcho {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void setup() throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(3000));
        while (true) {
            Socket socket = server.accept();
            new Thread(requestHandler(socket)).start();;
        }
    }

    private static Runnable requestHandler(Socket socket) {
        return () -> {
            try(
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
            ) {
                String line = "";
                while (!"quit".equals(line)) {
                    line = reader.readLine();
                    if (line == null) continue;
                    System.out.printf("Thread %s says: %s \n", Thread.currentThread().getName(),line);
                    writer.write(line + "\n");
                    writer.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
