package ru.mmlubyankina.march.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private int port;
    private String nickname;

    public Server(int port) {
        this.port = port;
        this.users = new HashMap<>();
    }

    private Map<String, ClientHandler> users = new HashMap<>();


    public void start(){
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Сервер запущен на порту: %d, ожидаем подключения клиентов\n", port);
            while (true){
                Socket socket = serverSocket.accept();
                subscribe(new ClientHandler(this, socket));
                /** subscribe - осуществляет взаимодействие с клиентом в отдельном потоке */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public synchronized void subscribe(ClientHandler clientHandler){
        users.put(clientHandler.getNickname(), clientHandler);
    }

    public synchronized void unsubscribe(ClientHandler clientHandler){
        users.remove(clientHandler);
    }

    public synchronized void broadcastMessage(String message){
        for (ClientHandler users : users.values()) {
            users.sendMessage(message);
        }
    }


    public synchronized void personalMessage(ClientHandler clientHandler, String recepient, String message){
        users.get(recepient).sendMessage(message);
    }

    public boolean isUsedNick(String nickname){
        for (String u : users.keySet()){
            if (users.containsKey(nickname)){
                return true;
            }
        } return false;
    }


}
