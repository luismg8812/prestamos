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
import com.prestamos.model.Cobrador;
import com.prestamos.vo.CuadreCajaVo;
import com.prestamos.vo.RecaudoVo;

public class Impresion {

	private static Logger log = Logger.getLogger(Impresion.class);
	private static final String LINEA = "-------------------------------------------------";

	public static void imprimirCuadreCaja(CuadreCajaVo cu) throws FileNotFoundException, DocumentException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String pdf = "C:\\prestamos\\cuadreCaja\\cuadre_" + df.format(new Date())
		+ cu.getCobradorId().getNombre() + ".pdf";
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
		String totalIngreso =Calculos.cortarCantidades(formatea.format(cu.getTotalRecaudo()), 15);
		String efectivo =Calculos.cortarCantidades(formatea.format(cu.getEfectivo()), 15);
		String prestado =Calculos.cortarCantidades(formatea.format(cu.getPrestado()), 15);
		String entregado =Calculos.cortarCantidades(formatea.format(cu.getTotalEntregado()), 15);
		documento.open();
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Cuadre de caja" + fechaRecaudo, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, "Fecha recaudo: " + fechaRecaudo, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, "Cobrador: " + cobrador, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Firma Cobrador:__________________" , FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Total ingresos: "+totalIngreso , FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Efectivo: "+efectivo , FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Prestado: "+prestado , FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.add(new Paragraph(new Phrase(lineSpacing, "Entregado: "+entregado , FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		documento.close();
		printer( pdf);
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
		fntSize = 9f;
		lineSpacing = 10f;
		PdfWriter.getInstance(documento, archivo);
		documento.setMargins(10, 1, 1, 1);
		String fechaRecaudo = df2.format(new Date());
		String cobrador = recaudos.get(0).getRecaudoId().getCreditoId().getCobradorId().getNombre();
		String hederCliente = Calculos.cortarDescripcion("CLIENTE", 25);
		String hederDireccion = Calculos.cortarDescripcion("DIRECCIÓN", 20);
		String hederTelefono = Calculos.cortarDescripcion("TELEFONO", 15);
		String hederVcuota = Calculos.cortarDescripcion("VALOR CUOTA", 15);
		String hederVRecaudado = Calculos.cortarDescripcion("VALOR RECUDADO", 15);
		String header = hederCliente +"|"+hederDireccion +"|"+ hederTelefono +"|"+ hederVcuota +"|"+  hederVRecaudado;
		documento.open();
		documento.add(new Paragraph(new Phrase(lineSpacing, LINEA))); // LINEA
		documento.add(new Paragraph(new Phrase(lineSpacing, "Fecha recaudo: " + fechaRecaudo, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo
		documento.add(new Paragraph(new Phrase(lineSpacing, "Cobrador: " + cobrador, FontFactory.getFont(FontFactory.COURIER_BOLD, 13f)))); // fecha recaudo	
		documento.add(new Paragraph(new Phrase(lineSpacing, "" + header, FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		for(RecaudoVo r: recaudos){
			String cliente = Calculos.cortarDescripcion(r.getRecaudoId().getCreditoId().getClienteId().getNombre(), 25);
			String direccion = Calculos.cortarDescripcion(r.getRecaudoId().getCreditoId().getClienteId().getDireccion(), 20);
			String celular = Calculos.cortarDescripcion(r.getRecaudoId().getCreditoId().getClienteId().getCelular().toString(), 15); 
			String valorCuota = Calculos.cortarCantidades(formatea.format(r.getRecaudoId().getCreditoId().getValorCuota()), 15);
			String recaudado = Calculos.cortarDescripcion("", 15);
			documento.add(new Paragraph(new Phrase(lineSpacing, "" + cliente+"|"+direccion+"|"+celular+"|"+valorCuota+"|"+recaudado, FontFactory.getFont(FontFactory.COURIER_BOLD, fntSize)))); // encabezado
		}
		documento.close();
		printer( pdf);
	}
	
	public static void printer( String rutaArchivo) {
		PrinterJob job = PrinterJob.getPrinterJob();
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		log.info("Number of printers configured1: " + printServices.length);
		
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(rutaArchivo));
			job.setPageable(new PDFPageable(document));
			try {
				job.print();
			} catch (PrinterException e) {
				log.error("error imprimiendo: "+e.getMessage());
			}
			document.close();
		} catch (IOException e) {
			log.error("error imprimiendo: "+e.getMessage());
		}
	}
}
