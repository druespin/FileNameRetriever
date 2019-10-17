package main.java;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class NamesParser {

    static File testPackages = new File("D:\\EQAT\\Learnosity\\web wizard test cases");
    static List<String> fileList = Arrays.asList(testPackages.list());

    public static void main(String[] args) throws IOException
    {
        // FileOutputStream excel = new FileOutputStream("path");

        for (String file:
             fileList) {
            getTitleFromZipFile(file);
        }
        System.out.println("done");
    }

    void createExcelFile(List<String> list) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("first");

        for (String fileName: list)
        {
            Row row = sheet.createRow(0);
            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(fileName);
            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(getTitleFromZipFile(fileName));
        }
    }

    static String getTitleFromZipFile(String zipFile) throws IOException
    {
        String title = "===";
        String tag = "uni:package-title>";

        FileInputStream fileBytes;
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
        ZipEntry zipEntry;

        while ((zipEntry = zin.getNextEntry()) != null)
        {
            if (zipEntry.getName().contains("manifest"))
            {
                fileBytes = new FileInputStream(zipEntry.getName());
                fileBytes.read(new byte[] {});
                String content = fileBytes.toString();
                fileBytes.close();

                if (content.contains(tag))
                {
                    title = content.substring(content.indexOf(tag), content.lastIndexOf(tag));
                    System.out.println(title);
                }
            }
        }
        zin.closeEntry();
        return title;
    }
}
