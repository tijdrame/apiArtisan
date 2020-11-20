package com.emard.artisan.repository;

import com.emard.artisan.domain.Client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select client from Client client where client.users.login = ?#{principal.username}")
    List<Client> findByUsersIsCurrentUser();

	Page<Client> findByDeletedFalseOrderByNomAscPrenomAsc(Pageable pageable);
}
