package main.java;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class NamesParser {

    static File testPackages;
    static List<File> fileList;
    static Path tempPath = Paths.get("D:\\EQAT\\Learnosity\\temp.txt");

    static final String tag = "uni:package-title>";

    public static void main(String[] args) throws IOException
    {
        testPackages = new File("D:\\EQAT\\Learnosity\\web wizard test cases");
        fileList = Arrays.asList(testPackages.listFiles());

        for (File file: fileList)
        {
            System.out.println(getTitleFromZipFile(file));
        }
    }

    void createExcelFile(List<File> list) throws IOException {
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("first");

        for (File file: list)
        {
            Row row = sheet.createRow(0);
            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(file.getName());
            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(getTitleFromZipFile(file));
        }
    }

    static String getTitleFromZipFile(File zip) throws IOException
    {
        String title = "-default-";
        File tempFile = new File(tempPath.toString());

        ZipFile zFile = new ZipFile(zip);
        ZipEntry zEntry = zFile.getEntry("imsmanifest.xml");

        InputStream is = zFile.getInputStream(zEntry);
        Files.copy(is, tempPath, StandardCopyOption.REPLACE_EXISTING);
        String content = Files.newBufferedReader(tempPath).toString();

        is.close();

        if (content.contains(tag))
        {
            title = content.substring(content.indexOf(tag), content.lastIndexOf(tag));
            System.out.println(title);
        }
        else {
            title = "aaa";
        }

        return title;
    }
}
