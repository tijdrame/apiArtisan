package com.emard.artisan.repository;

import com.emard.artisan.domain.Commande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Commande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

	Page<Commande> findByDeletedFalseOrderByDateCommandeDesc(Pageable pageable);
}
