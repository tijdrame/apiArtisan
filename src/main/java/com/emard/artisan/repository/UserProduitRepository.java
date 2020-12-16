package com.emard.artisan.repository;

import com.emard.artisan.domain.UserProduit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserProduit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProduitRepository extends JpaRepository<UserProduit, Long> {

    @Query("select userProduit from UserProduit userProduit where userProduit.user.login = ?#{principal.username}")
    List<UserProduit> findByUserIsCurrentUser();
}
