package aahl.appbd.ui.visualize;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aahl.appbd.R;
import aahl.appbd.data.DBOps;
import aahl.appbd.models.Inventory;
import aahl.appbd.models.Product;
import aahl.appbd.ui.visualize.recycler.ProductAdapter;

public class VisualizeFragment extends Fragment {

    private final int MARGIN_EXTRA_BOTTOM_RECYCLERVIEW = 20;

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Spinner spinner;
    private EditText etSearch;
    private ProductAdapter adapter;
    private List<Inventory> inventories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    private DBOps dbOps;
    private boolean isAnyInventoryAvailable = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inicializar la vista
        View view = inflater.inflate(R.layout.fragment_visualize, container, false);

        // Inicializar el contexto de la base de datos
        dbOps = new DBOps(getContext());

        // Inicializar componentes
        recyclerView = view.findViewById(R.id.rvProducts);
        spinner = view.findViewById(R.id.spinnerInventories);
        etSearch = view.findViewById(R.id.etSearch);

        // Poner el margin del recyclerview
        configurarEdgeToEdge(view);

        // Configurar el spinner
        // Dentro de esta función se obtienen los inventarios, y dependiendo de eso lit
        // lo que resta del código hace cositas o nel
        configurarSpinner();

        // Si no hay ningún inventario disponible, mostrar un Toast indicando que no hay inventarios
        if (!isAnyInventoryAvailable) {
            showToast("There are no inventories");
            feedbackNegativoPorNoInventarios();
            return view;
        }

        // Configurar el recyclerview
        configurarRecyclerView();

        // Devolver la vista
        return view;
    }

    private void configurarRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(products);
        adapter.setListener(this::navegarAEditarProducto);
        recyclerView.setAdapter(adapter);
    }

    // Agrega margin al recyclerview (Que ya debe de estar asignado) para que no se dibuje detrás de la barra
    // de navegación por gestos.
    private void configurarEdgeToEdge(View view) {

        ViewCompat.setOnApplyWindowInsetsListener(view, (v, windowInsets) -> {

            Insets navBarInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars());

            ViewGroup.MarginLayoutParams paramsRecycler = (ViewGroup.MarginLayoutParams) recyclerView
                    .getLayoutParams();

            paramsRecycler.setMargins(
                    paramsRecycler.leftMargin,
                    paramsRecycler.topMargin,
                    paramsRecycler.rightMargin,
                    navBarInsets.bottom + MARGIN_EXTRA_BOTTOM_RECYCLERVIEW);

            recyclerView.setLayoutParams(paramsRecycler);

            return windowInsets;
        });
    }

    private void configurarSpinner() {
        // Primero obtener si hay inventarios disponibles
        obtenerInventarios();

        // Construir opciones del spinner dependiendo de si hay inventarios o nepe
        List<String> opciones = isAnyInventoryAvailable ?
                inventories.stream().map(Inventory::getName).toList():
                List.of("No inventories detected");

        // Esto se hace para que el spinner inicie sin "nada" seleccionado
        if (isAnyInventoryAvailable) opciones.addFirst("");

        // Después construir el Adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                opciones
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Listener para cuando se selecciona un inventario en el spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                manejarInventarioSeleccionado(adapterView, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Pus q se va a hacer aquí si no es posible no seleccionar nada :p
            }
        });
    }

    private void manejarInventarioSeleccionado(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) return; // Considerar el espacio en blanco al inicio o el mensaje de que no hay inventarios

        // Inventario seleccionado
        Inventory selectedInventory = inventories.get(position);

        // Obtener todos los productos de un inventario
        products = dbOps.getProductsByInventory(selectedInventory.getId());

        // Lógica para notificar al recyclerView que el dataset de productos cambió
        adapter.notifyDataSetChanged();
    }

    private void navegarAEditarProducto(int position){
        // TODO: Implementar navegación
    }

    private void obtenerInventarios() {
        // SELECT de la base de datos
        inventories = dbOps.getAllInventories();

        // Si no hay inventarios, marcar esa flag
        isAnyInventoryAvailable = !inventories.isEmpty();
    }

    private void feedbackNegativoPorNoInventarios() {
        // Deshabilitar la barra de búsqueda y el recyclerview
        // Ambos se ponen de color gris por el selector
        etSearch.setEnabled(false);
        recyclerView.setEnabled(false);
    }

    private void showToast(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
