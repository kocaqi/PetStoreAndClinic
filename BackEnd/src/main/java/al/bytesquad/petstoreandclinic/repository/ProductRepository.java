package al.bytesquad.petstoreandclinic.repository;

import al.bytesquad.petstoreandclinic.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
