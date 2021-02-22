package com.example.transmobile.Salida.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.transmobile.R;

public class SalidaFragment extends Fragment {

    private SalidaViewModel salidaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salidaViewModel =
                ViewModelProviders.of(this).get(SalidaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salida, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        salidaViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}