package com.example.kelxstudios;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private ShoppingCart shoppingCart; // Instancia del carrito de compras
    private CartAdapter cartAdapter;   // Adaptador para el RecyclerView que muestra los productos en el carrito

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        shoppingCart = ShoppingCart.getInstance(); // Obtiene la instancia única del carrito

        List<CartProduct> cartProducts = shoppingCart.getProducts(); // Obtiene los productos en el carrito

        // Configura el RecyclerView para mostrar los productos del carrito
        RecyclerView recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartProducts);
        recyclerView.setAdapter(cartAdapter);

        // Botón para confirmar el pedido
        Button confirmOrderButton = findViewById(R.id.confirm_order_button);
        confirmOrderButton.setOnClickListener(v -> {
            // Verifica si el carrito está vacío
            if (cartProducts.isEmpty()) {
                Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                double total = calcularTotal(cartProducts); // Calcula el total del carrito
                int usuarioId = obtenerUsuarioId(); // Obtiene el ID del usuario

                // Envía el pedido al servidor
                enviarPedidoAlServidor(usuarioId, total, cartProducts);

                shoppingCart.clearCart(); // Vacía el carrito
                cartAdapter.notifyDataSetChanged(); // Notifica al adaptador que el carrito ha cambiado

                Toast.makeText(this, "Order success", Toast.LENGTH_SHORT).show(); // Muestra mensaje de éxito
                finish(); // Cierra la actividad
            }
        });
    }

    // Metodo para calcular el total del carrito
    private double calcularTotal(List<CartProduct> cartProducts) {
        double total = 0;

        // Suma el precio de cada producto multiplicado por su cantidad
        for (CartProduct producto : cartProducts) {
            total += producto.getPrice() * producto.getQuantity();
        }

        return total; // Retorna el total calculado
    }

    // Metodo para obtener el ID del usuario
    private int obtenerUsuarioId() {
        return 6; // ID de usuario de ejemplo
    }

    // Metodo para enviar el pedido al servidor
    private void enviarPedidoAlServidor(int usuarioId, double total, List<CartProduct> cartProducts) {
        new Thread(() -> {
            try {
                // URL del script PHP que guarda el pedido en el servidor
                URL url = new URL("http://10.0.2.2/kelxstudiosgst/guardar_pedido.php");

                // Configura la conexión HTTP
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                // Crea el objeto JSON para el pedido
                JSONObject pedidoJson = new JSONObject();
                pedidoJson.put("id_usuario", usuarioId); // ID del usuario
                pedidoJson.put("total", total); // Total del pedido

                // Crea el array JSON con los productos del carrito
                JSONArray productosArray = new JSONArray();
                for (CartProduct producto : cartProducts) {
                    JSONObject productoJson = new JSONObject();
                    productoJson.put("id_producto", producto.getProductId()); // ID del producto
                    productoJson.put("cantidad", producto.getQuantity()); // Cantidad del producto
                    productoJson.put("precio", producto.getPrice()); // Precio del producto
                    productosArray.put(productoJson);
                }
                pedidoJson.put("productos", productosArray); // Añade los productos al pedido

                // Envía el pedido al servidor
                OutputStream os = urlConnection.getOutputStream();
                os.write(pedidoJson.toString().getBytes()); // Envía el JSON
                os.close();

                // Obtiene el código de respuesta del servidor
                int responseCode = urlConnection.getResponseCode();

                // Verifica la respuesta del servidor
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Order saved to server", Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Error saving order", Toast.LENGTH_SHORT).show());
                }

                urlConnection.disconnect(); // Cierra la conexión
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(CheckoutActivity.this, "Connection error", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
