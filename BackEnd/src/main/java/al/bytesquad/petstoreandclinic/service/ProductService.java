package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.entity.Product;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.entity.productAttributes.Type;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ProductDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ProductSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ProductRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;

        modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public ProductDTO create(ProductSaveDTO productSaveDTO) {
        Product product = modelMapper.map(productSaveDTO, Product.class);
        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    public ProductDTO update(ProductSaveDTO productSaveDTO, long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setName(productSaveDTO.getName());
        product.setPricePerUnit(productSaveDTO.getPricePerUnit());
        product.setStock(productSaveDTO.getStock());
        product.setType(productSaveDTO.getType());
        return modelMapper.map(productRepository.save(product), ProductDTO.class);
    }

    public void delete(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setEnabled(false);
        productRepository.save(product);
    }

    public List<ProductDTO> get(String keyword, Principal principal) {
        if(keyword == null)
            return productRepository.findAllByEnabled(true).stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        List<Product> products = productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<Product> filteredProducts;

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

        if(selectedRole.equals("doctor"))
            filteredProducts = products.stream().filter(product -> product.getType().equals(Type.MEDICAL)).collect(Collectors.toList());
        else if (!selectedRole.equals("client"))
            filteredProducts = products;
        else
            filteredProducts = null;

        if (filteredProducts == null)
            return null;
        return filteredProducts.stream().map(product -> modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
    }
}
