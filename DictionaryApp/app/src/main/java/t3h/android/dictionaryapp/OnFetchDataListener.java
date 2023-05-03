package t3h.android.dictionaryapp;

import t3h.android.dictionaryapp.models.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}
