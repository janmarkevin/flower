package com.flower.shopping;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class AdminCategoryActivity extends AppCompatActivity {
    private Button LogoutBtn, CheckOrdersBtn, WeddingsBtn, BirthdaysBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        CheckOrdersBtn = (Button) findViewById(R.id.check_orders_btn);


        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        BirthdaysBtn = (Button) findViewById((R.id.admin_category_birthday));
        WeddingsBtn = (Button) findViewById((R.id.admin_category_Wedding));

        BirthdaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.flower.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Birthday");
                startActivity(intent);
            }
        });

        WeddingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, com.flower.shopping.AdminAddNewProductActivity.class);
                intent.putExtra("category", "Weddings");
                startActivity(intent);
            }
        });
    }
}