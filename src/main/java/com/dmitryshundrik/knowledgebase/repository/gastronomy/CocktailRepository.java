package com.dmitryshundrik.knowledgebase.repository.gastronomy;

import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface CocktailRepository extends JpaRepository<Cocktail, UUID> {

    @Query(value = "select count(m) from Cocktail m")
    Long getSize();

    Cocktail findBySlug(String slug);

    List<Cocktail> findAllByOrderByCreatedDesc();

    List<Cocktail> findFirst20ByOrderByCreatedDesc();
}
