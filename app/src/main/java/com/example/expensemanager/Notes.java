package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.RecognizerResultsIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Notes extends AppCompatActivity {
    Window window;
    ImageView micbutton;
    String txt;
    EditText e1;
    Button savebutton;
    ImageView deletebutton;
    NotesDatabase myDb;
    ListView userlist;
    String itemSelected="";
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String date;
    public int mYear, mMonth, mDay,mHour,mMinute;
    ArrayList<String> newlist;
    ArrayAdapter<String> adapter;

    private static final int REQUEST_CODE_SPEECH_INPUT=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window=this.getWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        if(Build.VERSION.SDK_INT>=21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.statusbar));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        userlist=(ListView)findViewById(R.id.list1);
        ArrayList<String> arrayList=new ArrayList<String>();
        micbutton=(ImageView)findViewById(R.id.mic);
        e1=(EditText)findViewById(R.id.editTextnotes);
        savebutton=(Button)findViewById(R.id.notessavingbutton);
        deletebutton=(ImageView)findViewById(R.id.delete);
        calendar = Calendar.getInstance();
        myDb=new NotesDatabase(this);

        micbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    speak();
            }
        });

        newlist=new ArrayList<String>();
        newlist=myDb.viewData();
        adapter=new ArrayAdapter<String>(Notes.this,android.R.layout.select_dialog_singlechoice,newlist);
        userlist.setAdapter(adapter);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = e1.getText().toString();
                if(x.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Input is Blank!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Calendar c = Calendar.getInstance();
                    c.get(Calendar.YEAR);
                    c.get(Calendar.MONTH);
                    c.get(Calendar.DAY_OF_MONTH);
                    c.get(Calendar.HOUR);
                    c.get(Calendar.MINUTE);
                    c.setTimeZone(TimeZone.getDefault());
                    simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");
                    date = simpleDateFormat.format(calendar.getTime());
                    boolean isInserted = myDb.insertData(date,x);
                    if(isInserted = true) {
                        Toast.makeText(getApplicationContext(),"Saved successfully",Toast.LENGTH_SHORT).show();
                        e1.setText("");
                        newlist.add("\n"+date+" :-"+"\n\n"+x+"\n");
                        adapter=new ArrayAdapter<String>(Notes.this,android.R.layout.select_dialog_singlechoice,newlist);
                        userlist.setAdapter(adapter);
                    }
                    else
                        Toast.makeText(getApplicationContext(),"Not Saved successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemSelected.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Select option from list!",Toast.LENGTH_SHORT).show();
                }
                else {
                    StringBuffer newtxt= new StringBuffer(itemSelected);
                    StringBuffer oldtxt = new StringBuffer(itemSelected);
                    int i=oldtxt.length();
                    int j=newtxt.length();
                    newtxt.delete(0,1);
                    newtxt.delete(21,j);
                    String datetxt=newtxt.toString();
                    oldtxt.delete(i-1,i);
                    oldtxt.delete(0,27);
                    String notetxt=oldtxt.toString();
                    //Toast.makeText(getApplicationContext(),notetxt,Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Notes.this);
                    builder.setMessage("Are you sure you want to delete this data").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean x=myDb.deleteData(datetxt,notetxt);
                            if(x==true) {
                                Toast.makeText(getApplicationContext(),"Data Successfully Deleted",Toast.LENGTH_SHORT).show();
                                newlist.clear();
                                newlist=myDb.viewData();
                                adapter=new ArrayAdapter<String>(Notes.this,android.R.layout.select_dialog_singlechoice,newlist);
                                userlist.setAdapter(adapter);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Error Occured! Data does not Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("CANCEL",null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle("ALERT BOX");
                    alertDialog.show();
                }

            }
        });

        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 itemSelected=userlist.getItemAtPosition(i).toString();
            }
        });
        
    }

    private void speak() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something");

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_SPEECH_INPUT: {
                if(resultCode == RESULT_OK && null!=data) {
                    ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    e1.setText(result.get(0));
                    txt=result.get(0);
                }
                break;
            }
        }
    }
}