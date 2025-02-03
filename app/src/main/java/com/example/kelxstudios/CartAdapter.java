package com.example.kelxstudios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartProduct> cartProductList;
    private ShoppingCart shoppingCart;

    // Constructor para inicializar contexto y lista de productos
    public CartAdapter(Context context, List<CartProduct> cartProductList) {
        this.context = context;
        this.cartProductList = cartProductList;
        this.shoppingCart = ShoppingCart.getInstance();
    }

    // Crea la vista para cada elemento en el RecyclerView
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false));
    }

    // Vincula los datos del producto con los elementos de la vista
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct product = cartProductList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice() + "€");
        holder.productQuantity.setText("Quantity: " + product.getQuantity());
        Picasso.get().load(product.getImageUrl()).into(holder.productImage);

        // Acción para aumentar la cantidad
        holder.increaseQuantityButton.setOnClickListener(v -> {
            product.incrementQuantity();
            notifyItemChanged(position); // Actualiza la vista
        });

        // Acción para disminuir la cantidad o eliminar producto
        holder.decreaseQuantityButton.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                notifyItemChanged(position); // Actualiza la vista
            } else {
                shoppingCart.getProducts().remove(position); // Elimina el producto
                notifyItemRemoved(position); // Actualiza la vista
            }
        });
    }

    // Retorna la cantidad de productos en el carrito
    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    // ViewHolder para representar un producto en el carrito
    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productPrice, productQuantity;
        Button increaseQuantityButton, decreaseQuantityButton;

        // Constructor para inicializar las vistas de cada item
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            increaseQuantityButton = itemView.findViewById(R.id.increase_quantity_button);
            decreaseQuantityButton = itemView.findViewById(R.id.decrease_quantity_button);
        }
    }
}
