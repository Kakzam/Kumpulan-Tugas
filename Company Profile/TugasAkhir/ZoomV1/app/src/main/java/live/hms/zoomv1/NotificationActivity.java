package live.hms.zoomv1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import live.hms.zoomv1.databinding.ActivityMainBinding;
import live.hms.zoomv1.network.RetrofitServerCallback;
import live.hms.zoomv1.presenter.RestPresenter;

public class NotificationActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            Toast.makeText(this, "Title : " + getIntent().getStringExtra("title") + "\n Message : " + getIntent().getStringExtra("message"), Toast.LENGTH_LONG).show();
//            Bundle args = new Bundle();
//            args.putString("param1", getIntent().getStringExtra("title"));
//            args.putString("param2", getIntent().getStringExtra("message"));
//            DialogFragment dialog = new DialogFragment();
//            dialog.setCancelable(false);
//            dialog.setArguments(args);
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(dialog, "DIALOG");
//            ft.commitAllowingStateLoss();
        }

        binding.nama.setHint("Title");
        binding.link.setHint("Message");
        binding.token.setText("PUSH");
        binding.join.setVisibility(View.GONE);
        binding.mute.setVisibility(View.GONE);
        binding.mic.setVisibility(View.GONE);
        binding.leave.setVisibility(View.GONE);
        binding.updatePeserta.setVisibility(View.GONE);

        binding.token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RestPresenter().sendMessage(binding.nama.getText().toString(), binding.link.getText().toString(), new RetrofitServerCallback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.v("ZAM", "sendMessage onSuccess: " + response);
                    }

                    @Override
                    public void onFailed(String response) {
                        Log.v("ZAM", "sendMessage onFailed: " + response);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.v("ZAM", "sendMessage onFailure: " + throwable.getMessage());
                    }
                });
            }
        });
    }
}