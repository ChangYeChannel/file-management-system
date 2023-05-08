package io.renren.modules.file.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.renren.modules.file.converter.SexConverterHandler;
import io.renren.modules.file.converter.StatusConverterHandler;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
@TableName("tb_file_base")
public class FileBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 档案编号
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {"档案编号"})
	private Long id;
	/**
	 * 姓名
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"姓名"})
	private String name;
	/**
	 * 性别[0-女，1-男]
	 */
	@ColumnWidth(10)
	@ExcelProperty(value = {"性别"},converter = SexConverterHandler.class)
	private Integer sex;
	/**
	 * 身份证号
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {"身份证号"})
	private String idCard;
	/**
	 * 出生日期
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"出生日期"})
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birthday;
	/**
	 * 所在党支部
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"所在党支部"})
	private Long partyBranchesId;
	/**
	 * 入党时间
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"入党时间"})
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date joinTime;
	/**
	 * 入党时所在党支部
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {"入党时所在党支部"})
	private String joinPartyBranchesName;
	/**
	 * 转正时间
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"转正时间"})
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date positiveTime;
	/**
	 * 转正时所在党支部
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {"转正时所在党支部"})
	private String positivePartyBranchesName;
	/**
	 * 第一入党介绍人
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"第一入党介绍人"})
	private String referencesOne;
	/**
	 * 第二入党介绍人
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"第二入党介绍人"})
	private String referencesTwo;
	/**
	 * 入党志愿书编号
	 */
	@ColumnWidth(25)
	@ExcelProperty(value = {"入党志愿书编号"})
	private Long volunteerBookId;
	/**
	 * 档案状态[0-正常，1-开除，2-死亡，3-转出，4-删除]
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"档案状态"},converter = StatusConverterHandler.class)
	private Integer status;
	/**
	 * 入库时间
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"入库时间"})
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date inboundTime;
	/**
	 * 转出时间
	 */
	@ColumnWidth(15)
	@ExcelProperty(value = {"转出时间"})
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date rolloutTime;
	/**
	 * 死亡时间
	 */
	@ColumnWidth(15)
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ExcelProperty(value = {"死亡时间"})
	private Date deathTime;
	/**
	 * 开除时间
	 */
	@ColumnWidth(15)
	@DateTimeFormat("yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ExcelProperty(value = {"开除时间"})
	private Date expelTime;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@TableField(fill = FieldFill.INSERT)
	@ExcelIgnore
	private Date createTime;
	/**
	 * 最后一次修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@TableField(fill = FieldFill.INSERT)
	@ExcelIgnore
	private Date updateTime;

}
