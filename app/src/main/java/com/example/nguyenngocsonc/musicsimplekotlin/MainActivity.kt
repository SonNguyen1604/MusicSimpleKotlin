package com.example.nguyenngocsonc.musicsimplekotlin

import android.Manifest
import android.annotation.TargetApi
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.databinding.DataBindingUtil
import android.os.*
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.example.nguyenngocsonc.musicsimplekotlin.Interface.CustomAlbumItemClickListener
import com.example.nguyenngocsonc.musicsimplekotlin.Interface.CustomItemClickListener
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.AlbumListAdapter
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter.Companion.MUSICLIST
import com.example.nguyenngocsonc.musicsimplekotlin.adapters.SongListAdapter.Companion.MUSICTEMPOS
import com.example.nguyenngocsonc.musicsimplekotlin.model.AlbumModel
import com.example.nguyenngocsonc.musicsimplekotlin.model.SongModel
import com.example.nguyenngocsonc.musicsimplekotlin.service.PlayMusicService
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.annotation.RequiresApi
import android.support.v7.widget.DividerItemDecoration
import com.example.nguyenngocsonc.musicsimplekotlin.builder.BlurBuilder
import com.example.nguyenngocsonc.musicsimplekotlin.data.local.AlbumLocalData
import com.example.nguyenngocsonc.musicsimplekotlin.databinding.ActivityMainBinding
import com.example.nguyenngocsonc.musicsimplekotlin.viewmodels.AlbumViewModel


class MainActivity : AppCompatActivity(), CustomItemClickListener, CustomAlbumItemClickListener {

    var musicSrv: PlayMusicService? = null
    var musicBound = false
    private var currentPos: Int = 0
    private lateinit var timer: CountDownTimer

    override fun onCustomItemClick(view: View, pos: Int) {
        allMusicList.clear()
        for (i in 0 until songModelData.size) {
            allMusicList.add(songModelData[i].mSongPath)
        }
        currentPos = pos
        var musicDataIntent = Intent(this, PlayMusicService::class.java)
        musicDataIntent.putStringArrayListExtra(MUSICLIST, allMusicList)
        musicDataIntent.putExtra(MUSICTEMPOS, pos)
        this.bindService(musicDataIntent, musicConnection, Context.BIND_AUTO_CREATE)
        this.startService(musicDataIntent)
        var model = songModelData[pos]
        track_name_tv.setText(model.mSongName)
        play_button.setImageResource(R.drawable.pause)
        progressBarTime.visibility = View.VISIBLE
        progressBarTime.setProgress(0)
        progressBarTime.max = model.mSongDuration.toInt()

        val mHandler = Handler()
        this@MainActivity.runOnUiThread(object : Runnable {

            override fun run() {
                musicSrv?.setSeekBarProgress(progressBarTime)
                mHandler.postDelayed(this, 1)
            }
        })

        timer = object : CountDownTimer(model.mSongDuration.toLong(), 1000) {
            override fun onFinish() {

            }

            override fun onTick(millisUntilFinished: Long) {
                track_time_tv.setText(songListAdapter!!.toMands(millisUntilFinished))
            }

        }
        timer.start()

        progressBarTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                musicSrv?.seekbarChangePosition(fromUser, progress)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCustomAlbumItemClick(view: View, pos: Int) {
        var model = albumModelData[pos]
        if (model.mAlbumArt != null) {
            val bmImg = BitmapFactory.decodeFile(model.mAlbumArt)
            var blurBuilder = BlurBuilder()
            var imageBlur = blurBuilder.blur(this, bmImg)
//            val background = BitmapDrawable(imageBlur)
            background_app.setImageBitmap(imageBlur)
        } else {
            background_app.setBackgroundColor(Color.parseColor("#3F51B5"))
        }

//        getSongInAlbum(model.mAlbumName)

    }

    private var allMusicList: ArrayList<String> = ArrayList()
    private var songModelData: ArrayList<SongModel> = ArrayList()
    private var albumModelData: ArrayList<AlbumModel> = ArrayList()
    private var songListAdapter: SongListAdapter? = null
    private var albumListAdapter: AlbumListAdapter? = null

    companion object {
        val PERMISSION_REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        } else {
            val albumData = AlbumLocalData(this)
            val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
            binding.viewModel = AlbumViewModel(albumData, this)
            binding.executePendingBindings()
        }
    }

