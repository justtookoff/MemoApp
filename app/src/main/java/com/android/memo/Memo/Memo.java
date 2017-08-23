package com.android.memo.Memo;

import android.content.Context;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Donghwee on 2017-08-23.
 */

public class Memo implements Serializable {
    private String title;
    private String content;
    private long dateTime;

    /*
     * Constructor
     */
    public Memo(String title, String content, long dateTime) {
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
    }

    /*
     * To set the time with the accurate time zone
     */
    public String getDateTimeFormatted(Context context) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"
                , context.getResources().getConfiguration().locale);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(this.dateTime));
    }

    /*
     * Getters
     */
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getDateTime() {
        return dateTime;
    }

    /*
     * Setters
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }
}
