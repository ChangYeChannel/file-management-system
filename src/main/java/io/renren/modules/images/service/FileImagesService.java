package io.renren.modules.images.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.images.entity.FileImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Lj
 * @email 14878702@qq.com
 * @date 2023-02-13 08:47:43
 */
public interface FileImagesService extends IService<FileImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 获取当前档案的材料类型
     * @param baseId  档案编号
     * @return  材料类型集合
     */
    List<Integer> getTypeByBaseId(Long baseId);

    /**
     * 根据档案编号和材料类型获取材料路径地址集合
     * @param baseId  档案编号
     * @param type  材料类型
     * @return  材料路径地址集合
     */
    List<String> getImagesPath(Long baseId, Integer type);

    /**
     * 删除某档案的某种材料
     * @param baseId  档案编号
     * @param type  材料类型
     */
    void removeType(Long baseId, Integer type);
}

