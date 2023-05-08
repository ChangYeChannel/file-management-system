package io.renren.modules.party.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.party.entity.FilePartyVo;
import io.renren.modules.party.entity.FilePartyEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface FilePartyService extends IService<FilePartyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 党支部树形列表显示
     * @return  数据
     */
    List<FilePartyEntity> listWithTree();

    /**
     * 验证传入的党组织编号和名称是否满足唯一性约束
     */
    boolean checkIdAndName(Map<String, Object> params);

    /**
     * 验证传入的党组织分类编号是否可以被删除
     */
    boolean checkRemove(Long[] partyIds);
}

