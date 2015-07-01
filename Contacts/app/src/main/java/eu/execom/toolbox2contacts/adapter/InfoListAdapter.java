package eu.execom.toolbox2contacts.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;

import eu.execom.toolbox2contacts.R;
import eu.execom.toolbox2contacts.model.ContactInformation;
import eu.execom.toolbox2contacts.service.InfoService;

public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {

    private ArrayList<ContactInformation> contactInformation;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;

        public TextView value;
        public TextView type;
        public Spinner categorySelector;

        public ViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            this.value = (TextView) view.findViewById(R.id.info_value);
            this.type = (TextView) view.findViewById(R.id.info_type);
            this.categorySelector = (Spinner) view.findViewById(R.id.info_category);
        }
    }

    public InfoListAdapter(ArrayList<ContactInformation> contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public InfoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_info, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ContactInformation info = contactInformation.get(position);

        holder.type.setText(info.getType().getStringId());

        holder.value.setText(info.getValue());
        holder.value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    info.setValue(textView.getText().toString());
                    InfoService.update(info);
                }
                return false;
            }
        });

        final ContactInformation.Category[] categories = ContactInformation.Category.values();
        final ArrayAdapter<ContactInformation.Category> categoryAdapter = new ArrayAdapter<>(holder.context, R.layout.drop_down_item, categories);
        holder.categorySelector.setAdapter(categoryAdapter);
        holder.categorySelector.setSelection(ArrayUtils.indexOf(categories, info.getCategory()), false);
        holder.categorySelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                info.setCategory(categories[position]);
                InfoService.update(info);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return contactInformation.size();
    }

}
