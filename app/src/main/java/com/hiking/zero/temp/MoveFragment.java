package com.hiking.zero.temp;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.hiking.base.util.DisplayUtil;
import com.hiking.base.util.TLog;
import com.hiking.zero.R;
import com.hiking.zero.base.BaseFragment;
import com.hiking.zero.music.player.MediaPlayerHelper;
import com.hiking.zero.view.ScaleTitleTypeOne;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MoveFragment extends BaseFragment {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.v_progress)
    View v_progress;

    @BindView(R.id.ll_scale)
    ScaleTitleTypeOne ll_scale;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_move;
    }

    @Override
    public void initData() {
        super.initData();
        setProgress(0);
        MediaPlayerHelper.getIns().setMediaPlayerHelperCallBack(new MediaPlayerHelper.MediaPlayerHelperCallBack() {
            @Override
            public void onCallBack(MediaPlayerHelper.CallBackState state, MediaPlayerHelper mediaPlayerHelper, Object... args) {
                switch (state) {
                    case PROGRESS:
                        long i = (long) args[0];
                        setProgress((int) i);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick(R.id.tv_move)
    public void clickMove(View v) {
        List<String> urls = new ArrayList<>();
        urls.add("http://download.xmcdn.com/group17/M03/6C/52/wKgJKVehhA_ThA6NADlzlE9cFqM546.m4a");
        urls.add("http://download.xmcdn.com/group17/M0A/9C/94/wKgJKVez0mChTo6cADsw8A2Stt4305.m4a");
        urls.add("http://m10.music.126.net/20200622193533/008fa5b81d83f183f96e903583e6dab9/ymusic/5353/0f0f/0358/d99739615f8e5153d77042092f07fd77.mp3");
//        MediaPlayerHelper.getIns().play("http://download.xmcdn.com/group17/M03/6C/52/wKgJKVehhA_ThA6NADlzlE9cFqM546.m4a");
        MediaPlayerHelper.getIns().play(1, urls);
        ViewCompat.offsetTopAndBottom(tv_title, tv_title.getHeight());
    }

    @OnClick(R.id.tv_pause)
    public void clickPause(View v) {

        MediaPlayerHelper.getIns().pause();
        ViewCompat.offsetTopAndBottom(tv_title, -tv_title.getHeight());
    }

    @OnClick(R.id.tv_play)
    public void clickPlay(View v) {
        MediaPlayerHelper.getIns().play();
        ViewCompat.offsetTopAndBottom(tv_title, tv_title.getHeight());
    }

    @OnClick(R.id.tv_seek)
    public void clickSeek(View v) {
        MediaPlayerHelper.getIns().seekTo(100);
    }

    @OnClick(R.id.tv_pre)
    public void clickPre(View v) {
        MediaPlayerHelper.getIns().getPlayer().previous();
        TLog.d(MediaPlayerHelper.getIns().getPlayer().getPlaybackState() + "");
        MediaPlayerHelper.getIns().play();
    }

    @OnClick(R.id.tv_next)
    public void clickNext(View v) {
        MediaPlayerHelper.getIns().getPlayer().next();
        TLog.d(MediaPlayerHelper.getIns().getPlayer().getPlaybackState() + "");
        MediaPlayerHelper.getIns().getPlayer().setPlayWhenReady(true);
    }

    @OnClick(R.id.b_open)
    public void clicOpen(View v) {
        ll_scale.open();
    }

    @OnClick(R.id.b_close)
    public void clicClose(View v) {
        ll_scale.close();
    }

    private void setProgress(int i) {
        int right = DisplayUtil.getScreenWidth(getContext()) * (100 - i) / 100;
        v_progress.setPadding(0, 0, right, 0);
    }
}
