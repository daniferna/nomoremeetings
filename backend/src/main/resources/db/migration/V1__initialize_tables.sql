CREATE TABLE google_credentials
(
    id                           VARCHAR PRIMARY KEY,
    access_token                 VARCHAR,
    expiration_time_milliseconds BIGINT,
    refresh_token                VARCHAR,
    created_at                   TIMESTAMP,
    updated_at                   TIMESTAMP
);

CREATE TABLE users
(
    email                 VARCHAR PRIMARY KEY,
    name                  VARCHAR NOT NULL,
    url_photo             VARCHAR,
    start_working_time    TIME DEFAULT '08:00:00',
    end_working_time      TIME DEFAULT '17:00:00',
    start_lunch_time      TIME DEFAULT '13:00:00',
    end_lunch_time        TIME DEFAULT '14:00:00',
    time_between_meetings INT  DEFAULT 15,
    calendar_id           VARCHAR,
    days_to_analyze       INT  DEFAULT 7
);
