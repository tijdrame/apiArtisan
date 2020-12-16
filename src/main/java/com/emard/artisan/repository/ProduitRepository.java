package com.emard.artisan.repository;

import com.emard.artisan.domain.Produit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

	//@Query("SELECT distinct p from Produit p join fetch p.photos ph where p.deleted=false  order by p.libelle")
	Page<Produit> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
