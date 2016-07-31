package com.pablopatarca.firebaseimplementation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pablopatarca.firebaseimplementation.R;
import com.pablopatarca.firebaseimplementation.models.Message;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Pablo on 30/7/16.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Message> mMessages;

    public MessagesAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder viewHolder, int position) {

        Message message = mMessages.get(position);
        viewHolder.userName.setText(message.userName);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(message.date);
        viewHolder.date.setText(calendar.get(Calendar.DAY_OF_MONTH)
                +"/"+calendar.get(Calendar.MONTH)
                +"/"+calendar.get(Calendar.YEAR)
                + " " + calendar.get(Calendar.HOUR)
                + ":" + calendar.get(Calendar.MINUTE)
                + ":" + calendar.get(Calendar.SECOND)
        );
        viewHolder.message.setText(message.message);
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView date;
        private TextView message;

        public ViewHolder(View view) {
            super(view);

            userName = (TextView)view.findViewById(R.id.userName);
            date = (TextView)view.findViewById(R.id.date);
            message = (TextView)view.findViewById(R.id.message);
        }
    }

}
