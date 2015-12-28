package com.thoughtworks.learning;

import com.thoughtworks.learning.core.UsersRepository;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080/admin/");
    public static final String ROOT_PATH = "users";

    public static void main(String[] args) {
        try {
            System.out.println("\"Hello World\" Jersey Example App");

//            Map<String, String> initParams = new HashMap<>();
//            initParams.put(
//                    ServerProperties.PROVIDER_PACKAGES,
//                    UsersResource.class.getPackage().getName());
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createSessionInViewConfig(), false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            server.start();

            System.out.println(String.format("Application started.%nTry out %s%s%nStop the application using CTRL+C",
                    BASE_URI, ROOT_PATH));

            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    public static ResourceConfig createSessionInViewConfig() throws IOException {
        final ResourceConfig resourceConfig = new ResourceConfig();
        String resource = "mybatis-config.xml";

        final Reader reader  = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");
        final SqlSessionManager sqlSessionManager = SqlSessionManager.newInstance(sqlSessionFactory);

        final UsersRepository usersRepository = sqlSessionManager.getMapper(UsersRepository.class);


        final ResourceConfig config = new ResourceConfig()
                .packages("com.thoughtworks.learning")
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(usersRepository).to(UsersRepository.class);
                        bind(sqlSessionManager).to(SqlSessionManager.class);
                    }
                });

        return config;
    }


    public static ResourceConfig create() throws IOException {
        final ResourceConfig resourceConfig = new ResourceConfig();
        String resource = "mybatis-config.xml";

        final Reader reader  = Resources.getResourceAsReader(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "test");

        SqlSession session = sqlSessionFactory.openSession();
        final UsersRepository usersRepository = session.getMapper(UsersRepository.class);

        resourceConfig.register(new AbstractBinder() {


            @Override
            protected void configure() {
                bind(usersRepository).to(UsersRepository.class);
            }
        }).packages("com.thoughtworks.learning.api");
//        final Resource.Builder resourceBuilder = Resource.builder(ROOT_PATH_PROGRAMMATIC);
//        resourceBuilder.addMethod("GET").handledBy(JaxrsInjectionReportingInflector.class);

        return resourceConfig;
    }

}

