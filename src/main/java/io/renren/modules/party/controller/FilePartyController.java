package io.renren.modules.party.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.modules.party.entity.FilePartyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.party.entity.FilePartyEntity;
import io.renren.modules.party.service.FilePartyService;
import io.renren.common.utils.R;

@RestController
@RequestMapping("/manage/party")
@Api("党支部信息管理接口")
public class FilePartyController {
    @Autowired
    private FilePartyService filePartyService;

    /**
     * 列表
     */
    @GetMapping("/list/tree")
    @ApiOperation("获取党支部列表树形数据")
//    @RequiresPermissions("file:party:tree")
    public R listWithTree(){
        List<FilePartyEntity> list = filePartyService.listWithTree();

        return R.ok().put("tree", list);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{partyId}")
    @ApiOperation("根据党支部编号获取党支部详细信息")
//    @RequiresPermissions("file:party:info")
    public R info(@PathVariable("partyId") Long partyId){
		FilePartyEntity fileParty = filePartyService.getById(partyId);

        return R.ok().put("data", fileParty);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存党支部信息")
//    @RequiresPermissions("file:party:save")
    public R save(@RequestBody FilePartyEntity fileParty){
		filePartyService.save(fileParty);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改党支部信息")
//    @RequiresPermissions("file:party:update")
    public R update(@RequestBody FilePartyEntity fileParty){
		filePartyService.updateById(fileParty);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation("批量删除党支部信息")
//    @RequiresPermissions("file:party:delete")
    public R delete(@RequestBody Long[] partyIds){
		filePartyService.removeByIds(Arrays.asList(partyIds));

        return R.ok();
    }

    /**
     * 验证传入的党组织编号和名称是否满足唯一性约束
     */
    @PostMapping("/checkidandname")
    @ApiOperation("验证传入的党组织编号和名称是否满足唯一性约束")
    public R checkIdAndName(@RequestParam Map<String, Object> params) {
        if (params.get("partyId")!=null || params.get("partyName")!=null) {
            if (filePartyService.checkIdAndName(params)) {
                return R.ok();
            }else {
                return R.error("已存在，请检查后重新填写");
            }
        }
        return R.error("传入参数不规范，请联系管理员！");
    }

    /**
     * 验证传入的党组织分类编号是否可以被删除
     */
    @PostMapping("/checkremove")
    @ApiOperation("验证传入的党组织分类编号是否可以被删除")
    public R checkRemove(@RequestBody Long[] partyIds) {
        if (partyIds!=null){
            if (filePartyService.checkRemove(partyIds)) {
                return R.ok();
            }else {
                return R.error("该党支部下仍存在档案信息，请转移后再尝试删除！");
            }
        }
        return R.error("传入参数不规范，请联系管理员！");
    }

}
