package com.example.brwtalk;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class Adapter extends ArrayAdapter
{
    Context ctx;
    List<Message> msg;
    LayoutInflater linf;


    public Adapter(Context x, int resource, List<Message> msg)
    {
        super(x, resource, msg);
        this.ctx = x;
        this.msg = msg;
        this.linf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Message message = msg.get(position);

        if (convertView == null)
        {
            convertView = linf.inflate(R.layout.msg, parent, false);

            String date = message.getDate().toString();
            String msgText = message.getMessage();
            String user = message.getUser().getEmail();
            TextView usertextView = convertView.findViewById(R.id.user);

            TextView timeTextView = convertView.findViewById(R.id.time);

            TextView messageTextView = convertView.findViewById(R.id.message);

            usertextView.setText(user);
            timeTextView.setText(date);
            messageTextView.setText(msgText);


//
        }
        return convertView;
    }
}
