package org.seckill.exception;

/**
 * Created by 张栋迪 on 2016/11/28.
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
