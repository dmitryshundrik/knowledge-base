package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.entity.art.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    @Query(value = "select count(m) from Artist m")
    Long getSize();

    Optional<Artist> findBySlug(String artistSlug);

    List<Artist> findAllByOrderByCreatedDesc();

    List<Artist> findFirst20ByOrderByCreatedDesc();

    List<Artist> findAllByOrderByBorn();

    @Query(value = "select * from artist where extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentBirth(LocalDate date);

    @Query(value = "select * from artist where extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentDeath(LocalDate date);

    @Query(value = "select * from artist where date_notification = :isNotify and extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentBirthAndNotification(LocalDate date, Boolean isNotify);

    @Query(value = "select * from artist where date_notification = :isNotify and extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Artist> findAllWithCurrentDeathAndNotification(LocalDate date, Boolean isNotify);
}
