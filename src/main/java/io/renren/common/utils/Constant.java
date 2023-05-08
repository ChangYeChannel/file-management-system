

package io.renren.common.utils;

import io.renren.common.validator.group.AliyunGroup;
import io.renren.common.validator.group.QcloudGroup;
import io.renren.common.validator.group.QiniuGroup;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 常量
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 云服务商
     */
    public enum CloudService {
        /**
         * 七牛云
         */
        QINIU(1, QiniuGroup.class),
        /**
         * 阿里云
         */
        ALIYUN(2, AliyunGroup.class),
        /**
         * 腾讯云
         */
        QCLOUD(3, QcloudGroup.class);

        private int value;

        private Class<?> validatorGroupClass;

        CloudService(int value, Class<?> validatorGroupClass) {
            this.value = value;
            this.validatorGroupClass = validatorGroupClass;
        }

        public int getValue() {
            return value;
        }

        public Class<?> getValidatorGroupClass() {
            return this.validatorGroupClass;
        }

        public static CloudService getByValue(Integer value) {
            Optional<CloudService> first = Stream.of(CloudService.values()).filter(cs -> value.equals(cs.value)).findFirst();
            if (!first.isPresent()) {
                throw new IllegalArgumentException("非法的枚举值:" + value);
            }
            return first.get();
        }
    }

    /**
     * 档案状态
     */
    public enum FileStatus {
        /**
         * 正常
         */
        JOINED(0),
        /**
         * 已开除
         */
        EXPEL(1),
        /**
         * 已死亡
         */
        DEATH(2),
        /**
         * 已转出
         */
        ROLL_OUT(3),
        /**
         * 已删除
         */
        DELETED(4);

        private int status;

        FileStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    /**
     * 档案资料类型
     */
    public enum FileImageType {
        /**
         * 入党申请材料
         */
        APPLICATION_PARTY(0),
        /**
         * 入党积极分子材料
         */
        AGGRESSIVE_PARTY(1),
        /**
         * 政审材料
         */
        POLITICAL_TRIAL(2),
        /**
         * 转正材料
         */
        TURN_POSITIVE(3),
        /**
         * 入党志愿书材料
         */
        VOLUNTEER_PARTY(4),
        /**
         * 转出证明
         */
        ROLL_OUT(5),
        /**
         * 死亡证明
         */
        DEATH(6),
        /**
         * 开除党籍证明
         */
        EXPEL(7),
        /**
         * 其他材料
         */
        OTHER(8);
        private int type;
        FileImageType(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
    }

    /**
     * 系统日志操作类型
     */
    public enum SysLogNum {
        INPUT("01"),
        UPDATE("02"),
        DELETE("03"),
        ROLL_OUT("04"),
        DEATH("05"),
        EXPEL("06"),
        ADD("07");
        private String logType;

        SysLogNum(String logType) {
            this.logType = logType;
        }

        public String getLogType() {
            return logType;
        }
    }

}
