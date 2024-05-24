package lab3test.beans;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import org.primefaces.PrimeFaces;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import lab3test.dao.RequestBeanDao;
import lab3test.mbeans.ClickIntervalMonitor;
import lab3test.mbeans.RequestsMonitor;
import lab3test.mbeans.RequestsNotificationListener;
import lab3test.model.RequestBean;


@Named("requestsController")
@ApplicationScoped
public class RequestsController implements Serializable {

    private List<RequestBean> requestBeansHistory;

    private RequestBean requestBean;

    private RequestsMonitor requestsMonitor;

    private ClickIntervalMonitor clickIntervalMonitor;

    public RequestsController() {  
    }

    @PostConstruct
    public void init() {
        requestBean = new RequestBean();
        requestBean.setX(Double.valueOf(0));
        requestBean.setY(Double.valueOf(0));
        requestBean.setR(Double.valueOf(1));

        requestBeansHistory = requestBeanDao.readAllRequestBeans();

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

            ObjectName name = new ObjectName("lab3test.beans:type=RequestsMonitor");
            requestsMonitor = new RequestsMonitor();
            mbs.registerMBean(requestsMonitor, name);

            ObjectName clickIntervalMonitorName = new ObjectName("lab3test.beans:type=ClickIntervalMonitor");
            clickIntervalMonitor = new ClickIntervalMonitor();
            mbs.registerMBean(clickIntervalMonitor, clickIntervalMonitorName);

            NotificationListener listener = new RequestsNotificationListener();

            mbs.addNotificationListener(name, listener, null, null);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
            requestsMonitor.addPoint(result);
            clickIntervalMonitor.addClickTimestamp(System.currentTimeMillis());
        }

        return "main.xhtml?faces-redirect=true";
    }

    public String clearRequests() {

        requestBeanDao.removeAllRequestBeans();

        requestBeansHistory.clear();

        requestsMonitor.clearMonitor();

        clickIntervalMonitor.clearMonitor();

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