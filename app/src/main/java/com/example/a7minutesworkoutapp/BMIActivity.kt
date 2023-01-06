package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkoutapp.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }

    private var binding: ActivityBmiBinding? = null
    private var weight = 0.0
    private var height = 0.0
    private var currentVisibleView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_metric_units) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUSUnitsView()
            }
        }

        binding?.btnCalculate?.setOnClickListener {
            if(validateUnitsInput()) {
                displayBMIResult(calcBMI())
            } else {
                Toast.makeText(this@BMIActivity,
                "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUnitsInput(): Boolean {
        weight = binding?.etWeight?.text?.toString()?.toDoubleOrNull() ?: 0.0

        height = if (currentVisibleView == METRIC_UNITS_VIEW) {
            binding?.etMetricHeight?.text?.toString()?.toDoubleOrNull() ?: 0.0
        } else {
            val feetHeightInInches =
                binding?.etUsHeightFeet?.text?.toString()?.toDoubleOrNull()?.times(12) ?: 0.0

            val inchHeight = binding?.etUsHeightInch?.text?.toString()?.toDoubleOrNull() ?: 0.0

            feetHeightInInches + inchHeight
        }

        return height != 0.0 && weight != 0.0
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        binding?.etMetricHeightView?.visibility = View.VISIBLE
        binding?.etMetricHeight?.text?.clear()
        binding?.etWeight?.text?.clear()
        binding?.etWeightView?.hint = "WEIGHT (in kg)"

        binding?.llUsHeightView?.visibility = View.GONE
        binding?.etUsHeightFeet?.text?.clear()
        binding?.etUsHeightInch?.text?.clear()

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW

        binding?.etMetricHeightView?.visibility = View.INVISIBLE
        binding?.etWeight?.text?.clear()
        binding?.etMetricHeight?.text?.clear()
        binding?.etWeightView?.hint = "WEIGHT (in pounds)"

        binding?.llUsHeightView?.visibility = View.VISIBLE
        binding?.etUsHeightFeet?.text?.clear()
        binding?.etUsHeightInch?.text?.clear()

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
    }

    private fun calcBMI(): Double {

        return if (currentVisibleView == METRIC_UNITS_VIEW) {
            weight / (height * height)
        } else {
            (weight / height / height) * 703
        }
    }

    private fun displayBMIResult(bmi: Double) {

        val bmiType: String
        val bmiDescription: String

        if (bmi <= 16.0) {
            bmiType= "Underweight (Severe thinness)"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi > 16.0 && bmi <= 16.9) {
            bmiType= "Underweight (Moderate thinness)"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi > 16.9 && bmi <= 18.4) {
            bmiType= "Underweight (Mild thinness)"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi > 18.4 && bmi <= 24.9) {
            bmiType= "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi > 24.9 && bmi <= 29.9) {
            bmiType= "Overweight (Pre-obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        } else if (bmi > 29.9 && bmi <= 34.9) {
            bmiType= "Obese (Class I)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout more!"
        } else if (bmi > 34.9 && bmi <= 39.9) {
            bmiType= "Obese (Class II)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiType= "Obese (Class III)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiAsString = BigDecimal(bmi).setScale(2, RoundingMode.HALF_DOWN).toString()
        binding?.tvBmiValue?.text = bmiAsString
        binding?.tvBmiType?.text = bmiType
        binding?.tvBmiDescription?.text = bmiDescription
        binding?.llDisplayBmiResult?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
        finish()
    }
}