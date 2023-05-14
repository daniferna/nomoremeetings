package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.GoogleAuthorizationCodeFlowFactory;
import com.dfernandezaller.configuration.GoogleConfiguration;
import com.dfernandezaller.controller.dto.UpdateUserTimesDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.repository.UserRepository;
import com.dfernandezaller.service.UserService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.model.Calendar;
import io.micronaut.core.convert.ConversionService;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Singleton
@Transactional
public class UserServiceImp implements UserService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final UserRepository userRepository;
    private final ConversionService<?> conversionService;
    private final GoogleConfiguration googleConfiguration;
    private final GoogleAuthorizationCodeFlowFactory authorizationCodeFlowFactory;

    public UserServiceImp(UserRepository userRepository, ConversionService<?> conversionService, GoogleConfiguration googleConfiguration, GoogleAuthorizationCodeFlowFactory authorizationCodeFlowFactory) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.googleConfiguration = googleConfiguration;
        this.authorizationCodeFlowFactory = authorizationCodeFlowFactory;
    }

    @Override
    public Optional<UserDTO> getUser(String email) {
        return conversionService.convert(userRepository.findById(email).orElse(null), UserDTO.class);
    }

    @Override
    public Optional<UserDTO> updateUser(String email, UpdateUserTimesDTO requestDTO) {
        var userDb = userRepository.findById(email).orElseThrow();
        var updatedUser = userDb.toBuilder()
                .startWorkingTime(requestDTO.startWorkingTime())
                .endWorkingTime(requestDTO.endWorkingTime())
                .startLunchTime(requestDTO.startLunchTime())
                .endLunchTime(requestDTO.endLunchTime())
                .timeBetweenMeetings(requestDTO.timeBetweenMeetings().orElse(userDb.getTimeBetweenMeetings()))
                .build();
        return conversionService.convert(userRepository.update(updatedUser), UserDTO.class);
    }

    @Override
    public Calendar getCalendar(String email) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var calendarService = initializeCalendarService(httpTransport);
        var calendarName = getUser(email).orElseThrow().calendarName();

        var calendar = calendarService.calendars().get(calendarName).execute();
        System.out.println(calendar);

        return calendar;
    }

    private com.google.api.services.calendar.Calendar initializeCalendarService(NetHttpTransport HTTP_TRANSPORT) throws IOException {
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                authorizationCodeFlowFactory.getAuthorizationCodeFlow().loadCredential("danixe.ferna@gmail.com"))
                .setApplicationName(googleConfiguration.getApplicationName())
                .build();
    }

}
