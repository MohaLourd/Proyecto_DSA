package edu.upc.dsa;

import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.User;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
public class UserManagerImpl implements UserManager{
    private static UserManager instance;
    protected List<User> users;
    final static Logger logger = Logger.getLogger(UserManagerImpl.class);

    private UserManagerImpl() {
        this.users = new LinkedList<>();
    }

    public static UserManager getInstance() {
        if (instance==null) instance = new UserManagerImpl();
        return instance;
    }

    @Override
    public User Register(User u) {
        logger.info("new User added: " + u.getUsername());
        this.users.add(u);
        return u;
    }

    @Override
    public User Register(String username, String password) {
        logger.info("new User added: " + username);
        return this.Register(new User(username, password));
    }

    @Override
    public List<User> findAll() {
        return this.users;
    }

    @Override
    public User updateUser(User u) {
        for (int i=0; i<this.users.size(); i++) {
            if (this.users.get(i).getUsername().equals(u.getUsername())) {
                this.users.set(i, u);
                logger.info("updateUser("+u+"): "+this.users.get(i));
                return u;
            }
        }
        logger.warn("not found "+u.getUsername());
        return null;
    }

    @Override
    public User deleteUser(String username, String password) {
        logger.info("Trying deleteUser("+username+")");
        for (int i=0; i<this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                logger.info("deleteUser("+username+"): "+u);
                this.users.remove(i);
                return u;
            }
        }
        logger.warn("not found "+username);
        return null;
    }

    @Override
    public int IniciarSesion(String username, String password) {
        logger.info("Trying logIn("+username+")");
        for (int i=0; i<this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                logger.info("logIn("+username+") succesful: ");
                return 0; //success
            }
        }
        logger.warn("not found "+username);
        return -1; //user not found
    }

    @Override
    public int size() {
        int ret = this.users.size();
        logger.info("size " + ret);
        return ret;
    }

    @Override
    public List<Products> getProductsOfUser(User u) {
        return u.getProductos();
    }

    @Override
    public void addProductToUser(User u, Products p) {
        u.addProducto(p);
        logger.info("Producto añadido al usuario " + u.getUsername());
    }

    @Override
    public User getUser(String username) {
        for (int i=0; i<this.users.size(); i++) {
            User u = this.users.get(i);
            if (u.getUsername().equals(username)) {
                logger.info("getUser("+username+"): "+u);
                return u;
            }
        }
        logger.warn("not found "+username);
        return null;
    }



}
