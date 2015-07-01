package eu.execom.toolbox3materialdesign.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater inflater;
    private ArrayList<String> items;
    private View headerView;

    public RecyclerAdapter(Context context, ArrayList<String> items, View headerView) {
        inflater = LayoutInflater.from(context);
        this.items = items;
        this.headerView = headerView;
    }

    @Override
    public int getItemCount() {
        if (headerView == null) {
            return items.size();
        } else {
            return items.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(headerView);
        } else {
            return new ItemViewHolder(inflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).textView.setText(items.get(position - 1));
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        public ItemViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(android.R.id.text1);
        }
    }
}