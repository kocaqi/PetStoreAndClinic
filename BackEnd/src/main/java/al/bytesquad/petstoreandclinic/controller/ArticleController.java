package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Pet;
import al.bytesquad.petstoreandclinic.entity.Product;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ArticleDTO;
import al.bytesquad.petstoreandclinic.service.ArticleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ArticleDTO create(@RequestParam Product product, @RequestParam double quantity, @RequestParam Pet pet, @RequestParam Client client){
        return null;
    }
}
