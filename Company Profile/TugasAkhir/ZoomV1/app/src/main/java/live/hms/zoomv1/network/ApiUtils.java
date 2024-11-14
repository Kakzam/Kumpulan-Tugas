package live.hms.zoomv1.network;

public final class ApiUtils {

    public static BaseApiService getBase() {
        return RetrofitClient.getClient(ApiConfig.URL).create(BaseApiService.class);
    }

    public static BaseApiService getBaseFCM() {
        return RetrofitClient.getClient(ApiConfig.URL_FIREBASE).create(BaseApiService.class);
    }
}