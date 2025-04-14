package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Article;
import com.dmitryshundrik.knowledgebase.model.dto.core.ArticleDto;
import com.dmitryshundrik.knowledgebase.repository.core.ArticleRepository;
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

    public ArticleDto getArticleDto(Article article) {
        return ArticleDto.builder()
                .id(article.getId().toString())
                .created(article.getCreated())
                .description(article.getDescription())
                .build();
    }
}
