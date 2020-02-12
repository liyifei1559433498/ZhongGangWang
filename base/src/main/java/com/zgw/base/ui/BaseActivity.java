package com.zgw.base.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.umeng.message.PushAgent;
import com.zgw.base.R;
import com.zgw.base.loadsir.EmptyCallback;
import com.zgw.base.loadsir.ErrorCallback;
import com.zgw.base.loadsir.LoadingCallback;
import com.zgw.base.util.ZGWToast;


public class BaseActivity extends AppCompatActivity implements IBaseView {

    public Dialog progressDialog;

    private boolean isProgressShowing;

    public LoadService mLoadService;

    private boolean isShowedContent = false;

    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).statusBarColor(R.color.orange).init();
        PushAgent.getInstance(this).onAppStart();
    }

    protected  void onRetryBtnClick() {

    }

    public void  initParameter() {
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
    }

    @LayoutRes
    protected int getLayoutId() {
        return 0;
    }

    public void setLoadSir(View view) {
        // You can change the callback on sub thread directly.
        mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRetryBtnClick();
            }
        });
    }

    public void showProgress(@StringRes int resId) {
        showProgress(resId, true);
    }

    public void showProgress(CharSequence msg) {
        showProgress(msg, true);
    }

    public void showProgress(@StringRes int resId, boolean cancelable) {
        String msg = getString(R.string.in_progress);
        if (resId != 0) {
            msg = getString(resId);
        }
        showProgress(msg, cancelable);
    }


    public void showProgress(CharSequence msg, boolean cancelable) {
        if (!isProgressShowing && !isFinishing()) {
//            ProgressDialogFragment fragment = (ProgressDialogFragment) getFragmentByTag(getSupportFragmentManager(),
//                    ProgressDialogFragment.TAG);
//            if (fragment == null) {
//                isProgressShowing = true;
//                fragment = ProgressDialogFragment.newInstance(msg, cancelable);
//                fragment.show(getSupportFragmentManager(), ProgressDialogFragment.TAG);
//            }
//            showProgressDialog(cancelable);
        }
    }

    private void showProgressDialog(boolean isCancelable) {
        if (progressDialog == null) {
            progressDialog = new Dialog(this, R.style.NoFrameDialog);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.loading_layout, null);
        progressDialog.setContentView(view);


        progressDialog.setCancelable(isCancelable);
//        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    public void hideProgress() {
//        ProgressDialogFragment fragment = (ProgressDialogFragment) getFragmentByTag(getSupportFragmentManager(),
//                ProgressDialogFragment.TAG);
//        if (fragment != null) {
//            fragment.dismiss();
//
//        }

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        isProgressShowing = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showContent() {
        if (mLoadService != null) {
            isShowedContent = true;
            mLoadService.showSuccess();
        }
    }

    @Override
    public void showLoading() {
        if (mLoadService != null) {
            mLoadService.showCallback(LoadingCallback.class);
        }
    }

    @Override
    public void showFailure() {
        if (mLoadService != null) {
            mLoadService.showCallback(ErrorCallback.class);
        }
    }

    @Override
    public void onRefreshEmpty() {
        if(mLoadService != null){
            mLoadService.showCallback(EmptyCallback.class);
        }
    }

    @Override
    public void onRefreshFailure(String message) {
        if (mLoadService != null) {
            if(!isShowedContent) {
                mLoadService.showCallback(ErrorCallback.class);
            } else {
                ZGWToast.show(this, message);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T $(int resId) {
        return (T) findViewById(resId);
    }

}
