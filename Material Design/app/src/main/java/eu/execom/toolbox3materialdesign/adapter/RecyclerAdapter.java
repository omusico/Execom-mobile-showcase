package eu.execom.toolbox3materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;

import eu.execom.toolbox3materialdesign.R;

@EBean
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private List<String> strings = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String text = strings.get(position);
        holder.title.setText(text);
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    public void update(List<String> strings){
        this.strings = strings;
        notifyChanges();
    }

    @UiThread
    void notifyChanges(){
        notifyDataSetChanged();
    }
}