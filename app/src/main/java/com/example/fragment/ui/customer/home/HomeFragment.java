package com.example.fragment.ui.customer.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fragment.databinding.FragmentGuideHomeBinding;
import com.example.fragment.databinding.FragmentHomeBinding;
import com.example.fragment.ui.SharedViewModel;
import com.example.fragment.ui.guide.home.HomeViewModel;

public class HomeFragment extends Fragment {

    private com.example.fragment.ui.guide.home.HomeViewModel viewModel;
    private SharedViewModel sharedViewModel; // ViewModel dùng chung
    private FragmentGuideHomeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGuideHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getCounter().observe(getViewLifecycleOwner(), currentCount -> {
            binding.tvCounterResult.setText("Số lần bấm bên Dashboard: " + currentCount);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
