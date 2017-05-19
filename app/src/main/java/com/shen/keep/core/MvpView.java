package com.shen.keep.core;

public interface MvpView {
    void startLoading();
    void hideLoading();
    void showError(String msg);
}