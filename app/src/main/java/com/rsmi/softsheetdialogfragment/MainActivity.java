package com.rsmi.softsheetdialogfragment;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.rsmi.softsheet.BottomSheetContainer;

/**
 * Created by R.Sharifi
 * on 28 Feb 2021
 */
class MainActivity extends AppCompatActivity {

    private Button showBtn;
    private BottomSheetContainer container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoordinatorLayout rootView = findViewById(R.id.root);

        container = new BottomSheetContainer(rootView, null);


        showBtn = findViewById(R.id.showBottomSheetBtn);
        showBtn.setOnClickListener(v -> {
            container.showView(new SampleView(this,null),false);
        });
    }
}
