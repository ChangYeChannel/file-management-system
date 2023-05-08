package io.renren.modules.images.controller;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.images.entity.FileImagesEntity;
import io.renren.modules.images.service.FileImagesService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author Lj
 * @email 14878702@qq.com
 * @date 2023-02-13 08:47:43
 */
@RestController
@RequestMapping("images/fileimages")
@Api("档案材料信息管理接口")
public class FileImagesController {
    @Autowired
    private FileImagesService fileImagesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fileImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{baseId}")
    public R info(@PathVariable("baseId") Long baseId){
		FileImagesEntity fileImages = fileImagesService.getById(baseId);

        return R.ok().put("fileImages", fileImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody FileImagesEntity fileImages){
		fileImagesService.save(fileImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FileImagesEntity fileImages){
		fileImagesService.updateById(fileImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{baseId}/{type}")
    @ApiOperation("删除某档案的某种材料")
//    @RequiresPermissions("file:image:delete")
    public R delete(@PathVariable Long baseId,@PathVariable Integer type){
		fileImagesService.removeType(baseId,type);

        return R.ok();
    }

    /**
     * 获取某档案所拥有的材料种类
     */
    @GetMapping("/type/{baseId}")
    @ApiOperation("传入档案编号，获取当前档案的材料种类")
//    @RequiresPermissions("file:image:type")
    public R getType(@PathVariable("baseId") Long baseId){
        List<Integer> typeList = fileImagesService.getTypeByBaseId(baseId);

        return R.ok().put("typelist",typeList);
    }

}
