package com.gunnarro.dietmanager.mvc.dto;

import java.util.ArrayList;
import java.util.List;

import com.gunnarro.dietmanager.domain.log.LogEntry;
import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Role;

public class DtoObjectMapper {

    public static List<UserDto> mapUsersToUserDtos(List<LocalUser> users) {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        for (LocalUser user : users) {
            userDtos.add(mapUserToUserDto(user));
        }
        return userDtos;
    }

    public static UserDto mapUserToUserDto(LocalUser user) {
        if (user != null) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            List<String> roles = new ArrayList<>();
            for (Role r : user.getRoles()) {
                roles.add(r.getName());
            }
            userDto.setRoles(roles);
            // do not add encrypted password
            userDto.setPassword("");
            userDto.setPasswordRepeat("");
            userDto.setSocialProvider(user.getSocialProvider());
            userDto.setCreatedDate(user.getCreatedDate());
            userDto.setLastModifiedDate(user.getLastModifiedDate());
            userDto.setActivated(user.isActivated());
            return userDto;
        }
        return null;
    }

    public static LocalUser mapUserDtoToUser(UserDto userDto) {
        if (userDto != null) {
            LocalUser user = new LocalUser();
            user.setId(userDto.getId());
            user.setUsername(userDto.getUsername());
            user.setSocialProvider(userDto.getSocialProvider());
            user.setPassword(userDto.getPassword());
            user.setPasswordRepeat(userDto.getPasswordRepeat());
            user.setEmail(userDto.getEmail());
            user.setActivated(userDto.isActivated());
            // user.setRoles(userDto.getRoles());
            return user;
        }
        return null;
    }

    public static List<LogEntryDto> mapLogEntryToLogEntryDtos(List<LogEntry> LogEntries) {
        List<LogEntryDto> userDtos = new ArrayList<LogEntryDto>();
        for (LogEntry logEntry : LogEntries) {
            userDtos.add(mapLogEntryToLogeEntryDto(logEntry));
        }
        return userDtos;
    }

    public static LogEntryDto mapLogEntryToLogeEntryDto(LogEntry logEntry) {
        if (logEntry != null) {
            LogEntryDto logEntryDto = new LogEntryDto();
            logEntryDto.setId(logEntry.getId());
            logEntryDto.setFkUserId(logEntry.getFkUserId());
            logEntryDto.setContent(logEntry.getContent());
            logEntryDto.setCreatedByUser(logEntry.getCreatedByUser());
            logEntryDto.setTitle(logEntry.getTitle());
            logEntryDto.setCreatedTime(logEntry.getCreatedTime());
            logEntryDto.setLastModifiedTime(logEntry.getLastModifiedTime());
            logEntryDto.setLevel(logEntry.getLevel());
            return logEntryDto;
        }
        return null;
    }

    public static LogEntry mapLogEntryDtoToLogeEntry(LogEntryDto logEntryDto) {
        if (logEntryDto != null) {
            LogEntry logEntry = new LogEntry();
            logEntry.setId(logEntryDto.getId());
            logEntry.setFkUserId(logEntryDto.getFkUserId());
            logEntry.setContent(logEntryDto.getContent());
            logEntry.setCreatedByUser(logEntryDto.getCreatedByUser());
            logEntry.setTitle(logEntryDto.getTitle());
            logEntry.setCreatedTime(logEntryDto.getCreatedTime());
            logEntry.setLastModifiedTime(logEntryDto.getLastModifiedTime());
            logEntry.setLevel(logEntryDto.getLevel());
            return logEntry;
        }
        return null;
    }

}
