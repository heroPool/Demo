package com.example.mvp_test.view.iview;

/**
 * Created by Administrator on 2017/5/3.
 */

public interface IViewContact<T> extends IViewBase, IViewShowProgerss {
    void showResult(T t, int action);
}
