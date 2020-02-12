package com.zgw.base.ui;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.gyf.immersionbar.ImmersionBar;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.zgw.base.loadsir.EmptyCallback;
import com.zgw.base.loadsir.ErrorCallback;
import com.zgw.base.loadsir.LoadingCallback;
import com.zgw.base.util.ZGWToast;

public class BaseFragment extends Fragment implements IBaseView {

    public LoadService mLoadService;

    private boolean isShowedContent = false;

    protected Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected  void onRetryBtnClick() {
    }

    protected void initParm() {
        if (toolbar != null) {
            ImmersionBar.setTitleBar(this, toolbar);
        }
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
                ZGWToast.show(getContext(), message);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T $(View view, int resId) {
        return (T) view.findViewById(resId);
    }

}
