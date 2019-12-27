package com.gunnarro.followup.utillity;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UtilityTest {
	
	@Test
	public void encodePass() {
		String idForEncode = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		PasswordEncoder bCryptPasswordEncoder =  new DelegatingPasswordEncoder(idForEncode, encoders);
		System.out.println(bCryptPasswordEncoder.encode("guro66"));
	}

}
