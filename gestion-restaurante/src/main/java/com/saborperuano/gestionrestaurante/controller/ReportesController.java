package com.saborperuano.gestionrestaurante.controller;

import java.awt.Color;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.saborperuano.gestionrestaurante.entity.Inventario;
import com.saborperuano.gestionrestaurante.entity.Plato;
import com.saborperuano.gestionrestaurante.repository.InventarioRepository;
import com.saborperuano.gestionrestaurante.repository.PlatoRepository;

import jakarta.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
public class ReportesController {

    private final InventarioRepository inventarioRepository;
    private final PlatoRepository platoRepository;

    public ReportesController(InventarioRepository inventarioRepository, PlatoRepository platoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.platoRepository = platoRepository;
    }

    // ---------------------- REPORTE PDF INVENTARIO ----------------------
    @GetMapping("/admin/reportes/inventario/pdf")
    public void exportInventarioPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=inventario.pdf");

        List<Inventario> inventarios = inventarioRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Reporte de Inventario", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" ")); // espacio

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        String[] headers = {"ID", "Insumo", "Sucursal", "Stock", "Unidad", "Actualizado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        for (Inventario i : inventarios) {
            table.addCell(String.valueOf(i.getId()));
            table.addCell(i.getInsumo().getNombre());
            table.addCell(i.getSucursal().getNombre());
            table.addCell(i.getStock().toString());
            table.addCell(i.getInsumo().getUnidad());
            table.addCell(i.getActualizado().toString());
        }

        document.add(table);
        document.close();
    }

    // ---------------------- REPORTE EXCEL INVENTARIO ----------------------
    @GetMapping("/admin/reportes/inventario/excel")
    public void exportInventarioExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=inventario.xlsx");

        List<Inventario> inventarios = inventarioRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventario");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Insumo", "Sucursal", "Stock", "Unidad", "Actualizado"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Inventario i : inventarios) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(i.getId());
            row.createCell(1).setCellValue(i.getInsumo().getNombre());
            row.createCell(2).setCellValue(i.getSucursal().getNombre());
            row.createCell(3).setCellValue(i.getStock().toString());
            row.createCell(4).setCellValue(i.getInsumo().getUnidad());
            row.createCell(5).setCellValue(i.getActualizado().toString());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    // ---------------------- REPORTE PDF PLATOS ----------------------
    @GetMapping("/admin/reportes/platos/pdf")
    public void exportPlatosPdf(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=platos.pdf");

        List<Plato> platos = platoRepository.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph titulo = new Paragraph("Reporte de Platos", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        String[] headers = {"ID", "Nombre", "Descripción", "Precio", "Estado"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Paragraph(header, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        for (Plato p : platos) {
            table.addCell(String.valueOf(p.getId()));
            table.addCell(p.getNombre());
            table.addCell(p.getDescripcion() != null ? p.getDescripcion() : "");
            table.addCell(p.getPrecio().toString());
            table.addCell(p.getEstado() ? "Activo" : "Inactivo");
        }

        document.add(table);
        document.close();
    }

    // ---------------------- REPORTE EXCEL PLATOS ----------------------
    @GetMapping("/admin/reportes/platos/excel")
    public void exportPlatosExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=platos.xlsx");

        List<Plato> platos = platoRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Platos");

        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nombre", "Descripción", "Precio", "Estado"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (Plato p : platos) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNombre());
            row.createCell(2).setCellValue(p.getDescripcion() != null ? p.getDescripcion() : "");
            row.createCell(3).setCellValue(p.getPrecio().toString());
            row.createCell(4).setCellValue(p.getEstado() ? "Activo" : "Inactivo");
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
