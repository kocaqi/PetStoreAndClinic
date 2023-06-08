package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Pet;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.PetDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.PetSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ClientRepository;
import al.bytesquad.petstoreandclinic.repository.PetRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PetService(PetRepository petRepository, ModelMapper modelMapper, UserRepository userRepository,
                      ClientRepository clientRepository, ObjectMapper objectMapper) {
        this.petRepository = petRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Pet, PetDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public PetDTO create(String jsonString) throws JsonProcessingException {
        PetSaveDTO petSaveDTO = objectMapper.readValue(jsonString, PetSaveDTO.class);
        Pet pet = modelMapper.map(petSaveDTO, Pet.class);
        return modelMapper.map(petRepository.save(pet), PetDTO.class);
    }

    public PetDTO update(String jsonString, long id) throws JsonProcessingException {
        PetSaveDTO petSaveDTO = objectMapper.readValue(jsonString, PetSaveDTO.class);

        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));
        pet.setName(petSaveDTO.getName());
        pet.setOwner(petSaveDTO.getOwner());
        pet.setSpecies(petSaveDTO.getSpecies());
        pet.setBreed(petSaveDTO.getBreed());
        pet.setGender(petSaveDTO.getGender());
        pet.setDateOfBirth(petSaveDTO.getDateOfBirth());
        pet.setColour(petSaveDTO.getColour());
        return modelMapper.map(petRepository.save(pet), PetDTO.class);
    }

    public String delete(long id) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", id));
        pet.setEnabled(false);
        petRepository.save(pet);
        return "Pet Deleted Successfully!";
    }

    public List<PetDTO> getAll(String keyword, Principal principal) {
        if(keyword == null)
            return petRepository.findAllByEnabled(true).stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        List<Pet> pets = petRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<Pet> filteredPets;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInEmail);
        List<String> userRoles = user.getRoles().stream()
                .map(Role::getName).toList();

        String selectedRole = null;

        if (authentication != null && userRoles.contains(authentication.getAuthorities().iterator().next().getAuthority())) {
            selectedRole = authentication.getAuthorities().iterator().next().getAuthority();
            // Assuming the authority is in the format "ROLE_{ROLE_NAME}"
            selectedRole = selectedRole.substring("ROLE_".length()).toLowerCase();
        }

        if (!selectedRole.equals("client"))
            filteredPets = pets;
        else
            filteredPets = pets.stream()
                    .filter(pet -> pet.getOwner().equals(clientRepository.findByEmail(loggedInEmail)))
                    .collect(Collectors.toList());

        return filteredPets.stream().map(pet -> modelMapper.map(pet, PetDTO.class)).collect(Collectors.toList());
    }
}
