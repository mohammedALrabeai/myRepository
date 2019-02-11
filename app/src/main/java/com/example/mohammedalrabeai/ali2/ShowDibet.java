package com.example.mohammedalrabeai.ali2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

public class ShowDibet extends AppCompatActivity {

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

                Toast.makeText(ShowDibet.this, "list "+id, Toast.LENGTH_SHORT).show();
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
        Dialog d=new Dialog(ShowDibet.this);
        Window window=d.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        d.setTitle(" عنوان النافذة");
        d.setContentView(R.layout.dialog_add_dibet);
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
                int idd_Custom=mDbHelper.findIdCUSTOM(name[0]);
                if(idd_Custom==-1){
                    Dialog dd=new Dialog(ShowDibet.this);
                    Window window=dd.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    dd.setTitle(" عنوان النافذة");
                    dd.setContentView(R.layout.dibet_dialog);
                    TextView textView1=(TextView)dd.findViewById(R.id.message11);
                    //textView1.setText(" "+String.format("%s \n  لم يتم العثور علية بين العملاء   \n هل تريد اضافتة؟",name[0]));
                    String phone="";
                    dd.show();

                    textView1.setText(String.format("%s \n  لم يتم العثور علية بين العملاء   \n هل تريد اضافتة؟",name[0]));

                    Button okB=(Button)dd.findViewById(R.id.yesButton);
                    Button cancleB=(Button)dd.findViewById(R.id.noButton);
                    cancleB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dd.hide();
                            d.hide();
                        }
                    });
                    okB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            name[0] =textView.getText().toString();
//                            name[1]=text.getText().toString();
                            Long iii=mDbHelper.createCustom(new String[]{"",name[0],"5","0","no","noo","j"});
                           // Refresh();
                          //  Toast.makeText(Show_Customers.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                            dd.hide();
                            Long mnb= mDbHelper.createDibet(new String[]{"",""+iii,name[1],dateToday,"time","no","noo","j"});
                            // Long iii=mDbHelper.createTajers(new String[]{"",name[0],"5",name[1],"no","noo","j"});
                            Refresh(dateToday);
                           /// Toast.makeText(ShowDibet.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                            d.hide();
                        }
                    });
                }else {
                Long iii= mDbHelper.createDibet(new String[]{"",""+idd_Custom,name[1],dateToday,"time","no","noo","j"});
               // Long iii=mDbHelper.createTajers(new String[]{"",name[0],"5",name[1],"no","noo","j"});
                Refresh(dateToday);
                Toast.makeText(ShowDibet.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                d.hide();
            }}
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
        Toast.makeText(ShowDibet.this, "timeButton"+timeForButton, Toast.LENGTH_SHORT).show();
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
        Cursor db_inner=null;

        try{

            db_inner = mDbHelper.qury("SELECT * FROM customers ct,dibet dt WHERE dt."+mDbHelper.ID_CUSTOM+" = "+"ct."+mDbHelper.ID_CUSTOM_OUTO);
            Toast.makeText(this, "CCcc=  "+db_inner.getCount(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        try {
//mDbHelper.dropTable("monyout");

            dateText.setText(mDate);
            db_custom = mDbHelper.fetchCustomTable("dibet");
        }catch (Exception e){
            Toast.makeText(this, "جاري انشاء جدول", Toast.LENGTH_SHORT).show();
            mDbHelper.createTable("dibet",new String[]{  mDbHelper.ID_CUSTOM ,mDbHelper.AMOUNT
                    ,mDbHelper.DATE_TIME_DIBET ,mDbHelper.TIME_DIBET,
                    mDbHelper.DISCRIPTION ,mDbHelper.NOTE });
            db_custom = mDbHelper.fetchCustomTable("dibet");
        }
        dataModels= new ArrayList<>();
        startManagingCursor(db_custom);
        totalPrice=0;
        int n = db_custom.getCount();
        if(n!=db_inner.getCount()){
            Toast.makeText(this, "هناك خطأ بين العلاقة", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "n= "+n, Toast.LENGTH_SHORT).show();
        db_custom.moveToFirst();
        db_inner.moveToFirst();
        for (int i = 0; i < n; i++) {
            String sd=db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DATE_TIME_DIBET));
           // Toast.makeText(this, "===  "+sd+" \n"+"===  "+mDate, Toast.LENGTH_SHORT).show();
         //   if (mDate.equals(sd)) {
                String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM));
                String name_test=db_inner.getString(db_inner.getColumnIndexOrThrow(A_TableDB.NAME_CUSTOM));

               // mDbHelper.qurey("df");
             //   Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show();
               // String phone = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.DISCRIPTION_PIECES));
               String id = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.TIME_DIBET));
               // String id_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.OWNER));
                double price=0;
                try {
                     price = Double.parseDouble(db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.AMOUNT)));
                    totalPrice = totalPrice + price;
                } catch (Exception e) {
                    Log.d("ddd", " ........ NO PRICE");
                }

               // dataModels.add(new DataModel(name, sd, ""+price, id));
            dataModels.add(new DataModel(name_test, sd, ""+price, id));
                // ere[db_custom.getPosition()] = name;
                db_custom.moveToNext();
                db_inner.moveToNext();
        }
        adapter= new CustomAdapter(dataModels,getApplicationContext());
        totalSales.setText(": "+totalPrice);
        listView.setAdapter(adapter);
    }
    public void addOut(View view) {
        addOut();
    }
}
