package com.exportcontact.contactoswhatsapp.exportimportcontact.whatscontact.util;

import android.os.SystemClock;
import android.view.View;

public abstract class SingleClickListener implements View.OnClickListener {
    protected int defaultInterval;
    private long lastTimeClicked;

    public SingleClickListener() {
        this(1000);
    }

    public SingleClickListener(int i) {
        this.lastTimeClicked = 0;
        this.defaultInterval = i;
    }

    public abstract void performClick(View view);

    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - this.lastTimeClicked >= ((long) this.defaultInterval)) {
            this.lastTimeClicked = SystemClock.elapsedRealtime();
            performClick(view);
        }
    }
}
