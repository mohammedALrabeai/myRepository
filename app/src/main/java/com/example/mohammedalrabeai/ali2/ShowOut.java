package com.example.mohammedalrabeai.ali2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.CustomAdapter;
import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ShowOut extends AppCompatActivity {

    double totalPrice;
    private static final String outTable = "out_table";
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
    String dateToday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_out);
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
        dateToday=timeForButton;
        Refresh(timeForButton);
        if(getIntent()!=null){
            Bundle extr=getIntent().getExtras();
            boolean isNew=extr.getBoolean("isNew",false);
            if(isNew){
                addOut();
            }
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // DataModel dataModel= dataModels.get(position);

                Toast.makeText(ShowOut.this, "list "+id, Toast.LENGTH_SHORT).show();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_PICKER_DIALOG);
            }
        });

    }

    private void addOut() {
        Dialog d=new Dialog(ShowOut.this);
        Window window=d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        d.setTitle(" عنوان النافذة");
        d.setContentView(R.layout.dialog_add_out);
        final String[] name = {"",""};
        String phone="";
        EditText textView=(EditText) d.findViewById(R.id.name_customT);
        EditText text=(EditText) d.findViewById(R.id.phone_customT);
        Button okB=(Button)d.findViewById(R.id.okButton);
        Button cancleB=(Button)d.findViewById(R.id.cancelButton);
        cancleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.hide();
            }
        });
        okB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name[0] =textView.getText().toString();
                name[1]=text.getText().toString();
                Long iii= mDbHelper.createMONY_OUT(new String[]{"","1",name[1],dateToday,name[0],"no","noo","j"});
               // Long iii=mDbHelper.createTajers(new String[]{"",name[0],"5",name[1],"no","noo","j"});
                Refresh(dateToday);
                Toast.makeText(ShowOut.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                d.hide();
            }
        });
        d.show();

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
        Toast.makeText(ShowOut.this, "timeButton"+timeForButton, Toast.LENGTH_SHORT).show();
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

        Cursor db_custom;
        try {
//mDbHelper.dropTable("monyout");

            dateText.setText(mDate);
            db_custom = mDbHelper.fetchCustomTable("monyout");
        }catch (Exception e){
            Toast.makeText(this, "جاري انشاء جدول", Toast.LENGTH_SHORT).show();
            mDbHelper.createTable("monyout",new String[]{  mDbHelper.ID_CUSTOM ,mDbHelper.NAME_PIECE
                    ,mDbHelper.DATE_TIME_MONY_OUT ,mDbHelper.COST_MNY_OUT ,
                    mDbHelper.DISCRIPTION_MONY_OUT ,mDbHelper.NOTE_MONY_OUT });
            db_custom = mDbHelper.fetchCustomTable("monyout");
        }
        dataModels= new ArrayList<>();
        startManagingCursor(db_custom);
        totalPrice=0;
        int n = db_custom.getCount();
        Toast.makeText(this, "n= "+n, Toast.LENGTH_SHORT).show();
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String sd=db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DATE_TIME_MONY_OUT));
            Toast.makeText(this, "===  "+sd+" \n"+"===  "+mDate, Toast.LENGTH_SHORT).show();
            if (mDate.equals(sd)) {
                String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NAME_PIECE));
                Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
               // String phone = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DISCRIPTION_PIECES));
               String id = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DISCRIPTION_MONY_OUT));
               // String id_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.OWNER));
                double price=0;
                try {
                     price = Double.parseDouble(db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.COST_MNY_OUT)));
                    totalPrice = totalPrice + price;
                } catch (Exception e) {
                    Log.d("ddd", " ........ NO PRICE");
                }

                dataModels.add(new DataModel(name, sd, ""+price, id));
                // ere[db_custom.getPosition()] = name;
                db_custom.moveToNext();
            }else {
                db_custom.moveToNext();
            }
        }
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        totalSales.setText(": "+totalPrice);
        listView.setAdapter(adapter);
    }
    public void addOut(View view) {
        addOut();
    }
}
