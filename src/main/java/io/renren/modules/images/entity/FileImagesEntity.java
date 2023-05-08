package io.renren.modules.images.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author Lj
 * @email 14878702@qq.com
 * @date 2023-02-13 08:47:43
 */
@Data
@TableName("tb_file_images")
public class FileImagesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属档案编号
	 */
	@TableId
	private Long baseId;
	/**
	 * 资料存储地址
	 */
	private String imgPath;
	/**
	 * 资料排序
	 */
	private Integer imgSort;
	/**
	 * 资料类型
	 */
	private Integer imgType;

}
