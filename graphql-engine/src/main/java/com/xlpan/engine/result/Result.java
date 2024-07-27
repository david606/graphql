package com.xlpan.engine.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xlpan.engine.exception.EngineException;
import lombok.Data;

/**
 * ClassName: Result
 * Description: 返回格式
 * date: 2020/9/28 8:42 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Data
public class Result<T> {
    @JsonProperty
    private boolean success = true;
    @JsonProperty
    private String code = "0";
    @JsonProperty
    private String message = null;
    @JsonProperty
    private String requestId = null;
    @JsonProperty
    private T data;

    public Result(T data) {
        this.data = data;
    }

    public Result(EngineException e) {
        this.success = false;
        code = e.getCode();
        message = e.getMessage();
    }

    public Result() {
    }
}
