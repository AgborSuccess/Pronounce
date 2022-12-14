package com.example.pronouncetwo.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pronouncetwo.R;

import java.text.DateFormat;
import java.util.List;

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.myViewHolder>{

    Context context;
    private List<Notification> mNotifications;

    public Notification_adapter(Context context, List<Notification> mNotifications) {
        this.context = context;
        this.mNotifications = mNotifications;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(context).inflate(R.layout.notifications_layout, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
//        Notification_adapter notification_adapter


        Notification notification = mNotifications.get(position);
        holder.NotificationTitle.setText(notification.getTitle());
        holder.NotificationDescription.setText(notification.getBody());










        holder.NotificationCard.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_four));





        int time = (int) System.currentTimeMillis();
        String formattedTime = DateFormat.getDateTimeInstance().format(time);
//        holder.NotificationTitle.setText("Download More of our apps");
//        holder.NotificationDescription.setText("You need to download more apps dont you");
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


    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView NotificationTitle;
        TextView NotificationDescription;
        TextView NotificationTime;
        CardView NotificationCard;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            NotificationTitle = itemView.findViewById(R.id.notification_title);
            NotificationDescription = itemView.findViewById(R.id.notification_text);
            NotificationTime = itemView.findViewById(R.id.notification_time);
            NotificationCard = itemView.findViewById(R.id.notification_card);
        }
    }
}
