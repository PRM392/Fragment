package com.example.fragment.ui.admin.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fragment.databinding.FragmentDashboardBinding;
import com.example.fragment.ui.viewmodel.SharedViewModel;

public class DashboardFragment extends Fragment {

    private DashboardViewModel viewModel; // ViewModel dùng riêng cho Fragment này
    private SharedViewModel sharedViewModel; // ViewModel dùng chung
    private FragmentDashboardBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.incrementCounter();
            }
        });

        sharedViewModel.getCounter().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCounterDashboard.setText(String.valueOf(integer));
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Lắng nghe biến đếm để cập nhật UI ngay tại Dashboard
        sharedViewModel.getCounter().observe(getViewLifecycleOwner(), count -> {
            binding.tvCounterDashboard.setText("Số lần đã bấm: " + count);
        });

        binding.btnSend.setOnClickListener(v -> {
            sharedViewModel.incrementCounter(); // Gọi hàm tăng biến đếm
            Toast.makeText(getContext(), "Đã gửi dữ liệu sang màn hình Customer!", Toast.LENGTH_SHORT).show();
        });

        binding.btnLogout.setOnClickListener(v -> {
            sharedViewModel.setUserRole(null); // Xóa role
            androidx.navigation.Navigation.findNavController(v).navigate(com.example.fragment.R.id.navigation_login);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
