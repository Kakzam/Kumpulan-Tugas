package com.sekud.id.network;

import android.net.Uri;

public interface RestInterface {

    /* SplashActivity --------------------------------------------------------------------------- */
    void getVersion(String base, String version, VersionCallback callback);

    /* LocationFragment ------------------------------------------------------------------------- */
    void getLocation(String base, String location, RestCallback callback);

    void deleteLocation(String base, String location, String id, RestCallback callback);

    void addLocation(String base, String location, String id, String title, String link, RestCallback callback);

    /* ItemFragment ----------------------------------------------------------------------------- */
    void addItem(String title, String usage, String status, String price, String advantage, String deficiency, ItemCallback callback);

    void updateItem(String title, String usage, String status, String price, String advantage, String deficiency, String id, ItemCallback callback);

    void deleteItem(String id, ItemCallback callback);

    void getItem(String id, VersionCallback callback);

    void getAllItem(RestCallback callback);

    void uploadImageItem(String id, Uri filePath, UploadImageCallback callback);

    void getImageItem(String id, ImageCallback callback);

    void deleteImage(String url, ImageCallback callback);

    /* DialogVerificationFragment */

    void addUser(String name, String userName, String password, String status, RestCallback callback);

    void updateUser(String userName, String password, String name, String check, String id, RestCallback callback);

    void deleteUser(String id, RestCallback callback);

    void getUser(RestCallback callback);

    /* Upload image item */
    void uploadImageItemV2(String baseImage, String idItem, RestCallback callback);
    void getImageItemV2(String idItem, RestCallback callback);

}
