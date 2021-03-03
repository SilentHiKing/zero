package com.hiking.zero.music.player;



public abstract class PlayerAdapter {





    public final void play() {
        onPlay();
    }

    public final void stop() {
        onStop();
    }


    public final void pause() {
        onPause();
    }

    protected abstract void onPlay();

    protected abstract void onPause();

    public abstract boolean isPlaying();

    protected abstract void onStop();

    public abstract void seekTo(long position);







}


