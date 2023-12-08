package com.itc.sua.analysis.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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


}

