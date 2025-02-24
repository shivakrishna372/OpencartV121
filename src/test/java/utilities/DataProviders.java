package utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataProviders {

    // Method to read login data from Excel
    public Object[][] getLoginData() {
        // Define the path to your Excel file (make sure this path is correct)
        String filePath = "C:/Users/91701/Downloads/shiva.xlsx";  // Update path as required
        Object[][] loginData = null;

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            // Open the workbook
            Workbook workbook = new XSSFWorkbook(fis);
            
            // Debugging: Check if the sheet exists
            Sheet sheet = workbook.getSheet("shiva");  // Corrected sheet name to "shiva"
            
            if (sheet == null) {
                System.out.println("Sheet 'shiva' not found!");
                return new Object[][]{};  // Return an empty array if the sheet is not found
            }
            
            // Debugging: Check sheet name
            System.out.println("Found sheet: " + sheet.getSheetName());

            // Determine the number of rows and columns in the sheet
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            
            loginData = new Object[rowCount - 1][colCount]; // Assuming first row is header

            // Read the data from the sheet
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    loginData[i - 1][j] = row.getCell(j).toString();
                }
            }

            workbook.close();  // Close the workbook

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loginData;
    }
}
