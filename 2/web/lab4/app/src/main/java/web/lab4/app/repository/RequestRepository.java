package web.lab4.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import web.lab4.app.model.AppRequest;
import web.lab4.app.model.AppUser;

public interface RequestRepository extends JpaRepository<AppRequest, Long> {

    List<AppRequest> findByOwner(AppUser owner);
    
}
