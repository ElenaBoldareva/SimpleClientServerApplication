package org.connectWithGson;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    static @Parameter(names = {"--typeOfTheRequest", "-t"})
    String type;
    static @Parameter(names = {"--key", "-k"})
    String key;
    static @Parameter(names = {"--value", "-v"})
    String value;
    static String connectClient = "Client started!";
    static String address = "127.0.0.1";
    static int port = 23456;

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        JCommander.newBuilder()
                .addObject(client)
                .build()
                .parse(args);
        client.run();
    }

    public void run() throws IOException {
        System.out.println(connectClient);
        Socket socket = new Socket(InetAddress.getByName(address), port);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        Gson gson = new Gson();
        Request request = new Request(type, key, value);
        String messageRequest = gson.toJson(request);
        output.writeUTF(messageRequest);
        String resultInput = input.readUTF();
        System.out.println("Sent: " + messageRequest);
        System.out.println("Received: " + resultInput);
        socket.close();
    }
}

