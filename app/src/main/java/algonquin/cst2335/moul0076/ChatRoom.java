package algonquin.cst2335.moul0076;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatRoom extends AppCompatActivity
{
    Button sendButton;
    Button receiveButton;
    EditText chatMessage;
    RecyclerView chatList;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chatlayout);
        chatList = findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter()  );

        sendButton = findViewById(R.id.sendButton);
        receiveButton = findViewById(R.id.receiveButton);
        //sendButton.setOnClickListener ( click -> );
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

        @Override
        public MyRowViews onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = getLayoutInflater();
            View loadedRow = inflater.inflate(R.layout.sent_message, parent, false);
            //MyRowViews initRow = new MyRowViews(loadedRow);
            return new MyRowViews(loadedRow);
        }

        @Override
        public void onBindViewHolder(MyRowViews holder, int position)
        {
            holder.messageText.setText( "" );
            holder.timeText.setText( "" );

            ///* 1 for Send, 2 for Receive */
            ChatMessage thisMessage = new ChatMessage( "message goes here", 1, "time goes here" );
        }

        @Override
        public int getItemCount() {
            return 0;
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

        public void setTimeSent(String timeSent) {
            this.timeSent = timeSent;
        }
    }
}
