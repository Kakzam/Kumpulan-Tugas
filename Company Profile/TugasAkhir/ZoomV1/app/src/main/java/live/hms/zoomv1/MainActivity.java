package live.hms.zoomv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import live.hms.video.error.HMSException;
import live.hms.video.media.tracks.HMSTrack;
import live.hms.video.sdk.HMSActionResultListener;
import live.hms.video.sdk.HMSSDK;
import live.hms.video.sdk.HMSUpdateListener;
import live.hms.video.sdk.models.HMSConfig;
import live.hms.video.sdk.models.HMSLocalPeer;
import live.hms.video.sdk.models.HMSMessage;
import live.hms.video.sdk.models.HMSPeer;
import live.hms.video.sdk.models.HMSRemovedFromRoom;
import live.hms.video.sdk.models.HMSRoleChangeRequest;
import live.hms.video.sdk.models.HMSRoom;
import live.hms.video.sdk.models.enums.HMSPeerUpdate;
import live.hms.video.sdk.models.enums.HMSRoomUpdate;
import live.hms.video.sdk.models.enums.HMSTrackUpdate;
import live.hms.video.sdk.models.trackchangerequest.HMSChangeTrackStateRequest;
import live.hms.zoomv1.model.TokenRequestWithCode;
import live.hms.zoomv1.model.TokenResponse;
import live.hms.zoomv1.network.RetrofitServerCallback;
import live.hms.zoomv1.presenter.RestPresenter;

public class MainActivity extends AppCompatActivity {

    HMSSDK hmssdk;
    HMSConfig config;
    HMSLocalPeer sound;
    String p = "";
    List<String> idCustomer = new ArrayList<>();
    List<String> nama = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runServiceFirebaseMessage();
//        sendMessage();
        hmssdk = new HMSSDK.Builder(this).build();

        EditText name = findViewById(R.id.nama);
        EditText link = findViewById(R.id.link);
        TextView peserta = findViewById(R.id.peserta);

        findViewById(R.id.token).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = link.getText().toString();
//                String url = "https://ujicoba.app.100ms.live/preview/kzu-jis-iel";
                Log.v("ZAM", "Token HOST: " + URI.create(url).getHost());
                new RestPresenter().getToken(URI.create(url).getHost(), new TokenRequestWithCode(url), new RetrofitServerCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(MainActivity.this, "Token Berhasil", Toast.LENGTH_SHORT).show();
                        Log.v("ZAM", "Token onSuccess: " + response);
                        TokenResponse tokenResponse = new Gson().fromJson(response, TokenResponse.class);
                        Log.v("ZAM", "Token getToken: " + tokenResponse.getToken());
                        config = new HMSConfig(name.getText().toString(), tokenResponse.getToken());
                        hmssdk.join(config, new HMSUpdateListener() {
                            @Override
                            public void onJoin(@NonNull HMSRoom hmsRoom) {
                                Log.v("ZAM", "HMSRoom : Join");
                                Log.v("ZAM", "HMSRoom : Join: " + hmsRoom.getPeerList().length);

                                for (HMSPeer peer : hmsRoom.getPeerList()) {
                                    idCustomer.add(peer.getCustomerUserID());
                                    nama.add(peer.getName());
                                }
                            }

                            @Override
                            public void onRoomUpdate(@NonNull HMSRoomUpdate hmsRoomUpdate, @NonNull HMSRoom hmsRoom) {
                                Log.v("ZAM", "onRoomUpdate");
                            }

                            @Override
                            public void onPeerUpdate(@NonNull HMSPeerUpdate hmsPeerUpdate, @NonNull HMSPeer hmsPeer) {
                                idCustomer.add(hmsPeer.getCustomerUserID());
                                nama.add(hmsPeer.getName());
                                Log.v("ZAM", "onPeerUpdate: getName: " + hmsPeer.getName());
                                Log.v("ZAM", "onPeerUpdate: getCustomerUserID: " + hmsPeer.getCustomerUserID());
                            }

                            @Override
                            public void onTrackUpdate(@NonNull HMSTrackUpdate hmsTrackUpdate, @NonNull HMSTrack hmsTrack, @NonNull HMSPeer hmsPeer) {
                                Log.v("ZAM", "onTrackUpdate");
                            }

                            @Override
                            public void onRoleChangeRequest(@NonNull HMSRoleChangeRequest hmsRoleChangeRequest) {

                            }

                            @Override
                            public void onMessageReceived(@NonNull HMSMessage hmsMessage) {
                                Log.v("ZAM", "HMSMessage : ==========================");
                                Log.v("ZAM", "HMSMessage : ==========================");
                                Log.v("ZAM", "HMSMessage getType: " + hmsMessage.getType());
                                Log.v("ZAM", "HMSMessage getServerReceiveTime: " + hmsMessage.getServerReceiveTime());
                                Log.v("ZAM", "HMSMessage getMessage: " + hmsMessage.getMessage());
                                Log.v("ZAM", "HMSMessage getRecipient: " + hmsMessage.getRecipient());
                                Log.v("ZAM", "HMSMessage getSenderName: " + hmsMessage.getSender().getName());
                                Log.v("ZAM", "HMSMessage getSenderCustomerId: " + hmsMessage.getSender().getCustomerUserID());
                            }

                            @Override
                            public void onReconnecting(@NonNull HMSException e) {
                                Log.v("ZAM", "onReconnecting");
//                                Toast.makeText(MainActivity.this, "Menghubungkan", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onReconnected() {
                                Log.v("ZAM", "onReconnected");
//                                Toast.makeText(MainActivity.this, "Terhubung", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onRemovedFromRoom(@NonNull HMSRemovedFromRoom hmsRemovedFromRoom) {
//                                Toast.makeText(MainActivity.this, "Kamu di kick", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onChangeTrackStateRequest(@NonNull HMSChangeTrackStateRequest hmsChangeTrackStateRequest) {

                            }

                            @Override
                            public void onError(@NonNull HMSException e) {
                                Log.v("ZAM", "HMSException : " + e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailed(String response) {
                        Log.v("ZAM", "Token onFailed: " + response);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v("ZAM", "Token onFailure: " + throwable.getMessage());
                    }
                });
            }
        });

