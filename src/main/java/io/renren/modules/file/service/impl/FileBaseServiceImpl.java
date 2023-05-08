package io.renren.modules.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.file.dao.FileBaseDao;
import io.renren.modules.file.entity.FileBaseEntity;
import io.renren.modules.file.service.FileBaseService;
import io.renren.modules.file.vo.FileInfoVo;
import io.renren.modules.file.vo.FileUpdatePartyVo;
import io.renren.modules.images.entity.FileImagesEntity;
import io.renren.modules.images.service.FileImagesService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("fileBaseService")
public class FileBaseServiceImpl extends ServiceImpl<FileBaseDao, FileBaseEntity> implements FileBaseService {
    @Resource
    private FileImagesService fileImagesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //条件查询，构造查询条件
        LambdaQueryWrapper<FileBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty((String) params.get("id"))) {//档案编号
            queryWrapper.eq(FileBaseEntity::getId,params.get("id"));
        }
        if (!StringUtils.isEmpty((String) params.get("name"))) {//姓名
            queryWrapper.like(FileBaseEntity::getName,params.get("name"));
        }
        if (!StringUtils.isEmpty((String) params.get("sex"))) {
            queryWrapper.eq(FileBaseEntity::getSex,params.get("sex"));
        }
        if (!StringUtils.isEmpty((String) params.get("idCard"))) {//身份证号
            queryWrapper.like(FileBaseEntity::getIdCard,params.get("idCard"));
        }
        if (!StringUtils.isEmpty((String) params.get("partyBranchesId"))) {//所在党支部编号
            queryWrapper.eq(FileBaseEntity::getPartyBranchesId,params.get("partyBranchesId"));
        }
        if (!StringUtils.isEmpty((String) params.get("status"))) {//状态
            queryWrapper.eq(FileBaseEntity::getStatus,params.get("status"));
        }
        if (!StringUtils.isEmpty((String) params.get("birthday"))) {//出生日期
            queryWrapper.eq(FileBaseEntity::getBirthday,params.get("birthday"));
        }
        if (!StringUtils.isEmpty((String) params.get("joinTime"))) {//入党日期
            queryWrapper.eq(FileBaseEntity::getJoinTime,params.get("joinTime"));
        }
        if (!StringUtils.isEmpty((String) params.get("positiveTime"))) {//转正日期
            queryWrapper.eq(FileBaseEntity::getPositiveTime,params.get("positiveTime"));
        }

        //已删除的档案不显示
        queryWrapper.ne(FileBaseEntity::getStatus, Constant.FileStatus.DELETED.getStatus());

        //按照入库时间进行排序
        queryWrapper.orderByDesc(FileBaseEntity::getInboundTime);

        IPage<FileBaseEntity> page = this.page(
                new Query<FileBaseEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void alterStatus(Long id, Integer status, String statusDateStr) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date statusDate;
        try {
            statusDate = ft.parse(statusDateStr);
        } catch (ParseException e) {
            throw new RRException("修改档案状态方法，将str类型时间转为date时出错");
        }
        FileBaseEntity fileBase = new FileBaseEntity();
        fileBase.setId(id);
        fileBase.setStatus(status);
        if (status.equals(1)) { //开除
            fileBase.setExpelTime(statusDate);
        } else if (status.equals(2)) {  //死亡
            fileBase.setDeathTime(statusDate);
        } else if (status.equals(3)) {  //转出
            fileBase.setRolloutTime(statusDate);
        }
        baseMapper.alterStatus(fileBase);
    }

    @Override
    public void partyTweaks(FileUpdatePartyVo fileUpdatePartyVo) {
        LambdaQueryWrapper<FileBaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(FileBaseEntity::getId,fileUpdatePartyVo.getBaseIds());
        List<FileBaseEntity> result = this.list(queryWrapper);
        result.forEach(item -> {
            item.setPartyBranchesId(fileUpdatePartyVo.getPartyId());
            this.updateById(item);
        });
    }

    @Override
    public void fileDelete(Long id) {
        baseMapper.fileDeleteById(id);
    }

    @Override
    public FileInfoVo getAllInfoById(Long id) throws InvocationTargetException, IllegalAccessException {
        //根据编号获取基本信息
        FileBaseEntity fileBase = this.getById(id);
        //根据编号获取所有材料信息
        LambdaQueryWrapper<FileImagesEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileImagesEntity::getBaseId,id);
        List<FileImagesEntity> fileImageList = fileImagesService.list(queryWrapper);

        //封装材料信息
        Map<Integer, List<FileImagesEntity>> fileImages = fileImageList
                .stream()
                .collect(Collectors.groupingBy(FileImagesEntity::getImgType));

        //封装档案信息结果类
        FileInfoVo fileInfoVo = new FileInfoVo();
        BeanUtils.copyProperties(fileInfoVo,fileBase);
        fileInfoVo.setFileImages(fileImages);

        return fileInfoVo;
    }

    @Override
    public void supplement(FileBaseEntity fileBase) {
        baseMapper.supplement(fileBase);
    }


}