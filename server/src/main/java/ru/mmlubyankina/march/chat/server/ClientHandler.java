package ru.mmlubyankina.march.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;
    public String getNickname() {
        return nickname;
    }


    public ClientHandler(Server server, Socket socket) throws IOException {
    this.server = server;
    this.socket = socket;
    this.in = new DataInputStream(socket.getInputStream());
    this.out = new DataOutputStream(socket.getOutputStream());
    this.nickname = nickname;

        sendMessage("Введите ваш nickname: ");
        this.nickname = in.readUTF();

        new Thread(() -> {
            try {
                System.out.println("Подключился новый клиент.");
                sendMessage("Ваш nickname: " + nickname);
                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.startsWith("/exit")) {
                            disconnect();
                            break;
                        }
                        if (message.startsWith("/w ")){
                            sendPersonalMessage(message);
                        }
                        continue;
                    }
                    server.broadcastMessage(nickname + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }


    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPersonalMessage(String msg) {
        try {
            String[] words = msg.split(" ", 3);
            String recipient = words[1];
            String messageToSend = words[2];
            server.personalMessage(this, recipient, messageToSend);
        } catch (Exception e){
            System.out.println("Ошибка при отправке личного сообщения.");
        }
    }


    public void disconnect(){

        server.unsubscribe(this);

        try {
            if(in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if(socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}