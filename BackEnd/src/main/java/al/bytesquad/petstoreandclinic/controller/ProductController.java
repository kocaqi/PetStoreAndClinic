package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ProductDTO;
import al.bytesquad.petstoreandclinic.repository.ProductRepository;
import al.bytesquad.petstoreandclinic.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductService productService,
                             ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductDTO> addNew(@Valid @RequestBody String productSaveDTO) throws JsonProcessingException {
        return new ResponseEntity<>(productService.create(productSaveDTO), HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody String productSaveDTO, @PathVariable(name = "id") long id) throws JsonProcessingException {
        return new ResponseEntity<>(productService.update(productSaveDTO, id), HttpStatus.OK);
    }

    @PostMapping("/addStock/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductDTO> addStock(@PathVariable(name = "id") long id, @RequestParam double imported){
        return new ResponseEntity<>(productService.addStock(id, imported), HttpStatus.OK);
    }

    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String delete(@PathVariable(name = "id") long id) {
        return productService.delete(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ProductDTO> get(@RequestParam(required = false) String keyword, Principal principal) {
        return productService.get(keyword, principal);
    }
}
