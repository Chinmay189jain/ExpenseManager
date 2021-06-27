package com.example.expensemanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment1 extends Fragment{
    ListView user_list_income;
    String itemSelected="";
    ImageButton delete;
    ArrayList<String> arr_list_income;
    ArrayAdapter arrayAdapter;

    public Fragment1() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment1,container,false);
        user_list_income=(ListView)v.findViewById(R.id.list_income);
        delete=(ImageButton)v.findViewById(R.id.delete_income);
        arr_list_income = new ArrayList<String>();
        IncomeDatabase in = new IncomeDatabase(getActivity());
        arr_list_income = in.getList_income();
        arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arr_list_income);
        user_list_income.setAdapter(arrayAdapter);

        user_list_income.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelected=user_list_income.getItemAtPosition(i).toString();
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
                            boolean x=in.deleteData_income(dttxt,tmtxt);
                            if(x==true) {
                                Toast.makeText(getActivity(),"Data Successfully Deleted",Toast.LENGTH_SHORT).show();
                                arr_list_income.clear();
                                arr_list_income = in.getList_income();
                                arrayAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_singlechoice,arr_list_income);
                                user_list_income.setAdapter(arrayAdapter);
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
