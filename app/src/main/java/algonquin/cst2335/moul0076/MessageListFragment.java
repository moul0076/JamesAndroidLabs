package algonquin.cst2335.moul0076;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageListFragment extends Fragment
{
    Button sendButton;
    Button receiveButton;
    EditText chatMessage;


    ArrayList<ChatMessage> messages = new ArrayList<>();
    MyChatAdapter adt = new MyChatAdapter();
    RecyclerView chatList;
    SQLiteDatabase db;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View chatLayout = inflater.inflate(R.layout.chatlayout, container, false);
        MyOpenHelper opener = new MyOpenHelper(getContext());
        db = opener.getWritableDatabase();

        Cursor result = db.rawQuery( "Select * from " +MyOpenHelper.TABLE_NAME + ";", null);

        int _idCol = result.getColumnIndex( "_id");
        int messageCol = result.getColumnIndex( MyOpenHelper.col_message );
        int sendCol = result.getColumnIndex( MyOpenHelper.col_send_receive);
        int timeCol = result.getColumnIndex( MyOpenHelper.col_time_sent);

        while (result.moveToNext() )
        {
            long id = result.getInt( _idCol );
            String message = result.getString( messageCol);
            String time = result.getString( timeCol);
            int sendOrReceive = result.getInt( sendCol);
            messages.add( new ChatMessage(message, sendOrReceive, time, id) );
        }

        chatList = chatLayout.findViewById(R.id.myrecycler);
        chatList.setAdapter(new MyChatAdapter()  );

        sendButton = chatLayout.findViewById(R.id.sendButton);
        receiveButton = chatLayout.findViewById(R.id.receiveButton);
        chatMessage = chatLayout.findViewById(R.id.chatText);

        chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        chatList.setAdapter(adt);
        chatList.setLayoutManager(new LinearLayoutManager(getContext()));



        sendButton.setOnClickListener( click ->
        {
            String whatIsTyped = chatMessage.getText().toString();
            Date timeNow = new Date();
            String currentDateAndTime = timeNow.toString();

            ///* 1 for Send, 2 for Receive */
            ChatMessage thisMessage = new ChatMessage( whatIsTyped, 1, currentDateAndTime );

            //week 7 stuff below
            ContentValues newRow = new ContentValues();

            newRow.put( MyOpenHelper.col_message, thisMessage.getMessage() );
            newRow.put( MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive() );
            newRow.put( MyOpenHelper.col_time_sent, thisMessage.getTimeSent() );

            long newID = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            thisMessage.setId(newID);

            //adding a new message to our history
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() -1);
            chatMessage.setText( "" ); //clear the chat box
        });

        receiveButton.setOnClickListener( click ->
        {
            String whatIsTyped = chatMessage.getText().toString();
            Date timeNow = new Date();
            String currentDateAndTime = timeNow.toString();

            ///* 1 for Send, 2 for Receive */
            ChatMessage thisMessage = new ChatMessage( whatIsTyped, 2, currentDateAndTime );

            //week 7 stuff below
            ContentValues newRow = new ContentValues();

            newRow.put( MyOpenHelper.col_message, thisMessage.getMessage() );
            newRow.put( MyOpenHelper.col_send_receive, thisMessage.getSendOrReceive() );
            newRow.put( MyOpenHelper.col_time_sent, thisMessage.getTimeSent() );

            long newID = db.insert(MyOpenHelper.TABLE_NAME, MyOpenHelper.col_message, newRow);

            thisMessage.setId(newID);

            //adding a new message to our history
            messages.add(thisMessage);
            adt.notifyItemInserted(messages.size() -1);
            chatMessage.setText( "" );
        });

        return chatLayout;
    }

    public void notifyMessageDeleted(ChatMessage chosenMessage, int chosenPostion)
    {
        //int position = getAbsoluteAdapterPosition();
        AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );


        builder.setMessage( "Do you want to delete the message: " + chosenMessage.getMessage() );
        builder.setTitle("Question:");
        builder.setNegativeButton("No",(dialog, cl) -> { });
        builder.setPositiveButton("Yes",(dialog, cl) ->
        {
            ChatMessage removedMessages = messages.get(chosenPostion);
            messages.remove(chosenPostion);
            adt.notifyItemRemoved(chosenPostion);
            db.delete(MyOpenHelper.TABLE_NAME, "_id=?", new String[] { Long.toString( removedMessages.getId() ) } );

            Snackbar.make(sendButton, "You Deleted Message #" + chosenPostion, Snackbar.LENGTH_LONG)
                    .setAction("UNDO", clk ->
                    {
                        messages.add(chosenPostion, removedMessages);
                        adt.notifyItemInserted(chosenPostion);


                        db.execSQL("Insert into " + MyOpenHelper.TABLE_NAME +
                                " values ('" + removedMessages.getId() +
                                "','" + removedMessages.getMessage() +
                                "','" + removedMessages.getSendOrReceive() +
                                "','" + removedMessages.getTimeSent() + "');" );

                    } )
                    .show();
        });
        builder.create().show();

    }

    private class MyRowViews extends RecyclerView.ViewHolder
    {
        TextView messageText;
        TextView timeText;

        public MyRowViews( View itemView) {
            super(itemView);

            itemView.setOnClickListener( click ->
                    {
                        ChatRoom parentActivity = (ChatRoom)getContext();
                        int position = getAbsoluteAdapterPosition();
                        parentActivity.userClickedMessage( messages.get(position), position);

                        /*
                        //This was done for just the phone

                         */


                    }
            );

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

    public class ChatMessage
    {

        String message;
        int sendOrReceive;
        String timeSent;
        long id;

        public void setId( long l) { id = l; }
        public long getId() { return id; }

        public ChatMessage(String message, int sendOrReceive, String timeSent, long id)
        {
            this.message = message;
            this.sendOrReceive = sendOrReceive;
            this.timeSent = timeSent;
            setId(id);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        //String currentDateandTime = sdf.format(new Date());

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