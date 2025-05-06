package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    Optional<Artist> findBySlug(String artistSlug);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.slug != :slug")
    List<Artist> findAllBy(@Param("slug") String slug);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.slug != :slug " +
            "ORDER BY a.born")
    List<Artist> findAllByOrderByBorn(@Param("slug") String slug);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.slug != :slug " +
            "ORDER BY a.created DESC")
    List<Artist> findAllByOrderByCreatedDesc(@Param("slug") String slug);

    @Query("SELECT a " +
            "FROM Artist a " +
            "WHERE a.slug != :slug " +
            "ORDER BY a.created DESC")
    List<Artist> findFirst20ByOrderByCreatedDesc(@Param("slug") String slug);

    @Query(value = "select * from artist where " +
            "extract(month from birth_date) = extract(month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract(day from birth_date) = extract(day from to_date(:date, 'YYYY-MM-DD')) " +
            "and (:isNotify IS NULL OR date_notification = :isNotify)", nativeQuery = true)
    List<Artist> findAllWithCurrentBirth(@Param("date") LocalDate date, @Param("isNotify") Boolean isNotify);

    @Query(value = "select * from artist where " +
            "extract(month from death_date) = extract(month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract(day from death_date) = extract(day from to_date(:date, 'YYYY-MM-DD')) " +
            "and (:isNotify IS NULL OR date_notification = :isNotify)", nativeQuery = true)
    List<Artist> findAllWithCurrentDeath(@Param("date") LocalDate date, @Param("isNotify") Boolean isNotify);

    @Query(value = "select count(m) from Artist m")
    Long getSize();
}
