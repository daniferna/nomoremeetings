package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.GoogleCredential;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import jakarta.inject.Singleton;

import java.util.Optional;
import java.util.Set;

@Singleton
@Repository
public interface GoogleCredentialRepository  extends CrudRepository<GoogleCredential, String> {

    Optional<GoogleCredential> findByKey(String key);
    void deleteByKey(String key);
    Optional<GoogleCredential> findByAccessToken(String accessToken);
    @Query(value = "select key from google_jpa_data_store_credential", nativeQuery = true)
    Set<String> findAllKeys();

}