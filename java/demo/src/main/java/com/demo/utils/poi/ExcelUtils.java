package com.demo.utils.poi;

import lombok.extern.java.Log;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Alex
 * @date 2021/10/15 15:31
 */
@Log
public class ExcelUtils {

    public static void downloadTemplate(String fileTitle, String[] headers, HttpServletResponse response) {

        try {

            customerExportData(fileTitle, headers, null, null, response);

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

    }

    /**
     * 自定义导出
     * @param headers
     * @param list
     * @param function 自定义function
     * @param response
     */
    public static void customerExportData(String fileTitle, String[] headers, List list, Function<Object, String[]> function, HttpServletResponse response) {

        try {

            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet("sheet1");

            addTitle(sheet, wb, fileTitle, headers.length);

            addHeaders(sheet, wb, headers);


            if (list == null) {
                writeToResponse(wb, response);
                return;
            }


            for (int i = 1; i <= list.size(); i++) {

                String[] values = function.apply(list.get(i - 1));
                Row dataRow = sheet.createRow(i + 1);

                int columnIndex = 0;
                for (String str : values) {
                    dataRow.createCell(columnIndex++)
                            .setCellValue(str);
                }

            }

            writeToResponse(wb, response);

        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }

    }

    /**
     * 自定义导入
     * @param file
     * @param consumer 自定义消费者
     * @throws Exception
     */
    public static void customerImportHandler(MultipartFile file, Consumer<Row> consumer) throws Exception {

        if (file.isEmpty()) {
            return;
        }

        String fileName = file.getOriginalFilename();

        if (!StringUtils.hasLength(fileName)) {
            return;
        }

        InputStream in = file.getInputStream();

        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            return;
        }

        Sheet sheet = work.getSheetAt(0);
        if(sheet == null) {
            return;
        }

        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getFirstCellNum() == i) {
                continue;
            }

            consumer.accept(row);
        }
        work.close();
    }

    private static Workbook getWorkbook(InputStream in, String fileName) throws Exception {

        String fileType = fileName.substring(fileName.lastIndexOf("."));

        if(".xls".equals(fileType)) {
            return new HSSFWorkbook(in);
        } else if (".xlsx".equals(fileType)) {
            return new XSSFWorkbook(in);
        } else {
            return null;
        }
    }

    private static void writeToResponse(Workbook wb, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setContentType("application/octet-stream;charset=utf-8");

        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    private static void addTitle(Sheet sheet, Workbook workbook, String title, int maxCol) {

        if (maxCol > 1) {
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxCol - 1));
        }

        Cell cell = sheet.createRow(0).createCell(0);

        CellStyle cellstyle = workbook.createCellStyle();
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellstyle.setAlignment(HorizontalAlignment.CENTER);
        cellstyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font=workbook.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setColor(IndexedColors.RED.index);

        cellstyle.setFont(font);

        cell.setCellStyle(cellstyle);

        cell.setCellValue(title);
    }

    private static void addHeaders(Sheet sheet, Workbook workbook, String[] headers) {

        Font font=workbook.createFont();
        font.setFontHeightInPoints((short) 16);
        font.setColor(IndexedColors.WHITE.index);
        font.setFontName("等线");

        CellStyle cellstyle = workbook.createCellStyle();
        cellstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellstyle.setAlignment(HorizontalAlignment.CENTER);
        cellstyle.setFillForegroundColor((short) 48);
        cellstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyle.setFont(font);

        Row row = sheet.createRow(1);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellstyle);

            cell.setCellValue(headers[i]);

            sheet.setColumnWidth(i, 10000);
        }

    }

}
