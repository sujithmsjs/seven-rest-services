package tech.suji.seven_prods.projects.usermgnt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import tech.suji.seven_prods.model.Gender;
import tech.suji.seven_prods.projects.empmgnt.domain.Role;
import tech.suji.seven_prods.projects.usermgnt.dto.SubscriptionType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false, unique = true, length = 25)
    private String username;

    @Column(nullable = false, length = 40)
    private String password;
    
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 40)
    private String email;

    @Column
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    @Column(nullable = false)
    private Boolean isEnabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", unique = true)
    private Role role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
