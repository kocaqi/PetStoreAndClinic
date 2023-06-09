package al.bytesquad.petstoreandclinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Bill bill = null;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Client client;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price_per_unit")
    private double pricePerUnit;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "total")
    private double total;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private Pet pet;

    @Column(name = "is_paid")
    private boolean isPaid = false;

    @Column(name = "time")
    @CreationTimestamp
    private Date time;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Transaction transaction = (Transaction) o;
        return id != null && Objects.equals(id, transaction.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
