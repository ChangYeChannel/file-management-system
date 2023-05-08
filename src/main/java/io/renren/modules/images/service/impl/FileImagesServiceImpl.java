package io.renren.modules.images.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.common.exception.RRException;
import io.renren.common.utils.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.images.dao.FileImagesDao;
import io.renren.modules.images.entity.FileImagesEntity;
import io.renren.modules.images.service.FileImagesService;


@Service("fileImagesService")
public class FileImagesServiceImpl extends ServiceImpl<FileImagesDao, FileImagesEntity> implements FileImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FileImagesEntity> page = this.page(
                new Query<FileImagesEntity>().getPage(params),
                new QueryWrapper<FileImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Integer> getTypeByBaseId(Long baseId) {
        LambdaQueryWrapper<FileImagesEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileImagesEntity::getBaseId,baseId);
        queryWrapper.groupBy(FileImagesEntity::getImgType);
        queryWrapper.select(FileImagesEntity::getImgType);
        List<FileImagesEntity> entityList = this.list(queryWrapper);

        ArrayList<Integer> typeList = new ArrayList<>();
        for (FileImagesEntity fileImagesEntity : entityList) {
            typeList.add(fileImagesEntity.getImgType());
        }

        return typeList;

    }

    @Override
    public List<String> getImagesPath(Long baseId, Integer type) {
        LambdaQueryWrapper<FileImagesEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileImagesEntity::getBaseId,baseId);
        queryWrapper.eq(FileImagesEntity::getImgType,type);
        queryWrapper.select(FileImagesEntity::getImgPath);

        ArrayList<String> paths = new ArrayList<>();
        for (FileImagesEntity fileImagesEntity : this.list(queryWrapper)) {
            paths.add(fileImagesEntity.getImgPath());
        }

        return paths;
    }

    @Override
    public void removeType(Long baseId, Integer type) {
        LambdaQueryWrapper<FileImagesEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileImagesEntity::getBaseId,baseId);
        queryWrapper.eq(FileImagesEntity::getImgType,type);
        this.remove(queryWrapper);
        //删除文件
        String filePath = "E:\\LiuJian\\img\\" + baseId + "\\" +type ;
        new FileUtils().deleteFileByPath(filePath);
    }



}