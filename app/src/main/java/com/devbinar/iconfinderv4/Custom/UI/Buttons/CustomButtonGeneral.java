package com.devbinar.iconfinderv4.Custom.UI.Buttons;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomButtonGeneral extends androidx.appcompat.widget.AppCompatButton {
    public CustomButtonGeneral(Context context) {
        super(context);
    }

    public CustomButtonGeneral(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButtonGeneral(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        final OnClickListener onCustomClickListener = l;
        l = new OnClickListener() {
            @Override
            public void onClick(View v) {
                assert onCustomClickListener != null;
                onCustomClickListener.onClick(v);
                setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setEnabled(true);
                    }
                }, 500);
            }
        };
        super.setOnClickListener(l);
    }
}
