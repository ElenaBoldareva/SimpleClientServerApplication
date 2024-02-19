package org.connectWithJcommander;

import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    static @Parameter(names = {"--typeOfTheRequest", "-t"})
    String typeOfTheRequest;
    static @Parameter(names = {"--index", "-i"})
    String index;
    static @Parameter(names = {"--valueToSaveInTheDatabase", "-m"})
    String valueToSaveInTheDatabase;

    static String connectClient = "Client started!";
    static String address = "127.0.0.1";
    static int port = 23456;


    public void run() throws IOException {

        System.out.println(connectClient);
        Socket socket = new Socket(InetAddress.getByName(address), port);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        String message = buildMessage();
        output.writeUTF(message);
        System.out.println("Sent: " + message);
        String resultInput = input.readUTF();
        System.out.println("Received: " + resultInput);
        socket.close();

    }

    private String buildMessage() {
        String message = typeOfTheRequest;
        if (index != null) {
            message = message + " " + index;
        }
        if(valueToSaveInTheDatabase!=null){
            message = message + " " + valueToSaveInTheDatabase;
        }
        return message;
    }


}
