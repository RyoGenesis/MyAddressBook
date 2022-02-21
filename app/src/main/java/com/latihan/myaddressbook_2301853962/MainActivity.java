package com.latihan.myaddressbook_2301853962;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.latihan.myaddressbook_2301853962.fragments.AddressBookFragment;
import com.latihan.myaddressbook_2301853962.fragments.SearchEmployeeFragment;

//Made by Rio - 2301853962
public class MainActivity extends AppCompatActivity {

    private SearchEmployeeFragment searchEmployeeFragment;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(btmNavListener);

        searchEmployeeFragment = new SearchEmployeeFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, searchEmployeeFragment).commit();
    }

    private final NavigationBarView.OnItemSelectedListener btmNavListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()) {
                case R.id.nav_search:
                    selectedFragment = new SearchEmployeeFragment();
                    break;
                case R.id.nav_address_book:
                    selectedFragment = new AddressBookFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .addToBackStack(null)
                    .commit();

            return true;
        }
    };
}