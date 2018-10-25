package com.prestamos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Calculos {
	
	static String[] strDays = new String[] { "Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado" };
	/**
	 * metodo que optine la fecha de inicio de las consultas para los cuadres y
	 * los reportes dependiendo de del parametro fecha combinada hace la
	 * gusqueda un dia antes
	 * 
	 * @return retorna fecha de inicio de busqueda
	 * @throws ParseException
	 */
	public static Date fechaInicial(Date hoy) throws ParseException {
		//

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hoy);
		String fhoyIni = df.format(hoy);
		hoy = df.parse(fhoyIni);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		hoy = calendar.getTime();
		return hoy;
	}

	public static Date fechaFinal(Date fin) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fhoyFin = df.format(fin);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fin);
		fin = df.parse(fhoyFin);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		fin = calendar.getTime();
		return fin;
	}

	/**
	 * Metodo que calcula la fecha final de pago de un credito dependiendo de la
	 * fecha de inicio y el numero de cuotas, el parametro sumOrLess indica si se suman o restan los dias 
	 * 
	 * @param fechaInicio2
	 * @param numeroCuota
	 * @param sumOrLess ==1 suma 2==resta
	 * @return
	 */
	public static Date diasSindomingo(Date fechaInicio2, int numeroCuota, int sumOrLess) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio2);
		
		while (numeroCuota != 0) {
			int diaSemana = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			calendar.add(Calendar.DAY_OF_YEAR, sumOrLess==1?1:-1);
			numeroCuota--;
			if (strDays[diaSemana].equals("Domingo")) {
				numeroCuota++;
			}
		}
		return calendar.getTime();
	}

	public static Integer cuotasEntreFechas(Date fechaInicio, Date fin) {
		int numeroCuota=0;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicio);
		while (calendar.getTime().before(fin)) {
			int diaSemana = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			numeroCuota++;
			if (strDays[diaSemana].equals("Domingo")) {
				numeroCuota--;
			}
		}
		return numeroCuota;
	}
	
	/**
	 * metodo encargado de cortar la descripcion para las facturas e informes
	 * 
	 * @param nombre
	 * @param maxTamañoNombre
	 * @return
	 */
	public static String cortarDescripcion(String nombre, int maxTamañoNombre) {
		int tamañoNombre = 0;
		nombre = nombre == null ? " " : nombre;
		try {
			nombre = nombre.trim().substring(0, maxTamañoNombre);
		} catch (Exception e2) {
			nombre = nombre.trim();
			tamañoNombre = nombre.length();
		}
		if (tamañoNombre != 0) {
			for (int j = tamañoNombre; j < maxTamañoNombre; j++) {
				nombre += " ";
			}
		}
		return nombre;
	}
	
	public static String cortarCantidades(String cantidad, int maxTamanoUnit) {
		String unit = "";
		int tamanoUnit = 0;
		unit = cantidad == null ? "0" : "" + cantidad;
		if (unit.endsWith(".0")) {
			unit = unit.substring(0, unit.length() - 2);
		}
		try {
			unit = unit.substring(0, maxTamanoUnit);
		} catch (Exception e2) {
			tamanoUnit = unit.length();
		}
		if (tamanoUnit != 0) {
			for (int j = tamanoUnit; j < maxTamanoUnit; j++) {
				unit = " " + unit;
			}
		}
		return unit;
	}

}
