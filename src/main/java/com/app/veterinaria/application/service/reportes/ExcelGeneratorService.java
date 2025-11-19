package com.app.veterinaria.application.service.reportes;

import com.app.veterinaria.infrastructure.web.dto.details.export.DuenoExportDTO;
import com.app.veterinaria.infrastructure.web.dto.details.export.MascotaExportDTO;
import com.app.veterinaria.infrastructure.web.dto.details.export.VacunaExportDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelGeneratorService {

    private final ExcelStyleFactoryService styleFactory;

    private static final String SHEET_NAME = "Registro de Vacunación";
    private static final String TITLE = "REGISTRO DE VACUNACIÓN - VETERINARIA";

    public Mono<byte[]> generateProfessionalExcel(List<DuenoExportDTO> duenos) {
        return Mono.fromCallable(() -> createExcelFile(duenos))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private byte[] createExcelFile(List<DuenoExportDTO> duenos) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet(SHEET_NAME);
            ExcelStyles styles = loadStyles(workbook);

            int rowNum = 0;
            rowNum = createTitle(sheet, styles, rowNum);
            rowNum = createContent(sheet, styles, duenos, rowNum);

            adjustColumnWidths(sheet);

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Error generando Excel profesional", e);
        }
    }

    private ExcelStyles loadStyles(Workbook workbook) {
        return new ExcelStyles(
                styleFactory.createTitleStyle(workbook),
                styleFactory.createDuenoHeaderStyle(workbook),
                styleFactory.createMascotaHeaderStyle(workbook),
                styleFactory.createVacunaHeaderStyle(workbook),
                styleFactory.createLabelStyle(workbook),
                styleFactory.createDataStyle(workbook),
                styleFactory.createCenteredDataStyle(workbook),
                styleFactory.createDateStyle(workbook),
                styleFactory.createVacunaRowEvenStyle(workbook),
                styleFactory.createVacunaRowOddStyle(workbook),
                styleFactory.createVacunaDateEvenStyle(workbook),
                styleFactory.createVacunaDateOddStyle(workbook),
                styleFactory.createNoDataStyle(workbook)
        );
    }

    private int createTitle(Sheet sheet, ExcelStyles styles, int rowNum) {
        Row titleRow = sheet.createRow(rowNum++);
        titleRow.setHeight((short) 700);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(TITLE);
        titleCell.setCellStyle(styles.title);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 5));

        return rowNum + 1; // Línea en blanco
    }

    private int createContent(Sheet sheet, ExcelStyles styles,
                              List<DuenoExportDTO> duenos, int rowNum) {
        for (DuenoExportDTO dueno : duenos) {
            rowNum = createDuenoSection(sheet, styles, dueno, rowNum);
            rowNum += 2; // Espacio entre dueños
        }
        return rowNum;
    }

    private int createDuenoSection(Sheet sheet, ExcelStyles styles,
                                   DuenoExportDTO dueno, int rowNum) {
        rowNum = createDuenoHeader(sheet, styles, rowNum);
        rowNum = createDuenoData(sheet, styles, dueno, rowNum);
        rowNum++; // Espacio

        for (MascotaExportDTO mascota : dueno.getMascotas()) {
            rowNum = createMascotaSection(sheet, styles, mascota, rowNum);
            rowNum++; // Espacio entre mascotas
        }

        return rowNum;
    }

    private int createDuenoHeader(Sheet sheet, ExcelStyles styles, int rowNum) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.setHeight((short) 400);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("DUEÑO");
        headerCell.setCellStyle(styles.duenoHeader);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 5));
        return rowNum;
    }

    private int createDuenoData(Sheet sheet, ExcelStyles styles,
                                DuenoExportDTO dueno, int rowNum) {
        // Primera fila: Nombre, DNI, Teléfono
        Row dataRow1 = sheet.createRow(rowNum++);
        dataRow1.setHeight((short) 350);

        createLabelCell(dataRow1, 0, "Nombre:", styles.label);
        createDataCell(dataRow1, 1, dueno.getNombre(), styles.data);

        createLabelCell(dataRow1, 2, "DNI:", styles.label);
        createDataCell(dataRow1, 3, dueno.getDni(), styles.data);

        createLabelCell(dataRow1, 4, "Teléfono:", styles.label);
        createDataCell(dataRow1, 5,
                dueno.getTelefono() != null ? dueno.getTelefono() : "N/A",
                styles.data);

        // Segunda fila: Dirección
        Row dataRow2 = sheet.createRow(rowNum++);
        dataRow2.setHeight((short) 350);

        createLabelCell(dataRow2, 0, "Dirección:", styles.label);
        createDataCell(dataRow2, 1, dueno.getDireccion(), styles.data);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 5));

        return rowNum;
    }

    private int createMascotaSection(Sheet sheet, ExcelStyles styles,
                                     MascotaExportDTO mascota, int rowNum) {
        rowNum = createMascotaHeader(sheet, styles, mascota, rowNum);
        rowNum = createMascotaData(sheet, styles, mascota, rowNum);
        rowNum++; // Espacio
        rowNum = createVacunasTable(sheet, styles, mascota.getVacunas(), rowNum);

        return rowNum;
    }

    private int createMascotaHeader(Sheet sheet, ExcelStyles styles,
                                    MascotaExportDTO mascota, int rowNum) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.setHeight((short) 380);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("MASCOTA: " + mascota.getNombre().toUpperCase());
        headerCell.setCellStyle(styles.mascotaHeader);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 0, 5));
        return rowNum;
    }

    private int createMascotaData(Sheet sheet, ExcelStyles styles,
                                  MascotaExportDTO mascota, int rowNum) {
        // Primera fila: Especie, Raza
        Row dataRow1 = sheet.createRow(rowNum++);
        dataRow1.setHeight((short) 330);

        createLabelCell(dataRow1, 0, "Especie:", styles.label);
        createDataCell(dataRow1, 1, mascota.getEspecie(), styles.data);

        createLabelCell(dataRow1, 2, "Raza:", styles.label);
        createDataCell(dataRow1, 3, mascota.getRaza(), styles.data);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 3, 5));

        // Segunda fila: Color, Sexo, Edad
        Row dataRow2 = sheet.createRow(rowNum++);
        dataRow2.setHeight((short) 330);

        createLabelCell(dataRow2, 0, "Color:", styles.label);
        createDataCell(dataRow2, 1, mascota.getColor(), styles.data);

        createLabelCell(dataRow2, 2, "Sexo:", styles.label);
        createDataCell(dataRow2, 3, mascota.getSexo(), styles.centeredData);

        createLabelCell(dataRow2, 4, "Edad:", styles.label);
        createDataCell(dataRow2, 5, mascota.getEdad(), styles.data);

        return rowNum;
    }

    private int createVacunasTable(Sheet sheet, ExcelStyles styles,
                                   List<VacunaExportDTO> vacunas, int rowNum) {
        if (vacunas.isEmpty()) {
            return createNoVacunasMessage(sheet, styles, rowNum);
        }

        rowNum = createVacunasHeader(sheet, styles, rowNum);
        rowNum = createVacunasData(sheet, styles, vacunas, rowNum);

        return rowNum;
    }

    private int createVacunasHeader(Sheet sheet, ExcelStyles styles, int rowNum) {
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.setHeight((short) 400);

        Cell tipoHeader = headerRow.createCell(1);
        tipoHeader.setCellValue("Tipo de Vacuna");
        tipoHeader.setCellStyle(styles.vacunaHeader);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 3));

        Cell fechaHeader = headerRow.createCell(4);
        fechaHeader.setCellValue("Fecha de Aplicación");
        fechaHeader.setCellStyle(styles.vacunaHeader);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 5));

        return rowNum;
    }

    private int createVacunasData(Sheet sheet, ExcelStyles styles,
                                  List<VacunaExportDTO> vacunas, int rowNum) {
        int index = 0;
        for (VacunaExportDTO vacuna : vacunas) {
            boolean isEven = index % 2 == 0;
            Row vacunaRow = sheet.createRow(rowNum++);
            vacunaRow.setHeight((short) 350);

            // Tipo de vacuna (columnas 1-3)
            Cell tipoCell = vacunaRow.createCell(1);
            tipoCell.setCellValue(vacuna.getTipo());
            tipoCell.setCellStyle(isEven ? styles.vacunaRowEven : styles.vacunaRowOdd);
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 3));

            // Fecha de aplicación (columnas 4-5)
            Cell fechaCell = vacunaRow.createCell(4);
            if (vacuna.getFechaAplicacion() != null) {
                fechaCell.setCellValue(vacuna.getFechaAplicacion().toString());
                fechaCell.setCellStyle(isEven ? styles.vacunaDateEven : styles.vacunaDateOdd);
            } else {
                fechaCell.setCellValue("N/A");
                fechaCell.setCellStyle(isEven ? styles.vacunaRowEven : styles.vacunaRowOdd);
            }
            sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 4, 5));

            index++;
        }
        return rowNum;
    }

    private int createNoVacunasMessage(Sheet sheet, ExcelStyles styles, int rowNum) {
        Row noVacunasRow = sheet.createRow(rowNum++);
        noVacunasRow.setHeight((short) 400);
        Cell noVacCell = noVacunasRow.createCell(1);
        noVacCell.setCellValue("⚠ Sin vacunas registradas");
        noVacCell.setCellStyle(styles.noData);
        sheet.addMergedRegion(new CellRangeAddress(rowNum - 1, rowNum - 1, 1, 5));
        return rowNum;
    }

    private void createLabelCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void createDataCell(Row row, int column, String value, CellStyle style) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "N/A");
        cell.setCellStyle(style);
    }

    private void adjustColumnWidths(Sheet sheet) {
        sheet.setColumnWidth(0, 4000);  // Labels/espacio
        sheet.setColumnWidth(1, 6000);  // Datos principales
        sheet.setColumnWidth(2, 4000);  // Labels
        sheet.setColumnWidth(3, 5000);  // Datos
        sheet.setColumnWidth(4, 4000);  // Labels/Fecha
        sheet.setColumnWidth(5, 4500);  // Datos/Fecha
    }

    private record ExcelStyles(
            CellStyle title,
            CellStyle duenoHeader,
            CellStyle mascotaHeader,
            CellStyle vacunaHeader,
            CellStyle label,
            CellStyle data,
            CellStyle centeredData,
            CellStyle date,
            CellStyle vacunaRowEven,
            CellStyle vacunaRowOdd,
            CellStyle vacunaDateEven,
            CellStyle vacunaDateOdd,
            CellStyle noData
    ) {}
}