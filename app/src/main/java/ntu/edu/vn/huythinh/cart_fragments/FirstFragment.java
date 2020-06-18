package ntu.edu.vn.huythinh.cart_fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ntu.edu.vn.huythinh.controller.ICartController;
import ntu.edu.vn.huythinh.model.Product;

public class FirstFragment extends Fragment {
    RecyclerView rvProductList;
    ProductAdapter adapter;
    List<Product> productList;
    FloatingActionButton fbtnCart;
    FloatingActionButton fbtnAddProduct;

    public static final String sharedRef = "shared_ref";
    public static final String keyName = "Name";
    public static final String keyPrice = "Price";
    public static final String keyDesc = "Description";
    public static final String keyPos = "Position";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        fbtnCart = view.findViewById(R.id.fbtnCart);
        fbtnAddProduct = view.findViewById(R.id.fbtnAddProduct);
        rvProductList = view.findViewById(R.id.rvProductList);
        rvProductList.setLayoutManager(new LinearLayoutManager(getActivity()));
        ICartController controller = (ICartController) getActivity().getApplication();
        productList = controller.getAllProduct();
        adapter = new ProductAdapter(productList);
        rvProductList.setAdapter(adapter);

        fbtnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        fbtnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_Shop_Fragment_to_productFragment);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.mnuCart){
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }
        else if(id == R.id.mnuExit) getActivity().finish();
        return super.onOptionsItemSelected(item);
    }
    private class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName, txtPrice, txtDesc;
        ImageView imvAddToCart, imvEdit;
        Product product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = this.itemView.findViewById(R.id.txtName);
            txtPrice = this.itemView.findViewById(R.id.txtPrice);
            txtDesc = this.itemView.findViewById(R.id.txtDesc);

            imvAddToCart = this.itemView.findViewById(R.id.imgAdd);
            imvAddToCart.setOnClickListener(this); //this has become an OnClickListener
            imvEdit = this.itemView.findViewById(R.id.imgEdit);
            imvEdit.setOnClickListener(this);
        }

        public void bind(Product product) {
            this.product = product;
            String cur = " Vnd";
            txtName.setText(product.getName());
            txtPrice.setText(new Integer(product.getPrice()).toString() + cur);
            txtDesc.setText(product.getDesc());
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            ICartController controller = (ICartController) getActivity().getApplication();
            switch (id){
                case R.id.imgAdd: {
                    if (controller.addToCart(product))
                        Toast.makeText(getContext(), product.getName() + " added to cart",
                                Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getContext(),product.getName() + " is already in cart",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.imgEdit: {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(sharedRef, Context.MODE_PRIVATE);
                    if(sharedPreferences != null){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(keyName, product.getName());
                        editor.putString(keyPrice, String.valueOf(product.getPrice()));
                        editor.putString(keyDesc, product.getDesc());
                        editor.putInt(keyPos, productList.indexOf(product));
                        editor.commit();
                    }
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_Shop_Fragment_to_editFragment);
                }
            }

        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

        List<Product> listProducts;

        public ProductAdapter(List<Product> listProducts) {
            this.listProducts = listProducts;
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.product, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            holder.bind(listProducts.get(position));
        }

        @Override
        public int getItemCount() {
            return listProducts.size();
        }
    }
}
