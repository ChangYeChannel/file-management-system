package io.renren.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtils {
    /**
     * 导出数据为excel文件
     *
     * @param filename       文件名称
     * @param dataResult     集合内的bean对象类型要与clazz参数一致
     * @param clazz          集合内的bean对象类型要与clazz参数一致
     * @param response       HttpServlet响应对象
     */
    public static void export(String filename, String sheetName,List<?> dataResult, Class<?> clazz, HttpServletResponse response) {
        response.setStatus(200);
        OutputStream outputStream = null;
        try {
            if (StringUtils.isBlank(filename)) {
                throw new RuntimeException("'filename' 不能为空");
            }
            response.setContentType("application/vnd.ms-excel");//告知浏览器下载文件
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            outputStream = response.getOutputStream();


            EasyExcel.write(response.getOutputStream(), clazz)
                    .registerWriteHandler(getStyleStrategy())
                    // 导出文件名
                    .autoCloseStream(Boolean.TRUE).sheet(sheetName)
                    .doWrite(dataResult);

            System.out.println("写入完成！");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     *  设置表格内容居中显示策略
     */
    public static HorizontalCellStyleStrategy getStyleStrategy(){
        // 这里需要设置不关闭流
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)15);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        //内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont2 = new WriteFont();
        headWriteFont2.setFontHeightInPoints((short)13);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}

