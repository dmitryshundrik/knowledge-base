package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.entity.common.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

}
