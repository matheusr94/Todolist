package matheusrodrigues.androidapps.totolist.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import matheusrodrigues.androidapps.totolist.databinding.ActivityAddTaskBinding
import matheusrodrigues.androidapps.totolist.datasource.TaskDataSource
import matheusrodrigues.androidapps.totolist.extensions.format
import matheusrodrigues.androidapps.totolist.extensions.text
import matheusrodrigues.androidapps.totolist.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra(TASK_ID)){
            val taskId = intent.getIntExtra(TASK_ID, 0)
            TaskDataSource.findById(taskId)?.let {
                binding.title.text = it.title
                binding.date.text = it.date
                binding.hour.text = it.date
            }
        }

        insertListeners()
    }

    private fun insertListeners() {

        binding.date.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.date.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.hour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if(timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if(timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.hour.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }

        binding.btnCancelTask.setOnClickListener {
            finish()
        }

        binding.btnCreateTask.setOnClickListener {
            val task = Task(
                title = binding.title.text,
                date = binding.date.text,
                hour = binding.hour.text,
                id = intent.getIntExtra(TASK_ID, 0)

            )
            TaskDataSource.insertTask(task)
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    companion object{
        const val TASK_ID = "task_id"
    }
}