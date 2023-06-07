package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.controller.ArticleController;
import al.bytesquad.petstoreandclinic.entity.Article;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ArticleDTO;
import al.bytesquad.petstoreandclinic.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    public ArticleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;

        this.modelMapper.addMappings(new PropertyMap<Article, ArticleDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public ArticleDTO create(long clientId, long productId, long petId, double quantity) {
        return null;
    }

    public List<ArticleDTO> getAll(String keyword, Principal principal) {
        return null;
    }

    public ArticleDTO update(long clientId, long productId, long petId, long id) {
        return null;
    }

    public void delete(long id) {
        System.out.println("...");
    }
}
