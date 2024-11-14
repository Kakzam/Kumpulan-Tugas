package live.hms.zoomv1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelNotificationFCM {

    @Expose
    @SerializedName("to")
    private String to;
    @Expose
    @SerializedName("notification")
    private NotificationEntity notification;

    public ModelNotificationFCM(String to, NotificationEntity notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public static class NotificationEntity {
        @Expose
        @SerializedName("body")
        private String body;
        @Expose
        @SerializedName("title")
        private String title;

        public NotificationEntity(String body, String title) {
            this.body = body;
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public String getTitle() {
            return title;
        }
    }


//    @SerializedName("title")
//    private String title;
//    @SerializedName("body")
//    private String body;
//
//    public ModelNotificationFCM(String title, String body) {
//        this.title = title;
//        this.body = body;
//    }
}
