package com.dfernandezaller.controller;

import com.dfernandezaller.controller.dto.CalendarDTO;
import com.dfernandezaller.controller.dto.UpdateUserDTO;
import com.dfernandezaller.controller.dto.UserDTO;
import com.dfernandezaller.model.TimeAnalysisResults;
import com.dfernandezaller.service.AnalyzeTimeService;
import com.dfernandezaller.service.UserService;
import io.micronaut.security.authentication.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private AnalyzeTimeService analyzeTimeService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser() {
        String username = "test";
        UserDTO userDTO = UserDTO.builder().build();
        when(userService.getUser(username)).thenReturn(Optional.of(userDTO));
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = userController.getUser(auth);

        assertThat(result).isEqualTo(userDTO);
    }

    @Test
    public void testGetUser_userNotFound() {
        String username = "test";
        when(userService.getUser(username)).thenReturn(Optional.empty());
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        assertThrows(RuntimeException.class, () -> userController.getUser(auth));
    }

    @Test
    public void testUpdateUser() {
        String username = "test";
        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder().build();
        UserDTO updatedUserDTO = UserDTO.builder().build();
        when(userService.updateUser(username, updateUserDTO)).thenReturn(Optional.of(updatedUserDTO));
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = userController.updateUser(auth, updateUserDTO);

        assertThat(result).isEqualTo(updatedUserDTO);
    }

    @Test
    public void testUpdateUser_userNotFound() {
        String username = "test";
        UpdateUserDTO updateUserDTO = UpdateUserDTO.builder().build();
        when(userService.updateUser(username, updateUserDTO)).thenReturn(Optional.empty());
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        assertThrows(RuntimeException.class, () -> userController.updateUser(auth, updateUserDTO));
    }

    @Test
    public void testGetUserCalendars() {
        String username = "test";
        List<CalendarDTO> calendars = Collections.singletonList(CalendarDTO.builder().build());
        when(userService.getUserCalendars(username)).thenReturn(calendars);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = userController.getUserCalendars(auth);

        assertThat(result).isEqualTo(calendars);
    }

    @Test
    public void testGetTimeSpentInMeetings() {
        String username = "test";
        TimeAnalysisResults timeAnalysisResults = new TimeAnalysisResults(1.0, 2.0, 3.0, 8.0);
        when(analyzeTimeService.getTimeSpentInMeetings(username)).thenReturn(timeAnalysisResults);
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(username);

        var result = userController.getTimeSpentInMeetings(auth);

        assertThat(result).isEqualTo(timeAnalysisResults);
    }
}
