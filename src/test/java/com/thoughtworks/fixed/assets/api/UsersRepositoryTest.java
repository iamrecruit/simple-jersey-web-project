package com.thoughtworks.fixed.assets.api;

import com.thoughtworks.learning.core.Contact;
import com.thoughtworks.learning.core.User;
import com.thoughtworks.learning.core.UsersRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class UsersRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private UsersRepository usersRepository;
    private SqlSession session;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        usersRepository = session.getMapper(UsersRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }
        

    @Test
    public void should_find_all_users() {
        List<User> users1 = usersRepository.findUsers();
        assertThat(users1.size(), is(2));
        assertThat(users1.get(0).getName(), is("Jerry"));
        assertThat(users1.get(1).getName(), is("Tom"));
        
    }

    @Test
    public void should_create_a_user(){
        Map newInstance = new HashMap();
        newInstance.put("name", "James");
        usersRepository.createUser(newInstance);

        assertThat((String) newInstance.get("name"), is("James"));
        assertThat((Integer) newInstance.get("id"), is(not(0)));
    }
    
    @Test
    public void should_get_user_by_id() {
        User theUser = usersRepository.getUserById(1);
        assertThat(theUser.getId(), is(1));
        assertThat(theUser.getName(), is("Jerry"));

        List<Contact> contacts = theUser.getContacts();
        assertThat(contacts.get(0).getId(), is(1));
        assertThat(contacts.get(0).getType(), is("PHONE"));
        assertThat(contacts.get(0).getValue(), is("13512345678"));
//        assertThat(contacts.get(0).getUser().getId(), is(1));
    }
    
    
}
