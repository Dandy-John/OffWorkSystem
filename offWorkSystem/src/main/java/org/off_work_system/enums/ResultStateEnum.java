package org.off_work_system.enums;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
public enum ResultStateEnum {
    OK(200, "OK"),
    FORBIDDEN(403, "访问被拒绝"),
    NOT_FOUND(404, "未找到该记录"),
    NOT_LOGIN(490, "未登录"),
    UPDATE_FAILED(600, "数据更新错误"),
    SAME_USERNAME(601, "用户名重复"),
    INVALID_INPUT(602, "输入非法"),
    WRONG_PASSWORD(603, "用户名不存在或密码错误"),
    CANT_DELETE(604, "不能删除顶层部门"),
    NOT_EMPTY(605, "部门内有员工，不能删除"),
    NO_ENOUGH_ANNUAL(606, "剩余年假不足"),
    TOO_LONG15(607, "陪产假不能超过15天"),
    TOO_LONG98(608, "产假不能超过98天"),
    TOO_LONG3(609, "婚假不能超过3天"),
    TOO_LONG13(610, "晚婚假不能超过13天"),
    UNKNOWN_INNER_FAULT(999, "系统内部未知错误");

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
