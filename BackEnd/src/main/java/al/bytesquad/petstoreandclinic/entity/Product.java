package al.bytesquad.petstoreandclinic.entity;

import al.bytesquad.petstoreandclinic.entity.productAttributes.Type;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Objects;

@Table(name = "product", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price_per_unit")
    private double pricePerUnit;

    @Column(name = "stock")
    private double stock;

    @Column(name = "type")
    private Type type;

    @Column(name = "enabled")
    private boolean enabled = true;

    @ManyToMany
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference
    private List<Bill> bills;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
