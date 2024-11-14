package live.hms.zoomv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FirebaseServiceMessage extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FirebaseServiceMessage.this);

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        FirebaseFirestore.getInstance().collection("token").document(sharedPreferences.getString("ID", "")).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(FirebaseServiceMessage.this, "Berhasil update token", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(FirebaseServiceMessage.this, "Gagal update token ke firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {

        if (message.getNotification() != null) {
            Intent intent = new Intent();
            intent.setClass(this, NotificationActivity.class);
            intent.putExtra("title", message.getNotification().getTitle());
            intent.putExtra("message", message.getNotification().getBody());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        Log.v("ZAM", "remoteMessage.getData(): " + message.getData());
        Log.v("ZAM", "remoteMessage.getNotification().getTitle: " + Objects.requireNonNull(message.getNotification()).getTitle());
        Log.v("ZAM", "remoteMessage.getNotification().getBody: " + message.getNotification().getBody());
    }

    @Override
    public void onDeletedMessages() {
    }
}
