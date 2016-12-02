package org.off_work_system.entity;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public class Department {

    //部门id
    private int departmentId;

    //部门名字
    private String departmentName;

    //部门所属的上级部门id
    private int departmentParent;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getDepartmentParent() {
        return departmentParent;
    }

    public void setDepartmentParent(int departmentParent) {
        this.departmentParent = departmentParent;
    }

    @Override
    public String toString() {
        return "Department{" +
                "\ndepartmentId=" + departmentId +
                ", \ndepartmentName='" + departmentName + '\'' +
                ", \ndepartmentParent=" + departmentParent +
                '}';
    }
}
