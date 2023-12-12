package com.itc.sua.device.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName DeviceDO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "device")
public class DeviceDO extends Model<DeviceDO> {
    private static final long serialVersionUID = 8828889912815889070L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String macId;

    private String macName;

    private String macModel;

    private Integer macType;

    private Boolean online;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object extendInfo;


    private Date createTime;
    private Date updateTime;

    private Boolean deleted;

}
