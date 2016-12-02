package org.off_work_system.dao;

import org.apache.ibatis.annotations.Param;
import org.off_work_system.entity.Form;

import java.util.List;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
public interface FormDao {

    Form queryById(@Param("formId") int formId);

    List<Form> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    int addForm(@Param("form") Form form);

    int updateForm(@Param("form") Form form);
}
