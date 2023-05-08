package io.renren.modules.file.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @ClassName StatusConverterHandler
 * @Description 自定义状态类型转换器
 */
public class StatusConverterHandler implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return null;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if ("正常".equals(cellData.getStringValue())) {
            return 0;
        } else if ("已开除".equals(cellData.getStringValue())) {
            return 1;
        } else if ("已死亡".equals(cellData.getStringValue())) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (integer.equals(0)){
            return new CellData("正常");
        }else if (integer.equals(1)){
            return new CellData("已开除");
        } else if (integer.equals(2)){
            return new CellData("已死亡");
        } else {
            return new CellData("已转出");
        }
    }
}
