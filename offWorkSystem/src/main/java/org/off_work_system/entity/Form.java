package org.off_work_system.entity;

import java.util.Date;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public class Form {

    //申请id
    private int formId;

    //申请者id
    private int userId;

    //申请当前状态
    //不同的审批状态、审批成功和审批失败分别用不同的数值表示
    private int formState;

    //请假类型
    //不用的请假类型用不同的数字表示。例如：公假、年假、产假……
    private int formType;

    //请假时长（天）
    //请假结束时间减去请假开始时间即为请假时长（这条其实有冗余，是否删去待定）
    private int formLength;

    //请假开始时间
    private Date formStartTime;

    //请假结束时间
    private Date formEndTime;

    private User user;

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFormState() {
        return formState;
    }

    public void setFormState(int formState) {
        this.formState = formState;
    }

    public int getFormType() {
        return formType;
    }

    public void setFormType(int formType) {
        this.formType = formType;
    }

    public int getFormLength() {
        return formLength;
    }

    public void setFormLength(int formLength) {
        this.formLength = formLength;
    }

    public Date getFormStartTime() {
        return formStartTime;
    }

    public void setFormStartTime(Date formStartTime) {
        this.formStartTime = formStartTime;
    }

    public Date getFormEndTime() {
        return formEndTime;
    }

    public void setFormEndTime(Date formEndTime) {
        this.formEndTime = formEndTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Form{" +
                "\nformId=" + formId +
                ", \nuserId=" + userId +
                ", \nformState=" + formState +
                ", \nformType=" + formType +
                ", \nformLength=" + formLength +
                ", \nformStartTime=" + formStartTime +
                ", \nformEndTime=" + formEndTime +
                ", \nuser=" + user +
                '}';
    }
}
