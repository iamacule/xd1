package vn.mran.xd1.instance;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

import vn.mran.xd1.R;
import vn.mran.xd1.helper.Log;

/**
 * Created by Mr An on 28/11/2017.
 */

public class Media {
    public static MediaPlayer mediaPlayer;

    private static String TAG = "Media";

    public static void playBackgroundMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.background);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.seekTo(2000);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    public static void stopBackgroundMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }
}
