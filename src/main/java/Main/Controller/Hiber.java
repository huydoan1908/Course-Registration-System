package Main.Controller;

import Main.DAO.UserDAO;
import Main.POJO.User;

import java.util.List;

public class Hiber {
    public static void main(String[] args) {
        List<User> user = UserDAO.getAllUser();
        for(User item : user)
            System.out.println(item.getId());
    }
}
