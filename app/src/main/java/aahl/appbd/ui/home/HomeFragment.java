package aahl.appbd.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import aahl.appbd.R;
import aahl.appbd.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inicializar la vista
       View view = inflater.inflate(R.layout.fragment_home, container, false);



       // Devolver la vista inicializada
       return view;
    }
}