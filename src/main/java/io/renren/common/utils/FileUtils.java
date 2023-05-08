package io.renren.common.utils;

import io.renren.common.exception.RRException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

@Component
public class FileUtils {

    /**
     * 文件上传功能
     * @param file 文件
     * @param baseId  文件所属档案编号
     * @param sort  当前文件顺序
     * @param imgtype  当前文件类型
     * @return  文件服务器存储路径
     */
    public String upload(MultipartFile file, Long baseId, Integer sort, Integer imgtype) {
        if (file==null || file.isEmpty()) {
            throw new RRException("文件为空！",6000);
        }
        String originalFilename = file.getOriginalFilename();
        //文件名为：当前文件顺序
        String fileName = sort + originalFilename.substring(originalFilename.lastIndexOf(".") );
        //文件路径为：根目录+档案编号+资料类型
        String filePath = "E:\\LiuJian\\img\\" + baseId + "\\" +imgtype + "\\" ;
        //创建file对象
        File dest = new File(filePath, fileName);
        //判断如果目标文件夹不存在则创建文件夹
        if (!dest.getParentFile().exists()) {
            if (!dest.getParentFile().mkdirs()) {
                throw new RRException("服务器存储文件夹创建失败，请联系管理员！",6001);
            }
        }
        //文件上传（将文件复制到目标文件夹）
        try {
            file.transferTo(dest);
            //返回文件存储路径
            System.out.println(filePath + fileName);
            return filePath + fileName;
        } catch (IOException e) {
            throw new RRException("文件上传失败，请重试！",6002);
        }
    }

    /**
     * 文件删除功能
     * @param path  文件夹路径
     */
    public void deleteFileByPath(String path){
        Path filePath = Paths.get(path);
        try {
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                // 先去遍历删除文件
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    System.out.printf("文件被删除 : %s%n", file);
                    return FileVisitResult.CONTINUE;
                }
                // 再去遍历删除目录
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    System.out.printf("文件夹被删除: %s%n", dir);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RRException("文件删除失败！");
        }
    }

//    /**
//     * 获取当前工程的resource资源文件绝对路径，用来保存文件
//     * @return  文件路径
//     */
//    public String getSavePath() {
//        // 这里需要注意的是ApplicationHome是属于SpringBoot的类
//        // 获取项目下resources/static/img路径
//        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
//
//        // 保存目录位置根据项目需求可随意更改
//        return applicationHome.getDir().getParentFile()
//                .getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img\\";
//    }
}