    private fun loadData() {
//        var songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null)
//
//        while (songCursor != null && songCursor.moveToNext()) {
//            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
//            var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
//            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//            songModelData.add(SongModel(songName, songDuration, songPath))
//        }
//        songListAdapter = SongListAdapter(songModelData, applicationContext, this)
//
//        var layoutTrackManager = LinearLayoutManager(applicationContext)
//        track_list_view.layoutManager = layoutTrackManager
//        var dividerItemDecoration = DividerItemDecoration(track_list_view.context, layoutTrackManager.orientation)
//        track_list_view.adapter = songListAdapter
//        track_list_view.addItemDecoration(dividerItemDecoration)
//        getAlbum()

    }

    private fun getSongInAlbum(albumName: String) {
//        val columns = arrayOf(android.provider.MediaStore.Audio.Albums._ID, android.provider.MediaStore.Audio.Albums.ALBUM)
//        val where = android.provider.MediaStore.Audio.Media.ALBUM + "=?"
//        val whereVal = arrayOf(albumName)
//
//        var songCursor: Cursor? = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, where, whereVal, null)
//        songModelData.clear()
//        //Song
//        while (songCursor != null && songCursor.moveToNext()) {
//            var songName = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
//            var songDuration = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
//            var songPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA))
//            songModelData.add(SongModel(songName, songDuration, songPath))
//        }
//        songListAdapter = SongListAdapter(songModelData, applicationContext, this)
//
//        var layoutTrackManager = LinearLayoutManager(applicationContext)
//        track_list_view.layoutManager = null
//        track_list_view.layoutManager = layoutTrackManager
//        var dividerItemDecoration = DividerItemDecoration(track_list_view.context, layoutTrackManager.orientation)
//        track_list_view.addItemDecoration(dividerItemDecoration)
//        track_list_view.adapter = songListAdapter
    }

    private fun getAlbum() {
//        var albumCursor: Cursor? = contentResolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, null, null, null, null)
//
//        while (albumCursor != null && albumCursor.moveToNext()) {
//            var albumName = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM))
//            var albumArt = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART))
//
//            if(albumModelData.none { x -> x.mAlbumName == albumName }) {
//                albumModelData.add(AlbumModel(albumName, albumArt))
//            }
//        }
//
//        albumListAdapter = AlbumListAdapter(albumModelData, applicationContext, this)
//
//        val layoutManager: CarouselLayoutManager  = object: CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true) {
//
//        }
//        recycler_view.setHasFixedSize(true)
//        layoutManager.setPostLayoutListener(object: CarouselZoomPostLayoutListener() {
//
//        })
//        recycler_view.addOnScrollListener(object: CenterScrollListener() {
//
//        })
//        recycler_view.layoutManager = layoutManager
//        recycler_view.adapter = albumListAdapter
    }

    private val musicConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as PlayMusicService.MusicBinder
            //get service
            musicSrv = binder.getService()
            //pass list
            musicBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            musicBound = false
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onStartButtonClick(v: View) {
        if (musicSrv!!.isPlaying()) {
            musicSrv?.pausePlayer()
            play_button.setImageResource(R.drawable.play)
        } else {
            musicSrv?.resumePlayer()
            play_button.setImageResource(R.drawable.pause)
        }
    }

    fun onNextButtonClick(v: View) {
        if (currentPos < songModelData.size - 1) {
            musicSrv?.nextTrack()
            currentPos++
            var model = songModelData[currentPos]
            track_name_tv.setText(model.mSongName)
        }
    }

    fun onPrevButtonClick(v: View) {
        if (currentPos > 0) {
            musicSrv?.prevTrack()
            currentPos--
            var model = songModelData[currentPos]
            track_name_tv.setText(model.mSongName)
        }
    }

//    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
//        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.name == service.service.className) {
//                return true
//            }
//        }
//        return false
//    }
}
