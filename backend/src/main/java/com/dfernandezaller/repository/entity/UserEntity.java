package com.dfernandezaller.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiration_date")
    private ZonedDateTime refreshTokenExpirationDate;

    @Column(name = "start_work_time")
    private ZonedDateTime startWorkTime;

    @Column(name = "end_work_time")
    private ZonedDateTime endWorkTime;

}
