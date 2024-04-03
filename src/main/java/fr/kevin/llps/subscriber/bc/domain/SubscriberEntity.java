package fr.kevin.llps.subscriber.bc.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subscriber")
public class SubscriberEntity {

    public SubscriberEntity(String firstname, String lastname, String email, String phone, boolean enabled) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.enabled = enabled;
    }

    @Id
    @GeneratedValue
    @Column(name = "subscriber_id", nullable = false, columnDefinition = "UUID")
    private UUID id;

    @Column(name = "firstname", nullable = false, columnDefinition = "VARCHAR(100)")
    private String firstname;

    @Column(name = "lastname", nullable = false, columnDefinition = "VARCHAR(200)")
    private String lastname;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(200)")
    private String email;

    @Column(name = "phone", nullable = false, columnDefinition = "CHAR(10)")
    private String phone;

    @Column(name = "enabled", nullable = false, columnDefinition = "BOOLEAN")
    private boolean enabled;

}
