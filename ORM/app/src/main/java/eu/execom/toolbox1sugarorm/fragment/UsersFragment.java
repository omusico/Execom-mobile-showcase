package eu.execom.toolbox1sugarorm.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.adapter.UserListAdapter;
import eu.execom.toolbox1sugarorm.service.UserService;
import eu.execom.toolbox1sugarorm.model.User;

public class UsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_users, container, false);

        final RecyclerView userList = (RecyclerView) view.findViewById(R.id.users);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userList.setLayoutManager(layoutManager);

        final List<User> otherUsers = UserService.findOtherUsers(getActivity());
        final UserListAdapter userListAdapter = new UserListAdapter(otherUsers);
        userList.swapAdapter(userListAdapter, true);

        return view;
    }

}
