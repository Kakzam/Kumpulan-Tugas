package live.hms.zoomv1.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import live.hms.zoomv1.model.ModelNotificationFCM;
import live.hms.zoomv1.model.TokenRequestWithCode;
import live.hms.zoomv1.network.ApiUtils;
import live.hms.zoomv1.network.BaseApiService;
import live.hms.zoomv1.network.RetrofitServerCallback;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestPresenter implements RestInterfaces {

    @Override
    public void getToken(String subdomain, TokenRequestWithCode tokenRequestWithCode, RetrofitServerCallback callback) {
        BaseApiService base = ApiUtils.getBase();
        base.getToken(subdomain, tokenRequestWithCode.getCode(), tokenRequestWithCode.getUserId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(Objects.requireNonNull(response.body()).string());
                    } else {
                        callback.onFailed(Objects.requireNonNull(response.errorBody()).string());
                    }
                } catch (Exception e) {
                    callback.onFailed(e.toString());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void sendMessage(String title, String body, RetrofitServerCallback callback) {
        BaseApiService base = ApiUtils.getBaseFCM();

        JSONObject object = new JSONObject();
        JSONObject objectTo = new JSONObject();
        try {
            objectTo.put("to", "esLKuTu5Q66Hwsc6QYHxhC:APA91bHSAGSXlDPMdhXVxMqoDezW2BPOB1dJoNTj5faZCHi6k8yUh11IhIVw-f3x_Y3vQySUGq1xHXOQMpRs1M0tpI5B7gU_xzRECVMXg6BskbhyAETjPt0nODtvRPf6wNTiRVvSpRoR");
            object.put("title", title);
            object.put("body", body);
            objectTo.put("notification", object);
            Log.v("ZAM", "OBJECT  : " + objectTo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(objectTo.toString(), MediaType.parse("application/json; charset=utf-8"));

        base.sendFCM("application/json",
                        "key=AAAAZAaOHHs:APA91bGVGV6tZGelLil1BJZKqgn5cr2nHJktZRNcSKPnDRE6r6bLL96OFoRr30t-nKLeWHwlaQC__fVZO6eWnDLQ74YBEj56M50r3cc6FDYhp0fxVWs0HLR4WvogkKTwMhpP-iJT9J83",
                        requestBody)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                callback.onSuccess(Objects.requireNonNull(response.body()).toString());
                            } else {
                                callback.onFailed(Objects.requireNonNull(response.errorBody()).toString());
                            }
                        } catch (Exception e) {
                            callback.onFailed(e.toString());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }
}
