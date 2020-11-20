package com.emard.artisan.repository;

import com.emard.artisan.domain.EtatCommande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EtatCommande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtatCommandeRepository extends JpaRepository<EtatCommande, Long> {

	Page<EtatCommande> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
