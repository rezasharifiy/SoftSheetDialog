package com.rsmi.softsheetdialogfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rsmi.softsheet.SheetView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by R.Sharifi
 * on 28 Feb 2021
 */
class SampleView extends SheetView implements View.OnClickListener {

    public SampleView(@NotNull Context context, @Nullable Bundle arguments) {
        super(context, arguments);
        Button closeBtn = view.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.sheet_sample;
    }

    @Override
    public void onStopSheet() {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.closeBtn:
                dismiss();
                break;
        }
    }
}
