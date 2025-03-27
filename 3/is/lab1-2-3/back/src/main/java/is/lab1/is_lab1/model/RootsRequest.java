package is.lab1.is_lab1.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class RootsRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @OneToOne
    private IsUser isUser;

    @Column()
    private Role role;

    @Column
    private Boolean isActive;

    public RootsRequest(IsUser user, Role role) {
        this.isUser = user;
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
        this.isActive = true;
    }

}
