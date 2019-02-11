package com.example.mohammedalrabeai.ali2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.CustomAdapter;
import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.util.ArrayList;

public class Show_Customers_Detile extends AppCompatActivity {

    A_TableDB mDbHelper;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main33);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();

        listView=(ListView)findViewById(R.id.list);
        Cursor db_custom = mDbHelper.fetchTablesInf();
        dataModels= new ArrayList<>();
        startManagingCursor(db_custom);
        int n = db_custom.getCount();
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.TABLE_NAME));
            String id_table_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_TABLE_OUTO));
            String cul_numb = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.CUL_NUM));
            String note = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NOTE_TABLES));
            dataModels.add(new DataModel(name, id_table_outo, cul_numb,note));
            db_custom.moveToNext();
        }

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);
                long id_oto= Long.parseLong(dataModel.getid_table_outo());
                String name55=dataModel.getName();
                int culN=Integer.parseInt(dataModel.getcul_numb());
//                Cursor db_custom3 = mDbHelper.fetchAllMONY_OUT_id(mm);
//                int ii=db_custom3.getCount();
//                db_custom3.moveToFirst();
//                String rowOut=" ";
//                for (int i = 0; i < ii; i++) {
//                    String name = db_custom3.getString(db_custom3.getColumnIndexOrThrow(A_TableDB.NAME_PIECE));
//                    String date = db_custom3.getString(db_custom3.getColumnIndexOrThrow(A_TableDB.DATE_TIME_MONY_OUT));
//                    String cost = db_custom3.getString(db_custom3.getColumnIndexOrThrow(A_TableDB.COST_MNY_OUT));
//                    rowOut=rowOut+"  "+cost+"  "+date+"  "+name+"\n";
//                    db_custom3.moveToNext();
//                }
                Intent ii=new Intent(Show_Customers_Detile.this,ShowTable.class);
                ii.putExtra("name",name55);
                ii.putExtra("cul",culN);
                startActivity(ii);
              //  Toast.makeText(Show_Customers_Detile.this,  "الدخل"+ii+"  "+dataModel.getName()+"  "+"\n"+rowOut+" الخرج"+i3+"   \n"+rowIn, Toast.LENGTH_LONG).show();
              //  Snackbar.make(view, ii+"  "+dataModel.getName()+"  "+"\n"+rowIn+" "+ii+"   \n"+rowOut, Snackbar.)
                   //     .setAction("No action", null).show();
            }
        });


    }

}
