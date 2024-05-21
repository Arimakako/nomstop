package com.thanhloi.nomstop.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.thanhloi.nomstop.R;
import com.thanhloi.nomstop.activity.MainActivity;
import com.thanhloi.nomstop.adapter.ContactAdapter;
import com.thanhloi.nomstop.constant.AboutUsConfig;
import com.thanhloi.nomstop.constant.GlobalFunction;
import com.thanhloi.nomstop.databinding.FragmentContactBinding;
import com.thanhloi.nomstop.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends BaseFragment {

    private FragmentContactBinding mFragmentContactBinding;
    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        initUi();
        initListener();

        return mFragmentContactBinding.getRoot();
    }

    private void initUi() {
        mFragmentContactBinding.tvAboutUsContent.setText(AboutUsConfig.ABOUT_US_CONTENT);
        mFragmentContactBinding.tvAboutUsWebsite.setText(AboutUsConfig.ABOUT_US_WEBSITE_TITLE);

        mContactAdapter = new ContactAdapter(getActivity(), getListContact(),
                () -> GlobalFunction.callPhoneNumber(getActivity()));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvData.setNestedScrollingEnabled(false);
        mFragmentContactBinding.rcvData.setFocusable(false);
        mFragmentContactBinding.rcvData.setLayoutManager(layoutManager);
        mFragmentContactBinding.rcvData.setAdapter(mContactAdapter);
    }

    private void initListener() {
        mFragmentContactBinding.layoutWebsite.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(AboutUsConfig.WEBSITE))));
    }

    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));


        return contactArrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContactAdapter.release();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setToolBar(false, getString(R.string.contact));
        }
    }
}
