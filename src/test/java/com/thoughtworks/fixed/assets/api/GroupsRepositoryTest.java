package com.thoughtworks.fixed.assets.api;

import com.thoughtworks.learning.core.Group;
import com.thoughtworks.learning.core.GroupsRepository;
import com.thoughtworks.learning.core.User;
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

public class GroupsRepositoryTest {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private GroupsRepository groupsRepository;

    @Before
    public void setUp() throws IOException, SQLException {
        String resource = "mybatis-config.xml";
        
        Reader reader  = Resources.getResourceAsReader(resource);

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        session = sqlSessionFactory.openSession();
        session.getConnection().setAutoCommit(false);
        groupsRepository = session.getMapper(GroupsRepository.class);

    }
    
    @After
    public void tearDown(){
        session.rollback();
        session.close();
        
    }
        

    @Test
    public void should_find_all_users() {
        List<Group> groups = groupsRepository.findGroups();
        assertThat(groups.size(), is(2));
        assertThat(groups.get(0).getName(), is("小组1"));
        assertThat(groups.get(1).getName(), is("小组2"));
        
    }

    @Test
    public void should_create_a_user(){
        Map newInstance = new HashMap();
        newInstance.put("name", "小组3");
        groupsRepository.createGroup(newInstance);

        assertThat((String) newInstance.get("name"), is("小组3"));
        assertThat((Integer) newInstance.get("id"), is(not(0)));
    }
    
    @Test
    public void should_get_group_by_id() {
        Group theGroup = groupsRepository.getGroupById(1);
        assertThat(theGroup.getId(), is(1));
        assertThat(theGroup.getName(), is("小组1"));

        List<User> members = theGroup.getMembers();
        assertThat(members.size(), is(2));
        assertThat(members.get(0).getId(), is(1));
        assertThat(members.get(0).getName(), is("Jerry"));
//        assertThat(members.get(0).getUser().getId(), is(1));
    }
    

    
    
}
