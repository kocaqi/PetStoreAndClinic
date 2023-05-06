package al.bytesquad.petstoreandclinic.repository;

import al.bytesquad.petstoreandclinic.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
}
