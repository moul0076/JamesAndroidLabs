package algonquin.cst2335.moul0076;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity
{
    Button sendButton;
    Button receiveButton;
    EditText chatMessage;
    RecyclerView chatList;


    ArrayList<ChatMessage> messages = new ArrayList<>();



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);

        chatList = findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter()  );

        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);
        chatMessage = findViewById(R.id.chatText);

        chatList.setLayoutManager(new LinearLayoutManager(this));

        MyChatAdapter adt = new MyChatAdapter();
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(this));


        sendButton.setOnClickListener( click ->
        {
            String whatIsTyped = chatMessage.getText().toString();
            Date timeNow = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            ///* 1 for Send, 2 for Receive */
            ChatMessage thisMessage = new ChatMessage( whatIsTyped, 1, currentDateandTime );

            //adding a new message to our history
            messages.add(thisMessage);

            adt.notifyItemInserted(messages.size() -1);

            chatMessage.setText( "" );
        });

        receiveButton.setOnClickListener( click ->
        {
            String whatIsTyped = chatMessage.getText().toString();
            Date timeNow = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a", Locale.getDefault());

            String currentDateandTime = sdf.format(timeNow);

            ///* 1 for Send, 2 for Receive */
            ChatMessage thisMessage = new ChatMessage( whatIsTyped, 2, currentDateandTime );

            //adding a new message to our history
            messages.add(thisMessage);

            adt.notifyItemInserted(messages.size() -1);

            chatMessage.setText( "" );
        });

    }

    private class MyRowViews extends RecyclerView.ViewHolder
    {
        TextView messageText;
        TextView timeText;

        public MyRowViews( View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

    private class MyChatAdapter extends RecyclerView.Adapter<MyRowViews>
    {

        @NonNull
        @Override
        public MyRowViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = getLayoutInflater();
            int layoutID;

            if (viewType==1) //send
                layoutID = R.layout.sent_message;
            else
                layoutID = R.layout.receive_message;

            View loadedRow = inflater.inflate(layoutID, parent, false);
            //MyRowViews initRow = new MyRowViews(loadedRow);
            return new MyRowViews(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position)
        {
            holder.messageText.setText( messages.get(position).getMessage() );
            holder.timeText.setText( messages.get(position).getTimeSent() );

        }

        @Override
        public int getItemViewType(int position) {
            ChatMessage thisRow = messages.get(position);
            return thisRow.getSendOrReceive();
        }

        @Override
        public int getItemCount()
        {
            return messages.size();
        }
    }

    private class ChatMessage
    {
        String message;
        int sendOrReceive;
        String timeSent;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        String currentDateandTime = sdf.format(new Date());

        public ChatMessage(String message, int sendOrReceive, String timeSent) {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
        }

        public String getMessage() {
            return message;
        }

        public int getSendOrReceive() {
            return sendOrReceive;
        }

        public String getTimeSent() {
            return timeSent;
        }
    }
}
