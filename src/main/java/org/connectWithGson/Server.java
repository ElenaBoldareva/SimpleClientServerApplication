package org.connectWithGson;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    static Map<String, String> dataBase = new HashMap<>();
    static String responseMessage;
    static String reason;
    static String connectServer = "Server started!";
    static String address = "127.0.0.1";
    static int port = 23456;

    public static void main(String[] args) throws IOException {
        run();
    }

    public static void run() throws IOException {
        Gson gson = new Gson();
        Response response = null;
        Request request = null;
        System.out.println(connectServer);
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
        String type = null;
        while (!"exit".equals(type)) {
            Socket socket = server.accept();
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            type = request.getType();
            switch (type) {
                case "set":
                    dataBase.put(request.getKey(), request.getValue());
                    responseMessage = "OK";
                    response = new Response(responseMessage);
                    break;
                case "get":
                    if (dataBase.get(request.getKey()) == null) {
                        responseMessage = "ERROR";
                        reason = "No such key";
                        response = new Response(responseMessage, reason);
                    } else {
                        String value = dataBase.get(request.getKey());
                        responseMessage = "OK";
                        response = new Response(responseMessage, value);
                    }
                    break;
                case "delete":
                    if (dataBase.get(request.getKey()) != null) {
                        dataBase.remove(request.getKey());
                        responseMessage = "OK";
                        response = new Response(responseMessage);
                    } else responseMessage = "ERROR";
                    reason = "No such key";
                    response = new Response(responseMessage, reason);
                    break;
                case "exit":
                    responseMessage = "OK";
                    response = new Response(responseMessage);
                    break;
                default:
                    responseMessage = "Unknown command";
                    break;
            }
            String messageToJson = gson.toJson(response);
            output.writeUTF(messageToJson);
            socket.close();
        }
    }
}
