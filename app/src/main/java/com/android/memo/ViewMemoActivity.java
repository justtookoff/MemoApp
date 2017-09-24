package com.android.memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Donghwee on 2017-08-27.
 */

public class ViewMemoActivity extends AppCompatActivity {
    private TextView memoTitle;
    private TextView memoContent;

    private String fileName;
    private Memo loadMemo = null;
    private long memoCreationTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_memo);
        setToolbar();

        memoTitle = (TextView) findViewById(R.id.memo_view_title);
        memoContent = (TextView) findViewById(R.id.memo_view_content);

        /* Upload the contents from the memo that the users clicked to see
         *
         */
        fileName = getIntent().getStringExtra(Utilities.EXTRAS_NOTE_FILENAME);
        if(fileName != null && !fileName.isEmpty() && fileName.endsWith(Utilities.FILE_EXTENSION)) {
            loadMemo = Utilities.getNoteByFileName(getApplicationContext(), fileName);

            if(loadMemo != null){
                memoTitle.setText(loadMemo.getTitle());
                memoContent.setText(loadMemo.getContent());
                memoCreationTime = loadMemo.getDateTime();
            }
            else{
                memoCreationTime = System.currentTimeMillis();
            }
        }
        /* Floating option to write -> this calls the WriteMemoActivity
         * contents should be uploaded too!
         */
        FloatingActionButton rewriteMemo = (FloatingActionButton) findViewById(R.id.memo_view_floating);
        rewriteMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WriteMemoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.memo_view_toolbar);
        setSupportActionBar(toolbar);
    }
}
