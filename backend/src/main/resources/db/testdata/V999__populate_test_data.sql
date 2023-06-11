INSERT INTO google_credentials
(id, access_token, expiration_time_milliseconds, refresh_token, created_at, updated_at)
VALUES ('test1@gmail.com', 'access_token_1', 1686503755796, 'refresh_token_1', current_timestamp, current_timestamp),
       ('test2@gmail.com', 'access_token_2', 1686503755796, 'refresh_token_2', current_timestamp, current_timestamp),
       ('test3@gmail.com', 'access_token_3', 1686503755796, 'refresh_token_3', current_timestamp, current_timestamp),
       ('test4@gmail.com', 'access_token_4', 1686503755796, 'refresh_token_4', current_timestamp, current_timestamp),
       ('test5@gmail.com', 'access_token_5', 1686503755796, 'refresh_token_5', current_timestamp, current_timestamp)
ON CONFLICT DO NOTHING;

INSERT INTO google_credentials
(id, access_token, expiration_time_milliseconds, refresh_token, created_at, updated_at)
VALUES ('test6@gmail.com', 'access_token_6', 1686503755796, 'refresh_token_6', current_timestamp, current_timestamp),
       ('test7@gmail.com', 'access_token_7', 1686503755796, 'refresh_token_7', current_timestamp, current_timestamp),
       ('test8@gmail.com', 'access_token_8', 1686503755796, 'refresh_token_8', current_timestamp, current_timestamp),
       ('test9@gmail.com', 'access_token_9', 1686503755796, 'refresh_token_9', current_timestamp, current_timestamp),
       ('test10@gmail.com', 'access_token_10', 1686503755796, 'refresh_token_10', current_timestamp,
        current_timestamp)
ON CONFLICT DO NOTHING;

INSERT INTO google_credentials
(id, access_token, expiration_time_milliseconds, refresh_token, created_at, updated_at)
VALUES ('test11@gmail.com', 'access_token_11', 1686503755796, 'refresh_token_11', current_timestamp,
         current_timestamp),
        ('test12@gmail.com', 'access_token_12', 1686503755796, 'refresh_token_12', current_timestamp,
         current_timestamp),
        ('test13@gmail.com', 'access_token_13', 1686503755796, 'refresh_token_13', current_timestamp,
         current_timestamp),
        ('test14@gmail.com', 'access_token_14', 1686503755796, 'refresh_token_14', current_timestamp,
         current_timestamp),
        ('test15@gmail.com', 'access_token_15', 1686503755796, 'refresh_token_15', current_timestamp,
         current_timestamp)
ON CONFLICT DO NOTHING;

INSERT INTO users
(email, name, url_photo, start_working_time, end_working_time, start_lunch_time, end_lunch_time, time_between_meetings,
 calendar_id, days_to_analyze)
VALUES ('test1@gmail.com', 'User 1', 'https://testurl1.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
        'calendar_id_1', 30),
       ('test2@gmail.com', 'User 2', 'https://testurl2.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
        'calendar_id_2', 30),
       ('test3@gmail.com', 'User 3', 'https://testurl3.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
        'calendar_id_3', 30),
       ('test4@gmail.com', 'User 4', 'https://testurl4.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
        'calendar_id_4', 30),
       ('test5@gmail.com', 'User 5', 'https://testurl5.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
        'calendar_id_5', 30)
ON CONFLICT DO NOTHING;

INSERT INTO users
(email, name, url_photo, start_working_time, end_working_time, start_lunch_time, end_lunch_time, time_between_meetings,
 calendar_id, days_to_analyze)
VALUES ('test6@gmail.com', 'User 6', 'https://testurl6.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_6', 30),
        ('test7@gmail.com', 'User 7', 'https://testurl7.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_7', 30),
        ('test8@gmail.com', 'User 8', 'https://testurl8.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_8', 30),
        ('test9@gmail.com', 'User 9', 'https://testurl9.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_9', 30),
        ('test10@gmail.com', 'User 10', 'https://testurl10.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_10', 30)
ON CONFLICT DO NOTHING;

INSERT INTO users
(email, name, url_photo, start_working_time, end_working_time, start_lunch_time, end_lunch_time, time_between_meetings,
 calendar_id, days_to_analyze)
VALUES ('test11@gmail.com', 'User 11', 'https://testurl11.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_11', 30),
        ('test12@gmail.com', 'User 12', 'https://testurl12.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_12', 30),
        ('test13@gmail.com', 'User 13', 'https://testurl13.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_13', 30),
        ('test14@gmail.com', 'User 14', 'https://testurl14.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_14', 30),
        ('test15@gmail.com', 'User 15', 'https://testurl15.com', '08:00:00', '19:00:00', '13:00:00', '13:30:00', 15,
         'calendar_id_15', 30)
ON CONFLICT DO NOTHING;