package e.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        EditText emailField = findViewById(R.id.email_field);
        String email = emailField.getText().toString();
        Log.v("main", "email" + email);

        //whipped cream checked box
        CheckBox whippedCream = findViewById(R.id.whippedCream_checkbox);
        boolean hasWhippedCream = whippedCream.isChecked();

        //chocolate checked box
        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        // Icecream
        CheckBox icecream = findViewById(R.id.iceCream_checkbox);
        boolean hasIcecream = icecream.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate, hasIcecream);

        String summary = createOrderSummary(name, price, hasWhippedCream, hasChocolate ,hasIcecream);

        // Intent for Email
        composeEmail(email, summary);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nameField.getWindowToken(), 0);
    }

    public void composeEmail(String add, String summary) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + add));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary of JustJava Coffee");
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * Create summary of the order.
     *
     * @param name            of customer
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate    is whether or not the user wants chocolate topping
     * @param hasIcecream     is whether or not the user wants icecream topping
     * @param price           of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate
    , boolean hasIcecream) {
        String priceMessage = " Name : " + name;
        priceMessage += "\n Add Whipped cream ?" + hasWhippedCream;
        priceMessage += "\n Add Chocolate ?" + hasChocolate;
        priceMessage += "\n Add Icecream ?" + hasIcecream;
        priceMessage += "\n Quantity : " + quantity;
        priceMessage += "\n Total :£ " + price +"\n";
        priceMessage += getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream add +10 in price if checked
     * @param hasChocolate    add +20 in price if checked
     * @param hasIcecream    add +30 in price if checked
     * @return baseprice -> total  price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate ,boolean hasIcecream) {
        int baseprice = 70;
        // if whipped cream is checked add +10
        if (hasWhippedCream) {
            baseprice += 10;
        }
        // if Chocolate  is checked add +20
        if (hasChocolate) {
            baseprice += 20;
        }

        // if IceCream is checked add +30
        if (hasIcecream) {
            baseprice += 30;
        }
        return quantity * baseprice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int noOfCoffe) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+noOfCoffe);
    }

    public void increment(View view) {
        quantity = quantity + 1;
        // so that quantity does not go above 10
        if (quantity > 10) {
            quantity = 10;
            Toast.makeText(MainActivity.this, "Please don't order more coffee ", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = (quantity - 1);
        // so that quantity does not go below 1
        if (quantity < 1) {
            quantity = 1;
            Toast.makeText(MainActivity.this, "Please order 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

}
