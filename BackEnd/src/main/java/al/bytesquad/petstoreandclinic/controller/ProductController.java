package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ProductDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ProductSaveDTO;
import al.bytesquad.petstoreandclinic.service.ProductService;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductDTO> addNew(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        return new ResponseEntity<>(productService.create(productSaveDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductSaveDTO productSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(productService.update(productSaveDTO, id), HttpStatus.OK);
    }

    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id) {
        productService.delete(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ProductDTO> get(@RequestParam(required = false) String keyword, Principal principal) {
        return productService.get(keyword, principal);
    }
}
