package com.dmitryshundrik.knowledgebase.repository.gastronomy;

import com.dmitryshundrik.knowledgebase.model.enums.Country;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query(value = "select count(m) from Recipe m")
    Long getSize();

    Recipe findBySlug(String slug);

    List<Recipe> findAllByCountry(Country country);

    List<Recipe> findAllByOrderByCreatedDesc();

    List<Recipe> findFirst20ByOrderByCreatedDesc();
}
