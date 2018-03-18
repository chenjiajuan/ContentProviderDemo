package com.cjj.client;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

/**
 * Created by chenjiajuan on 2018/3/18.
 */

public class PersonObserver extends ContentObserver {
    private Handler handler;
    public PersonObserver(Handler handler) {
        super(handler);
        this.handler=handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Message message=new Message();
        handler.sendMessage(message);
    }
}
