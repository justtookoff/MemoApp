package com.android.memo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.memo.Memo;

import java.util.List;

/**
 * Created by Donghwee on 2017-08-23.
 */

public class MemoAdapter extends ArrayAdapter<Memo> {

    public MemoAdapter(Context context, int resource, List<Memo> memos) {
        super(context, resource, memos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_cardview, null);
        }

        Memo memo = getItem(position);

        //If post exists, set the texts on the list
        if(memo != null){
            TextView title = (TextView) convertView.findViewById(R.id.memo_cardview_title);
            TextView content = (TextView) convertView.findViewById(R.id.memo_cardview_content);
            TextView date = (TextView) convertView.findViewById(R.id.memo_cardview_dateTime);

            title.setText(memo.getTitle());

            //Check the length of the content
            if(memo.getContent().length() > 50) {
                content.setText(memo.getContent().substring(0, 50));
            }
            else{
                content.setText(memo.getContent());
            }

            date.setText(memo.getDateTimeFormatted(getContext()));
        }
        return convertView;
    }

}
