package org.connectWithJcommander;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static String typeOfTheRequest;
    static String index;
    static String valueToSaveInTheDatabase;
    static String[] dataBase = new String[1000];
    static String connectServer = "Server started!";
    static final String OK = "OK";
    static final String ERROR = "ERROR";
    static String address = "127.0.0.1";
    static int port = 23456;

    public static void main(String[] args) throws IOException {
        run();
    }

    public static void run() throws IOException {
        System.out.println(connectServer);
        ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));

        while (!"exit".equals(typeOfTheRequest)) {
            Socket socket = server.accept();
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());

            String resultInput = input.readUTF();
            System.out.println(resultInput);
            String[] parseResultInput = resultInput.split(" ");
            if (parseResultInput.length == 1) {
                typeOfTheRequest = parseResultInput[0];
            }
            if (parseResultInput.length == 2) {
                typeOfTheRequest = parseResultInput[0];
                index = parseResultInput[1];
            }
            if (parseResultInput.length == 3) {
                typeOfTheRequest = parseResultInput[0];
                index = parseResultInput[1];
                valueToSaveInTheDatabase = parseResultInput[2];
            }
            String message;
            switch (typeOfTheRequest) {
                case "set":
                    dataBase[Integer.parseInt(index)] = valueToSaveInTheDatabase;
                    message = OK;
                    break;
                case "get":
                    if (dataBase[Integer.parseInt(index)] == null) {
                        message = ERROR;
                    } else {
                        message = dataBase[Integer.parseInt(index)];
                    }
                    break;
                case "delete":
                    if (dataBase[Integer.parseInt(index)] == null) {
                        message = ERROR;
                    } else {
                        dataBase[Integer.parseInt(index)] = null;
                        message = OK;
                    }
                    break;
                case "exit":
                    message = OK;
                    break;

                default:
                    message = "Unknown command";
                    break;
            }

            output.writeUTF(message);
        }
    }
}