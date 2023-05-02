package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.User;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
