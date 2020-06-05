package ntu.edu.vn.huythinh.cart_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

import ntu.edu.vn.huythinh.controller.ICartController;
import ntu.edu.vn.huythinh.model.Product;

public class SecondFragment extends Fragment {
    List<Product> cartList;
    TextView txtCart;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCart = view.findViewById(R.id.txtCart);
        showCart();
        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_Cart_Fragment_to_confirmFragment);
            }
        });
    }

    protected void showCart(){
        ICartController controller = (ICartController) getActivity().getApplication();
        cartList = controller.getCart();
        StringBuilder builder = new StringBuilder();
        if(cartList.size()==0) txtCart.setText("Empty");
        else{
            for (Product p:cartList){
                builder.append(p.getName())
                        .append("\t\t\t\t")
                        .append(p.getPrice())
                        .append(" Vnd")
                        .append("\n");
            }
            txtCart.setText(builder.toString());
        }
    }
}
