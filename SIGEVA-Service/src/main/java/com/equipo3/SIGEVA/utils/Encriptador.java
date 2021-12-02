package com.equipo3.SIGEVA.utils;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

/**
 * Clase destinada a la creación de un encriptador para encriptar la información
 * sensibles
 * 
 * @author Equipo3
 *
 */
@Component
public class Encriptador {
	private static final String KEY = "2189474sdGHSGDA223hdf";
	AES256TextEncryptor encrypter;

	
	public Encriptador() {
		encrypter = new AES256TextEncryptor();
		encrypter.setPassword(KEY);
	}

	public String encriptar(String cadena) {
		return encrypter.encrypt(cadena);
	}

	public String desencriptar(String cadena) {
		try {
			return encrypter.decrypt(cadena);
		} catch (Exception e) {
			return null;
		}

	}
}
