package live.hms.zoomv1.network;

public interface RetrofitServerCallback {

    void onSuccess(String response);

    void onFailed(String response);

    void onFailure(Throwable throwable);
}
