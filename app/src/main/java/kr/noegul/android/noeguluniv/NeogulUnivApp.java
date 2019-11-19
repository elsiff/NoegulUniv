package kr.noegul.android.noeguluniv;

import android.app.Application;
import android.content.Context;

import kr.noegul.android.noeguluniv.player.PlayerData;
import kr.noegul.android.noeguluniv.player.SharedPrefPlayerData;

public class NeogulUnivApp extends Application {
    private static NeogulUnivApp instance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
    }

    public PlayerData getPlayerData() {
        return new SharedPrefPlayerData(context.getSharedPreferences("player-data", MODE_PRIVATE));
    }

    public static NeogulUnivApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }
}
