package Controllers;

import Data.UserDAO;
import Models.User;

public class UserController {
private static UserDAO user;
    public UserController() {
        user = new UserDAO();
    }

    public User login(String name, String pass) {
        return user.login(name,pass);
    }

    public <T> User getUser(String columna,T value) {
        return user.getUser(columna, value);
    }

    public Object[][] getUserList(){
        return user.getUserList();
    }

    public boolean createUser(String name, String pass){
        return user.createUser(name, pass);
    }

    public boolean editUser(int id,String name, int permisos){
        return user.editUser(id, name, permisos);
    }

    public boolean deleteUser(int id){
        return user.deleteUser(id);
    }

}
