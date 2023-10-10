package lab3new.beans;

import java.io.Serializable;
import java.util.Date;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
// import jakarta.persistence.EntityManager;
// import jakarta.persistence.EntityManagerFactory;
// import jakarta.persistence.Persistence;
// import lab3new.model.RequestBean;


@Named("requestsController")
@RequestScoped
public class RequestsController implements Serializable {

    public RequestsController() {
    }

    // private RequestBean requestBean = new RequestBean();


    public void processRequest() {

        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("yourPU");
        // EntityManager em = emf.createEntityManager();

        System.out.println(123);

        // em.getTransaction().begin();

        // requestBean.setX(Double.valueOf(3));
        // requestBean.setY(Double.valueOf(3));
        // requestBean.setR(Double.valueOf(3));
        // requestBean.setResult(false);
        // requestBean.setTime(new Date());

        // // Сохраняем сущность
        // em.persist(requestBean);

        // // Завершаем транзакцию
        // em.getTransaction().commit();

        // // Закрываем EntityManager и EntityManagerFactory
        // em.close();

    }

    // public void setRequestBean(RequestBean requestBean) {
    //     this.requestBean = requestBean;
    // }

    // public RequestBean getRequestBean() {
    //     return this.requestBean;
    // }

}