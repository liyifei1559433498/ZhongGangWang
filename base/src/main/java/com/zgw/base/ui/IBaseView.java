package com.zgw.base.ui;


public interface IBaseView {

    void showContent();

    void showLoading();

    void showFailure();

    void onRefreshEmpty();

    void onRefreshFailure(String message);
}
