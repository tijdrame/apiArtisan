package com.emard.artisan.repository;

import com.emard.artisan.domain.TypeUser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeUserRepository extends JpaRepository<TypeUser, Long> {

	Page<TypeUser> findByDeletedFalseOrderByLibelle(Pageable pageable);
}
