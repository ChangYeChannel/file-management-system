package io.renren.modules.file.controller;

import io.renren.common.utils.FileUtils;
import io.renren.common.utils.R;
import io.renren.modules.images.entity.FileImagesEntity;
import io.renren.modules.images.service.FileImagesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/fileupload")
@Api("档案材料上传管理接口")
public class FileUploadController {

    @Resource
    private FileImagesService fileImagesService;

    @ApiOperation("档案材料上传")
    @PostMapping("/{baseId}/{sort}/{imgtype}")
//    @RequiresPermissions("file:upload:materials")
    public R uploadFile(MultipartFile file,
                        @PathVariable("baseId") Long baseId,
                        @PathVariable("sort") Integer sort,
                        @PathVariable("imgtype") Integer imgtype){
        String uploadPath = new FileUtils().upload(file,baseId,sort,imgtype);
        FileImagesEntity fileImages = new FileImagesEntity();
        fileImages.setBaseId(baseId);
        fileImages.setImgPath(uploadPath);
        fileImages.setImgSort(sort);
        fileImages.setImgType(imgtype);
        fileImagesService.save(fileImages);
        return R.ok();
    }
}