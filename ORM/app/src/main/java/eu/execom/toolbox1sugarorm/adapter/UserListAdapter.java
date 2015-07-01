package eu.execom.toolbox1sugarorm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.service.UserService;
import eu.execom.toolbox1sugarorm.model.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder>{

    private List<User> users;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public Context context;

        public TextView name;

        public ViewHolder(View view, Context context) {
            super(view);

            this.context = context;

            name = (TextView) view.findViewById(R.id.row_user_name);
        }
    }

    public UserListAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = users.get(position);
        holder.name.setText(user.getName());
        final boolean isConnected = UserService.isConnected(holder.context, user);
        holder.name.setSelected(isConnected);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isConnected = UserService.isConnected(holder.context, user);
                if (isConnected){
                    UserService.dissconect(holder.context, user);
                    view.setSelected(false);
                } else {
                    UserService.connect(holder.context, user);
                    view.setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
