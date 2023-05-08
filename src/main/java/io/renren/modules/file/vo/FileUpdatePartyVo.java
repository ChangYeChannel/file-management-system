package io.renren.modules.file.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by DELL on 2023/2/15
 **/
@Data
public class FileUpdatePartyVo {
    private List<Long> baseIds;
    private Long partyId;
}
