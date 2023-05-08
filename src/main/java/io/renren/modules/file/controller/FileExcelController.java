package io.renren.modules.file.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.ExcelUtils;
import io.renren.modules.file.entity.FileBaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/excel")
@Api("档案基本信息转excel导出管理")
public class FileExcelController {
    @ApiOperation("导出到excel")
    @PostMapping("/write")
//    @RequiresPermissions("file:excel:write")
    public void write(HttpServletResponse response, @RequestBody List<FileBaseEntity> fileBaseEntityList) {
        //测试数据
//        List<FileBaseEntity> fileBaseEntityList = initFileBaseEntityList();
        if (fileBaseEntityList.size() == 0) {
            throw new RRException("即将导出的数据为空！");
        }
        String fileName = new String("党员基本信息表.xlsx".getBytes(), StandardCharsets.UTF_8);
        try {
            ExcelUtils.export(fileName,"sheet1",fileBaseEntityList,FileBaseEntity.class,response);
        } catch (Exception e) {
            throw new RRException("Excel导出异常，请联系管理员！");
        }
    }

//    //测试方法
//    public List<FileBaseEntity> initFileBaseEntityList() {
//        ArrayList<FileBaseEntity> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            FileBaseEntity fileBase = new FileBaseEntity();
//            fileBase.setId(1L+i);
//            fileBase.setSex(1);
//            fileBase.setStatus(0);
//            fileBase.setBirthday(new Date());
//            list.add(fileBase);
//        }
//        return list;
//    }
}
