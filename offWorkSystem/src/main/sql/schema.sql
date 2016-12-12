CREATE DATABASE off_work_system;

use off_work_system;


CREATE TABLE user(
`user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
`user_username` varchar(20) NOT NULL COMMENT '用户账户',
`user_password` char(32) NOT NULL COMMENT '用户密码',
`user_name` varchar(20) NOT NULL COMMENT '用户姓名',
`user_sex` char(10) NOT NULL COMMENT '用户性别',
`user_age` int NOT NULL COMMENT '用户年龄',
`user_department` int NOT NULL COMMENT '用户所属部门id',
-- 每个第一级或者第二季部门一般有一个或者多个是部门的领导
`user_leader` int COMMENT '是否是所属部门的领导,1代表是 0代表不是',
-- 默认每个人有年假10天
`user_time_left` int DEFAULT 10 COMMENT '用户今年年假剩余时间',
PRIMARY KEY (user_id)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE form(
`form_id` int NOT NULL AUTO_INCREMENT COMMENT '申请id',
`user_id` int NOT NULL COMMENT '申请者id',
`form_state` int COMMENT '申请状态',
`form_type` int COMMENT '申请请假类型',
-- 请假结束时间减去请假开始时间即为请假时长（这条其实有冗余，是否删去待定）
`form_length` int COMMENT '请假时长（天）',
-- 记录请假时间开始和结束不仅是因为需要备案，同时由于请假期间并不是每一天都是请假人的工作日，
-- 科室负责人在调用接口来确认请假人需要扣除多少奖金的时候也需要这两个时间结合科室的轮休表换算（本系统只负责提供接口，不实现）
`form_start_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '请假开始时间',
`form_end_time` TIMESTAMP  DEFAULT CURRENT_TIMESTAMP COMMENT '请假结束时间',
PRIMARY KEY (form_id)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='请假申请表';

CREATE TABLE department(
`department_id` int NOT NULL COMMENT '部门id',
`department_name` varchar(100) NOT NULL COMMENT '部门名称',
-- 医院的部门是一个简单的两层结构：第一层有三个部门，分别是医务科、护理部、勤工部,这三个部门没有上级部门；
-- 第二层有多个科室，上级部门是第一层的三个部门之一。
`department_parent` int DEFAULT -1 COMMENT '上级部门id',
PRIMARY KEY (department_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';


-- 初始化数据库

-- 导入初始的三个一级部门以及几个初始的二级部门
insert into department(department_id, department_name, department_parent)
VALUES
(1000, '医务科', -1),
(1001, '护理部', -1),
(1002, '勤工部', -1),
(1003, '普外一', 1000),
(1004, '普外二', 1000),
(1005, '护理一', 1001);

-- 导入初始的用户信息
-- 涉及密码需要实现md5相关加密后再修改
insert into user
(user_username, user_password, user_name, user_sex, user_age, user_department, user_leader, user_time_left)
VALUES
('y1','000000', '医务科科长', '男', 46, 1000, 1, 10),
('y2','000000', '医务科员工', '男', 28, 1000, 0, 10),
('h1','000000', '护理部科长', '女', 49, 1001, 1, 10),
('h2','000000', '护理部员工', '男', 21, 1001, 0, 10),
('p11','000000', '普外一科主任', '男', 48, 1003, 1, 10),
('p12','000000', '普外一员工一', '男', 22, 1003, 0, 10),
('p13','000000', '普外一员工二', '女', 35, 1003, 0, 10),
('p21','000000', '普外二科主任', '男', 50, 1004, 1, 10),
('p22','000000', '普外二员工一', '男', 29, 1004, 0, 10),
('p23','000000', '普外二员工二', '男', 37, 1004, 0, 10),
('h11','000000', '护理一科主任', '女', 44, 1005, 1, 10),
('h12','000000', '护理一员工一', '女', 24, 1005, 0, 10),
('h13','000000', '护理一员工二', '女', 23, 1005, 0, 10);