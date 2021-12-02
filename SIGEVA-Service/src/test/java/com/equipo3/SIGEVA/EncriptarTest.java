package com.equipo3.SIGEVA;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.equipo3.SIGEVA.utils.Encriptador;

class EncriptarTest {

	static Encriptador encriptador = new Encriptador();
	static String cad = "Test encriptador";
	
	
	@Test
	void encriptar() {
		assertNotEquals(encriptador.encriptar(cad), cad);
	}
	
	@Test
	void desencriptar() {
		String encrip = "";
		encrip = encriptador.encriptar(cad);
		assertEquals(encriptador.desencriptar(encrip), cad);
	}

}
