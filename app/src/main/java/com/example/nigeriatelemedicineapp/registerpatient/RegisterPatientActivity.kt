package com.example.nigeriatelemedicineapp.registerpatient

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nigeriatelemedicineapp.R
import com.example.nigeriatelemedicineapp.databinding.ActivityAppointmentFormBinding
import android.widget.RelativeLayout
import android.widget.ProgressBar




class RegisterPatientActivity : AppCompatActivity() {

    private lateinit var viewModel: RegisterPatientViewModel
    private lateinit var binding: ActivityAppointmentFormBinding
    var progressOverlay: View? = null
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, com.example.nigeriatelemedicineapp.R.layout.activity_appointment_form)
        viewModel = ViewModelProviders.of(this).get(RegisterPatientViewModel::class.java)
        //progressOverlay= findViewById(R.id.progress_overlay)
        setUpUI()
        setUpObservers()
    }

    private fun setUpObservers() {
        val nameObserver = Observer<String> { newName ->
           // viewModel.stopAnimation(progressOverlay!!)
            progressBar.visibility=View.GONE
            val builder = AlertDialog.Builder(this)
            builder.setMessage("You have been successfully registered. You will be contacted asap")
            builder.setTitle("Success")
            builder.setCancelable(false)
            builder.setPositiveButton("Back to DASHBOARD", DialogInterface.OnClickListener { dialog, which ->  })
            //builder.setNegativeButton("NOT Ok", DialogInterface.OnClickListener { dialog, which ->  })
            builder.create().show()
            //Toast.makeText(this, " $newName ", Toast.LENGTH_LONG).show()
        }

        viewModel.responseString.observe(this, nameObserver)

        val idfObv = Observer<String> { idf ->
           // Toast.makeText(this, "Successfully Fetched identifier : $idf ", Toast.LENGTH_SHORT).show()
        }

        viewModel.identifier.observe(this, idfObv)

    }

    fun setUpUI() {
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = getString(com.example.nigeriatelemedicineapp.R.string.appointmentFormheading)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.register.setOnClickListener { Register() }

    }

    private fun Register() {

        var gender : String? = null
        val radioGroup=findViewById<RadioGroup>(binding.gender.id)

        if(radioGroup.checkedRadioButtonId==binding.male.id)
            gender="M"
        else if (radioGroup.checkedRadioButtonId==binding.female.id)
            gender="F"


        binding.aftertext.visibility=View.VISIBLE
        progressBar = binding.progressBar
        progressBar.setIndeterminate(true)
        progressBar.setVisibility(View.VISIBLE)
        //val params = RelativeLayout.LayoutParams(100, 100)
        //params.addRule(RelativeLayout.CENTER_IN_PARENT)

      binding.CardView.visibility=View.INVISIBLE
      binding.text.visibility=View.INVISIBLE
        //viewModel.startAnimation(progressOverlay!!)
        viewModel.registerPatient(
            binding.firstName.text.toString(),
            binding.lastName.text.toString(),
            binding.DOB.text.toString(),
            gender.toString(),
            binding.phoneNumber.text.toString()
        )
    }

}
