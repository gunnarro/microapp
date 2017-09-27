package com.gunnarro.sportsteam.mvc.dto.party;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gunnarro.sportsteam.mvc.dto.UserDto;

public class UserDtoTest {

    @Test
    public void validInput() {
        UserDto user = new UserDto("username", "pWd", "pWd", "myemail@sportsteam.no");
        assertEquals("username", user.getUserName());
        assertEquals("pWd", user.getPassword());
        assertEquals("pWd", user.getPasswordRepeat());
        assertEquals("myemail@sportsteam.no", user.getEmail());
    }
}
