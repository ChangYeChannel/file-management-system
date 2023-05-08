package io.renren.modules.file.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.file.entity.FileBaseEntity;
import io.renren.modules.file.service.FileBaseService;
import io.renren.modules.file.vo.FileInfoVo;
import io.renren.modules.file.vo.FileUpdatePartyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/manage/file")
@Api("档案基本信息管理接口")
public class FileBaseController {
    @Autowired
    private FileBaseService fileBaseService;

    /**
     * 删除档案
     */
    @PostMapping("/filedelete/{id}")
    @ApiOperation("根据档案编号删除档案")
    @SysLog(code = "03",msg = "删除了档案")
//    @RequiresPermissions("file:base:delete")
    public R fileDelete(@PathVariable("id") Long id) {
        fileBaseService.fileDelete(id);
        return R.ok();
    }

    /**
     * 档案内容修改
     */
    @PostMapping("/fileupdate")
    @ApiOperation("档案内容修改（封装fileBase对象，要求传入所有需要写入的属性，包括未修改属性）")
    @SysLog(code = "02")
//    @RequiresPermissions("file:base:update")
    public R fileUpdate(@RequestBody FileBaseEntity fileBase) {
        fileBaseService.updateById(fileBase);
        return R.ok();
    }


    /**
     * 系统内党支部调整
     */
    @PostMapping("/partytweaks")
    @ApiOperation("系统内党支部调整（封装FileUpdatePartyVo实体类<档案编号集合(大小可为1)，调整到党支部编号>）")
//    @RequiresPermissions("file:base:adjust")
    public R partyTweaks(@RequestBody FileUpdatePartyVo fileUpdatePartyVo) {
        fileBaseService.partyTweaks(fileUpdatePartyVo);
        return R.ok();
    }

    /**
     * 档案状态修改
     */
    @PostMapping("/filealter/{id}/{status}/{date}")
    @ApiOperation("档案状态修改")
    @SysLog(code = "0X",msg = "修改了状态")
//    @RequiresPermissions("file:base:status")
    public R fileStatusAlter(@PathVariable("id") Long id, @PathVariable("status") Integer status, @PathVariable("date") String statusDate) {
        fileBaseService.alterStatus(id,status,statusDate);
        return R.ok();
    }


    /**
     * 档案补录
     */
    @PostMapping("/baseadd")
    @ApiOperation("档案补录(封装fileBase实体类，必须携带档案编号，再加需要补录的内容)")
    @SysLog(code = "07")
//    @RequiresPermissions("file:base:add")
    public R baseAdd(@RequestBody FileBaseEntity fileBase) {
        fileBaseService.supplement(fileBase);
        return R.ok();
    }

    /**
     * 新档案录入
     */
    @PostMapping("/newfilesave")
    @ApiOperation("新档案录入")
    @SysLog(code = "01",msg = "录入了新档案")
//    @RequiresPermissions("file:base:save")
    public R newFileSave(@RequestBody FileBaseEntity fileBase) {
        fileBaseService.save(fileBase);
        return R.ok();
    }

    /**
     * 旧档案录入
     */
    @PostMapping("/oldfilesave")
    @ApiOperation("旧档案录入（即必须携带入库时间属性）")
    @SysLog(code = "01",msg = "录入了旧档案")
//    @RequiresPermissions("file:base:save")
    public R oldFileSave(@RequestBody FileBaseEntity fileBase) {
        if (fileBase.getInboundTime() != null) {
            fileBaseService.save(fileBase);
            return R.ok();
        }else {
            return R.error(2001,"入库旧档案必须携带入库时间参数！");
        }
    }

    /**
     * 档案信息列表（待条件查询）
     */
    @GetMapping("/list")
    @ApiOperation("档案信息列表（带条件<档案编号，姓名，身份证号，性别，所在党支部编号，状态，出生日期，入党日期，转正日期>查询）")
//    @RequiresPermissions("file:base:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = fileBaseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 获取某条档案的详细信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("获取某条档案的详细信息（包括档案资料数据）")
//    @RequiresPermissions("file:base:info")
    public R info(@PathVariable("id") Long id){
        FileInfoVo fileAllInfo = null;
        try {
            fileAllInfo = fileBaseService.getAllInfoById(id);
        } catch (Exception e) {
            throw new RRException("获取指定档案信息失败，请联系管理员！",1000);
        }

        return R.ok().put("fileAllInfo", fileAllInfo);
    }

}
