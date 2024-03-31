package ru.mmlubyankina.march.chat.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (
                Socket socket = new Socket("localhost", 8189);
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        ){
            System.out.println("Подключились к серверу.");

            new Thread(() -> {
                try {
                    while (true) {
                        String inMessage = in.readUTF();
                        System.out.println(inMessage);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();

            while (true){
                String message = scanner.nextLine();
                out.writeUTF(message);
                if(message.equals("/exit")){
                    break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
