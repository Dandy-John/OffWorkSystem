package org.seckill.exception;

/**
 * Created by 张栋迪 on 2016/11/28.
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
