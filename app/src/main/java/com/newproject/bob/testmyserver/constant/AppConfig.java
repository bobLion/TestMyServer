package com.newproject.bob.testmyserver.constant;

/**
 * Created by Administrator on 2018/2/1.
 */

public class AppConfig {

    public final static String BASE_URL_PATH = "http://172.20.25.153:8080";
    public final static String DELETE_STUDENT_BY_NAME = BASE_URL_PATH.concat("/delete_by_name");
    public final static String INSERT_STUDENT = BASE_URL_PATH.concat("/insert");
    public final static String ADD_MORE_STUDENTS = BASE_URL_PATH.concat("/add_more_students");

}
