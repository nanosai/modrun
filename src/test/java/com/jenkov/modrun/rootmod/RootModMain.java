package com.jenkov.modrun.rootmod;

import com.jenkov.modrun.mod.TestPojo;

/**
 * Created by jjenkov on 23-10-2016.
 */
public class RootModMain {


    public static void main(String[] args) {

        System.out.println(args[0]);
        System.out.println(args[1]);

        TestPojo testPojo = new TestPojo();

        testPojo.doIt();
    }
}
