package matheusrodrigues.androidapps.totolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import matheusrodrigues.androidapps.totolist.databinding.ActivityMainBinding
import matheusrodrigues.androidapps.totolist.datasource.TaskDataSource

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }

    private fun insertListeners() {
        binding.fabAddTask.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK )
        }
        adapter.listenerEdit = {

        }

        adapter.listenerDelete = {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CREATE_NEW_TASK){
            binding.recyclerViewTasks.adapter = adapter
            adapter.submitList(TaskDataSource.getList())
        }
    }

    companion object{
        private const val CREATE_NEW_TASK = 1000
    }
}