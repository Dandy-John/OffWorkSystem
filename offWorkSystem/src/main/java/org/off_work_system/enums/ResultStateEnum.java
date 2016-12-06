package org.off_work_system.enums;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
public enum ResultStateEnum {
    OK(200, "OK"),
    FORBIDDEN(403, "访问被拒绝"),
    NOT_FOUND(404, "未找到该记录"),
    NOT_LOGIN(490, "未登录"),
    UPDATE_FAILED(600, "数据更新错误");

    private int state;
    private String stateInfo;

    ResultStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ResultStateEnum stateof(int index) {
        for (ResultStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
