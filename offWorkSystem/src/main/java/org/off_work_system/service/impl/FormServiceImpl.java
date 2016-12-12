package org.off_work_system.service.impl;

import org.off_work_system.dao.DepartmentDao;
import org.off_work_system.dao.FormDao;
import org.off_work_system.dao.UserDao;
import org.off_work_system.entity.Department;
import org.off_work_system.entity.Form;
import org.off_work_system.entity.User;
import org.off_work_system.enums.FormTypeEnum;
import org.off_work_system.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/12.
 */
@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private FormDao formDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    public List<Form> getFormList(int offset, int limit) {
        return formDao.queryAll(offset, limit);
    }

    public int size() {
        return formDao.size();
    }

    public int addForm(int userId, int formType, int formLength, Date formStartTime, Date formEndTime) {
        FormTypeEnum formTypeEnum = FormTypeEnum.stateof(formType);
        if (formEndTime == null) {
            return 602;
        }
        Form form = Form.getInstance(0, userId, 1, formType, formLength, formStartTime, formEndTime);
        int result = formDao.addForm(form);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    public int advanceForm(int userId, int formId) {
        int visible = isVisible(formId, userId);

        if (visible != 200) {
            return visible;
        }

        Form form = formDao.queryById(formId);
        int result = advance(form);
        return result;
    }

    private int advance(Form form) {
        int curState = form.getFormState();

        int formType = form.getFormType();
        FormTypeEnum formTypeEnum = FormTypeEnum.stateof((formType));

        int nextState;
        switch (formTypeEnum) {
            case PUBLIC:
                nextState = longProcessAdvance(curState);
                break;
            case ANNUAL:
                nextState = shortProcessAdvance(curState);
                break;
            case SICK:
                if (form.getFormLength() < 15) {
                    nextState = shortProcessAdvance(curState);
                }
                else {
                    nextState = longProcessAdvance(curState);
                }
                break;
            case AFFAIR:
                if (form.getFormLength() < 15) {
                    nextState = shortProcessAdvance(curState);
                }
                else {
                    nextState = longProcessAdvance(curState);
                }
                break;
            case MARRIAGE:
                nextState = longProcessAdvance(curState);
                break;
            case MATERNITY:
                nextState = longProcessAdvance(curState);
                break;
            case PATERNITY:
                nextState = longProcessAdvance(curState);
                break;
            case INJURY:
                nextState = longProcessAdvance(curState);
                break;
            default:
                nextState = 999;
        }
        if (nextState == 999) {
            return 999;
        }

        form.setFormState(nextState);
        int result = formDao.updateForm(form);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    private int shortProcessAdvance(int curState) {
        if (curState == 1) {
            return 0;
        }
        else {
            return 999;
        }
    }

    private int longProcessAdvance(int curState) {
        if (curState == 1) {
            return 2;
        }
        else if (curState == 2) {
            return 0;
        }
        else {
            return 999;
        }
    }

    public int rejectForm(int userId, int formId) {
        int visible = isVisible(formId, userId);

        if (visible != 200) {
            return visible;
        }

        Form form = formDao.queryById(formId);
        form.setFormState(-1);
        int result = formDao.updateForm(form);
        if (result != 0) {
            return 200;
        }
        else {
            return 600;
        }
    }

    private int isVisible(int formId, int userId) {
        Form form = formDao.queryById(formId);
        User user = userDao.queryById(userId);

        if (form == null || user == null) {
            return 404;
        }

        int formType = form.getFormType();
        FormTypeEnum formTypeEnum = FormTypeEnum.stateof((formType));

        switch (formTypeEnum) {
            case PUBLIC:
                if (form.getFormLength() < 5) {
                    return permission(1, form, user);
                }
                else {
                    permission(2, form, user);
                }
            case ANNUAL:
                return permission(0, form, user);
            case SICK:
                if (form.getFormLength() < 15) {
                    return permission(0, form, user);
                }
                else {
                    return permission(1, form, user);
                }
            case AFFAIR:
                if (form.getFormLength() < 15) {
                    return permission(0, form, user);
                }
                else {
                    return permission(1, form, user);
                }
            case MARRIAGE:
                return permission(1, form, user);
            case MATERNITY:
                return permission(1, form, user);
            case PATERNITY:
                return permission(1, form, user);
            case INJURY:
                return permission(1, form, user);
            default: return 999;
        }
    }

    //flowType: 0代表短流程（只通过科室主任一步审核）, 1代表长流程（还需要上级部门审核）, 2代表长流程且需要领导审核
    private int permission(int flowType, Form form, User user) {
        /*
        if (form.getUser().getUserId() == user.getUserId()) {
            //本人查看
            return 200;
        }
        */

        int formState = form.getFormState();
        if (formState == 0 || formState == -1) {
            //已通过或者未通过的申请不能由本人以外的人查看
            return 403;
        }
        if (flowType == 0) {
            //短流程
            if (formState == 1) {
                Department requestDepartment = form.getUser().getDepartment();
                if (requestDepartment.getDepartmentId() == user.getUserDepartment() && user.getUserLeader() == 1) {
                    //user是申请者所在的科室的科室主任
                    return 200;
                }
                else {
                    return 403;
                }
            }
            else {
                return 999;
            }
        }
        else if (flowType == 1) {
            if (formState == 1) {
                Department requestDepartment = form.getUser().getDepartment();
                if (requestDepartment.getDepartmentId() == user.getUserDepartment() && user.getUserLeader() == 1) {
                    //user是申请者所在的科室的科室主任
                    return 200;
                }
                else {
                    return 403;
                }
            }
            else if (formState == 2) {
                Department requestDepartment = form.getUser().getDepartment();
                if (requestDepartment.getDepartmentParent() == user.getUserDepartment()) {
                    //user是申请者所在的科室的上级部门的成员
                    return 200;
                }
                else {
                    return 403;
                }
            }
            else {
                return 999;
            }
        }
        else {
            if (formState == 1) {
                Department requestDepartment = form.getUser().getDepartment();
                if (requestDepartment.getDepartmentId() == user.getUserDepartment() && user.getUserLeader() == 1) {
                    //user是申请者所在的科室的科室主任
                    return 200;
                }
                else {
                    return 403;
                }
            }
            else if (formState == 2) {
                Department requestDepartment = form.getUser().getDepartment();
                if (requestDepartment.getDepartmentParent() == user.getUserDepartment() && user.getUserLeader() == 1) {
                    //user是申请者所在的科室的上级部门的领导
                    return 200;
                }
                else {
                    return 403;
                }
            }
            else {
                return 999;
            }
        }
    }

    public List<Form> showVisibleFormList(int userId) {
        User user = userDao.queryById(userId);
        if (user == null) {
            return null;
        }
        List<Form> formList = formDao.queryAll(0, userDao.size());
        List<Form> result = new ArrayList<Form>();
        for (Form form : formList) {
            if (isVisible(form.getFormId(), userId) == 200) {
                form.setUser(null); //form内的user中存储着用户个人信息，输出前需要删除
                result.add(form);
            }
        }
        return result;
    }

    public List<Form> showMyFormList(int userId) {
        User user = userDao.queryById(userId);
        if (user == null) {
            return null;
        }
        List<Form> formList = formDao.queryAll(0, userDao.size());
        List<Form> result = new ArrayList<Form>();
        for (Form form : formList) {
            if (form.getUserId() == userId) {
                form.setUser(null); //form内的user中存储着用户个人信息，输出前需要删除
                result.add(form);
            }
        }
        return result;
    }
}
