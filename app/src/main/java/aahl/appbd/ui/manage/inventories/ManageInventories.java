package aahl.appbd.ui.manage.inventories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import aahl.appbd.R;
import aahl.appbd.models.Inventory;

public class ManageInventories extends Fragment {

    private List<Inventory> inventoryList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private FloatingActionButton fabNuevoInventory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inventories, container, false);

        // Configuración del recycler
        configurarRecyclerView(view);

        // Configuracion del FAB
        configurarFAB(view);

        return view;
    }

    private void configurarRecyclerView(View view){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView = view.findViewById(R.id.rvInventories);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new InventoryAdapter(inventoryList);
        adapter.setListener(new onItemActionsClicked() {
            @Override
            public void onEditInventoryClicked(int position) {
                // TODO: Lógica para abrir add_edit_inventory con datos
            }

            @Override
            public void onDeleteInventoryClicked(int position) {
                // TODO: Lógica para borrar el inventario y recargar el recycler
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void configurarFAB(View view) {
        fabNuevoInventory = view.findViewById(R.id.fabAddInventory);

        fabNuevoInventory.setOnClickListener(v -> {
            // TODO: Lógica para abrir add_edit_inventory sin datos previos
        });
    }
}
