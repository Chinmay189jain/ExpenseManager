package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Add_income extends AppCompatActivity {
    Window window;
    Button date;
    Button time;
    Button save;
    EditText date_edit;
    EditText time_edit;
    EditText amount;
    EditText info;
    public int mYear, mMonth, mDay,mHour,mMinute;
    String dt="",tm="",desc="",rup="";
    int amt=0;
    IncomeDatabase indb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window=this.getWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        if(Build.VERSION.SDK_INT>=21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        date=(Button)findViewById(R.id.btn_date);
        time=(Button)findViewById(R.id.btn_time);
        save=(Button)findViewById(R.id.button_save);
        date_edit=(EditText) findViewById(R.id.in_date);
        time_edit=(EditText)findViewById(R.id.in_time);
        amount=(EditText)findViewById(R.id.in_amount);
        info=(EditText)findViewById(R.id.in_source);
        indb=new IncomeDatabase(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_income.this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.YEAR,year);
                                c.set(Calendar.MONTH,monthOfYear);
                                c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                                String time = format.format(c.getTime());
                                date_edit.setText(time);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mHour = calendar.get(Calendar.HOUR);
                mMinute = calendar.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add_income.this, new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
                                String time = format.format(c.getTime());
                                time_edit.setText(time);
                            }
                        },mHour,mMinute,false);
                timePickerDialog.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dt=date_edit.getText().toString();
                tm=time_edit.getText().toString();
                rup=amount.getText().toString();
                if(!rup.isEmpty()) {
                    amt=Integer.parseInt(rup);
                }
                desc=info.getText().toString();

                if(dt.isEmpty() || tm.isEmpty() || amt==0 || desc.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Input is Blank!",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isInserted=indb.insertIncomeData(dt,tm,amt,desc);
                    if(isInserted = true) {
                        Toast.makeText(getApplicationContext(),"Saved successfully",Toast.LENGTH_SHORT).show();
                        date_edit.setText("");
                        time_edit.setText("");
                        amount.setText("");
                        info.setText("");
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Not Saved successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}