package edu.upc.dsa.services;


import edu.upc.dsa.ProductsManagerImp;
import edu.upc.dsa.ProductsManager;
import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
import edu.upc.dsa.models.Products;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/users", description = "Endpoint to User Service")
@Path("/users")
public class UserService {
    private UserManager um;
    private ProductsManager om;

    public UserService() {
        this.um = UserManagerImpl.getInstance();
        this.om = ProductsManagerImp.getInstance();

        if (um.size() == 0) {
            this.um.Register("Dennis", "1234");
            this.um.Register("Bob", "1");
            this.um.Register("Manolo", "miau");
        }

    }

    @POST
    @ApiOperation(value = "login", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        User u = new User(username, password);
        if (this.um.IniciarSesion(username, password) == 0)
            return Response.status(201).entity(u).build();
        else
            return Response.status(404).build();
    }

    //register
    @POST
    @ApiOperation(value = "register", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not valid")
    })
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@FormParam("username") String username, @FormParam("password") String password) {
        if (username == null || password == null) return Response.status(404).build();
        else {
            this.um.Register(username, password);
            User u = new User(username, password);
            return Response.status(201).entity(u).build();
        }
    }

    //Get all products of a user
    @GET
    @ApiOperation(value = "get all products of a user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/{username}/products")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProductsOfUser(@PathParam("username") String username) {
        User u = this.um.getUser(username);
        if (u == null)
        {
            return Response.status(404).build();
        }
        else {
            List<Products> products = this.um.getProductsOfUser(u);
            GenericEntity<List<Products>> entity = new GenericEntity<List<Products>>(products) {
            };
            return Response.status(201).entity(entity).build();
        }
    }

    //add product to user
    @POST
    @ApiOperation(value = "add product to user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Products.class),
            @ApiResponse(code = 404, message = "User or Product not found")
    })
    @Path("/{username}/products/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProductToUser(@PathParam("username") String username, @PathParam("id") int id) {
        User u = this.um.getUser(username);
        Products p = this.om.findProduct(id);

        if (u == null || p == null) {
            return Response.status(404).build();
        } else {
            this.um.addProductToUser(u, p);
            return Response.status(201).entity(p).build();
        }
    }

    @GET
    @ApiOperation(value = "Get dinero del user", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful" )
    })
    @Path("/{username}/dinero")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getDineroUser(@PathParam("username") String username) {
        User u = this.um.getUser(username);
        int dinero = u.getDinero();
        return Response.status(201).entity(dinero).build();
    }
    @POST
    @Path("/login2")
    @Consumes(MediaType.APPLICATION_JSON)  // Cambiado a JSON
    @Produces(MediaType.APPLICATION_JSON)
    public Response login2(User user) {  // Ahora recibe un objeto User
        if (this.um.IniciarSesion(user.getUsername(), user.getPassword()) == 0) {
            return Response.status(201).entity(user).build();
        } else {
            return Response.status(404).entity("User not found or incorrect password").build();
        }
    }

    @POST
    @Path("/register2")
    @Consumes(MediaType.APPLICATION_JSON)  // Cambiado a JSON
    @Produces(MediaType.APPLICATION_JSON)
    public Response register2(User user) {  // Ahora recibe un objeto User
        if (user.getUsername() == null || user.getPassword() == null) {
            return Response.status(400).entity("Invalid username or password").build();
        } else {
            this.um.Register(user.getUsername(), user.getPassword());
            return Response.status(201).entity(user).build();
        }
    }

}
