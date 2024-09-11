package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterEntityUpdateInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WriterRepository extends JpaRepository<Writer, UUID> {

    Writer findBySlug(String writerSlug);

    void deleteBySlug(String writerSlug);

    List<Writer> getAllByOrderByCreatedDesc();

    List<WriterEntityUpdateInfoDTO> findFirst20ByOrderByCreatedDesc();

    @Query(value = "select * from writer where extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Writer> findAllWithCurrentBirth(LocalDate date);

    @Query(value = "select * from writer where extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Writer> findAllWithCurrentDeath(LocalDate date);

    List<Writer> findAllByOrderByBorn();

}
