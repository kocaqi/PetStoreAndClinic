package al.bytesquad.petstoreandclinic.entity;

import al.bytesquad.petstoreandclinic.entity.petAttributes.Breed;
import al.bytesquad.petstoreandclinic.entity.petAttributes.Gender;
import al.bytesquad.petstoreandclinic.entity.petAttributes.Species;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Table(name = "pet")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Client owner;

    @Column(name = "species")
    private Species species;

    @Column(name = "breed")
    private Breed breed;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "colour")
    private String colour;

    @Column(name = "enabled")
    private boolean enabled = true;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Appointment> appointments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Pet pet = (Pet) o;
        return id != null && Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
