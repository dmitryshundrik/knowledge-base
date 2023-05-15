package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.Article;
import com.dmitryshundrik.knowledgebase.model.common.dto.ArticleDTO;
import com.dmitryshundrik.knowledgebase.repository.common.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Article getById(String articleId) {
        return articleRepository.findById(UUID.fromString(articleId)).orElse(null);
    }

    public ArticleDTO getArticleDTO(Article article) {
        return ArticleDTO.builder()
                .id(article.getId().toString())
                .created(article.getCreated())
                .description(article.getDescription())
                .build();
    }

}
