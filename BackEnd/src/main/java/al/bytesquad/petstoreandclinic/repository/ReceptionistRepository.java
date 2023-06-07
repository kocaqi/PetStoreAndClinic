package al.bytesquad.petstoreandclinic.repository;

import al.bytesquad.petstoreandclinic.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceptionistRepository extends JpaRepository<Receptionist, Long>, JpaSpecificationExecutor<Receptionist> {
    List<Receptionist> findAllByEnabled(Boolean enabled);
}
