package com.itc.sua.analysis.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName CommonDataDO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "common_data")
public class CommonDataDO extends Model<CommonDataDO> {
    private static final long serialVersionUID = -4182336070280589494L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String eventId;
    private Integer eventType;

    private Integer inCount;
    private Integer outCount;
    private Integer sumCount;

    private Date appearTime;
    private Date disappearTime;

    private Date createTime;
    private Date updateTime;

    private Boolean deleted;

}

