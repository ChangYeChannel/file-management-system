package io.renren.modules.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.file.entity.FileBaseEntity;
import io.renren.modules.file.vo.FileInfoVo;
import io.renren.modules.file.vo.FileUpdatePartyVo;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

public interface FileBaseService extends IService<FileBaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据传入的status标志，更改档案的状态
     */
    void alterStatus(Long id, Integer status, String statusDate);

    /**
     * 修改某些档案的所属党支部
     */
    void partyTweaks(FileUpdatePartyVo fileUpdatePartyVo);

    /**
     * 根据传入的档案编号，逻辑删除档案信息
     * @param id  档案编号
     */
    void fileDelete(Long id);

    /**
     * 根据传入的档案编号，查询档案的所有详细信息，包括档案材料信息
     * @param id  档案编号
     * @return  档案的所有详细信息
     */
    FileInfoVo getAllInfoById(Long id) throws InvocationTargetException, IllegalAccessException;

    /**
     * 补录档案信息
     * @param fileBase  需要补录的内容（档案id必填，加剩余补录内容）
     */
    void supplement(FileBaseEntity fileBase);
}

