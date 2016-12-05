package org.off_work_system.dto;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
public class ResultWrapper<T> {

    private int state;

    private T data;

    public ResultWrapper(int state, T data) {
        this.state = state;
        this.data = data;
    }

    public boolean isSuccess() {
        return state == 200;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
