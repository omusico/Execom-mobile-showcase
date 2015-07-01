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
import eu.execom.toolbox1sugarorm.adapter.MessageListAdapter;
import eu.execom.toolbox1sugarorm.service.MessageService;
import eu.execom.toolbox1sugarorm.service.UserService;
import eu.execom.toolbox1sugarorm.model.Message;
import eu.execom.toolbox1sugarorm.model.User;

public class MessagesForMeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_messages_for_me, container, false);

        final RecyclerView messageList = (RecyclerView) view.findViewById(R.id.messages);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageList.setLayoutManager(layoutManager);

        final User currentUser = UserService.getCurrentUser(getActivity());
        final List<Message> messagesForUser = MessageService.getMessagesForUser(currentUser);
        final MessageListAdapter messageListAdapter = new MessageListAdapter(messagesForUser);
        messageList.swapAdapter(messageListAdapter, true);

        return view;
    }

}
