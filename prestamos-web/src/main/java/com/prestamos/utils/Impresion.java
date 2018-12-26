package com.prestamos.utils;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.jboss.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.prestamos.vo.CuadreCajaVo;
import com.prestamos.vo.RecaudoVo;

public class Impresion {

	private static Logger log = Logger.getLogger(Impresion.class);
	private static final String LINEA = "-------------------------------------------------";
	private static final String LINEA1 = "______________________________________________________________________________________";

	public static void imprimirCuadreCaja(CuadreCajaVo cu) throws FileNotFoundException, DocumentException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pdf = "C:\\prestamos\\cuadreCaja\\cuadre_" + df.format(new Date()) + cu.getCobradorId().getNombre()
				+ ".pdf";
		FileOutputStream archivo = new FileOutputStream(pdf);
		DecimalFormat formatea = new DecimalFormat("###,###.##");
		Document documento = new Document();
		float fntSize;
		float lineSpacing;
		fntSize = 9f;
		lineSpacing = 10f;
		PdfWriter.getInstance(documento, archivo);
		documento.setMargins(10, 1, 1, 1);
		String fechaRecaudo = df2.format(new Date());
		String cobrador = cu.getCobradorId().getNombre();
		String totalIngreso = Calculos.cortarCantidades(formatea.format(cu.getTotalRecaudo()), 15);
		String efectivo = Calculos.cortarCantidades(formatea.format(cu.getEfectivo()), 15);
		String prestado = Calculos.cortarCantidades(formatea.format(cu.getPrestado()), 15);
		String entregado = Calculos.cortarCantidades(formatea.format(cu.getTotalEntregado()), 15);
		documento.open();
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Cuadre de caja" + fechaRecaudo,
				FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, "Fecha recaudo: " + fechaRecaudo,
				FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(
				new Phrase(lineSpacing, "Cobrador: " + cobrador, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha
																														// recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Firma Cobrador:__________________",
				FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Total ingresos: " + totalIngreso,
				FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Efectivo: " + efectivo,
				FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Prestado: " + prestado,
				FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Entregado: " + entregado,
				FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.close();
		printer(pdf);
	}

	public static void imprimirRecaudosDia(List<RecaudoVo> recaudos) throws FileNotFoundException, DocumentException {

		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pdf = "C:\\prestamos\\recaudos\\recaudo_" + df.format(new Date())
				+ recaudos.get(0).getRecaudoId().getCreditoId().getCobradorId().getNombre() + ".pdf";
		FileOutputStream archivo = new FileOutputStream(pdf);
		DecimalFormat formatea = new DecimalFormat("###,###.##");
		Document documento = new Document();
		float fntSize;
		float lineSpacing;
		fntSize = 8f;
		lineSpacing = 10f;
		PdfWriter.getInstance(documento, archivo);
		documento.setMargins(10, 1, 1, 1);
		String fechaRecaudo = df2.format(new Date());
		String cobrador = recaudos.get(0).getRecaudoId().getCreditoId().getCobradorId().getNombre();
		String hederCliente = Calculos.cortarDescripcion("CLIENTE", 17);
		String hederValorCredito = Calculos.cortarDescripcion("VALOR CREDITO", 10);
		String hederValorCuota = Calculos.cortarDescripcion("VALOR CUOTA", 10);
		String hederCuotas = Calculos.cortarDescripcion("CUOTA", 3);
		String hederAbono = Calculos.cortarDescripcion("Abono", 10);
		String hederEspacio = Calculos.cortarDescripcion("Espacio", 4);

		String header = hederCliente + "|" + hederValorCredito + "|" + hederValorCuota + "|" + hederCuotas + "|"
				+ hederAbono+"|" +hederEspacio+ "||" + hederCliente + "|" + hederValorCredito + "|" + hederValorCuota + "|" + hederCuotas
				+ "|" + hederAbono+"|"+hederEspacio;
		documento.open();
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Fecha recaudo: " + fechaRecaudo,
				FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(
				new Phrase(lineSpacing, "Cobrador: " + cobrador, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, "" + header, FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA1))); // LINEA
		for (int i = 0; i < recaudos.size() - 1; i++) {
			String cliente = Calculos
					.cortarDescripcion(recaudos.get(i).getRecaudoId().getCreditoId().getClienteId().getNombre(), 17);
			String valorCredito = Calculos.cortarCantidades(
					formatea.format(recaudos.get(i).getRecaudoId().getCreditoId().getTotalPrestamo()), 10);
			String cuotas = Calculos
					.cortarCantidades("" + recaudos.get(i).getRecaudoId().getCreditoId().getNumeroCuotas(), 3);
			String valorCuota = Calculos.cortarCantidades(
					formatea.format(recaudos.get(i).getRecaudoId().getCreditoId().getValorCuota()), 10);
			Double abonoT= recaudos.get(i).getRecaudoId().getCreditoId().getAbonoTotal()==null?0.0:recaudos.get(i).getRecaudoId().getCreditoId().getAbonoTotal();
			String abono = Calculos.cortarCantidades(formatea.format(abonoT), 10);
			String espacio="    ";
			
			String cliente1 = Calculos.cortarDescripcion(recaudos.get(i + 1).getRecaudoId().getCreditoId().getClienteId().getNombre(), 17);
			String valorCredito1 = Calculos.cortarCantidades(formatea.format(recaudos.get(i + 1).getRecaudoId().getCreditoId().getTotalPrestamo()), 10);
			String cuotas1 = Calculos
					.cortarCantidades("" + recaudos.get(i + 1).getRecaudoId().getCreditoId().getNumeroCuotas(), 3);
			String valorCuota1 = Calculos.cortarCantidades(
					formatea.format(recaudos.get(i + 1).getRecaudoId().getCreditoId().getValorCuota()), 10);
			abonoT= recaudos.get(i+1).getRecaudoId().getCreditoId().getAbonoTotal()==null?0.0:recaudos.get(i+1).getRecaudoId().getCreditoId().getAbonoTotal();
			String abono1 = Calculos.cortarCantidades(formatea.format(abonoT), 10);

			// documento.add(new Paragraph(new Phrase(lineSpacing, LINEA_))); // LINEA
			documento.add(new Paragraph(new Phrase(lineSpacing,
					"" + cliente + "|" + valorCredito + "|" + valorCuota + "|" + cuotas + "|" + abono +"|"+espacio+ "||" + cliente1
							+ "|" + valorCredito1 + "|" + valorCuota1 + "|" + cuotas1 + "|" + abono1+"|"+espacio,
					FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
			documento.add(new Paragraph(new Phrase(lineSpacing, LINEA1))); // LINEA
		}
		documento.newPage();
	        // we don't add anything to this page: newPage() will be ignored

		documento.newPage();
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA1))); // LINEA
		for(int i=0; i<10; i++) {
			documento.add(new Paragraph(new Phrase(lineSpacing, "|"+LINEA1+"|"))); // LINEA
		}
		
		documento.close();
		printer(pdf);
	}

	public static void printer(String rutaArchivo) {
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		log.info("Number of printers configured1: " + printServices.length);

		PDDocument document = null;
		try {
			document = PDDocument.load(new File(rutaArchivo));
			job.setPageable(new PDFPageable(document));
			job.print();
			document.close();
		} catch (IOException | PrinterException e) {
			log.error("error imprimiendo: " + e.getMessage());
		}
	}
}