        findViewById(R.id.join).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.mute).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound = hmssdk.getLocalPeer();
                sound.getAudioTrack().setMute(true);
            }
        });

        findViewById(R.id.mic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound = hmssdk.getLocalPeer();
                sound.getAudioTrack().setMute(false);
            }
        });

        findViewById(R.id.leave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
//                hmssdk.leave(new HMSActionResultListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.v("ZAM", "onSuccess Leave");
//                    }
//
//                    @Override
//                    public void onError(@NonNull HMSException e) {
//                        Log.v("ZAM", "HMSException: " + e.getMessage());
//                    }
//                });
            }
        });
    }

    private void sendMessage() {
        new RestPresenter().sendMessage("kirim", "dapet", new RetrofitServerCallback() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(MainActivity.this, "sendMessage onSuccess: " + response, Toast.LENGTH_SHORT).show();
                Log.v("ZAM", "sendMessage onSuccess: " + response);
            }

            @Override
            public void onFailed(String response) {
                Toast.makeText(MainActivity.this, "sendMessage onFailed: " + response, Toast.LENGTH_SHORT).show();
                Log.v("ZAM", "sendMessage onFailed: " + response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(MainActivity.this, "sendMessage onFailure: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.v("ZAM", "sendMessage onFailure: " + throwable.getMessage());
            }
        });
    }

    private void runServiceFirebaseMessage() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            Log.v("ZAM", "Token : " + task.getResult());
                            Map<String, String> map = new HashMap<>();
                            map.put("token", task.getResult());
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            if (sharedPreferences.getString("ID", "").isEmpty()) {
                                FirebaseFirestore.getInstance().collection("token").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("ID", task.getResult().getId()).apply();
                                        } else
                                            Toast.makeText(MainActivity.this, "Gagal ngirim token ke firebase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                FirebaseFirestore.getInstance().collection("token").document(sharedPreferences.getString("ID", "")).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Berhasil update token", Toast.LENGTH_SHORT).show();
                                        } else
                                            Toast.makeText(MainActivity.this, "Gagal ngirim token ke firebase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        } else
                            Toast.makeText(MainActivity.this, "Silahkan periksa koneksi internet anda : " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}