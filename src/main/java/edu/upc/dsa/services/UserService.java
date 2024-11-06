package edu.upc.dsa.services;


import edu.upc.dsa.UserManager;
import edu.upc.dsa.UserManagerImpl;
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

    public UserService() {
        this.um = UserManagerImpl.getInstance();
        if (um.size() == 0) {
            this.um.Register("Dennis", "1234");
            this.um.Register("Bob", "1");
            this.um.Register("Manolo", "miau");
        }

    }

    //login
    @POST
    @ApiOperation(value = "login", notes = "asdasd")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = User.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User u) {
        this.um.logIn(u.getUsername(), u.getPassword());
        if(this.um.logIn(u.getUsername(), u.getPassword()) == 0)
            return Response.status(201).entity(u).build();
        else return Response.status(404).build();


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
    public Response register(User u) {
        if (u.getUsername() == null || u.getPassword() == null) return Response.status(404).build();
        else {
            this.um.Register(u);
            return Response.status(201).entity(u).build();
        }
    }



}
