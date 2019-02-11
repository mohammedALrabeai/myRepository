package com.example.mohammedalrabeai.ali2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.CustomAdapter;
import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Add_List  extends AppCompatActivity {
    private static final int REQUEST_PATH = 1;
    double total=0;
    static ArrayList<DataModel> phoneModels;
    double totalPrice;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int DATE_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_DIALOG = 1;
    private static Calendar mCalendar;
    private Button dialogButton;
    private TextView dateText,totalSales,tajerName;

    A_TableDB mDbHelper;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    Button addCustom;
    ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_list);
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();
        totalPrice=0;
        mCalendar = Calendar.getInstance();
        dialogButton=(Button) findViewById(R.id.dialog_Button);
        dateText=(TextView)findViewById(R.id.DateText);
        totalSales=(TextView)findViewById(R.id.total_text) ;
        tajerName=(TextView)findViewById(R.id.tajerNameText) ;
        listView=(ListView)findViewById(R.id.list);
        phoneModels=new ArrayList<>();
        SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        dateText.setText(timeForButton);
       // Refresh(timeForButton);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);


            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showDialog(DATE_PICKER_DIALOG);
                Intent i=new Intent(Add_List.this,SearchProd.class);
                //i.putExtra("names" ,ere);
//        startActivity(intent1);
                startActivityForResult(i,1);
//                startActivityForResult(i);
            }
        });

    }



    private DatePickerDialog showDatePicker(){
        DatePickerDialog datePicker=new DatePickerDialog(this, new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mCalendar.set(Calendar.YEAR, i);
                        mCalendar.set(Calendar.MONTH, i1);
                        mCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        updateDateButtonText();
                    }
                }, mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
        return datePicker;
    }

    private void updateDateButtonText() {
        SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        Refresh(timeForButton);
        Toast.makeText(Add_List.this, "timeButton"+timeForButton, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {

//            case TIME_PICKER_DIALOG:
//                return showTimePicker();
            case DATE_PICKER_DIALOG:
                return showDatePicker();
        }
        return super.onCreateDialog(id);
    }

    private void Refresh(String mDate) {
        dateText.setText(mDate);
       // Cursor db_custom = mDbHelper.fetchPIECES_AS_Date(mDate);
        dataModels= new ArrayList<>();
        Cursor db_custom2 = mDbHelper.fetchTablesInf();

        // phoneModels[1]=new ArrayList<>();
        startManagingCursor(db_custom2);
        int n2 = db_custom2.getCount();
        db_custom2.moveToFirst();
        for (int i = 0; i < n2; i++) {
            String name = db_custom2.getString(db_custom2.getColumnIndexOrThrow(A_TableDB.TABLE_NAME));
            String id_table_outo = db_custom2.getString(db_custom2.getColumnIndexOrThrow(A_TableDB.ID_TABLE_OUTO));
            String cul_numb = db_custom2.getString(db_custom2.getColumnIndexOrThrow(A_TableDB.CUL_NUM));
            String note = db_custom2.getString(db_custom2.getColumnIndexOrThrow(A_TableDB.NOTE_TABLES));
            dataModels.add(new DataModel(name, id_table_outo, cul_numb,note));
            db_custom2.moveToNext();
        }

        phoneModels=new ArrayList<>();
        // adapter= new CustomAdapter(dataModels,getApplicationContext());
        for(int oo =0;oo<2;oo++){
            Cursor db_custom = mDbHelper.fetchCustomTable(dataModels.get(1+oo).getName());


            // dataModels= new ArrayList<>();
//        phoneModels=new ArrayList<>();
            startManagingCursor(db_custom);
            int n = db_custom.getCount();
            db_custom.moveToFirst();
            for (int i = 0; i < n; i++) {
                String name = db_custom.getString(db_custom.getColumnIndexOrThrow("discription"));
                String id_table_outo = db_custom.getString(db_custom.getColumnIndexOrThrow("price"));
                String cul_numb = db_custom.getString(db_custom.getColumnIndexOrThrow("num"));
                String note = db_custom.getString(db_custom.getColumnIndexOrThrow("num"));
                phoneModels.add(new DataModel(name, id_table_outo, cul_numb,note));
                db_custom.moveToNext();
            }
        }

//        startManagingCursor(db_custom);
//        totalPrice=0;
//        int n = db_custom.getCount();
//        db_custom.moveToFirst();
//        for (int i = 0; i < n; i++) {
//            String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NAME_PIECE_PIECES));
//            String phone = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DISCRIPTION_PIECES));
//            String id = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM));
//            String id_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.OWNER));
//
//            try {
//                double price=Double.parseDouble(db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.MANUFACTURE_PIECES)));
//                totalPrice=totalPrice+price;
//            }catch ( Exception e){
//                Log.d("ddd"," ........ NO PRICE");
//            }
//
//            dataModels.add(new DataModel(name, phone, id_outo,id));
//            // ere[db_custom.getPosition()] = name;
//            db_custom.moveToNext();
//        }
//        adapter= new CustomAdapter(dataModels,getApplicationContext());
//        totalSales.setText(""+totalPrice);
//        listView.setAdapter(adapter);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        // See which child activity is calling us back.
        if (requestCode == REQUEST_PATH){
            if (resultCode == RESULT_OK) {
             String   Name = data.getStringExtra("GetName");
                String   num = data.getStringExtra("GetNum");
                String   price = data.getStringExtra("Getprice");
                String   tableN = data.getStringExtra("GetTableN");
                try {
                    total=total+Double.parseDouble(price);
                    totalSales.setText(""+total);
                }catch (Exception e){
                    Toast.makeText(this, "error in price", Toast.LENGTH_SHORT).show();
                    totalSales.setText(""+total+"+ error ");
                }
                phoneModels.add(new DataModel(Name, price, num,tableN));
                adapter= new CustomAdapter(phoneModels,getApplicationContext());
                listView.setAdapter(adapter);
                //   path_name=data.getStringExtra("GetPath");
//                nameT.setText(curName);
                Toast.makeText(this, ""+Name, Toast.LENGTH_SHORT).show();


            }else if (resultCode == RESULT_OK-1){
                String   Name = data.getStringExtra("GetName");
                tajerName.setText(Name);
            }
        }
    }

    public void selectTajer(View view) {
        Intent i=new Intent(this,Show_Tajers.class);
        startActivityForResult(i,1);
    }
}