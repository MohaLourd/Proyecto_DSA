package edu.upc.dsa;
import edu.upc.dsa.models.User;

import java.util.List;

public interface UserManager {

    public User Register(String username, String password);
    public User Register(User u);

    public List<User> findAll();
    public int logIn(String username, String password);
    public User updateUser(User u);
    public User deleteUser(String username, String password);




    public int size();


}
