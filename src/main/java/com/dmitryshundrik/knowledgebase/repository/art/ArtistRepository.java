package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    Artist getBySlug(String artistSlug);

    List<Artist> getAllByOrderByCreatedDesc();

    List<Artist> findFirst20ByOrderByCreatedDesc();

    List<Artist> getAllByOrderByBorn();

    @Query(value = "select * from artists where extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentBirth(LocalDate date);

    @Query(value = "select * from artists where extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentDeath(LocalDate date);

}
