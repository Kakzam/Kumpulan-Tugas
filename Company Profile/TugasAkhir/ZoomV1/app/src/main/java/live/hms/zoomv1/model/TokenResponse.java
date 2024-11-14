package live.hms.zoomv1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    @Expose
    @SerializedName("api_version")
    private String apiVersion;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("msg")
    private String msg;
    @Expose
    @SerializedName("token")
    private String token;

    public TokenResponse(String apiVersion, int status, String msg, String token) {
        this.apiVersion = apiVersion;
        this.status = status;
        this.msg = msg;
        this.token = token;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }
}
