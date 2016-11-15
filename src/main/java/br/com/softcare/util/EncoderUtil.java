package br.com.softcare.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class EncoderUtil {

	public static String hash(String value){
		return new ShaPasswordEncoder().encodePassword(value,null);
	}
	
}
