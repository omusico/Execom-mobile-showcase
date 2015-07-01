package eu.execom.toolbox1sugarorm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.model.Message;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private List<Message> messages;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView from;
        public TextView text;
        public TextView time;

        public ViewHolder(View view) {
            super(view);

            from = (TextView) view.findViewById(R.id.row_message_from);
            text = (TextView) view.findViewById(R.id.row_message_text);
            time = (TextView) view.findViewById(R.id.row_message_time);
        }
    }

    public MessageListAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Message message = messages.get(position);

        final DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

        holder.from.setText(message.getAuthor().getName());
        holder.text.setText(message.getText());
        holder.time.setText(dateFormat.format(message.getDateTime()));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
