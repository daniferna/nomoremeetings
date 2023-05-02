package com.dfernandezaller.repository.entity;

import com.google.api.client.auth.oauth2.StoredCredential;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Data
@Entity
@Serdeable
@Table(name = "google_credentials")
@NoArgsConstructor
@AllArgsConstructor
public class GoogleCredential {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String key;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "expiration_time_milliseconds")
    private Long expirationTimeMilliseconds;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "created_at")
    private Instant createdAt;
    @Column(name = "updated_at")
    private Instant updatedAt;

    public void apply(StoredCredential credential) {
        this.accessToken = credential.getAccessToken();
        this.expirationTimeMilliseconds = credential.getExpirationTimeMilliseconds();
        this.refreshToken = credential.getRefreshToken();
        this.updatedAt = Instant.now();
    }
}