package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.entity.common.Article;
import com.dmitryshundrik.knowledgebase.dto.common.ArticleDTO;
import com.dmitryshundrik.knowledgebase.repository.common.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

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
