package io.renren.modules.file.controller;

import io.renren.common.utils.PdfUtils;
import io.renren.modules.images.service.FileImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pdf")
@Api("档案材料转为pdf文件导出接口")
public class FilePdfController {

    @Resource
    private FileImagesService fileImagesService;

    @ApiOperation("将某档案的某种类型材料导出到pdf文件")
    @GetMapping("/write/{baseId}/{type}")
//    @RequiresPermissions("file:pdf:write")
    public ResponseEntity write(@PathVariable("baseId") Long baseId, @PathVariable("type") Integer type) {
        List<String> imagesPaths = fileImagesService.getImagesPath(baseId,type);
        //测试数据
//        ArrayList<String> imagesPaths = new ArrayList<>();
//        String imagesPath = "E:\\LiuJian\\img\\schoolLogo.jpg";
//        imagesPaths.add(imagesPath);
        return PdfUtils.downPdf(baseId,imagesPaths);
    }
}
