package com.dfernandezaller.repository;

import com.dfernandezaller.repository.entity.GoogleCredential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class JPADataStoreTest {

    @Inject
    GoogleCredentialRepository googleCredentialRepository;

    @Inject
    JPADataStoreFactory jpaDataStoreFactory;

    private DataStore<StoredCredential> jpaDataStore;
    private final Set<GoogleCredential> testEntities = new HashSet<>();

    @BeforeEach
    void setUp() {
        jpaDataStore = jpaDataStoreFactory.getDataStore("testDataStore");
    }

    @AfterEach
    void cleanup() {
        googleCredentialRepository.deleteAll(testEntities);
        testEntities.clear();
    }

    @Test
    public void testSize() throws IOException {
        assertEquals(15, jpaDataStore.size());
    }

    @Test
    public void testIsEmpty() throws IOException {
        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        Assertions.assertFalse(jpaDataStore.isEmpty());
    }

    @Test
    public void testContainsKey() throws IOException {
        Assertions.assertFalse(jpaDataStore.containsKey("key"));

        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        assertTrue(jpaDataStore.containsKey("key"));
    }

    @Test
    public void testContainsValue() throws IOException {
        StoredCredential testCredential = new StoredCredential();
        testCredential.setAccessToken("accessToken");
        testCredential.setRefreshToken("refreshToken");
        testCredential.setExpirationTimeMilliseconds(3600L);

        Assertions.assertFalse(jpaDataStore.containsValue(testCredential));

        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        assertTrue(jpaDataStore.containsValue(testCredential));
    }

    @Test
    public void testKeySet() throws IOException {
        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        assertTrue(jpaDataStore.keySet().contains("key"));
        assertFalse(jpaDataStore.keySet().contains("testKey"));
    }

    @Test
    public void testValues() throws IOException {
        assertEquals(15, jpaDataStore.values().size());

        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        assertEquals(16, jpaDataStore.values().size());
    }

    @Test
    public void testGet() throws IOException {
        assertNull(jpaDataStore.get("key"));

        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        StoredCredential returnedCredential = jpaDataStore.get("key");
        assertNotNull(returnedCredential);
        assertEquals("accessToken", returnedCredential.getAccessToken());
    }

    @Test
    public void testSet() throws IOException {
        StoredCredential testCredential = new StoredCredential();
        testCredential.setAccessToken("newAccessToken");
        testCredential.setRefreshToken("newRefreshToken");
        testCredential.setExpirationTimeMilliseconds(3600L);

        jpaDataStore.set("key", testCredential);

        GoogleCredential credential = googleCredentialRepository.findByKey("key").get();
        testEntities.add(credential);

        assertEquals("newAccessToken", credential.getAccessToken());
        assertEquals("newRefreshToken", credential.getRefreshToken());
    }

    @Test
    public void testClear() throws IOException {
        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        jpaDataStore.clear();
        assertTrue(jpaDataStore.isEmpty());
    }

    @Test
    public void testDelete() throws IOException {
        GoogleCredential credential = new GoogleCredential("key", "accessToken", 3600L, "refreshToken", Instant.now(), Instant.now());
        googleCredentialRepository.save(credential);
        testEntities.add(credential);

        jpaDataStore.delete("key");
        Assertions.assertFalse(jpaDataStore.containsKey("key"));
    }
}
