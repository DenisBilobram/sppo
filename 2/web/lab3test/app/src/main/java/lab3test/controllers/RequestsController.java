package lab3test.controllers;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.primefaces.PrimeFaces;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab3test.dao.RequestBeanDao;
import lab3test.model.RequestBean;


@Named("requestsController")
@ApplicationScoped
public class RequestsController implements Serializable {

    private List<RequestBean> requestBeansHistory;

    private RequestBean requestBean;

    public RequestsController() {  
    }

    @PostConstruct
    public void init() {
        requestBean = new RequestBean();
        requestBeansHistory = requestBeanDao.readAllRequestBeans();
    }


    @Inject
    private RequestBeanDao requestBeanDao;

    public String processRequest() {

        this.requestBean.setTime(new Timestamp(System.currentTimeMillis()));

        boolean result = calcResult();

        PrimeFaces.current().ajax().addCallbackParam("result", result);
        PrimeFaces.current().ajax().addCallbackParam("x", requestBean.getX());
        PrimeFaces.current().ajax().addCallbackParam("y", requestBean.getY());

        this.requestBean.setResult(result);

        if (requestBeanDao.createRequest(requestBean)) {
            requestBeansHistory.add(requestBean);
            requestBean = new RequestBean(requestBean);
        }

        return "main.xhtml?faces-redirect=true";
    }

    public String clearRequests() {

        requestBeanDao.removeAllRequestBeans();

        requestBeansHistory.clear();

        return "main.xhtml?faces-redirect=true";
    }

    public void setRequestBean(RequestBean requestBean) {
        this.requestBean = requestBean;
    }

    public RequestBean getRequestBean() {
        return this.requestBean;
    }

    

    public boolean calcResult() {

        Double x = requestBean.getX();
        Double y = requestBean.getY();
        Double r = requestBean.getR();
        if (((x <= 0 && y >= 0) && (y <= x + r)) || ((x <= 0 && y <= 0) && (Math.sqrt(x*x + y*y) <= r/2)) || ((x >= 0 && y <= 0) && (x <= r && y >= -r/2))) {
            return true;
        } else {
            return false;
        }

    }

    public List<RequestBean> getReversedRequestBeansHistory() {
        List<RequestBean> reversedList = new ArrayList<>(requestBeansHistory);
        Collections.reverse(reversedList);
        return reversedList;
    }

}