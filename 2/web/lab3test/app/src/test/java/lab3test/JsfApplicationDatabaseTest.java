package lab3test;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.LinkedList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import lab3test.dao.HibernateUtil;
import lab3test.model.RequestBean;

@Testcontainers
public class JsfApplicationDatabaseTest {

    @Container()
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
        .withDatabaseName("testdb")
        .withUsername("testuser")
        .withPassword("testpass")
        .withInitScript("init.sql");

        @Test
        public void testDataBase() {
            SessionFactory sessionFactory = HibernateUtil.createSessionFactory(postgreSQLContainer);
            Session session = sessionFactory.openSession();
    
            LinkedList<RequestBean> requestsTo = new LinkedList<>();
            

            for (int i = 0; i < 100; i++) {

                RequestBean requestTo = new RequestBean();
                requestTo.setX(Double.valueOf(i));
                requestTo.setY(Double.valueOf(i));
                requestTo.setR(Double.valueOf(i));
                requestTo.setResult(true);
                requestTo.setTime(new Timestamp(System.currentTimeMillis()));
                session.save(requestTo);
                requestsTo.add(requestTo);

            }


            for (int i = 1; i < 101; i++) {

                RequestBean requestFrom = session.get(RequestBean.class, (long)i);
                RequestBean requestTo = requestsTo.get(i-1);
                
                assertEquals(requestFrom.getX(), requestTo.getX());
                assertEquals(requestFrom.getY(), requestTo.getY());
                assertEquals(requestFrom.getResult(), requestTo.getResult());
                assertEquals(requestFrom.getTime(), requestTo.getTime());

            }

            for (int i = 0; i < 100; i++) {
                session.remove(requestsTo.get(i));
            }

        }
    
}
