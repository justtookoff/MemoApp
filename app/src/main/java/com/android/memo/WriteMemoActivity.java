package com.android.memo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.android.memo.Memo;
import com.android.memo.R;

/**
 * Created by Donghwee on 2017-08-24.
 */

public class WriteMemoActivity extends AppCompatActivity{
    private EditText memoTitle;
    private EditText memoContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_write_memo);
        setToolbar();

        memoTitle = (EditText) findViewById(R.id.memo_title);
        memoContent = (EditText) findViewById(R.id.memo_content);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.memo_toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * When the back button get pressed
     */
    @Override
    public void onBackPressed(){
        //If there is any word, show the dialog to ask users the options [save, discard, cancel]
        if(memoTitle.getText().toString().length() > 0 ||
                memoContent.getText().toString().length() > 0) {
            AlertDialog.Builder dialogConfirm = new AlertDialog.Builder(this)
                    .setMessage("Save or discard the post?")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            saveMemo();
                            finish();
                        }
                    })
                    .setNegativeButton("Discard", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            finish();
                        }
                    })
                    .setNeutralButton("Cancel", null);
            dialogConfirm.show();
        }
        //if there is nothing just finish it
        else{
            finish();
        }
    }
    /**
     * This method is to save the post
     */
    private void saveMemo(){
        //Make new post and make a new post
        Memo memo = new Memo(this.memoTitle.getText().toString(), this.memoContent.getText().toString(), System.currentTimeMillis());

        //To save the memo through the utilities file
        if(Utilities.savePost(this, memo)){
            Toast.makeText(this, "Your post is saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Your post is not saved", Toast.LENGTH_SHORT).show();
        }

    }

}
