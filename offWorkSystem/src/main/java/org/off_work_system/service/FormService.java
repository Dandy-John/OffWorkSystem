package org.off_work_system.service;

import org.off_work_system.entity.Form;

import java.util.Date;
import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/12.
 */
public interface FormService {

    List<Form> getFormList(int offset, int limit);

    int size();

    int addForm(int userId, int formType, int formLength, Date formStartTime, Date formEndTime);

    int advanceForm(int userId, int formId);

    int rejectForm(int userId, int formId);
}
