package com.example.expensemanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ListView user_list_expense;
    String itemSelected="";
    ImageButton delete;
    ArrayList<String> arr_list_expense;
    ArrayAdapter arrayAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment2,container,false);
        user_list_expense=(ListView)v.findViewById(R.id.list_expense);
        delete=(ImageButton)v.findViewById(R.id.delete_expense);
        arr_list_expense = new ArrayList<String>();
        IncomeDatabase ex = new IncomeDatabase(getActivity());
        arr_list_expense = ex.getList_expense();
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arr_list_expense);
        user_list_expense.setAdapter(arrayAdapter);

        user_list_expense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected=user_list_expense.getItemAtPosition(i).toString();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemSelected.isEmpty()) {
                    Toast.makeText(getActivity(),"Select option from list!",Toast.LENGTH_SHORT).show();
                }
                else {
                    StringBuffer dtbuff= new StringBuffer(itemSelected);
                    StringBuffer tmbuff = new StringBuffer(itemSelected);
                    int i=dtbuff.length();
                    int j=tmbuff.length();
                    dtbuff.delete(11,i);
                    dtbuff.delete(0,1);
                    String dttxt=dtbuff.toString();
                    tmbuff.delete(22,j);
                    tmbuff.delete(0,14);
                    String tmtxt=tmbuff.toString();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Are you sure you want to delete this data").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean x=ex.deleteData_expense(dttxt,tmtxt);
                            if(x==true) {
                                Toast.makeText(getActivity(),"Data Successfully Deleted",Toast.LENGTH_SHORT).show();
                                arr_list_expense.clear();
                                arr_list_expense = ex.getList_expense();
                                arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arr_list_expense);
                                user_list_expense.setAdapter(arrayAdapter);
                            }
                            else {
                                Toast.makeText(getActivity(),"Error Occured! Data does not Deleted",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("CANCEL",null);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.setTitle("ALERT BOX");
                    alertDialog.show();
                }
            }
        });
        return v;
    }
}
