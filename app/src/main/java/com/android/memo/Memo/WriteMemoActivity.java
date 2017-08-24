package com.android.memo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

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
        //If there is any word
        if(memoTitle.getText().toString().length() > 0 ||
                memoContent.getText().toString().length() > 0) {
            AlertDialog.Builder dialogConfirm = new AlertDialog.Builder(this)
                    .setMessage("Save or discard the post?")
                    .setPositiveButton("Save", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            savePost();
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
    }
    /**
     * This method is to save the post
     */
    private void savePost(){
        //Make new post and make a new post
        Memo memo = new Memo(memoTitle.getText().toString(), memoContent.getText().toString(), System.currentTimeMillis());

        //To show the toast message
        if(Utilities.savePost(this, memo)){
            Toast.makeText(this, "Your post is saved", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Your post is not saved", Toast.LENGTH_SHORT).show();
        }

    }

}
