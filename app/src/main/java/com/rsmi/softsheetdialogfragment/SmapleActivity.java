package com.rsmi.softsheetdialogfragment;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rsmi.softsheet.BottomSheetContainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.Button;

public class SmapleActivity extends AppCompatActivity {

    private Button showBtn;
    private BottomSheetContainer container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smaple);

        CoordinatorLayout rootView = findViewById(R.id.root);

        container = new BottomSheetContainer(rootView, null);


        showBtn = findViewById(R.id.showBottomSheetBtn);
        showBtn.setOnClickListener(v -> {
            container.showView(new SampleView(this,null),false);
        });
    }
}