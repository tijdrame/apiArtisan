package com.emard.artisan.repository;

import com.emard.artisan.domain.Photo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

	Page<Photo> findByDeletedFalseOrderByProduit_Libelle(Pageable pageable);
}
