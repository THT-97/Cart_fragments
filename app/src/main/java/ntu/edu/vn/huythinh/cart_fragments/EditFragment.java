package ntu.edu.vn.huythinh.cart_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import ntu.edu.vn.huythinh.controller.ICartController;
import ntu.edu.vn.huythinh.model.Product;

public class EditFragment extends Fragment implements View.OnClickListener {


    EditText edtName;
    EditText edtPrice;
    EditText edtDesc;
    Button btnEdit, btnDelete, btnCancel;
    SharedPreferences sharedPreferences;
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
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtName = view.findViewById(R.id.edtName);
        edtPrice = view.findViewById(R.id.edtPrice);
        edtDesc = view.findViewById(R.id.edtDesc);
        Load();
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        btnCancel = view.findViewById(R.id.btnCancelEdit);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case (R.id.btnEdit):{
                if(sharedPreferences != null){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(FirstFragment.keyName, edtName.getText().toString());
                    editor.putString(FirstFragment.keyPrice, edtPrice.getText().toString());
                    editor.putString(FirstFragment.keyDesc, edtDesc.getText().toString());
                    editor.commit();
                    ICartController controller = (ICartController) getActivity().getApplication();
                    Product p = new Product(
                            edtName.getText().toString(),
                            Integer.parseInt(edtPrice.getText().toString()),
                            edtDesc.getText().toString());
                    controller.getAllProduct().set(sharedPreferences.getInt(FirstFragment.keyPos,0), p);
                }
                break;
            }
            case (R.id.btnDelete):{
                edtName.setText("");
                edtPrice.setText("");
                edtDesc.setText("");
                break;
            }

            case(R.id.btnCancelEdit):{
                    NavHostFragment.findNavController(EditFragment.this)
                            .navigate(R.id.action_editFragment_pop);
            }
        }

    }

    private void Load(){
        sharedPreferences = getActivity().getSharedPreferences(FirstFragment.sharedRef, Context.MODE_PRIVATE);
        edtName.setText(sharedPreferences.getString(FirstFragment.keyName, "None"));
        edtPrice.setText(sharedPreferences.getString(FirstFragment.keyPrice, "0"));
        edtDesc.setText(sharedPreferences.getString(FirstFragment.keyDesc, "None"));
    }

}
