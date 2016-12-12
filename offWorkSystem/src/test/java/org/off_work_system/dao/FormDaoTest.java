package org.off_work_system.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.off_work_system.entity.Form;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 张栋迪 on 2016/12/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class FormDaoTest {

    @Resource
    private FormDao formDao;

    @Test
    public void queryById() throws Exception {
        int id = 1000;
        Form form = formDao.queryById(id);
        System.out.println(form);
    }

    @Test
    public void queryAll() throws Exception {
        List<Form> formList = formDao.queryAll(0, formDao.size());
        for (Form form : formList) {
            System.out.println(form);
        }
    }

    @Test
    public void addForm() throws Exception {
        Form form = Form.getInstance(0,
                1001,
                0,
                0,
                5,
                new Date(),
                new Date());
        int result = formDao.addForm(form);
        System.out.println(result);
    }

    @Test
    public void updateForm() throws Exception {
        Form form = Form.getInstance(1000,
                1001,
                1,
                0,
                5,
                new Date(),
                new Date());
        int result = formDao.updateForm(form);
        System.out.println(result);
    }

    @Test
    public void size() throws Exception {
        int size = formDao.size();
        System.out.println(size);
    }

}