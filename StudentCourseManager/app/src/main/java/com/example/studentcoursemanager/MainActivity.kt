package com.university.coursemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.university.coursemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var courseAdapter: CourseAdapter
    private val courseList = mutableListOf<Course>()
    private val database = FirebaseDatabase.getInstance()
    private val coursesRef = database.getReference("courses")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadCourses()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddCourseActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        courseAdapter = CourseAdapter(courseList,
            onEditClick = { course ->
                val intent = Intent(this, EditCourseActivity::class.java)
                intent.putExtra("COURSE", course)
                startActivity(intent)
            },
            onDeleteClick = { course ->
                deleteCourse(course)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = courseAdapter
        }
    }

    private fun loadCourses() {
        binding.progressBar.visibility = View.VISIBLE

        coursesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                courseList.clear()
                for (child in snapshot.children) {
                    val course = child.getValue(Course::class.java)
                    course?.let { courseList.add(it) }
                }
                courseAdapter.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE

                binding.tvEmpty.visibility = if (courseList.isEmpty()) View.VISIBLE else View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteCourse(course: Course) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Delete Course")
            .setMessage("Are you sure you want to delete ${course.name}?")
            .setPositiveButton("Delete") { _, _ ->
                coursesRef.child(course.id).removeValue()
                    .addOnSuccessListener {
                        Toast.makeText(this, "Course deleted", Toast.LENGTH_SHORT).show()
                    }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}