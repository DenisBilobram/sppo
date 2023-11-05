package web.lab4.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import web.lab4.app.model.AppRequest;
import web.lab4.app.model.AppUser;
import web.lab4.app.repository.RequestRepository;
import web.lab4.app.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AppRequestService {

    @Autowired
    private RequestRepository appRequestRepository;

    @Autowired
    private UserRepository appUserRepository;

    public List<AppRequest> getAppRequestsForUser(UserDetails userDetails) {
        AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User by username not found"));

        return appRequestRepository.findByOwner(appUser);
    }

    public AppRequest saveRequest(UserDetails userDetails, Double x, Double y, Double r, boolean result) {
        AppUser appUser = appUserRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User by username not found"));

        AppRequest appRequest = new AppRequest();
        appRequest.setOwner(appUser);
        appRequest.setX(x);
        appRequest.setY(y);
        appRequest.setR(r);
        appRequest.setResult(result);
        appRequest.setTime(new Timestamp(System.currentTimeMillis()));

        return appRequestRepository.save(appRequest);
    }

    public void clearRequests(UserDetails userDetails) {
        appRequestRepository.deleteAll(getAppRequestsForUser(userDetails));
    }

}
