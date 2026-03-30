package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity , Long>,
        PagingAndSortingRepository<UserEntity , Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
