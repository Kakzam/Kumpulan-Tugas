package com.sekud.id.network;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.sekud.id.base.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RestPresenter implements RestInterface {

    @Override
    public void getVersion(String base, String version, VersionCallback callback) {
        FirebaseFirestore.getInstance().collection(base).document(version).get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void getLocation(String base, String location, RestCallback callback) {
        FirebaseFirestore.getInstance().collection(base).document(location).collection("-").get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void deleteLocation(String base, String location, String id, RestCallback callback) {
        FirebaseFirestore.getInstance().collection(base).document(location).collection("-").document(id).delete().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void addLocation(String base, String location, String id, String title, String link, RestCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("location", link);

        if (id.isEmpty()) {
            FirebaseFirestore.getInstance().collection(base).document(location).collection("-").add(map).addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful()) {
                        callback.Success(null);
                    } else {
                        callback.Failed(task.getException().getMessage() + "");
                    }
                } catch (NullPointerException e) {
                    callback.Failure(StringUtil.FAILED_REST);
                }
            });
        } else {
            FirebaseFirestore.getInstance().collection(base).document(location).collection("-").document(id).set(map).addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful()) {
                        callback.Success(null);
                    } else {
                        callback.Failed(task.getException().getMessage() + "");
                    }
                } catch (NullPointerException e) {
                    callback.Failure(StringUtil.FAILED_REST);
                }
            });
        }
    }

    @Override
    public void addItem(String title, String usage, String status, String price, String advantage, String deficiency, ItemCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("usage", usage);
        map.put("status", status);
        map.put("price", price);
        map.put("advantage", advantage);
        map.put("deficiency", deficiency);

        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").add(map).addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void updateItem(String title, String usage, String status, String price, String advantage, String deficiency, String id, ItemCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("usage", usage);
        map.put("status", status);
        map.put("price", price);
        map.put("advantage", advantage);
        map.put("deficiency", deficiency);

        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").document(id).set(map).addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void deleteItem(String id, ItemCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").document(id).delete().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void getItem(String id, VersionCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").document(id).get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void getAllItem(RestCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                try {
                    if (task.isSuccessful()) {
                        callback.Success(task.getResult());
                    } else {
                        callback.Failed(task.getException().getMessage());
                    }
                } catch (NullPointerException e) {
                    callback.Failure(StringUtil.FAILED_REST);
                }
            }
        });
    }

    @Override
    public void uploadImageItem(String id, Uri filePath, UploadImageCallback callback) {
        FirebaseStorage.getInstance().getReference().child(BaseConfig.BASE + "/" + BaseConfig.ITEM + "/" + id + "/" + UUID.randomUUID().toString()).putFile(filePath)
                .addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            callback.Success(task.getResult());
                        } else {
                            callback.Failed(task.getException().getMessage());
                        }
                    } catch (NullPointerException e) {
                        callback.Failure(StringUtil.FAILED_REST);
                    }
                });
    }

    @Override
    public void getImageItem(String id, ImageCallback callback) {
        FirebaseStorage.getInstance().getReference().child(BaseConfig.BASE + "/" + BaseConfig.ITEM + "/" + id).listAll()
                .addOnCompleteListener(task -> {
                    try {
                        if (task.isSuccessful()) {
                            callback.Success(task.getResult());
                        } else {
                            callback.Failed(task.getException().getMessage());
                        }
                    } catch (NullPointerException e) {
                        callback.Failure(StringUtil.FAILED_REST);
                    }
                });
    }

    @Override
    public void deleteImage(String url, ImageCallback callback) {
        FirebaseStorage.getInstance().getReferenceFromUrl(url).delete().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage());
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void addUser(String name, String userName, String password, String status, RestCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("username", userName);
        map.put("password", password);
        map.put("status", status);

        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.USER).collection("-").add(map).addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void updateUser(String userName, String password, String name, String check, String id, RestCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("username", userName);
        map.put("password", password);
        map.put("status", check);

        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.USER).collection("-").document(id).set(map).addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void deleteUser(String id, RestCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.USER).collection("-").document(id).delete().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void getUser(RestCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.USER).collection("-").get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }



    @Override
    public void uploadImageItemV2(String baseImage, String idItem, RestCallback callback) {
        Map<String, String> map = new HashMap<>();
        map.put("image", baseImage);

        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").document(idItem).collection("-").add(map).addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(null);
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

    @Override
    public void getImageItemV2(String idItem, RestCallback callback) {
        FirebaseFirestore.getInstance().collection(BaseConfig.BASE).document(BaseConfig.ITEM).collection("-").document(idItem).collection("-").get().addOnCompleteListener(task -> {
            try {
                if (task.isSuccessful()) {
                    callback.Success(task.getResult());
                } else {
                    callback.Failed(task.getException().getMessage() + "");
                }
            } catch (NullPointerException e) {
                callback.Failure(StringUtil.FAILED_REST);
            }
        });
    }

}
