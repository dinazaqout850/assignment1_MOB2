package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.databinding.ActivityMainBinding;

// The host Activity implements both Fragment interfaces to receive data/events from the fragments.
public class MainActivity extends AppCompatActivity
        implements Fragment2.OnFragmentInteractionListener,
        Fragment3.OnCheckBoxChangeListener {

    private ActivityMainBinding binding;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Fragment1 fragment1 = Fragment1.newInstance(null, null);
        Fragment2 fragment2 = Fragment2.newInstance(null, null);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, fragment1)
                .commit();

        // The Activity's Continue button is only used to move from Fragment1 to Fragment2.
        binding.buttonContinue.setOnClickListener(view -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment2)
                    .addToBackStack(null)
                    .commit();
        });

        // Fragment2 and Fragment3 have their own buttons, so hide the Activity's button
        // whenever a fragment is on the back stack (and show it again on Fragment1).
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            binding.buttonContinue.setVisibility(count == 0 ? View.VISIBLE : View.GONE);
        });
    }

    // Callback from Fragment2: receives the user's name, then switches to Fragment3.
    @Override
    public void onContinueClicked(String name) {
        this.name = name;

        // Pass the name to Fragment3 using a Bundle (inside newInstance()).
        Fragment3 fragment3 = Fragment3.newInstance(name);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment3)
                .addToBackStack(null)
                .commit();
    }

    // Callback from Fragment3: the checkbox state changed,
    // so tell Fragment3 to update its button (enabled/disabled + text).
    @Override
    public void onCheckBoxChanged(boolean isChecked) {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        if (fragment instanceof Fragment3) {
            ((Fragment3) fragment).updateButton(isChecked);
        }
    }
}