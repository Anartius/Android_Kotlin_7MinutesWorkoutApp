package com.example.a7minutesworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkoutapp.databinding.ActivityExerciseBinding
import com.example.a7minutesworkoutapp.databinding.DialogCustomBackConfirmationBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restTimerDuration: Long = 1
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseTimerDuration: Long = 1
    private var exerciseProgress = 0

    private var exerciseList: MutableList<Exercise>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.getDefaultExerciseList()

        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        tts = TextToSpeech(this@ExerciseActivity, this@ExerciseActivity)
        tts?.setPitch(1.1f)
        tts?.setSpeechRate(0.75f)

        setupRestView()
        setupExerciseStatusView()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this@ExerciseActivity)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun setupExerciseStatusView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(
                this@ExerciseActivity, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = exerciseList?.let { ExerciseStatusAdapter(it) }
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView() {

        try {
            val soundURI = Uri.parse(
                "android.resource://com.example.a7minutesworkoutapp/" +
                        R.raw.press_start
            )
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            //player?.start()

        } catch (e:Exception) {
            e.printStackTrace()
        }

        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE

        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivExercise?.visibility = View.INVISIBLE

        restTimer?.let {
            it.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar () {
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(
            restTimerDuration * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()

                if (restProgress == 3) {
                    val currentExerciseName = exerciseList!![currentExercisePosition + 1].name
                    binding?.tvUpcomingExerciseName?.text = currentExerciseName
                    //speakOut("Upcoming exercise is $currentExerciseName")
                }
            }

            override fun onFinish() {
                currentExercisePosition++

                exerciseList?.let { it[currentExercisePosition].isSelected = true }
                exerciseAdapter?.notifyItemChanged(currentExercisePosition)

                setupExerciseView()
            }

        }.start()
    }

    private fun setupExerciseView() {
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivExercise?.visibility = View.VISIBLE

        exerciseTimer?.let {
            it.cancel()
            exerciseProgress = 0
        }

        binding?.ivExercise?.setImageResource(exerciseList!![currentExercisePosition].image)
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].name
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar () {
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(
            exerciseTimerDuration * 1000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList?.let { it[currentExercisePosition].isSelected = false }
                exerciseList?.let { it[currentExercisePosition].isCompleted = true }
                exerciseAdapter?.notifyItemChanged(currentExercisePosition)

                if (currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                } else {
                    finish()
                    startActivity(Intent(
                        this@ExerciseActivity, FinishActivity::class.java))
                }
            }
        }.start()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val setLanguageResult = tts?.setLanguage(Locale.US)

            if (setLanguageResult == TextToSpeech.LANG_NOT_SUPPORTED ||
                    setLanguageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The specified language isn't supported.")
            } else {
                Log.e("TTS", "Initialization failed.")
            }
        }
    }

    private fun speakOut(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()

        restTimer?.let {
            it.cancel()
            restProgress = 0
        }
        exerciseTimer?.let {
            it.cancel()
            exerciseProgress = 0
        }
        tts?.let {
            it.stop()
            it.shutdown()
        }
        player?.stop()
        binding = null
    }

}