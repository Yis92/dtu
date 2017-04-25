package com.sixe.dtu.http.util;


/**
 * Http 常量类
 * Created by liuyi on 2015/11/12.
 */
public class HttpConstant {


    public static final String BASE_URL = "http://139.129.239.172:7710";

    /**
     * 1.	登录
     */
//    public static final String LOGIN = BASE_URL + "/php/check_usr.php";
    public static final String LOGIN = BASE_URL + "/php/check_user.php";

    /**
     * 2.	查看用户个人信息
     */
    public static final String QUERY_USER_INFO = BASE_URL + "/php/querry_user_info.php";

    /**
     * 3.	修改用户个人信息
     */
    public static final String UPDATE_USER_INFO = BASE_URL + "/php/update_user_info.php";

    /**
     * 4.	用户密码修改
     */
    public static final String UPDATE_USER_PWD = BASE_URL + "/php/update_user_pwd.php";

    /**
     * 5.	单位信息查询
     */
    public static final String QUERY_COMPANY = BASE_URL + "/php/querry_unit_info.php";

    /**
     * 6.	修改单位信息（管理员操作）
     */
    public static final String UPDATE_COMPANY = BASE_URL + "/php/update_unit_info.php";

    /**
     * 7.	查询本单位所有用户信息(管理员操作)
     */
    public static final String QUERRY_ALL_USERS_INFO = BASE_URL + "/php/querry_all_users_info.php";

    /**
     * 8.	查询本单位所有用户信息(管理员操作)
     */
    public static final String UPDATE_USER_INFO_BY_HOST = BASE_URL + "/php/update_user_info_by_host.php";

    /**
     * 9.	修改本单位用户密码(管理员操作)
     */
    public static final String UPDATE_USER_PWD_BY_HOST = BASE_URL + "/php/update_user_pwd_by_host.php";

    /**
     * 10.	添新增单位用户(管理员操作)
     */
    public static final String ADD_USER = BASE_URL + "/php/add_user.php";

    /**
     * 11.	删除用户(管理员操作)
     */
    public static final String DEL_USER = BASE_URL + "/php/del_user.php";

    /**
     * 12.	查询单位信息 - 添加员工的时候所用
     */
    public static final String QUERT_COMPANY_INFO = BASE_URL + "/php/querry_user_info2.php";

    /**
     * 13.	查询dtu信息
     */
    public static final String QUERRY_UNIT_INFO2 = BASE_URL + "/php/querry_unit_info2.php";

    /**
     * 14.	实时查询dtu数据
     */
//    public static final String QUERRY_DTU_REALDATA = BASE_URL + "/php/querry_dtu_realdata.php";
    public static final String QUERRY_DTU_REALDATA = BASE_URL + "/php/querry_dtu_real_data.php";

    /**
     * 15.	分组数据-根据分组id查询
     */
    public static final String QUERRY_DTU_GROUPDATA = BASE_URL + "/php/querry_dtu_group_data.php";

    /**
     * 16.	分组数据-默认情况
     */
    public static final String QUERRY_DTU_GROUPINFO = BASE_URL + "/php/querry_dtu_group_info.php";

    /**
     * 17.	dtu状态
     */
    public static final String QUERRY_DTU_STATE = BASE_URL + "/php/querry_dtu_state.php";

    /**
     * 18.	dtu信息
     */
    public static final String QUERRY_DTU_INFO = BASE_URL + "/php/querry_dtu_info.php";

    /**
     * 20.	查询dtu节点信息
     */
    public static final String QUERRY_DTU_NODE_INFO = BASE_URL + "/php/querry_dtu_sensor_node_info.php";

    /**
     * 21.  设置dtu信息(管理员和高级员工操作)
     */
    public static final String UPDATE_DTU_INFO = BASE_URL + "/php/update_dtu_info.php";

    /**
     * 22.  查询dtu所有要素的报警信息
     */
    public static final String QUERRY_DTU_SENSOR_WARNING_INFO = BASE_URL + "/php/querry_dtu_sensor_warning_info.php";
    public static final String QUERRY_DTU_SENSOR_WARNING_INFO2 = BASE_URL + "/php/querry_dtu_sensor_warning_info2.php";

    /**
     * 23.  查询dtu所有要素的报警信息
     */
    public static final String UPDATE_DTU_SENSOR_WARNING_INFO = BASE_URL + "/php/update_dtu_sensor_warning_info.php";

    /**
     * 24.  18.查询dtu报警消息
     */
    public static final String QUERRY_DTU_SENSOR_WARNING_MSG = BASE_URL + "/php/querry_dtu_sensor_warning_msg.php";

    /**
     * 25.  标记报警消息已处理
     */
    public static final String DEAL_DTU_SENSOR_WARNING_MSG = BASE_URL + "/php/deal_dtu_sensor_warning_msg.php";

    /**
     * 26.  查询控制节点的信息
     */
    public static final String QUERRY_DTU_CTRL_NODE_INFO = BASE_URL + "/php/querry_dtu_ctrl_node_info.php";

    /**
     * 27. 查询控制节点的任务状态
     */
    public static final String QUERRY_DTU_CTRL_NODE_TASK = BASE_URL + "/php/querry_dtu_ctrl_node_task.php";

    /**
     * 28. 查询dtu控制器任务分组信息
     */
    public static final String QUERRY_DTU_CTRL_TASK_GROUP_INFO = BASE_URL + "/php/querry_dtu_ctrl_task_group_info.php";

    /**
     * 30. 设置控制节点的任务状态
     */
    public static final String UPDATE_DTU_CTRL_NODE_TASK = BASE_URL + "/php/update_dtu_ctrl_node_task.php";


}