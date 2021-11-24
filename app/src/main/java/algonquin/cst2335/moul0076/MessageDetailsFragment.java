package algonquin.cst2335.moul0076;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageDetailsFragment extends Fragment
{
    MessageListFragment.ChatMessage chosenMessage;
    int chosenPostion;

    public MessageDetailsFragment (MessageListFragment.ChatMessage message, int position)
    {
        chosenMessage = message;
        chosenPostion = position;
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View detailsView = inflater.inflate(R.layout.details_layout, container, false);

        TextView messageView = detailsView.findViewById(R.id.messageView);
        TextView sendView = detailsView.findViewById(R.id.sendView );
        TextView timeView = detailsView.findViewById(R.id.timeView );
        TextView idView = detailsView.findViewById(R.id.databaseIdView );

        //wants string placeholders instead of using setText()
        String messageMessage = "Message is: " + chosenMessage.getMessage();
        String sendMessage = "Send or Receive?: " +chosenMessage.getSendOrReceive();
        String timeMessage = "Time send: " + chosenMessage.getTimeSent();
        String idMessage = "Database id is: " + chosenMessage.getId();

        messageView.setText(messageMessage);
        sendView.setText(sendMessage);
        timeView.setText(timeMessage);
        idView.setText(idMessage);

        Button closeButton = detailsView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener( closeClicked ->
        {
            getParentFragmentManager().beginTransaction().remove( this ).commit();
        });

        Button deleteButton = detailsView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener( deleteClicked ->
        {
            ChatRoom parentActivity = (ChatRoom)getContext();
            parentActivity.notifyMessageDeleted(chosenMessage, chosenPostion);

            getParentFragmentManager().beginTransaction().remove(this).commit();
        });

        return detailsView;
    }
}
