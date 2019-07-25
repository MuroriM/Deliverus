package com.whitewebteam.deliverus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

class BackgroundWorker extends AsyncTask<String, Void, String>
{

    private Context context;
    private String ipv4Address, type, phone, pwd, fName, lName, email, term;
    //private List<Bitmap> logos;
    private List<String> itemNames;
    private List<Item> items;
    private List<Order> orders;

    BackgroundWorker(Context context){
        this.context = context;
        //String laptop = "http://192.168.0.199/";
        ipv4Address = "http://whitewebteam.com/";
    }

    private List<String> httpRequest(String name, String postData)
    {
        List<String> result = new ArrayList<>();
        try
        {
            URL url = new URL(ipv4Address + "deliverus/" + name + ".php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter
                    (new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(inputStream, "iso-8859-1"));

            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                result.add(line.trim());
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        }
        catch (Exception e)
        {
            result.add("conn error");
            return result;
        }
    }

    @Override
    protected String doInBackground(String... params)
    {
        type = params[0];
        String charsetName = "UTF-8";
        String postData;
        List<String> result;
        String connError = "conn error";
        switch (type)
        {
            case "signIn":
                try
                {
                    postData = URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName) + "&"
                            + URLEncoder.encode("pwd", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName);
                    return httpRequest("sign_in", postData).get(0);
                }
                catch (Exception e)
                {
                    return connError;
                }
            case "forgotPwd":
                try
                {
                    phone = params[1].trim();
                    postData = URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(phone, charsetName);
                    return httpRequest("forgot_pwd", postData).get(0);
                }
                catch (Exception e)
                {
                    return connError;
                }
            case "securityQ":
                try
                {
                    phone = context.getSharedPreferences
                            ("phonePref", Context.MODE_PRIVATE).getString("phone", null);
                    String securityA = params[1];
                    postData = URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(phone, charsetName) + "&"
                            + URLEncoder.encode("security_a", charsetName) + "="
                            + URLEncoder.encode(securityA.trim(), charsetName);
                    return httpRequest("security_q", postData).get(0);
                }
                catch (Exception e)
                {
                    return connError;
                }
            case "resetPwd":
                try
                {
                    phone = context.getSharedPreferences("phonePref", Context.MODE_PRIVATE)
                            .getString("phone", null);
                    pwd = params[1];
                    postData = URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(phone, charsetName) + "&"
                            + URLEncoder.encode("pwd", charsetName) + "="
                            + URLEncoder.encode(pwd, charsetName);
                    return httpRequest("reset_pwd", postData).get(0);
                }
                catch (Exception e)
                {
                    return connError;
                }
            case "phoneValidation":
                try
                {
                    postData = URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName);
                    return httpRequest("phone_validation", postData).get(0);
                } catch (Exception e)
                {
                    return connError;
                }
            case "signUp":
                try
                {
                    phone = params[3];
                    pwd = params[5];
                    postData = URLEncoder.encode("fname", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName) + "&"
                            + URLEncoder.encode("lname", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName)+ "&"
                            + URLEncoder.encode("phone", charsetName) + "="
                            + URLEncoder.encode(phone, charsetName)+ "&"
                            + URLEncoder.encode("email", charsetName) + "="
                            + URLEncoder.encode(params[4], charsetName)+ "&"
                            + URLEncoder.encode("pwd", charsetName) + "="
                            + URLEncoder.encode(pwd, charsetName);
                    return httpRequest("sign_up", postData).get(0);
                }
                catch (Exception e) {
                    return connError;
                }
            case "updateUserDetails":
                try {
                    fName = params[2].trim();
                    lName = params[3].trim();
                    email = params[4].trim();
                    postData = URLEncoder.encode("userId", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName) + "&"
                            + URLEncoder.encode("fName", charsetName) + "="
                            + URLEncoder.encode(fName, charsetName)+ "&"
                            + URLEncoder.encode("lName", charsetName) + "="
                            + URLEncoder.encode(lName, charsetName)+ "&"
                            + URLEncoder.encode("email", charsetName) + "="
                            + URLEncoder.encode(email, charsetName);
                    return httpRequest("update_user_details", postData).get(0);
                } catch (Exception e) {
                    return connError;
                }
            /*case "getSupermarkets":
                try {
                    postData = URLEncoder.encode("selection", charsetName) + "="
                            + URLEncoder.encode("name", charsetName);
                    result = httpRequest("get_supermarkets", postData);
                    if (!result.get(0).equals(connError)) {
                        int i = 0;
                        SharedPreferences supermarketsPref = context.getSharedPreferences
                                ("supermarketsPref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = supermarketsPref.edit();
                        editor.clear().apply();
                        for (String supermarket : result) {
                            editor.putString(String.valueOf(i), supermarket).apply();
                            i++;
                        }

                        int j = 0;
                        logos = new ArrayList<>();
                        while (j < i){
                            URL url = new URL(ipv4Address + "supermarkets/"
                                    + supermarketsPref.getString(String.valueOf(j), null)
                                    + ".png");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream inputStream = connection.getInputStream();
                            logos.add(BitmapFactory.decodeStream(inputStream));
                            j++;
                        }
                        return null;
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }*/
            case "getItemNames":
                try {
                    postData = URLEncoder.encode("supermarket", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName);
                    result = httpRequest("get_item_names", postData);
                    if (!result.get(0).equals(connError)) {
                        itemNames = new ArrayList<>();
                        for (String name : result) {
                            itemNames.add(name);
                        }
                        return null;
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "getSupermarketItems":
            case "getCategoryItems":
                try {
                    term = params[1];
                    postData = URLEncoder.encode("term", charsetName) + "="
                            + URLEncoder.encode(term, charsetName) + "&"
                            + URLEncoder.encode("supermarket", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName);
                    result = httpRequest("get_supermarket_items", postData);
                    if (!result.get(0).equals(connError)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String detail : result) {
                            stringBuilder.append(detail).append("\n");
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        if (jsonArray.length() != 0) {
                            items = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Item item = new Item();
                                item.setId(jsonObject.getInt("id"));
                                item.setName(jsonObject.getString("name"));
                                item.setPrice(jsonObject.getInt("price"));
                                item.setThumbnailURL(jsonObject.getString("thumbnailURL"));

                                items.add(item);
                            }
                            return "gotten";
                        } else {
                            return "nothing";
                        }
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "getSubCategoryItems":
                try {
                    postData = URLEncoder.encode("supermarket", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName) + "&"
                            + URLEncoder.encode("subCategory", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName);
                    result = httpRequest("get_sub_category_items", postData);
                    if (!result.get(0).equals(connError)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String detail : result) {
                            stringBuilder.append(detail).append("\n");
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        itemNames = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Item item = new Item();
                            item.setId(jsonObject.getInt("id"));
                            item.setName(jsonObject.getString("name"));
                            item.setPrice(jsonObject.getInt("price"));
                            item.setThumbnailURL(jsonObject.getString("thumbnailURL"));

                            itemNames.add(jsonObject.getString("name"));
                            ItemsActivity.items.add(item);
                        }
                        return null;
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "getSearchedItems":
                try {
                    term = params[1];
                    postData = URLEncoder.encode("term", charsetName) + "="
                            + URLEncoder.encode(term, charsetName) + "&"
                            + URLEncoder.encode("supermarket", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName) + "&"
                            + URLEncoder.encode("subCategory", charsetName) + "="
                            + URLEncoder.encode(params[3], charsetName);
                    result = httpRequest("get_searched_items", postData);
                    if (!result.get(0).equals(connError)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String detail : result) {
                            stringBuilder.append(detail).append("\n");
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        if (jsonArray.length() != 0) {
                            items = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Item item = new Item();
                                item.setId(jsonObject.getInt("id"));
                                item.setName(jsonObject.getString("name"));
                                item.setPrice(jsonObject.getInt("price"));
                                item.setThumbnailURL(jsonObject.getString("thumbnailURL"));

                                items.add(item);
                            }
                            return "gotten";
                        } else {
                            return "nothing";
                        }
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "addOrder":
                try {
                    postData = URLEncoder.encode("order_id", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName) + "&"
                            + URLEncoder.encode("user_id", charsetName) + "="
                            + URLEncoder.encode(params[2], charsetName) + "&"
                            + URLEncoder.encode("time", charsetName) + "="
                            + URLEncoder.encode(params[3], charsetName)+ "&"
                            + URLEncoder.encode("supermarket", charsetName) + "="
                            + URLEncoder.encode(params[4], charsetName) + "&"
                            + URLEncoder.encode("total", charsetName) + "="
                            + URLEncoder.encode(params[5], charsetName)+ "&"
                            + URLEncoder.encode("delivery_fee", charsetName) + "="
                            + URLEncoder.encode(params[6], charsetName)+ "&"
                            + URLEncoder.encode("user_coordinates", charsetName) + "="
                            + URLEncoder.encode(params[7], charsetName);
                    return httpRequest("add_order", postData).get(0);
                } catch (Exception e) {
                    return connError;
                }
            case "addOrderDetails":
                try {
                    result = new ArrayList<>();
                    for (CartItem cartItem : SupermarketHomeActivity.cartItems) {
                        postData = URLEncoder.encode("order_id", charsetName) + "="
                                + URLEncoder.encode(params[1], charsetName) + "&"
                                + URLEncoder.encode("item_id", charsetName) + "="
                                + URLEncoder.encode(String.valueOf(cartItem.getId()),
                                charsetName) + "&" + URLEncoder.encode("pcs", charsetName) + "="
                                + URLEncoder.encode(String.valueOf(cartItem.getPcs()), charsetName);
                        result.add(httpRequest("add_order_detail", postData).get(0));
                    }
                    if (result.contains("fail")) {
                        return "fail";
                    } else {
                        return "success";
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "getOrders":
                try {
                    postData = URLEncoder.encode("user_id", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName);
                    result = httpRequest("get_orders", postData);
                    if (!result.get(0).equals(connError)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String detail : result) {
                            stringBuilder.append(detail).append("\n");
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        if (jsonArray.length() != 0) {
                            orders = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String orderId = jsonObject.getString("orderId");
                                String time = jsonObject.getString("time");
                                String supermarket = jsonObject.getString("supermarket");
                                int total = jsonObject.getInt("total");
                                int deliveryFee = jsonObject.getInt("deliveryFee");

                                orders.add(new Order(0, orderId, time, MainActivity.user, supermarket,
                                        total, deliveryFee, "Kinuthia", null, 0, 0));
                            }
                            return "gotten";
                        } else {
                            return "nothing";
                        }
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
            case "getOrderDetails":
                try {
                    postData = URLEncoder.encode("order_id", charsetName) + "="
                            + URLEncoder.encode(params[1], charsetName);
                    result = httpRequest("get_order_details", postData);
                    if (!result.get(0).equals(connError)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (String detail : result) {
                            stringBuilder.append(detail).append("\n");
                        }
                        JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String itemName = jsonObject.getString("itemName");
                            int itemPrice = jsonObject.getInt("itemPrice");
                            int itemPcs = jsonObject.getInt("itemPcs");

                            databaseHelper.addOrderDetail(HistoryActivity.orderId, itemName,
                                    itemPrice, itemPcs);
                        }
                        return null;
                    } else {
                        return connError;
                    }
                } catch (Exception e) {
                    return connError;
                }
			case "sendConfirmation":
				try {
					postData = URLEncoder.encode("phone_number", charsetName) + "="
							+ URLEncoder.encode(params[1], charsetName) + "&"
							+ URLEncoder.encode("confirmation_code", charsetName) + "="
							+ URLEncoder.encode(params[2], charsetName);
					return httpRequest("send_confirmation", postData).get(0);
				} catch (Exception e) {
					return connError;
				}
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (ProcessHandler.progressDialog != null)
            ProcessHandler.progressDialog.dismiss();
        String errorMsg1 = "Error connecting to network";
        String errorMsg2 = "Something's not right";
        switch (type) {
            case "signIn":
                switch (result) {
                    case "conn error":
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                    case "absent":
                        Toast.makeText(context, "Oops! Wrong Phone Number or Password",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        try {
                            JSONArray jsonArray = new JSONArray(result);
                            SharedPreferences.Editor editor = context.getSharedPreferences
                                    ("userDetailsPref", Context.MODE_PRIVATE).edit();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                editor.putInt("id", jsonObject.getInt("id"));
                                editor.putString("fname", jsonObject.getString("fname"));
                                editor.putString("lname", jsonObject.getString("lname"));
                                editor.putString("phone", jsonObject.getString("phone"));
                                editor.putString("email", jsonObject.getString("email"));
                                editor.apply();
                            }
                            signInAftermath();
                        } catch (Exception e) {
                            Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                break;
            case "forgotPwd":
                switch (result) {
                    case "conn error":
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                    case "absent":
                        ForgotPwdActivity.phone.setError("Phone does not exist");
                        break;
                    default:
                        ForgotPwdActivity.securityQTextView.setText
                                (String.format("Security Question - 07%s", phone));
                        ForgotPwdActivity.securityQ.setText
                                (context.getResources().getStringArray(R.array.security_qs)
                                        [Integer.parseInt(result)]);

                        context.getSharedPreferences("phonePref",
                                Context.MODE_PRIVATE).edit().putString("phone", phone).apply();

                        ForgotPwdActivity.securityQTextView.setVisibility(View.VISIBLE);
                        ForgotPwdActivity.securityQ.setVisibility(View.VISIBLE);
                        ForgotPwdActivity.securityA.setVisibility(View.VISIBLE);
                        ForgotPwdActivity.submit.setVisibility(View.VISIBLE);
                        break;
                }
                break;
            case "securityQ":
                switch (result) {
                    case "present":
                        context.startActivity(new Intent(context, ResetPwdActivity.class));
                        break;
                    case "absent":
                        Toast.makeText(context, "Oops! Incorrect Answer",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "resetPwd":
                switch (result) {
                    case "success":
                        Toast.makeText(context, "Password reset successfully",
                                Toast.LENGTH_SHORT).show();
                        new ProcessHandler(context, "Signing in...", "signIn", phone, pwd);
                        break;
                    case "fail":
                        Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "phoneValidation":
                switch (result) {
                    case "present":
                        AnimationSignInSignUpActivity.phoneSignUp.setError("Phone already exists");
                        break;
                    case "absent":
                        new ProcessHandler(context, "Signing up...", "signUp",
                                AnimationSignInSignUpActivity.fname.getText().toString(),
                                AnimationSignInSignUpActivity.lname.getText().toString(),
                                AnimationSignInSignUpActivity.phoneSignUp.getText().toString(),
                                AnimationSignInSignUpActivity.email.getText().toString(),
                                AnimationSignInSignUpActivity.pwdSignUp.getText().toString());
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "signUp":
                switch (result) {
                    case "success":
                        new ProcessHandler(context, "Signing in...", "signIn", phone, pwd);
                        signInAftermath();
                        Toast.makeText(context, "Welcome to Deliverus", Toast.LENGTH_SHORT).show();
                        break;
                    case "fail":
                        Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "updateUserDetails":
                switch (result) {
                    case "success":
                        SharedPreferences.Editor editor = context.getSharedPreferences
                                ("userDetailsPref", Context.MODE_PRIVATE).edit();
                        editor.putString("fname", fName);
                        editor.putString("lname", lName);
                        editor.putString("email", email);
                        editor.apply();

                        Toast.makeText(context, "Your details have been updated successfully",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case "fail":
                        Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            /*case "getSupermarkets":
                if (result == null) {
                    SupermarketsActivity.recyclerView.setAdapter
                            (new SupermarketsAdapter(context, logos));
                } else {
                    Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                }
                break;*/
            case "getItemNames":
                if (result == null) {
                    SupermarketHomeActivity.suggestions = itemNames.toArray(new String[0]);
                } else {
                    Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                }
                break;
            case "getSupermarketItems":
                switch (result) {
                    case "gotten":
                        CategoriesFragment.recyclerView.setAdapter(new ItemsAdapter(context, items));
                        break;
                    case "nothing":
                        Toast.makeText(context, "No results found for '" + term + "'",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "getCategoryItems":
                switch (result) {
                    case "gotten":
                        SubCategoriesActivity.recyclerView.setAdapter(new ItemsAdapter(context, items));
                        break;
                    case "nothing":
                        Toast.makeText(context, "No results found for '" + term + "'",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "getSubCategoryItems":
                if (result == null) {
                    ItemsActivity.suggestions = itemNames.toArray(new String[0]);
                    ItemsActivity.recyclerView.setAdapter(new ItemsAdapter(context, ItemsActivity.items));
                } else {
                    Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                }
                break;
            case "getSearchedItems":
                switch (result) {
                    case "gotten":
                        ItemsActivity.recyclerView.setAdapter(new ItemsAdapter(context, items));
                        break;
                    case "nothing":
                        Toast.makeText(context, "No results found for '" + term + "'",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "addOrder":
                switch (result) {
                    case "success":
                        new ProcessHandler(context, "Please wait...", "addOrderDetails",
                                shopping_cart_activity.orderId);
                        break;
                    case "fail":
                        Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "addOrderDetails":
                switch (result) {
                    case "success":
                        SupermarketHomeActivity.cartItems.clear();
                        SupermarketHomeActivity.cartItemsNumber = 0;
                        SupermarketHomeActivity.updateCartNumber();
                        SubCategoriesActivity.updateCartNumber();
                        context.startActivity(new Intent(context, payment.class));
                        ((Activity) context).finish();
                        Toast.makeText(context, "Order placed successfully",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case "fail":
                        Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "getOrders":
                switch (result) {
                    case "gotten":
                        HistoryActivity.recyclerView.setAdapter(new OrdersAdapter(context, orders));
                        HistoryActivity.orders = orders;
                        break;
                    case "nothing":
                        Toast.makeText(context, "No history found", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                        break;
                }
                break;
            case "getOrderDetails":
                if (result == null) {
                    OrderDetailsActivity.loadOrderDetails(context);
                } else {
                    Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
                }
                break;
			case "sendConfirmation":
				switch (result) {
					case "success":
						context.startActivity(new Intent(context, EndActivity.class));
						((Activity) context).finish();
						break;
					case "fail":
						Toast.makeText(context, errorMsg2, Toast.LENGTH_SHORT).show();
						break;
					default:
						Toast.makeText(context, errorMsg1, Toast.LENGTH_SHORT).show();
						break;
				}
				break;
        }
    }

    private void signInAftermath()
    {
        context.getSharedPreferences("initPref", Context.MODE_PRIVATE).edit()
                .putBoolean("signed", true).apply();

        Intent launchMainActivity = new Intent(context, MainActivity.class);
        launchMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchMainActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(launchMainActivity);
    }
}