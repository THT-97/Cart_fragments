package ntu.edu.vn.huythinh.cart_fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ntu.edu.vn.huythinh.controller.ICartController;
import ntu.edu.vn.huythinh.model.Product;

public class ProductFragment extends Fragment implements View.OnClickListener {

    EditText edtName;
    EditText edtPrice;
    EditText edtDesc;
    Button btnAdd, btnCancel;
    NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       navController = NavHostFragment.findNavController(this);
        ((MainActivity)getActivity()).controller = navController;
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        edtDesc = view.findViewById(R.id.edtDesc);

        btnAdd = view.findViewById(R.id.btnConfirmAdd);
        btnCancel = view.findViewById(R.id.btnCancelAdd);
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case (R.id.btnConfirmAdd):{
                int price = Integer.parseInt(edtPrice.getText().toString());
                Product p = new Product(edtName.getText().toString(), price, edtDesc.getText().toString());
                ICartController controller = (ICartController) getActivity().getApplication();
                if(controller.addProduct(p)) Toast.makeText(getActivity(), "Product added", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getActivity(), "Product is already in shop", Toast.LENGTH_SHORT).show();
                pop();
                break;
            }

            case (R.id.btnCancelAdd):{
                pop();
                break;
            }
        }
    }

    private void pop(){
        NavHostFragment.findNavController(ProductFragment.this)
                .navigate(R.id.action_productFragment_pop);
    }
}
