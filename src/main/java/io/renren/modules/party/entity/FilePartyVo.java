package io.renren.modules.party.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class FilePartyVo {

    /**
     * 党支部编号
     */
    @TableId
    private Long partyId;
    /**
     * 党支部名称
     */
    private String partyName;

}
