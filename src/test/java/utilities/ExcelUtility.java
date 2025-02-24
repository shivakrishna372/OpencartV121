package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
    private FileInputStream fis;
    private Workbook workbook;

    // Constructor to load the Excel file
    public ExcelUtility(String filePath) {
        try {
            fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Excel file: " + e.getMessage());
        }
    }

    // Check if a sheet exists
    public boolean isSheetExists(String sheetName) {
        return workbook.getSheet(sheetName) != null;
    }

    // Get row count
    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return 0;
        return sheet.getPhysicalNumberOfRows();
    }

    // Get cell count in a row
    public int getCellCount(String sheetName, int rowNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null || sheet.getRow(rowNum) == null) return 0;
        return sheet.getRow(rowNum).getLastCellNum();
    }

    // Get cell data
    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null || sheet.getRow(rowNum) == null) return "";
        Cell cell = sheet.getRow(rowNum).getCell(colNum);
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    // Close workbook
    public void close() {
        try {
            if (workbook != null) {
                workbook.close();
            }
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Excel file: " + e.getMessage());
        }
    }
}
