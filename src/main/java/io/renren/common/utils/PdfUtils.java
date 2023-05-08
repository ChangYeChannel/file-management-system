package io.renren.common.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import io.renren.common.exception.RRException;
import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PdfUtils {

    public static ResponseEntity downPdf(Long baseId, List<String> imageUrlList) {
        //新建一个pdf文档
        String pdfUrl = baseId + ".pdf";
        //将图片存储到pdf文件过程
        try {
            //A4大小，四周边距20px
            Document doc = new Document(PageSize.A4, 20, 20, 20, 20);
            //pdf写入
            PdfWriter.getInstance(doc, Files.newOutputStream(Paths.get(pdfUrl)));
            //打开文档
            doc.open();
            //遍历集合，将图片放在pdf文件
            for (String imgUrl : imageUrlList) {
                //在pdf创建一页，每一张图片是pdf文件的一页
                doc.newPage();
                //通过文件路径获取image
                Image img = Image.getInstance(imgUrl);
                //图片高度
                float height = img.getHeight();
                //图片宽度
                float width = img.getWidth();
                //获取图片百分比
                int percent = getPercent2(height, width);
                //设置图片居中对齐
                img.setAlignment(Image.MIDDLE);
                // 表示是原来图像的比例;
                img.scalePercent(percent + 3);
                //添加图片
                doc.add(img);
            }
            //关闭文档
            doc.close();
        } catch (Exception e) {
            throw new RRException("图片转pdf文件失败！");
        }
        //将pdf文件导出到响应体过程
        File pdfFile = new File(pdfUrl);
        InputStream in = null;
        try {
            in = new FileInputStream(pdfFile);
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "attachment; filename=" + pdfFile.getName() );
            return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (IOException e) {
            throw new RRException("pdf文件导出失败！");
        } finally {
            try {
                //关闭文件输入流
                assert in != null;
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //删除文件
            boolean delete = pdfFile.delete();
            System.out.println(delete);
        }
    }

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

}
