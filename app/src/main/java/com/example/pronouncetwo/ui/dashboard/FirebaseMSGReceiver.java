//package com.example.pronouncetwo.ui.notifications;
//
//import android.util.Log;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.firebase.messaging.FirebaseMessaging;
//
//public class FirebaseMSGReceiver {
//    FirebaseMessaging.getInstance().subscribeToTopic("notifications")
//        .addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            String msg = getString(R.string.msg_subscribed);
//            if (!task.isSuccessful()) {
//                msg = getString(R.string.msg_subscribe_failed);
//            }
//            Log.d("FCM", msg);
//            Toast.makeText(MyActivity.this, msg, Toast.LENGTH_SHORT).show();
//        }
//    });
//
//FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//
//FirebaseMessaging.getInstance().getToken()
//        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//        @Override
//        public void onComplete(@NonNull Task<InstanceIdResult> task) {
//            if (!task.isSuccessful()) {
//                Log.w("FCM", "getInstanceId failed", task.getException());
//                return;
//            }
//
//            // Get new Instance ID token
//            String token = task.getResult().getToken();
//
//            // Log and toast
//            String msg = getString(R.string.msg_token_fmt, token);
//            Log.d("FCM", msg);
//            Toast.makeText(MyActivity.this, msg, Toast.LENGTH_SHORT).show();
//        }
//    });
//
//
//}
