package aahl.appbd.ui.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import aahl.appbd.R;

public class ManageFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inicializar la vista
        View view = inflater.inflate(R.layout.fragment_manage, container, false);

        // Devolver la vista
        return view;
    }
}
