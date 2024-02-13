package lab3test.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lab3test.model.RequestBean;


@Named
@RequestScoped
public class RequestBeanDao implements Serializable {

    public RequestBeanDao() {
    }
    
    public Boolean createRequest(RequestBean requestBean) {

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(requestBean);
            session.getTransaction().commit();
            return true;

        } catch (Exception exp) {
            exp.printStackTrace();
            return false;
        }

    }

    public List<RequestBean> readAllRequestBeans() {

        try (Session session = HibernateUtil.getSession()) {

            String hql = "FROM RequestBean";
            List<RequestBean> requestBeans = session.createQuery(hql, RequestBean.class).getResultList();
            return requestBeans;

        } catch (Exception exp) {
            exp.printStackTrace();
            return new ArrayList<RequestBean>();
        }
        
    }

    public void removeAllRequestBeans() {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            String hql = "DELETE FROM RequestBean";
            session.createQuery(hql).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

}
