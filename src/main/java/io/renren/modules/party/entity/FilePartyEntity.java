package io.renren.modules.party.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName("tb_file_party")
public class FilePartyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 党支部编号
	 */
	@TableId
	private Long partyId;
	/**
	 * 党支部名称
	 */
	private String partyName;
	/**
	 * 上级党支部编号
	 */
	private Long parentId;
	/**
	 * 子分类党支部
	 */
	@TableField(exist = false)    //数据表中不存在此属性
	@JsonInclude(JsonInclude.Include.NON_EMPTY)  //当属性为空时，返回的json串的对应位置不包含此属性
	private List<FilePartyEntity> children;

}
