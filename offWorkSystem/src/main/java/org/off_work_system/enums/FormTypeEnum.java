package org.off_work_system.enums;

/**
 * Created by 张栋迪 on 2016/12/12.
 */
public enum FormTypeEnum {
    ANNUAL(1, "年假"),
    PUBLIC(2, "公假"),
    MARRIAGE(3, "婚假"),
    MATERNITY(4, "产假"),
    INJURY(5, "工伤假"),
    PATERNITY(6, "陪产假"),
    SICK(7, "病假"),
    AFFAIR(8, "事假");

    private int state;
    private String stateInfo;

    FormTypeEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static FormTypeEnum stateof(int index) {
        for ( FormTypeEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

    public static String typeInfoOf(int index){
        for ( FormTypeEnum state : values()) {
            if (state.getState() == index) {
                return state.getStateInfo();
            }
        }
        return null;
    }
}
