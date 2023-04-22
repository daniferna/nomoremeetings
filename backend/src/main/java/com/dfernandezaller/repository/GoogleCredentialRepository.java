package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.GoogleCredential;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface GoogleCredentialRepository extends CrudRepository<GoogleCredential, String> {

    Optional<GoogleCredential> findByKey(String key);

    void deleteByKey(String key);

    Optional<GoogleCredential> findByAccessToken(String accessToken);

    Set<String> findKey();

}