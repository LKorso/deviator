package com.sdr.repository;

import com.sdr.domain.Distribution;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class FileDistributionRepository{

    public void save(Distribution distribution, File file) {
        try (
                FileOutputStream excelFile = new FileOutputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook()
        ) {
            XSSFSheet sheet = workbook.createSheet();
            int rowNumber = 0;
            for (Double value : distribution.getValues()) {
                Row row = sheet.createRow(rowNumber++);
                Cell cell = row.createCell(0);
                cell.setCellValue(value);
            }
            workbook.write(excelFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Distribution readFromFile(File file) {
        Distribution distribution = new Distribution(readFromExcel(file));
        return distribution;
    }

    private List<Double> readFromExcel(File file) {
        List<Double> values = new ArrayList<>();
        try (
                FileInputStream excelFile = new FileInputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook(excelFile)
        ) {
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = dataTypeSheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    values.add(currentCell.getNumericCellValue());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }
}