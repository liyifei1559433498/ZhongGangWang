package com.zgw.network_api.rx;



import androidx.annotation.StringRes;

import com.zgw.base.ui.BaseActivity;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class RxProgress {

    private RxProgress() {
        throw new AssertionError("No instances.");
    }

    public static <U> ObservableTransformer<U, U> bindToLifecycle(BaseActivity activity, @StringRes int stringRes) {

        return bindToLifecycle(activity, activity.getString(stringRes), true);
    }

    public static <U> ObservableTransformer<U, U> bindToLifecycle(BaseActivity activity, @StringRes int stringRes, boolean cancelable) {

        return bindToLifecycle(activity, activity.getString(stringRes), cancelable);
    }
    public static <U> ObservableTransformer<U, U> bindToLifecycle(BaseActivity activity, CharSequence message) {

        return bindToLifecycle(activity, message, true);
    }
    public static <U> ObservableTransformer<U, U> bindToLifecycle(BaseActivity activity, CharSequence message, boolean cancelable) {
        return upstream -> {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onTerminateDetach()//解决内存泄漏
                    .doOnSubscribe(disposable -> activity.showProgress(message, cancelable))
//                    .doOnSubscribe(null)
                    .doOnNext(u -> activity.hideProgress())
                    .doOnError(throwable -> activity.hideProgress())
                    .onTerminateDetach();
        };

    }


}
