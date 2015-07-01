package eu.execom.toolbox1sugarorm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import eu.execom.toolbox1sugarorm.R;
import eu.execom.toolbox1sugarorm.adapter.MessageListAdapter;
import eu.execom.toolbox1sugarorm.service.MessageService;
import eu.execom.toolbox1sugarorm.service.UserService;
import eu.execom.toolbox1sugarorm.model.Message;
import eu.execom.toolbox1sugarorm.model.User;

public class MyMessagesFragment extends Fragment {

    private EditText newMessage;
    private User currentUser;
    private RecyclerView messageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_messages, container, false);

        newMessage = (EditText) view.findViewById(R.id.new_message);

        messageList = (RecyclerView) view.findViewById(R.id.messages);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        messageList.setLayoutManager(layoutManager);

        currentUser = UserService.getCurrentUser(getActivity());
        populateList(messageList);

        final View sendButton = view.findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

        return view;
    }

    private void populateList(RecyclerView messageList) {
        final List<Message> messagesForUser = MessageService.getMessagesFromUser(currentUser);
        final MessageListAdapter messageListAdapter = new MessageListAdapter(messagesForUser);
        messageList.swapAdapter(messageListAdapter, true);
    }

    private void send(){
        final String text = newMessage.getText().toString();
        final Message message = new Message();
        message.setAuthor(currentUser);
        message.setText(text);
        message.setDateTime(new Date().getTime());
        message.save();

        populateList(messageList);
    }

}
