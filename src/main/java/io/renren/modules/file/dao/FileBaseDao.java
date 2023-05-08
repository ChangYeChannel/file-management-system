package io.renren.modules.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.file.entity.FileBaseEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface FileBaseDao extends BaseMapper<FileBaseEntity> {

    /**
     * 修改某档案状态
     * @param fileBase  档案
     */
    void alterStatus(FileBaseEntity fileBase);

    void fileDeleteById(Long id);

    /**
     * 补录档案信息
     * @param fileBase  需要补录的内容（档案id必填，加剩余补录内容）
     */
    void supplement(FileBaseEntity fileBase);
}
