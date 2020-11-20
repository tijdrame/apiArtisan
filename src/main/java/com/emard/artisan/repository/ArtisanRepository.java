package com.emard.artisan.repository;

import com.emard.artisan.domain.Artisan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Artisan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtisanRepository extends JpaRepository<Artisan, Long> {

    @Query("select artisan from Artisan artisan where artisan.user.login = ?#{principal.username}")
    List<Artisan> findByUserIsCurrentUser();

	Page<Artisan> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);
}
