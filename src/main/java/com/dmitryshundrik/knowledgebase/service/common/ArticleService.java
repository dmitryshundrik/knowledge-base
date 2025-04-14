package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.entity.common.Article;
import com.dmitryshundrik.knowledgebase.model.dto.common.ArticleDto;
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

    public ArticleDto getArticleDTO(Article article) {
        return ArticleDto.builder()
                .id(article.getId().toString())
                .created(article.getCreated())
                .description(article.getDescription())
                .build();
    }
}
