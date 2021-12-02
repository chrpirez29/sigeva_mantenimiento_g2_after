package com.equipo3.SIGEVA.controller;

import java.util.Date;

/***
 * Clase para la definición de constantes que se usan en el proyecto.
 * 
 * @author Equipo3
 *
 */
public class Condicionamientos {

	private static final boolean CONTROL = true;

	private static final int DIA_FIN = 31;
	private static final int MES_FIN = 1;
	private static final int ANYO_FIN = 2022;

	private static final int TIEMPO_ENTRE_DOSIS = 21; // dias

	private static final boolean BUSCAR_A_PARTIR_DE_MANANA = true;

	private Condicionamientos() {
	}

	public static boolean control() {
		return CONTROL;
	}

	public static int diaFin() {
		return DIA_FIN;
	}

	public static int mesFin() {
		return MES_FIN;
	}

	public static int anyoFin() {
		return ANYO_FIN;
	}

	@SuppressWarnings("deprecation")
	public static Date fechaFin() {
		return new Date(ANYO_FIN - 1900, MES_FIN - 1, DIA_FIN);
	}

	public static int tiempoEntreDosis() {
		return TIEMPO_ENTRE_DOSIS;
	}

	public static boolean buscarAPartirDeManana() {
		return BUSCAR_A_PARTIR_DE_MANANA;
	}
}
