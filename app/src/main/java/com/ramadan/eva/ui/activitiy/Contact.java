package com.ramadan.eva.ui.activitiy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramadan.eva.R;
import com.ramadan.eva.ui.adapter.ContactAdapter;

public class Contact extends AppCompatActivity {
    RecyclerView recyclerView;
    public static ContactAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(adapter);

    }

}
