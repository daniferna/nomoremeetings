package com.dfernandezaller.repository;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.DataStoreFactory;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

@Factory
@Singleton
public class JPADataStoreFactory implements DataStoreFactory {

    private final GoogleCredentialRepository repository;

    public JPADataStoreFactory(GoogleCredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    @Singleton
    public DataStore<StoredCredential> getDataStore(String id) {
        return new JPADataStore(this, id, repository);
    }
}
