package org.off_work_system.enums;

/**
 * Created by 张栋迪 on 2016/12/5.
 */
public enum FormStateEnum {
    SUBMITTED(1, "已提交"),
    PASSED(0, "已通过"),
    WAITING_CHECK(2, "通过科室主任审核"),
    FAILED(-1, "未通过");

    private int state;
    private String stateInfo;

    FormStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static FormStateEnum stateof(int index) {
        for (FormStateEnum state :values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

    public static String stateInfoof(int index){
        for (FormStateEnum state :values()) {
            if (state.getState() == index) {
                return state.getStateInfo();
            }
        }
        return null;
    }
}
