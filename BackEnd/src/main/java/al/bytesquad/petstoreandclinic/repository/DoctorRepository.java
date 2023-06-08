package al.bytesquad.petstoreandclinic.repository;

import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {

    List<Doctor> findAllByShop(Shop shop);

    Optional<Doctor> findDoctorById(long id);

    Doctor findById(long id);

    Doctor findByEmail(String email);

    List<Doctor> findAllByEnabled(Boolean enabled);
}
