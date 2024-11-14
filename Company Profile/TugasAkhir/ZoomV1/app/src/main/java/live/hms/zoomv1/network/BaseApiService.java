package live.hms.zoomv1.network;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import live.hms.zoomv1.MainActivity;
import live.hms.zoomv1.model.ModelNotificationFCM;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface BaseApiService {


    @FormUrlEncoded
    @POST(ApiConfig.TOKEN)
    Call<ResponseBody> getToken(@Header("subdomain") String subdomain,
                                @Field("code") String code,
                                @Field("user_id") String userId);

    @POST("send")
    Call<ResponseBody> sendFCM(@Header("Content-Type") String content,
                               @Header("Authorization") String auth,
                               @Body RequestBody modelNotificationFCM);
//                               @Field("to") String to,
//                               @Field("notification") JsonObject jsonObject);
//                               @Field("notification") JSONObject jsonObject);

}
