package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.GoogleCredential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.AbstractDataStore;
import com.google.api.client.util.store.DataStore;
import com.google.common.collect.Streams;
import jakarta.inject.Singleton;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;
import static java.time.Instant.now;


@Singleton
public class JPADataStore extends AbstractDataStore<StoredCredential> {
    private final GoogleCredentialRepository repository;

    /**
     * @param dataStoreFactory data store factory
     * @param id               data store ID
     */
    protected JPADataStore(JPADataStoreFactory dataStoreFactory, String id, GoogleCredentialRepository repository) {
        super(dataStoreFactory, id);
        this.repository = repository;
    }

    @Override
    public int size() {
        return toIntExact(repository.count());
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(String key) {
        return repository.findByKey(key).isPresent();
    }

    @Override
    public boolean containsValue(StoredCredential value) {
        return repository.findByAccessToken(value.getAccessToken()).isPresent();
    }

    @Override
    public Set<String> keySet() {
        return repository.findKey();
    }

    @Override
    public Collection<StoredCredential> values() {
        return Streams.stream(repository.findAll()).map(c -> {
            StoredCredential credential = new StoredCredential();
            credential.setAccessToken(c.getAccessToken());
            credential.setRefreshToken(c.getRefreshToken());
            credential.setExpirationTimeMilliseconds(c.getExpirationTimeMilliseconds());
            return credential;
        }).collect(Collectors.toList());
    }

    @Override
    public StoredCredential get(String key) {
        Optional<GoogleCredential> jpaStoredCredentialOptional = repository.findByKey(key);
        if (!jpaStoredCredentialOptional.isPresent()) {
            return null;
        }
        GoogleCredential googleCredential = jpaStoredCredentialOptional.get();
        StoredCredential credential = new StoredCredential();
        credential.setAccessToken(googleCredential.getAccessToken());
        credential.setRefreshToken(googleCredential.getRefreshToken());
        credential.setExpirationTimeMilliseconds(googleCredential.getExpirationTimeMilliseconds());
        return credential;
    }

    @Override
    public DataStore<StoredCredential> set(String key, StoredCredential value) {
        GoogleCredential googleCredential = repository.findByKey(key).orElse(
                new GoogleCredential(key, value.getAccessToken(), value.getExpirationTimeMilliseconds(), value.getRefreshToken(), now(), now())
        );
        googleCredential.apply(value);
        repository.update(googleCredential);
        return this;
    }

    @Override
    public DataStore<StoredCredential> clear() {
        repository.deleteAll();
        return this;
    }

    @Override
    public DataStore<StoredCredential> delete(String key) {
        repository.deleteByKey(key);
        return this;
    }
}