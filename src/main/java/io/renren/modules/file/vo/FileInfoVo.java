package io.renren.modules.file.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.renren.modules.images.entity.FileImagesEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 档案详细信息（包括图片资料信息）
 */
@Data
public class FileInfoVo {
    /**
     * 档案编号
     */
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别[0-女，1-男]
     */
    private Integer sex;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 所在党支部
     */
    private Long partyBranchesId;
    /**
     * 入党时间
     */
    private Date joinTime;
    /**
     * 入党时所在党支部
     */
    private String joinPartyBranchesName;
    /**
     * 转正时间
     */
    private Date positiveTime;
    /**
     * 转正时所在党支部
     */
    private String positivePartyBranchesName;
    /**
     * 第一入党介绍人
     */
    private String referencesOne;
    /**
     * 第二入党介绍人
     */
    private String referencesTwo;
    /**
     * 入党志愿书编号
     */
    private Long volunteerBookId;
    /**
     * 档案状态[0-正常，1-开除，2-死亡，3-转出，4-删除]
     */
    private Integer status;
    /**
     * 入库时间
     */
    private Date inboundTime;
    /**
     * 转出时间
     */
    private Date rolloutTime;
    /**
     * 死亡时间
     */
    private Date deathTime;
    /**
     * 开除时间
     */
    private Date expelTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后一次修改时间
     */
    private Date updateTime;
    /**
     * 档案资料
     */
    private Map<Integer, List<FileImagesEntity>> fileImages;
}
