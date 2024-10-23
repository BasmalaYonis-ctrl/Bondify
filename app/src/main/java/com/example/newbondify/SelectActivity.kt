package com.example.newbondify

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SelectActivity : AppCompatActivity() {
    // Declare variables for MediaPlayer, SeekBar, song title, and pause button
     companion object {
        private var mediaPlayer: MediaPlayer?=null
    }
    private lateinit var songTitleTextView: TextView
    private lateinit var volumeSeekBar: SeekBar
    private lateinit var pauseResumeButton: Button
    private lateinit var buttonGame: Button
    private lateinit var buttonQuiz: Button
    private var isPaused: Boolean = false
    private val songList = arrayOf("fly_me_to_the_moon_frank_sinatra", "my_way_frank_sinatra", "pharrell_williams_happy")
    private val songResIds = arrayOf(R.raw.fly_me_to_the_moon_frank_sinatra, R.raw.my_way_frank_sinatra, R.raw.pharrell_williams_happy)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        songTitleTextView = findViewById(R.id.songTitle)
        volumeSeekBar = findViewById(R.id.volumeSeekBar)
        pauseResumeButton = findViewById(R.id.pauseResumeButton)
        buttonGame = findViewById(R.id.buttonGame)
        buttonQuiz = findViewById(R.id.buttonQuiz)
        val selectMusicButton: Button = findViewById(R.id.selectMusicButton)

        selectMusicButton.setOnClickListener {
            showSongSelectionDialog()
        }

        setupVolumeControl()

        pauseResumeButton.setOnClickListener {
            togglePauseResumeMusic()
        }

        buttonGame.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }

        buttonQuiz.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    private fun showSongSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Background Music")

        builder.setItems(songList) { _, which ->
            playSelectedSong(which)
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun playSelectedSong(songIndex: Int) {
        mediaPlayer?.release()

        songTitleTextView.text = songList[songIndex]

        mediaPlayer = MediaPlayer.create(this, songResIds[songIndex])
        mediaPlayer?.start()

        isPaused = false
        pauseResumeButton.text = "⏸" // Set to pause icon
    }

    private fun setupVolumeControl() {
        volumeSeekBar.progress = 50

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val volumeLevel = progress / 100f
                mediaPlayer?.setVolume(volumeLevel, volumeLevel)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun togglePauseResumeMusic() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                isPaused = true
                pauseResumeButton.text = "▶" // Set to play icon
            } else if (isPaused) {
                it.start()
                isPaused = false
                pauseResumeButton.text = "⏸" // Set to pause icon
            }
        }
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }
}
