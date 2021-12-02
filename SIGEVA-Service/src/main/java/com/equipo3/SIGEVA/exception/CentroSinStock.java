package com.equipo3.SIGEVA.exception;

public class CentroSinStock extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6384881952086181657L;

	public CentroSinStock(String mensaje) {
		super(mensaje);
	}

}