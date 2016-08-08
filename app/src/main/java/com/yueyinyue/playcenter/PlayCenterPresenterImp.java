package com.yueyinyue.playcenter;

import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;

/**
 * Created by Administrator on 2016/4/11.
 */
public interface PlayCenterPresenterImp
{
    void playMusic(PlayMusicMessage playMusicMessage);

    void onResume();

    void onPause();

    void onDestroy();
}
