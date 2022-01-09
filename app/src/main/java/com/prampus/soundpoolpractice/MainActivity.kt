package com.prampus.soundpoolpractice

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.prampus.soundpoolpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private lateinit var soundPool: SoundPool

    private val soundIdMap = hashMapOf<String, Int>()
    private val streamIdMap = hashMapOf<String, Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.tv.setOnClickListener {
            soundPool.play(1, 4F, 4F, 1, 1, 1F)
        }



        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            SoundPool.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setMaxStreams(1) // 동시에 재생 가능한 사운드 최대 수
                .build()
        } else {
            SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }

        soundPool.apply {
            soundIdMap["sound1"] = load(this@MainActivity, R.raw.sound_dingdong, 1)

            setOnLoadCompleteListener { soundPool, sampleId, status ->
                Toast.makeText(this@MainActivity, "load complete", Toast.LENGTH_SHORT).show()
            }
        }




    }



    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }

    fun onClickPlay() {
        streamIdMap["sound1"] = soundPool.play(soundIdMap["sound1"]!!, 0.5f, 0.5f, 1, 0, 1f)
    }

    fun onClickStop(view: View) {
        soundPool.stop(streamIdMap["sound1"]!!)
        // soundPool.pause(streamIdMap["sound1"]!!) // 해당 사운드 일시정지
        // soundPool.autoPause() // 재생중인 사운드 모두 일시정지
    }



}