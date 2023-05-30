package al.bytesquad.petstoreandclinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "shop")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToMany
    @ToString.Exclude
    private List<Doctor> doctors;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany
    @ToString.Exclude
    private List<Receptionist> receptionists;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Shop shop = (Shop) o;
        return id != null && Objects.equals(id, shop.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
