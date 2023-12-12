package com.itc.sua.analysis.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName CommonDataAddReq
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/12
 */
@Data
public class CommonDataAddReq implements Serializable {
    private static final long serialVersionUID = 7291689866628531354L;

    private String eventId;
    private Integer eventType;

    private Integer inCount;
    private Integer outCount;
    private Integer sumCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appearTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date disappearTime;
}
