package com.example.nguyenngocsonc.musicsimplekotlin.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.SeekBar
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter

/**
 * Created by nguyen.ngoc.sonc on 12/28/17.
 */
class PlayMusicService : Service() {

    var currentPos: Int = 0
    var musicDataList: ArrayList<String> = ArrayList()
    var mp: MediaPlayer? = null
    private val musicBind = MusicBinder()
    var pauseTime: Int = 0

    inner class MusicBinder : Binder() {
        fun getService(): PlayMusicService {
            return this@PlayMusicService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return musicBind
    }

    override fun onUnbind(intent: Intent): Boolean {
        mp!!.stop()
        mp!!.release()
        return false
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        currentPos = intent!!.getIntExtra(SongListAdapter.MUSICTEMPOS, 0)
        musicDataList = intent!!.getStringArrayListExtra(SongListAdapter.MUSICLIST)

        playMusic(currentPos)
        return super.onStartCommand(intent, flags, startId)
    }

    fun playMusic(pos: Int) {
        if (mp != null) {
            mp!!.stop()
            mp!!.release()
            mp = null
            pauseTime = 0
        }

        mp = MediaPlayer()
        mp!!.setDataSource(musicDataList[pos])
        mp!!.prepare()
        mp!!.setOnPreparedListener {
            mp!!.start()
        }

        mp!!.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer?) {
                nextTrack()
            }
        })
    }

    override fun onDestroy() {
        if (mp != null) {
            mp!!.stop()
            mp!!.release()
            mp = null
        }
    }

    fun isPlaying(): Boolean {
        return mp!!.isPlaying()
    }

    fun resumePlayer() {
        mp!!.seekTo(pauseTime)
        mp!!.start()
    }

    fun pausePlayer() {
        mp!!.pause()
        pauseTime = mp!!.currentPosition
    }

    fun nextTrack() {
        if (currentPos < musicDataList.size - 1) {
            currentPos++
            playMusic(currentPos)
        }
    }

    fun prevTrack() {
        if (currentPos > 0) {
            currentPos--
            playMusic(currentPos)
        }
    }

    fun setSeekBarProgress(mSeekbar: SeekBar) {
        if (mp != null) {
            var mCurrentTime = mp!!.currentPosition
            mSeekbar.setProgress(mCurrentTime)
        }
    }

    fun seekbarChangePosition(fromUser: Boolean, progress: Int) {
        if (mp != null && fromUser) {
            mp!!.seekTo(progress)
        }
    }
}