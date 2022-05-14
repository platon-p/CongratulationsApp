package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var homePage: HomePage
    lateinit var libraryPage: LibraryPage

    val MAX_PACKAGE_NOTIFICATIONS: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homePage = HomePage()
        libraryPage = LibraryPage()

        replaceFragment(homePage)
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportActionBar?.setTitle(R.string.home_title)
                    replaceFragment(homePage)
                    true
                }
                R.id.library -> {
                    supportActionBar?.setTitle(R.string.library_title)
                    replaceFragment(libraryPage)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }



}