package com.example.mohammedalrabeai.ali2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.nfc.Tag;
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

public class ShowSale extends AppCompatActivity {

    double totalPrice;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int DATE_PICKER_DIALOG = 0;
    private static final int TIME_PICKER_DIALOG = 1;
    private static Calendar mCalendar;
    private Button dialogButton;
    private TextView dateText,totalSales;
    A_TableDB mDbHelper;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    Button addCustom;
    ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_sales);
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();
        totalPrice=0;
        mCalendar = Calendar.getInstance();
        dialogButton=(Button) findViewById(R.id.dialog_Button);
        dateText=(TextView)findViewById(R.id.DateText);
        totalSales=(TextView)findViewById(R.id.total_text) ;
        listView=(ListView)findViewById(R.id.list);

        SimpleDateFormat timeFormat = new SimpleDateFormat(DATE_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        Refresh(timeForButton);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);


            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_DIALOG);
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
        Toast.makeText(ShowSale.this, "timeButton"+timeForButton, Toast.LENGTH_SHORT).show();
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
        Cursor db_custom = mDbHelper.fetchPIECES_AS_Date(mDate);
        dataModels= new ArrayList<>();
        startManagingCursor(db_custom);
        totalPrice=0;
        int n = db_custom.getCount();
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NAME_PIECE_PIECES));
            String phone = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DISCRIPTION_PIECES));
            String id = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM));
            String id_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.OWNER));

           try {
               double price=Double.parseDouble(db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.MANUFACTURE_PIECES)));
               totalPrice=totalPrice+price;
           }catch ( Exception e){
               Log.d("ddd"," ........ NO PRICE");
           }

            dataModels.add(new DataModel(name, phone, id_outo,id));
            // ere[db_custom.getPosition()] = name;
            db_custom.moveToNext();
        }
        adapter= new CustomAdapter(dataModels,getApplicationContext());
       totalSales.setText(""+totalPrice);
        listView.setAdapter(adapter);
    }
}
