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

    @PostMapping("/addNew")
    public ResponseEntity<ProductDTO> addNew(@Valid @RequestBody ProductSaveDTO productSaveDTO) {
        return new ResponseEntity<>(productService.create(productSaveDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductSaveDTO productSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(productService.update(productSaveDTO, id), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        productService.delete(id);
    }

    @GetMapping
    public List<ProductDTO> get(@RequestParam String keyword, Principal principal) {
        return productService.get(keyword, principal);
    }
}
