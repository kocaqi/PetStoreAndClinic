package al.bytesquad.petstoreandclinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Objects;

@Table(name = "manager", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Manager {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled = true;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "about")
    private String about;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne
    @JoinColumn(name = "shop_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Shop shop;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Manager manager = (Manager) o;
        return id != null && Objects.equals(id, manager.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public long getId() {
        return this.id;
    }
}
