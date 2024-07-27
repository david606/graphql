package com.xlpan.engine.exception;

import lombok.Data;

/**
 * ClassName: EngineException
 * Description: 自定义系统异常
 * date: 2020/9/28 8:36 上午
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Data
public class EngineException extends RuntimeException {
    private String code;
    private String message;

    public EngineException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public EngineException(String message) {
        super(message);
        this.message = message;
    }

    public EngineException(EngineErrorCodeEnmu engineErrorCodeEnmu) {
        super(engineErrorCodeEnmu.getName());
        this.message = engineErrorCodeEnmu.getName();
        this.code = engineErrorCodeEnmu.getCode() + "";
    }
}
