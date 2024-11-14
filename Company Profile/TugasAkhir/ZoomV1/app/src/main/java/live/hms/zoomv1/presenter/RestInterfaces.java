package live.hms.zoomv1.presenter;

import live.hms.zoomv1.model.TokenRequestWithCode;
import live.hms.zoomv1.network.RetrofitServerCallback;

interface RestInterfaces {

    void getToken(String subdomain, TokenRequestWithCode tokenRequestWithCode, RetrofitServerCallback callback);
    void sendMessage(String title, String body, RetrofitServerCallback callback);

}
