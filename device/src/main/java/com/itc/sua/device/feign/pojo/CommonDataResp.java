package com.itc.sua.device.feign.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName CommonDataDO
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/2
 */
@Data
public class CommonDataResp implements Serializable {
    private static final long serialVersionUID = 8200258069209965191L;

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

