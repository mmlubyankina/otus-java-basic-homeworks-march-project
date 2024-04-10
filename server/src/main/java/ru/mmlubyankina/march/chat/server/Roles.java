package ru.mmlubyankina.march.chat.server;

public enum Roles {

    USER(0),
    ADMIN(1);

    private int superUser;


    public boolean isAdminRole() {
        if (this.superUser == 1){
            return true;
        }
        return false;
    }


    Roles(int superUser) {
        this.superUser = superUser;
    }




}
