package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ArticleDTO;
import al.bytesquad.petstoreandclinic.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ArticleDTO> create(@RequestParam long clientId, @RequestParam long productId, @RequestParam long petId, @RequestParam double quantity) {
        return new ResponseEntity<>(articleService.create(clientId, productId, petId, quantity), HttpStatus.CREATED);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ArticleDTO> getAll(@RequestParam(required = false) String keyword, Principal principal) {
        return articleService.getAll(keyword, principal);
    }

    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id) {
        articleService.delete(id);
    }
}
