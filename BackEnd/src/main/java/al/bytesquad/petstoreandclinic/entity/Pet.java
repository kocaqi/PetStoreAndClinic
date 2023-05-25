package al.bytesquad.petstoreandclinic.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "pet")
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
    private Client owner;

    enum Species{
        FELINE,
        CANINE
    }

    @Column(name = "species")
    private Species species;

    enum Breed{
        MIXED,
        SCOTTISH_FOLD,
        BRITISH_SHORTHAIR
    }

    @Column(name = "breed")
    private Breed breed;

    enum Gender{
        MALE,
        FEMALE
    }

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "colour")
    private String colour;

    @OneToMany
    @ToString.Exclude
    private List<Bill> bills;

    @OneToMany
    @ToString.Exclude
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
