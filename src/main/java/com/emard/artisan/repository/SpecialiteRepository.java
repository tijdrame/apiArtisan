package com.emard.artisan.repository;

import com.emard.artisan.domain.Specialite;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Specialite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {

	Page<Specialite> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
