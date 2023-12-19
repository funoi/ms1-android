package com.funoi.mapp55.service;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.funoi.mapp55.vo.Student;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SubmitServiceTest {
    @Test
    public void testRequestByPost() throws Exception {
        String url = "http://172.28.99.151:8080/ms1/addStu";
        Student student = new Student("芙宁娜", "女", "c", "d", "me");
        Gson gson = new Gson();
        String json = gson.toJson(student);

        SubmitService service = new SubmitService();
        String msg = service.submitDataByPost(url, json);

        System.out.println(msg);
    }
}
