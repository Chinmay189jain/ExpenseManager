package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Window window;
    Button buttonnotes;
    Button buttonincome;
    Button buttonexpense;
    Button buttonall_transaction;
    TextView income;
    TextView expense;
    TextView balance;
    ImageView about;
    int totalincome=0,totalexpense=0,totalbalance=0;
    IncomeDatabase mainIndb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window=this.getWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        buttonnotes=(Button) findViewById(R.id.notes);
        buttonincome=(Button)findViewById(R.id.addIncome);
        buttonexpense=(Button)findViewById(R.id.addExpense);
        buttonall_transaction=(Button)findViewById(R.id.allTransaction);
        income=(TextView) findViewById(R.id.income);
        expense=(TextView) findViewById(R.id.expense);
        balance=(TextView) findViewById(R.id.balance);
        about=(ImageView)findViewById(R.id.imageView_info);
        mainIndb = new IncomeDatabase(this);

        buttonnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(MainActivity.this,Notes.class);
                startActivity(i3);
            }
        });
        buttonincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(MainActivity.this, Add_income.class);
                startActivity(i4);
            }
        });
        buttonexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(MainActivity.this, Add_expense.class);
                startActivity(i5);
            }
        });
        buttonall_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(MainActivity.this, All_transaction.class);
                startActivity(i6);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i7 = new Intent(MainActivity.this, About.class);
                startActivity(i7);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        totalincome=mainIndb.totalIncome();
        income.setText(String.valueOf(totalincome)+"  Rs.");
        totalexpense=mainIndb.totalExpense();
        expense.setText(String.valueOf(totalexpense)+"  Rs.");
        totalbalance=totalincome-totalexpense;
        balance.setText(String.valueOf(totalbalance)+"  Rs.");

    }
}