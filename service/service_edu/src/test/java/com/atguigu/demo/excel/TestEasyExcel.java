package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gcq
 * @Create 2020-09-14
 */
public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "E:\\桌面\\2.xlsx";
        EasyExcel.read(filename,DemoData.class,new ExceListener()).sheet().doRead();
    }

    private static void tesetMethod1() {
        //实现excel写的操作
        //1、设置写入文件夹地址和excel地址名称
        String filename = "E:\\桌面\\2.xlsx";

        //2、调用easyexcel里面方法实现写操作
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    public static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy" + i);
            list.add(data);
        }
        return list;
    }
}