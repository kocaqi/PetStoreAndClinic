package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.entityDTO.TransactionDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.TransactionSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final ReceptionistRepository receptionistRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final PetRepository petRepository;

    public TransactionService(TransactionRepository transactionRepository, ModelMapper modelMapper, ObjectMapper objectMapper, UserRepository userRepository,
                              ManagerRepository managerRepository,
                              ReceptionistRepository receptionistRepository,
                              ProductRepository productRepository,
                              ClientRepository clientRepository,
                              PetRepository petRepository) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;

        this.modelMapper.addMappings(new PropertyMap<Transaction, TransactionDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setProductName(source.getProductName());
            }
        });
        this.managerRepository = managerRepository;
        this.receptionistRepository = receptionistRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
    }

    @Transactional
    public TransactionDTO create(String jsonString, Principal principal) throws JsonProcessingException {
        TransactionSaveDTO transactionSaveDTO = objectMapper.readValue(jsonString, TransactionSaveDTO.class);

        Transaction transaction = new Transaction();
        Client client = clientRepository.findClientById(transactionSaveDTO.getClientId()).orElseThrow(() -> new ResourceNotFoundException("Client", "id", transactionSaveDTO.getClientId()));;
        Product product = productRepository.findById(transactionSaveDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", transactionSaveDTO.getProductId()));
        Pet pet = petRepository.findById(transactionSaveDTO.getPetId()).orElseThrow(() -> new ResourceNotFoundException("Pet", "id", transactionSaveDTO.getPetId()));

        transaction.setClient(client);
        /*User user = userRepository.findByEmail(principal.getName());
        transaction.setUser(user);*/
        transaction.setProduct(product);
        transaction.setProductName(product.getName());
        transaction.setPricePerUnit(product.getPricePerUnit());
        transaction.setQuantity(transactionSaveDTO.getQuantity());
        transaction.setTotal(product.getPricePerUnit() * transactionSaveDTO.getQuantity());
        transaction.setPet(pet);

        //Product product = transactionSaveDTO.getProduct();
        product.setStock(product.getStock() - transactionSaveDTO.getQuantity());
        productRepository.save(product);

        return modelMapper.map(transactionRepository.save(transaction), TransactionDTO.class);
    }

    public List<TransactionDTO> getAll(String keyword, Principal principal) {
        if (keyword == null)
            return transactionRepository.findAll().stream().map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }

        List<Transaction> transactions = transactionRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        /*List<Transaction> filtered;

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

        if (selectedRole.equals("admin"))
            filtered = transactions;
        else if (selectedRole.equals("manager")) {
            Shop managerShop = managerRepository.findByEmail(loggedInEmail).getShop();
            filtered = transactions.stream().filter(transaction -> managerRepository.findByEmail(transaction.getUser().getEmail()).getShop().equals(managerShop)).collect(Collectors.toList());
        } else if (selectedRole.equals("receptionist")) {
            Shop receptionistShop = receptionistRepository.findByEmail(loggedInEmail).getShop();
            filtered = transactions.stream().filter(transaction -> receptionistRepository.findByEmail(transaction.getUser().getEmail()).getShop().equals(receptionistShop)).collect(Collectors.toList());
        } else if (selectedRole.equals("client"))
            filtered = transactions.stream().filter(transaction -> transaction.getUser().getEmail().equals(loggedInEmail)).collect(Collectors.toList());
        else filtered = null;

        if (filtered == null)
            return null;*/
        return transactions.stream().map(transaction -> modelMapper.map(transaction, TransactionDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public String delete(long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id));
        if(transaction.isPaid())
            return "Transaction is paid! Cannot delete!";
        Product product = productRepository.findById(transaction.getProduct().getId()).orElseThrow(() -> new ResourceNotFoundException("Product", "id", transaction.getProduct().getId()));;
        product.setStock(product.getStock()+transaction.getQuantity());
        productRepository.save(product);
        transactionRepository.delete(transaction);
        return "Transaction deleted successfully!";
    }
}
