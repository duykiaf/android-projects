package t3h.android.elife.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import t3h.android.elife.R;
import t3h.android.elife.databinding.FragmentSplashScreenBinding;

public class SplashScreenFragment extends Fragment {
    private Animation topAnimation, bottomAnimation;

    FragmentSplashScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false);

        topAnimation = AnimationUtils.loadAnimation(requireActivity(), R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(requireActivity(), R.anim.bottom_animation);

        binding.logo.setAnimation(topAnimation);
        binding.appName.setAnimation(bottomAnimation);
        binding.slogan.setAnimation(bottomAnimation);
        binding.startBtn.setAnimation(bottomAnimation);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.startBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment);
            navController.navigate(R.id.action_splashScreenFragment_to_homeFragment);
        });
    }
}