package com.thanhloi.nomstop.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.thanhloi.nomstop.ControllerApplication;
import com.thanhloi.nomstop.R;
import com.thanhloi.nomstop.adapter.OrderAdapter;
import com.thanhloi.nomstop.databinding.ActivityOrderHistoryBinding;
import com.thanhloi.nomstop.model.Order;
import com.thanhloi.nomstop.prefs.DataStoreManager;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends BaseActivity {

    private ActivityOrderHistoryBinding mActivityOrderHistoryBinding;
    private List<Order> mListOrder;
    private OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHistoryBinding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(mActivityOrderHistoryBinding.getRoot());

        initToolbar();
        initView();
        getListOrders();
    }

    private void initToolbar() {
        mActivityOrderHistoryBinding.toolbar.imgBack.setVisibility(View.VISIBLE);
        mActivityOrderHistoryBinding.toolbar.imgCart.setVisibility(View.GONE);
        mActivityOrderHistoryBinding.toolbar.tvTitle.setText(getString(R.string.order_history));

        mActivityOrderHistoryBinding.toolbar.imgBack.setOnClickListener(v -> onBackPressed());
    }

    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mActivityOrderHistoryBinding.rcvOrderHistory.setLayoutManager(linearLayoutManager);
    }

    public void getListOrders() {
        ControllerApplication.get(this).getBookingDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (mListOrder != null) {
                            mListOrder.clear();
                        } else {
                            mListOrder = new ArrayList<>();
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Order order = dataSnapshot.getValue(Order.class);
                            if (order != null) {
                                String strEmail = DataStoreManager.getUser().getEmail();
                                if (strEmail.equalsIgnoreCase(order.getEmail())) {
                                    mListOrder.add(0, order);
                                }
                            }
                        }
                        mOrderAdapter = new OrderAdapter(OrderHistoryActivity.this, mListOrder);
                        mActivityOrderHistoryBinding.rcvOrderHistory.setAdapter(mOrderAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrderAdapter != null) {
            mOrderAdapter.release();
        }
    }
}