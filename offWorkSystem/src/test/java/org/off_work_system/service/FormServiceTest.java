package org.off_work_system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 张栋迪 on 2016/12/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class FormServiceTest {

    @Autowired
    private FormService formService;

    @Test
    public void getFormList() throws Exception {
        List<Form> formList = formService.getFormList(0, formService.size());
        for (Form form : formList) {
            System.out.println(form);
        }
    }

    @Test
    public void size() throws Exception {
        int size = formService.size();
        System.out.println("size:" + size);
    }

    @Test
    public void addForm() throws Exception {
        int result = formService.addForm(1005, 7, 16, new Date(), new Date());
        System.out.println(result);
    }

    @Test
    public void advanceForm() throws Exception {
        int result = formService.advanceForm(1001, 1005);
        System.out.println(result);
    }

    @Test
    public void rejectForm() throws Exception {
        int result = formService.rejectForm(1004, 1002);
        System.out.println("reject result: " + result);
    }

}