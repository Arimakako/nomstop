package com.thanhloi.nomstop.fragment.admin;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.thanhloi.nomstop.R;
import com.thanhloi.nomstop.activity.AdminMainActivity;
import com.thanhloi.nomstop.activity.AdminReportActivity;
import com.thanhloi.nomstop.activity.ChangePasswordActivity;
import com.thanhloi.nomstop.activity.SignInActivity;
import com.thanhloi.nomstop.constant.GlobalFunction;
import com.thanhloi.nomstop.databinding.FragmentAdminAccountBinding;
import com.thanhloi.nomstop.fragment.BaseFragment;
import com.thanhloi.nomstop.prefs.DataStoreManager;

import java.util.Locale;

public class AdminAccountFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentAdminAccountBinding fragmentAdminAccountBinding = FragmentAdminAccountBinding.inflate(inflater, container, false);

        fragmentAdminAccountBinding.tvEmail.setText(DataStoreManager.getUser().getEmail());

        fragmentAdminAccountBinding.layoutReport.setOnClickListener(v -> onClickReport());
        fragmentAdminAccountBinding.layoutSignOut.setOnClickListener(v -> onClickSignOut());
        fragmentAdminAccountBinding.layoutChangePassword.setOnClickListener(v -> onClickChangePassword());
        fragmentAdminAccountBinding.layoutChangeLanguage.setOnClickListener(v -> showLanguageDialog()); // Thêm sự kiện nhấn vào layoutChangeLanguage

        return fragmentAdminAccountBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        if (getActivity() != null) {
            ((AdminMainActivity) getActivity()).setToolBar(getString(R.string.account));
        }
    }

    private void onClickReport() {
        GlobalFunction.startActivity(getActivity(), AdminReportActivity.class);
    }

    private void onClickChangePassword() {
        GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class);
    }

    private void onClickSignOut() {
        if (getActivity() == null) {
            return;
        }
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }

    private void showLanguageDialog() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.change_language)
                .items(R.array.language_options)
                .itemsCallback((dialog, view, which, text) -> {
                    String selectedLanguage = (which == 0) ? "en" : "vi";
                    DataStoreManager.setLanguage(selectedLanguage);
                    applyLanguage(selectedLanguage);
                    getActivity().recreate();
                })
                .show();
    }

    private void applyLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
