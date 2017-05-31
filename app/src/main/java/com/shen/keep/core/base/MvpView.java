package com.shen.keep.core.base;

public interface MvpView {
    void startLoading();
    void hideLoading();
    void showError(String msg);
}