package com.dmitryshundrik.knowledgebase.repository.gastronomy;

import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CocktailRepository extends JpaRepository<Cocktail, UUID> {

}
