package com.bruno.vertx.echo;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class SyncDownload {

    public static String fileSource = "https://s3-eu-central-1.amazonaws.com/ucu.edu.ua/wp-content/uploads/sites/8/2019/12/Petro-Karabyn.pdf";

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(3000));
        while (true) {
            Socket socket = server.accept();
            new Thread(requestHandler(socket)).start();;
        }
    }

    private static Runnable requestHandler(Socket socket) {
        return () -> {
            var start = System.currentTimeMillis();
            try(
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(fileSource).openStream()));
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
            ) {
                String line = "";
                line = reader.readLine();
                while (line != null) {
                    writer.write(line);
                    writer.flush();
                    line = reader.readLine();
                }
                System.out.printf("Thread %s finished download in: %d \n", Thread.currentThread().getName(), getElapsed(start));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    private static long getElapsed(long start) {
        return System.currentTimeMillis() - start;
    }
}
