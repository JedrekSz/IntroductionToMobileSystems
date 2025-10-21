package com.example.introductiontomobilesystems;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.button_go_profile);
        if (button != null) {
            button.setOnClickListener(v -> {
                NavController navController = NavHostFragment.findNavController(HomeFragment.this);
                navController.navigate(R.id.action_homeFragment_to_profileFragment);
            });
        }
    }
}
