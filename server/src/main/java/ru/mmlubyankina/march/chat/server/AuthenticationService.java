package ru.mmlubyankina.march.chat.server;

public interface AuthenticationService {
    String getNicknameByLoginAndPassword(String login, String password);
    boolean register(String login, String password, String nickname);
    boolean isLoginAlreadyExist(String login);
    boolean isNicknameAlreadyExist(String nickname);
    public void setRoles();
    Roles getUserRole(ClientHandler clientHandler, String nickname);
    boolean isAdmin();

}
