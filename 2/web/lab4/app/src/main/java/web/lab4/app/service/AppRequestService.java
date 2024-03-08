package web.lab4.app.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.lab4.app.model.AppRequest;
// import web.lab4.app.model.AppUser;
import web.lab4.app.repository.RequestRepository;
// import web.lab4.app.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AppRequestService {

    @Autowired
    private RequestRepository appRequestRepository;

    // @Autowired
    // private UserRepository appUserRepository;

    public List<AppRequest> getAppRequestsForUser(String owner) {
        return appRequestRepository.findByOwner(owner);
    }

    public AppRequest saveRequest(Double x, Double y, Double r, boolean result, String owner) {
    
        AppRequest appRequest = new AppRequest();
        appRequest.setOwner(owner);
        appRequest.setX(x);
        appRequest.setY(y);
        appRequest.setR(r);
        appRequest.setResult(result);
        appRequest.setTime(new Timestamp(System.currentTimeMillis()));

        System.out.println(appRequest.getOwner());

        return appRequestRepository.save(appRequest);
    }

    public void clearRequests(String owner) {
        appRequestRepository.deleteAll(getAppRequestsForUser(owner));
    }

}
