package al.bytesquad.petstoreandclinic.repository;

import al.bytesquad.petstoreandclinic.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    List<Pet> findAllByEnabled(Boolean enabled);
}
