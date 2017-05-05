package com.example.mvp_test.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvp_test.R;
import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.presenter.IPresenterLogin;
import com.example.mvp_test.presenter.PresenterLogin;
import com.example.mvp_test.view.iview.IViewLogin;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements IViewLogin<UserBean> {
    IPresenterLogin mPresenter;
    EditText metUserName, metPassword;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_login, container, false);
        mPresenter = new PresenterLogin(this);
        initView(inflate);
        setListener(inflate);
        return inflate;
    }

    private void setListener(View inflate) {
        inflate.findViewById(R.id.btn_Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = metUserName.getText().toString();
                String passwortd = metPassword.getText().toString();
                try {
                    Log.e("Loginfragment", "Login()zhixing");
                    mPresenter.login(getActivity(), userName, passwortd);
                } catch (Exception e) {
                    if (e.getMessage().equals("账号不能为空")) {
                        metUserName.setError("账号不能为空");
                        metUserName.requestFocus();
                    } else if ("密码不能为空".equals(e.getMessage())) {
                        metPassword.setError("密码不能为空");
                        metPassword.requestFocus();
                    }
                }
            }
        });
    }

    private void initView(View inflate) {
        metUserName = (EditText) inflate.findViewById(R.id.etUserName);
        metPassword = (EditText) inflate.findViewById(R.id.etPassword);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResult(UserBean userBean) {
        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
        Log.i("main", userBean.toString());
    }
}
