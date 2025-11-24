package aahl.appbd.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.hoko.blur.HokoBlur;

import aahl.appbd.R;
import aahl.appbd.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private final int PADDING_BOTTOM_DISCLAIMER = 20;
    private ImageView ivFondoHome;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inicializar la vista
       View view = inflater.inflate(R.layout.fragment_home, container, false);

       // Inicializar el fondo
       aplicarBlurAlFondo(view);

       // Configurar edge-to-edge para que el contenido se dibuje detrás de la barra de navegación
       configurarEdgeToEdge(view);

       // Devolver la vista inicializada
       return view;
    }

    private void aplicarBlurAlFondo(View view){
        ivFondoHome = view.findViewById(R.id.ivFondoHome);

        Bitmap imgFondo = BitmapFactory.decodeResource(getResources(), R.drawable.logisticaimagenstock);

        Bitmap imgFondoConBlur = HokoBlur.with(requireContext())
                .scheme(HokoBlur.SCHEME_NATIVE)
                .mode(HokoBlur.MODE_STACK)
                .radius(10)
                .blur(imgFondo);

        ivFondoHome.setImageBitmap(imgFondoConBlur);
    }

    private void configurarEdgeToEdge(View view) {
        // Obtener el TextView del disclaimer
        TextView tvDisclaimer = view.findViewById(R.id.tvDisclaimerBottomHome);

        // Aplicar listener de insets para manejar solo la barra de navegación inferior
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {
            // Solo obtener los insets de la barra de navegación, NO del status bar
            Insets navBarInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars());

            // Aplicar padding solo en la parte inferior para el disclaimer
            // Esto hará que el texto tenga un padding adicional para no quedar escondido
            // completamente detrás de la barra de navegación por gestos
            tvDisclaimer.setPadding(
                tvDisclaimer.getPaddingLeft(),
                tvDisclaimer.getPaddingTop(),
                tvDisclaimer.getPaddingRight(),
                navBarInsets.bottom + PADDING_BOTTOM_DISCLAIMER
            );

            return windowInsets;
        });
    }
}