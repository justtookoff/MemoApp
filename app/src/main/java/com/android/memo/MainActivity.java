package com.android.memo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        listView = (ListView) findViewById(R.id.memo_listview);

        //Floating action button for writing
        FloatingActionButton writePost = (FloatingActionButton) findViewById(R.id.write_post);
        writePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WriteMemoActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }
    /**
     * This is for action menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_edit:
                return true;
            case R.id.action_categorized:
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Restart the activity
     */
    @Override
    protected  void onResume(){
        super.onResume();

        //load saved notes into the listview
        //first, reset the listview
        listView.setAdapter(null);
        ArrayList<Memo> memos = Utilities.getAllSavedPosts(getApplicationContext());

        //sort notes from new to old
        Collections.sort(memos, new Comparator<Memo>() {
            @Override
            public int compare(Memo lhs, Memo rhs) {
                if(lhs.getDateTime() > rhs.getDateTime()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        if(memos != null && memos.size() > 0) { //check if we have any notes!
            final MemoAdapter na = new MemoAdapter(this, R.layout.layout_cardview, memos);
            listView.setAdapter(na);

            //set click listener for items in the list, by clicking each item the note should be loaded into NoteActivity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String fileName = ((Memo) listView.getItemAtPosition(position)).getDateTime()
                            + Utilities.FILE_EXTENSION;

                    Intent viewIntent = new Intent(getApplicationContext(), ViewMemoActivity.class);
                    viewIntent.putExtra(Utilities.EXTRAS_NOTE_FILENAME, fileName);
                    startActivity(viewIntent);
                }
            });
        }
        else { //remind user that we have no notes!
            Toast.makeText(getApplicationContext(), "you have no saved notes!\ncreate some new notes :)"
                    , Toast.LENGTH_SHORT).show();
        }
    }
}
