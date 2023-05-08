package io.renren.modules.party.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.renren.modules.file.entity.FileBaseEntity;
import io.renren.modules.file.service.FileBaseService;
import io.renren.modules.party.entity.FilePartyVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.party.dao.FilePartyDao;
import io.renren.modules.party.entity.FilePartyEntity;
import io.renren.modules.party.service.FilePartyService;

import javax.annotation.Resource;


@Service("filePartyService")
public class FilePartyServiceImpl extends ServiceImpl<FilePartyDao, FilePartyEntity> implements FilePartyService {

    @Resource
    private FileBaseService fileBaseService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FilePartyEntity> page = this.page(
                new Query<FilePartyEntity>().getPage(params),
                new QueryWrapper<FilePartyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<FilePartyEntity> listWithTree() {
        //查询所有党组织数据
        List<FilePartyEntity> all = this.list();

        //递归所有数据完成树形结构组装
        return all.stream() //组装流数据
                .filter((filePartyEntity) -> filePartyEntity.getParentId() == 0) //过滤器过滤出父分类id为0的一级目录
                .peek((filePartyEntity) -> filePartyEntity.setChildren(getChildren(filePartyEntity, all)))  //递归写入当前分类的子分类
                .collect(Collectors.toList());  //返回当前菜单数据
    }

    @Override
    public boolean checkIdAndName(Map<String, Object> params) {
        LambdaQueryWrapper<FilePartyEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty((String) params.get("partyName"))) {
            queryWrapper.eq(FilePartyEntity::getPartyName,params.get("partyName"));
        }
        if (!StringUtils.isEmpty((String) params.get("partyId"))) {
            queryWrapper.eq(FilePartyEntity::getPartyId,params.get("partyId"));
        }
        return !(this.count(queryWrapper) > 0);
    }

    @Override
    public boolean checkRemove(Long[] partyIds) {
        LambdaQueryWrapper<FileBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FileBaseEntity::getPartyBranchesId,partyIds);
        return !(fileBaseService.count(queryWrapper) > 0);
    }

    private List<FilePartyEntity> getChildren(FilePartyEntity root, List<FilePartyEntity> all) {
        return all.stream() //组装流数据
                .filter((filePartyEntity) -> filePartyEntity.getParentId().equals(root.getPartyId()))  //过滤器过滤出父分类id为当前分类的id的目录
                .peek((filePartyEntity) -> filePartyEntity.setChildren(getChildren(filePartyEntity, all)))  //递归写入当前分类的子分类 //递归写入当前分类的子分类
                .collect(Collectors.toList());  //返回当前菜单数据
    }
}