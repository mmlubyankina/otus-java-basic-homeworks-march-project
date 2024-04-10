package ru.mmlubyankina.march.chat.server;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuthenticationService implements AuthenticationService {
    private class User {
        private String login;
        private String password;
        private String nickname;

        private Roles roleName;
        public Roles getRoleName(String nickname) {
            return roleName;
        }
        public void setRoleName(Roles roleName) {
            this.roleName = roleName;
        }

        public User(String login, String password, String nickname) {
            this.login = login;
            this.password = password;
            this.nickname = nickname;
            this.roleName = null;
        }
    }

    private List<User> users;


    private Roles roleName;
    public Roles getRoleName() {
        return roleName;
    }



    public InMemoryAuthenticationService() {
        this.users = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            this.users.add(new User("login" + i, "pass" + i, "nick" + i));
//        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        for (User u : users) {
            if (u.login.equals(login) && u.password.equals(password)) {
                return u.nickname;
            }
        }
        return null;
    }

    @Override
    public boolean register(String login, String password, String nickname) {
        if (isLoginAlreadyExist(login)) {
            return false;
        }
        if (isNicknameAlreadyExist(nickname)) {
            return false;
        }
        users.add(new User(login, password, nickname));
        return true;
    }

    @Override
    public boolean isLoginAlreadyExist(String login) {
        for (User u : users) {
            if (u.login.equals(login)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNicknameAlreadyExist(String nickname) {
        for (User u : users) {
            if (u.nickname.equals(nickname)) {
                return true;
            }
        }
        return false;
    }


    public void setRoles(){
        for (User u : users) {
            if (u.login.equals("admin") && u.password.equals("admin") && u.nickname.equals("admin")){
                u.setRoleName(Roles.ADMIN);
            } else u.setRoleName(Roles.USER);
        }
    }


    @Override
    public Roles getUserRole(ClientHandler clientHandler, String nickname) {
        for (User u : users) {
            if (u.nickname.equals(nickname))
                return u.roleName;
        }
        return null;
    }

    @Override
    public boolean isAdmin() {
        if (this.getRoleName().isAdminRole()){
            return true;
        }
        return false;
    }




}
