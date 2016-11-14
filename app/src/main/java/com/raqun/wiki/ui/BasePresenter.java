package com.raqun.wiki.ui;

import android.support.annotation.NonNull;

/**
 * Created by tyln on 21.08.16.
 */
public interface BasePresenter {
    void subscribe();

    void unsubscribe();

    void destroy();
}
