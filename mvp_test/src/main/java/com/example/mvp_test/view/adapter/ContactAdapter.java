package com.example.mvp_test.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvp_test.R;
import com.example.mvp_test.bean.UserBean;
import com.example.mvp_test.presenter.IPresenterContact;
import com.example.mvp_test.view.holder.FooterHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/3.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;

    IPresenterContact mPresenter;
    Context context;
    ArrayList<UserBean> contactList;

    boolean isMore;
    boolean isDragging;
    String footerText;

    public ContactAdapter(IPresenterContact mPresenter, Context context, ArrayList<UserBean> contactList) {
        this.mPresenter = mPresenter;
        this.context = context;
        this.contactList = contactList;
    }

    public void setDragging(boolean dragging) {

        this.isDragging = dragging;
        notifyDataSetChanged();

    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
        notifyDataSetChanged();

    }

    public boolean isMore() {

        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    public void initContactList(ArrayList<UserBean> contactList) {
        this.contactList.clear();
        this.contactList.addAll(contactList);
        notifyDataSetChanged();
    }

    public void addContactList(ArrayList<UserBean> contactList) {
        this.contactList.addAll(contactList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return new FooterHolder(View.inflate(context, R.layout.item_footer, null));
            case TYPE_ITEM:

                return new ContactHolder(View.inflate(context, R.layout.item_contact, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder perentholder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            FooterHolder holder = (FooterHolder) perentholder;
            holder.tvFooter.setVisibility(View.VISIBLE);
            holder.tvFooter.setText(footerText);
            return;
        }
        ContactHolder holder = (ContactHolder) perentholder;
        UserBean user = contactList.get(position);
        holder.tvNick.setText(user.getNick());
        holder.tvUserName.setText(user.getUserName());
        mPresenter.showImage(context, user.getUserName(), holder.ivAvatar, R.drawable.default_face, isDragging);
    }

    @Override
    public int getItemCount() {
        return contactList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvUserName, tvNick;

        public ContactHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvNick = (TextView) itemView.findViewById(R.id.tvNick);
        }
    }
}
