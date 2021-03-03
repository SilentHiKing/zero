package com.hiking.zero.music.player;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.hiking.base.util.ContextUtil;
import com.hiking.base.util.TLog;

import java.util.List;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;
import static com.google.android.exoplayer2.Player.STATE_ENDED;
import static com.google.android.exoplayer2.Player.STATE_IDLE;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class MediaPlayerHelper extends PlayerAdapter {
    private Context mContext;
    private static MediaPlayerHelper instance;

    public void setMediaPlayerHelperCallBack(MediaPlayerHelper.MediaPlayerHelperCallBack mediaPlayerHelperCallBack) {
        this.mMediaPlayerHelperCallBack = mediaPlayerHelperCallBack;
    }

    private MediaPlayerHelperCallBack mMediaPlayerHelperCallBack;
    private int delaySecondTime = 1000;

    /**
     * 获得静态类
     *
     * @return 类对象
     */
    public static synchronized MediaPlayerHelper getIns() {
        if (instance == null) {
            instance = new MediaPlayerHelper();
        }
        return instance;
    }

    public SimpleExoPlayer getPlayer() {
        return mExoPlayer;
    }

    private SimpleExoPlayer mExoPlayer;

    private MediaPlayerHelper() {
        super();
        mContext = ContextUtil.getApplication();
        mExoPlayer = initPlayer();

    }

    DataSource.Factory mDataSourceFactory;


    public boolean play(int index, List<String> urls) {
        try {
            stopPlayerForPrepare(false);
            ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
            for (String url : urls) {
                MediaSource mediaSource =
                        new ProgressiveMediaSource.Factory(mDataSourceFactory)
                                .setTag(url)
                                .createMediaSource(Uri.parse(url));
                concatenatingMediaSource.addMediaSource(mediaSource);
            }
            mExoPlayer.prepare(concatenatingMediaSource);
            mExoPlayer.seekToDefaultPosition(index);
            play();
            startTrackingPlayback();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过文件路径或者网络路径播放音视频
     *
     * @param localPathOrURL 路径
     * @return 是否成功
     */
    public boolean play(final String localPathOrURL) {
        try {
            TLog.d("Index=" + mExoPlayer.getCurrentWindowIndex());
            MediaSource mediaSource =
                    new ProgressiveMediaSource.Factory(mDataSourceFactory)
                            .createMediaSource(Uri.parse(localPathOrURL));
            mExoPlayer.prepare(mediaSource);
            play();
            startTrackingPlayback();
        } catch (Exception e) {
            callBack(CallBackState.EXCEPTION, e);
            return false;
        }
        return true;
    }


    public void stopPlayerForPrepare(boolean playWhenReady) {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.setPlayWhenReady(playWhenReady);
        }
    }


    private SimpleExoPlayer initPlayer() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(C.CONTENT_TYPE_MUSIC)
                .setUsage(C.USAGE_MEDIA)
                .build();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, new DefaultLoadControl());
        player.setAudioAttributes(audioAttributes, true);
        player.addListener(new PlayerEventListener(this));
        player.addAnalyticsListener(new EventLogger(trackSelector));
        mDataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "nihao"), null);
        return player;
    }

    @Override
    protected void onPlay() {
        if (mExoPlayer != null /*&& !mExoPlayer.getPlayWhenReady()*/) {
            //发出播放指令
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    protected void onPause() {
        if (mExoPlayer != null && mExoPlayer.getPlayWhenReady()) {
            TLog.d("onPause: mediacontr  exoplayer");
            mExoPlayer.setPlayWhenReady(false);
        }
    }

    @Override
    public boolean isPlaying() {
        return mExoPlayer != null && mExoPlayer.getPlayWhenReady();
    }

    @Override
    protected void onStop() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }
    }

    @Override
    public void seekTo(long position) {
        if (mExoPlayer != null) {
            long seek = mExoPlayer.getCurrentPosition() + (mExoPlayer.getDuration() - mExoPlayer.getCurrentPosition()) / 5;
            TLog.d("getCurrentPosition=" + mExoPlayer.getCurrentPosition() + "mExoPlayer.getDuration()=" + mExoPlayer.getDuration() + "seek=" + seek);
            mExoPlayer.seekTo(seek);
            TLog.d("getCurrentPosition=" + mExoPlayer.getCurrentPosition());
        }
    }


    public void setRepeatMode(@Player.RepeatMode int repeatMode) {
        if (mExoPlayer != null) {
            mExoPlayer.setRepeatMode(repeatMode);
        }
    }


    public void release() {//release the player when not being used
        if (mExoPlayer != null) {
            mExoPlayer.release();
            mExoPlayer = null;
        }
        refress_time_handler.removeCallbacks(refress_time_Thread);
    }

    /**
     * 播放进度定时器
     */
    Handler refress_time_handler = new Handler();
    Runnable refress_time_Thread = new Runnable() {
        @Override
        public void run() {
            refress_time_handler.removeCallbacks(refress_time_Thread);
            if (mExoPlayer != null && isPlaying()) {
                TLog.d("mExoPlayer.getCurrentPosition()=" + mExoPlayer.getCurrentPosition());
                callBack(CallBackState.PROGRESS, 100 * mExoPlayer.getCurrentPosition() / mExoPlayer.getDuration());
            }
            refress_time_handler.postDelayed(refress_time_Thread, delaySecondTime);
        }
    };

    public void startTrackingPlayback() {//这里可以进行Service Callback通信
        refress_time_handler.postDelayed(refress_time_Thread, delaySecondTime);
    }

    private class PlayerEventListener implements Player.EventListener {

        private MediaPlayerHelper helper;

        public PlayerEventListener(MediaPlayerHelper helper) {
            this.helper = helper;
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            if (helper != null) {
                helper.callBack(CallBackState.EXCEPTION, error);
            }
            TLog.e("error=" + error.toString());
            error.printStackTrace();
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case STATE_IDLE:
                    if (helper != null) {
                        helper.callBack(CallBackState.IDLE);
                    }
                    TLog.d("STATE_IDLE");
                    break;
                //缓冲状态
                case STATE_BUFFERING:
                    TLog.d("STATE_BUFFERING");
                    break;
                //播放状态
                case STATE_READY:
                    if (helper != null) {
                        helper.callBack(CallBackState.PREPARE);
                    }
                    TLog.d("STATE_READY");
                    break;
                //播放完成
                case STATE_ENDED:
                    if (helper != null) {
                        helper.callBack(CallBackState.COMPLETE);
                    }
                    TLog.d("STATE_ENDED");

                default:
                    break;
            }
        }

        @Override
        public void onPositionDiscontinuity(int reason) {
            TLog.d("CurrentTag=" + mExoPlayer.getCurrentTag());
        }
    }

    /**
     * 统一回调
     *
     * @param state 状态
     * @param args  若干参数
     */
    private void callBack(CallBackState state, Object... args) {
        if (mMediaPlayerHelperCallBack != null) {
            mMediaPlayerHelperCallBack.onCallBack(state, instance, args);
        }
    }

    /**
     * 回调接口
     */
    public interface MediaPlayerHelperCallBack {
        /**
         * 状态回调
         *
         * @param state             状态
         * @param mediaPlayerHelper MediaPlayer
         * @param args              若干参数
         */
        void onCallBack(CallBackState state, MediaPlayerHelper mediaPlayerHelper, Object... args);
    }

    /**
     * 状态枚举
     */
    public enum CallBackState {
        IDLE("MediaPlayer--空闲"),
        PREPARE("MediaPlayer--准备完毕"),
        COMPLETE("MediaPlayer--播放结束"),
        ERROR("MediaPlayer--播放错误"),
        EXCEPTION("MediaPlayer--播放异常"),
        INFO("MediaPlayer--播放开始"),
        PROGRESS("MediaPlayer--播放进度回调"),
        SEEK_COMPLETE("MediaPlayer--拖动到尾端"),
        VIDEO_SIZE_CHANGE("MediaPlayer--读取视频大小"),
        BUFFER_UPDATE("MediaPlayer--更新流媒体缓存状态"),
        FORMATE_NOT_SURPORT("MediaPlayer--音视频格式可能不支持"),
        SURFACEVIEW_NULL("SurfaceView--还没初始化"),
        SURFACEVIEW_NOT_ARREADY("SurfaceView--还没准备好"),
        SURFACEVIEW_CHANGE("SurfaceView--Holder改变"),
        SURFACEVIEW_CREATE("SurfaceView--Holder创建"),
        SURFACEVIEW_DESTROY("SurfaceView--Holder销毁");

        private final String state;

        CallBackState(String var3) {
            this.state = var3;
        }

        @Override
        public String toString() {
            return this.state;
        }
    }

}
