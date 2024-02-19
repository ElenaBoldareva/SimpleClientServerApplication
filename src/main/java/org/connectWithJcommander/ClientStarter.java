package org.connectWithJcommander;

import com.beust.jcommander.JCommander;

import java.io.IOException;

public class ClientStarter {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        JCommander.newBuilder()
                .addObject(client)
                .build()
                .parse(args);
        client.run();
    }
}
