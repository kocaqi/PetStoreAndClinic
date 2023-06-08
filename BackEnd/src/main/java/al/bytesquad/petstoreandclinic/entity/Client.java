package al.bytesquad.petstoreandclinic.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Objects;

@Table(name = "client", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Client {
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Pet> pets;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Bill> bills;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Appointment> appointments;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Feedback> feedbacks;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Article> articles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getId() {
        return this.id;
    }
}
