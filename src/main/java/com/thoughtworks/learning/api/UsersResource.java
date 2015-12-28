package com.thoughtworks.learning.api;

import com.thoughtworks.learning.core.User;
import com.thoughtworks.learning.core.UsersRepository;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/users")
public class UsersResource {

    @Inject
    private UsersRepository userRepository;


    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(){
        List<Map> result = new ArrayList<>();
        List<User> list = userRepository.findUsers();
        for(User user : list){
            Map userBean = new HashMap();
            userBean.put("uri","/users/"+user.getId());
            userBean.put("name",user.getName());
            result.add(userBean);
        }
        
        return Response.status(200).entity(result).build();
    }
    
    @Path("/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@FormParam("name") String name){
        Map result = new HashMap();
        Map newInstanceBean = new HashMap();
        newInstanceBean.put("name", name);
        
         userRepository.createUser(newInstanceBean);
        
        result.put("uri", "/users/"+newInstanceBean.get("id"));
        result.put("name",newInstanceBean.get("name"));
        
        
        
        return Response.status(201).entity(result).build();
    }
    
    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("userId") int userId, @Context SecurityContext context){

        Map result = new HashMap();
        User instance = userRepository.getUserById(userId);
        result.put("uri", "/users/"+userId);
        result.put("name", instance.getName());
        return Response.status(200).entity(result).build();
    }
}
