package com.dfernandezaller.service.imp;

import com.dfernandezaller.authentication.google.AuthorizationCodeFlowFactory;
import com.dfernandezaller.configuration.GoogleConfiguration;
import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.exceptions.BusinessException;
import com.dfernandezaller.repository.UserRepository;
import com.dfernandezaller.service.UserService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import io.micronaut.core.convert.ConversionService;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@Singleton
@Transactional
public class UserServiceImp implements UserService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private final UserRepository userRepository;
    private final ConversionService<?> conversionService;
    private final GoogleConfiguration googleConfiguration;
    private final AuthorizationCodeFlowFactory authorizationCodeFlowFactory;

    public UserServiceImp(UserRepository userRepository, ConversionService<?> conversionService, GoogleConfiguration googleConfiguration, AuthorizationCodeFlowFactory authorizationCodeFlowFactory) {
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
    public Optional<UserDTO> updateUser(String email, UpdateUserDTO requestDTO) {
        var userDb = userRepository.findById(email).orElseThrow();
        var updatedUser = userDb.toBuilder()
                .startWorkingTime(requestDTO.startWorkingTime())
                .endWorkingTime(requestDTO.endWorkingTime())
                .startLunchTime(requestDTO.startLunchTime())
                .endLunchTime(requestDTO.endLunchTime())
                .timeBetweenMeetings(requestDTO.timeBetweenMeetings().orElse(userDb.getTimeBetweenMeetings()))
                .calendarId(requestDTO.calendarId().orElse(userDb.getCalendarId()))
                .daysToAnalyze(requestDTO.daysToAnalyze().orElse(userDb.getDaysToAnalyze()))
                .build();
        return conversionService.convert(userRepository.update(updatedUser), UserDTO.class);
    }

    @Override
    public List<CalendarDTO> getUserCalendars(String name) {
        final var calendarService = initializeCalendarService(name);
        final var calendars = getRawCalendars(calendarService);
        return calendars.getItems().stream()
                .filter(this::isValidCalendarEntry)
                .map(calendarListEntry -> conversionService.convert(calendarListEntry, CalendarDTO.class))
                .map(Optional::orElseThrow)
                .toList();
    }

    private boolean isValidCalendarEntry(CalendarListEntry calendarListEntry) {
        return !(Boolean.TRUE.equals(calendarListEntry.getDeleted()) || Boolean.TRUE.equals(calendarListEntry.getHidden()));
    }

    private com.google.api.services.calendar.Calendar initializeCalendarService(String userId) {
        final NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            return new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY,
                    authorizationCodeFlowFactory.getGoogleAuthorizationCodeFlow().loadCredential(userId))
                    .setApplicationName(googleConfiguration.getApplicationName())
                    .build();
        } catch (IOException e) {
            throw new BusinessException("There has been a problem connecting with calendar provider", e);
        } catch (GeneralSecurityException e) {
            throw new BusinessException("There is no sufficient permissions to retrieve calendar service", e);
        }
    }

    private CalendarList getRawCalendars(com.google.api.services.calendar.Calendar calendarService) {
        try {
            return calendarService.calendarList().list().execute();
        } catch (IOException e) {
            throw new BusinessException("There has been a problem retrieving user calendars", e);
        }
    }

}
