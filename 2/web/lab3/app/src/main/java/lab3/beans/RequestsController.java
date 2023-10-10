package lab3.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import javax.inject.Inject;
import jakarta.persistence.EntityManager;
import lab3.model.RequestBean;

@Named
@RequestScoped
public class RequestsController {

    RequestBean currentRequestBean = new RequestBean();

    @Inject
    private EntityManager entityManager;

}
