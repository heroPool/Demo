package com.example.okutilstest.bean;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/5/4.
 */

public class A<T> {
    T[] array;
    int i;

    @Override
    public String toString() {
        return "A{" +
                "array=" + Arrays.toString(array) +
                ", i=" + i +
                '}';
    }
}
