package al.bytesquad.petstoreandclinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.Objects;

@Table(name = "appointment")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Appointment {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Doctor doctor;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "finish_Time")
    private Date finishTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Appointment that = (Appointment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public long getId() {
        return this.id;
    }
}
