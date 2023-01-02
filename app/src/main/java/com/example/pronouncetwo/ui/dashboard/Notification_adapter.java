package com.example.pronouncetwo.ui.dashboard;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pronouncetwo.R;

import java.text.DateFormat;

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.myViewHolder>{

    Context context;

    public Notification_adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.notifications_layout, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
//        Notification_adapter notification_adapter
        int time = (int) System.currentTimeMillis();
        String formattedTime = DateFormat.getDateTimeInstance().format(time);
        holder.NotificationTitle.setText("Download More of our apps");
        holder.NotificationDescription.setText("You need to download more apps dont you");
        holder.NotificationTime.setText(formattedTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context, v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getTitle().equals("DELETE")){
//                            delete the note
                            Toast.makeText(context, "Notification Deleted", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                    NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_HIGH);
//                    NotificationManager manager = getSystemService(NotificationManager.class);
//                    manager.createNotificationChannel(channel);
//                }


                NotificationCompat.Builder builder= new NotificationCompat.Builder(context.getApplicationContext(), "notification");
                builder.setContentTitle(holder.NotificationTitle.getText());
                builder.setContentText(holder.NotificationDescription.getText());
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context.getApplicationContext());
                managerCompat.notify(1,builder.build());
            }
        });
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView NotificationTitle;
        TextView NotificationDescription;
        TextView NotificationTime;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            NotificationTitle = itemView.findViewById(R.id.notification_title);
            NotificationDescription = itemView.findViewById(R.id.notification_text);
            NotificationTime = itemView.findViewById(R.id.notification_time);
        }
    }
}
