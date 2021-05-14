package com.example.qtracker.bottomNavigation

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.example.qtracker.R
import com.example.qtracker.profile.ProfileFragment
import com.example.qtracker.shedule.SheduleFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.botnav.*


class BottomNavigation : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.botnav)

        botnav.setOnNavigationItemSelectedListener(navListener)

        botnav.itemIconTintList = null

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.boxForFragments,
                    SheduleFragment()).commit()
        }
    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                var selectedFragment: Fragment? = null
                when (item.itemId) {
                    R.id.list_item_botnav -> selectedFragment = ListFragment()
                    R.id.calendar_item_botnav -> selectedFragment = SheduleFragment()
                    R.id.user_item_botnav -> selectedFragment = ProfileFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.boxForFragments,
                        selectedFragment!!).commit()
                true
            }

}