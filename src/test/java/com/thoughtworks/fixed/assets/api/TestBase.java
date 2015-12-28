package com.thoughtworks.fixed.assets.api;


import com.thoughtworks.learning.core.UsersRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import redis.clients.jedis.Jedis;

import javax.ws.rs.core.Application;

import static org.mockito.Mockito.mock;

public class TestBase extends JerseyTest {
    protected UsersRepository usersRepository = mock(UsersRepository.class);

    @Override
    protected Application configure() {
        
//        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
//        enable(TestProperties.RECORD_LOG_LEVEL);

        return new ResourceConfig().register(new AbstractBinder() {


            @Override
            protected void configure() {

                bind(usersRepository).to(UsersRepository.class);


            }
        }).packages("com.thoughtworks.learning.api");
    }
}
