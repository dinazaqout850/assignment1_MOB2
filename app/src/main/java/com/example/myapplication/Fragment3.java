package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.databinding.Fragment3Binding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    private Fragment3Binding binding;

    // Key used to find the name inside the Bundle.
    private static final String ARG_NAME = "name";

    private String mName;

    // 1. Define a listener interface with a callback method.
    //    The host Activity will implement it to be told when the checkbox changes.
    public interface OnCheckBoxChangeListener {
        void onCheckBoxChanged(boolean isChecked);
    }

    // 2. Variable for the listener (the host Activity).
    private OnCheckBoxChangeListener mListener;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided name.
     *
     * @param name The user's name (received from Fragment2 through the Activity).
     * @return A new instance of fragment Fragment3.
     */
    public static Fragment3 newInstance(String name) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    // 3. onAttach() makes sure the host Activity has implemented the interface.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCheckBoxChangeListener) {
            mListener = (OnCheckBoxChangeListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement OnCheckBoxChangeListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = Fragment3Binding.inflate(inflater, container, false);

        // Display the user's name that came from the Bundle.
        binding.textViewName.setText("Name: " + mName);

        // 4. When the checkbox is checked/unchecked, tell the Activity using the interface.
        binding.checkBoxConfirm.setOnCheckedChangeListener(
                (buttonView, isChecked) -> mListener.onCheckBoxChanged(isChecked));

        // Make sure the button matches the checkbox state when the view is (re)created.
        updateButton(binding.checkBoxConfirm.isChecked());

        return binding.getRoot();
    }

    // 5. The Activity calls this method (after receiving the callback) to update the button:
    //    checked   -> button enabled and its text becomes "Finish"
    //    unchecked -> button disabled and its text goes back to "Continue"
    public void updateButton(boolean isChecked) {
        binding.buttonContinue.setEnabled(isChecked);
        binding.buttonContinue.setText(isChecked ? "Finish" : "Continue");
    }
}