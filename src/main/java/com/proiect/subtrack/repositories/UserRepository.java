package com.proiect.subtrack.repositories;

import com.proiect.subtrack.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity , Long> {
}
