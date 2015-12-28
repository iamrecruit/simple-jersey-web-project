package com.thoughtworks.fixed.assets.api;

import com.thoughtworks.learning.core.User;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static sun.security.util.HostnameChecker.match;

public class UsersResourceTest extends TestBase {
    private String basePath = "/users";
    private User firstUser = mock(User.class);
    private User secondUser = mock(User.class);
    private User newUser = mock(User.class);


    @Override
    public void setUp() throws Exception {
        when(usersRepository.findUsers()).thenReturn(Arrays.asList(firstUser, secondUser));
        when(firstUser.getId()).thenReturn(1);
        when(firstUser.getName()).thenReturn("Name");

        when(newUser.getId()).thenReturn(3);
        when(newUser.getName()).thenReturn("James");
        when(usersRepository.newUser(anyString())).thenReturn(mock(User.class));

        when(usersRepository.getUserById(1)).thenReturn(firstUser);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                ((Map)arguments[0]).put("id", 3);
                return null;
            }
        }).when(usersRepository).createUser(anyMap());
        super.setUp();
    }



    @Test
    public void should_list_all_users(){
        Response response = target(basePath).request().get();

        assertThat(response.getStatus(), is(200));
        
        List<Map> result = response.readEntity(List.class);

        assertThat(result.size(), is(2));

        Map user = result.get(0);

        assertThat((String) user.get("uri"), is(basePath+"/"+firstUser.getId()));
        assertThat((String) user.get("name"), is(firstUser.getName()));
    }
    
    @Test
    public void should_create_one_user(){
        Form formData = new Form();

        formData.param("name", "James");
        
        Response response = target(basePath).request().post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        assertThat(response.getStatus(), is(201));
        
        Map user = response.readEntity(Map.class);
        
        assertThat((String) user.get("uri"), is(basePath+"/3"));
        assertThat((String) user.get("name"), is("James"));

    }
    
    @Test
    public void should_get_user_by_Id(){
        Response response = target(basePath+"/1").request().get();

        assertThat(response.getStatus(), is(200));
        
        Map user = response.readEntity(Map.class);
        
        assertThat((String) user.get("uri"), is(basePath+"/1"));
        assertThat((String) user.get("name"), is("Name"));
        

    }
    
    

    
}
