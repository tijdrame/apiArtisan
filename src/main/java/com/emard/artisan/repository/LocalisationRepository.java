package com.emard.artisan.repository;

import com.emard.artisan.domain.Localisation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Localisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalisationRepository extends JpaRepository<Localisation, Long> {

	Page<Localisation> findByDeletedFalseOrderByAdresse(Pageable pageable);
}
