package live.hms.zoomv1.model;

import android.net.Uri;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class TokenRequestWithCode {
    @SerializedName("code")
    private String code;
    @SerializedName("user_id")
    private String userId = UUID.randomUUID().toString();

    public TokenRequestWithCode(String code) {
        Log.v("ZAM", "Token CODE: " + code);
        Log.v("ZAM", "Token CODE Last: " + getCodeFromMeetingUrl(code));
        this.code = getCodeFromMeetingUrl(code);
    }

    String getCodeFromMeetingUrl(String meetingUrl) {
        return Uri.parse(meetingUrl).getLastPathSegment();
    }

    public String getCode() {
        return code;
    }

    public String getUserId() {
        return userId;
    }
}