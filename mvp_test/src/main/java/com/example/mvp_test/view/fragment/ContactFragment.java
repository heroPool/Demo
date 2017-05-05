package com.example.mvp_test.view.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp_test.I;
import com.example.mvp_test.R;
import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.model.utils.ConvertUtils;
import com.example.mvp_test.presenter.IPresenterContact;
import com.example.mvp_test.view.adapter.ContactAdapter;
import com.example.mvp_test.view.iview.IViewContact;

import java.util.ArrayList;

import static android.R.attr.action;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements IViewContact<UserBean[]> {

    IPresenterContact mPresenter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mTvRefreshHint;
    RecyclerView mrvContact;
    LinearLayoutManager linearLayoutManager;

    ContactAdapter mAdapter;
    ArrayList<UserBean> mContactList;
    int pageId;
    ProgressDialog mDialog;

    public ContactFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(inflate);
        setListener();
        return inflate;
    }

    private void setListener() {
        setPullDownListener();
        mrvContact.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                int lastPostion = linearLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastPostion == mAdapter.getItemCount() - 1 && mAdapter.isMore()) {
                    pageId++;
                    downLoadContactList(pageId, I.ACTION_PULL_UP);
                }
            }
        });
    }

    private void setPullDownListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId = 1;
                downLoadContactList(pageId, I.ACTION_DOWNLOAD);

            }
        });
    }

    private void downLoadContactList(int pageId, int actionDownload) {
        mPresenter.downloadContactList(getActivity(), "a", pageId, action);
    }

    private void initView(View layout) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.srl);
        mTvRefreshHint = (TextView) layout.findViewById(R.id.tvRefreshHint);
        mrvContact = (RecyclerView) layout.findViewById(R.id.rvContact);
        linearLayoutManager = new LinearLayoutManager(getContext());
        mrvContact.setLayoutManager(linearLayoutManager);
        mContactList = new ArrayList<>();
        mAdapter = new ContactAdapter(mPresenter, getActivity(), mContactList);
        mrvContact.setAdapter(mAdapter);
        pageId = 1;
        downLoadContactList(pageId, I.ACTION_DOWNLOAD);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.release();

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog(Context context, String message) {

        mDialog = new ProgressDialog(context);
        mDialog.setTitle("加载联系人");
        mDialog.setMessage(message);
        mDialog.show();

    }

    @Override
    public void hideDialog(Context context) {
        mDialog.dismiss();
        mDialog = null;
    }

    @Override
    public void showResult(UserBean[] result, int action) {
        mAdapter.setMore(result != null && result.length > 0);
        if (!mAdapter.isMore()) {
            if (action == I.ACTION_PULL_UP) {
                mAdapter.setFooterText("没有更多数据");
            }
            return;
        }
        ArrayList<UserBean> contactList = ConvertUtils.array2List(result);
        mAdapter.setFooterText("加载更多数据");
        switch (action) {
            case I.ACTION_DOWNLOAD:
                mAdapter.initContactList(contactList);
                break;
            case I.ACTION_PULL_DOWN:
                mAdapter.initContactList(contactList);
                mSwipeRefreshLayout.setRefreshing(false);
                mTvRefreshHint.setVisibility(View.GONE);
                break;
            case I.ACTION_PULL_UP:
                mAdapter.addContactList(contactList);
                break;
        }
    }
}
