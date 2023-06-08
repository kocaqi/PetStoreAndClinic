package al.bytesquad.petstoreandclinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Table(name = "bill")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Client client;

    @OneToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    List<Article> articles;

    @Column(name = "amount")
    private double amount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bill bill = (Bill) o;
        return id != null && Objects.equals(id, bill.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
