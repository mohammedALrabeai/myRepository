package com.example.mohammedalrabeai.ali2;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.CustomAdapter;
import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.util.ArrayList;

public class Show_Tajers extends AppCompatActivity {

    A_TableDB mDbHelper;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
Button addCustom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_tajers);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();
        addCustom=(Button)findViewById(R.id.add_custom_button);
        listView=(ListView)findViewById(R.id.list);
//        Long iii=mDbHelper.createCustom(new String[]{" ","mmm","5","777","no","noo","j"});
//        Toast.makeText(this, ""+iii, Toast.LENGTH_SHORT).show();
     Refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent();
//        intent.putExtra("GetPath",currentDir.toString());
                intent.putExtra("GetName",dataModels.get(position).getName());

                setResult(RESULT_OK-1, intent);
                finish();

//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+"   ID: "+dataModel.getVersion_number()+"  ID_OUT: "
//                        +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
            }
        });

        addCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d=new Dialog(Show_Tajers.this);
                Window window=d.getWindow();
                window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

                d.setTitle(" عنوان النافذة");
                d.setContentView(R.layout.dialog_view1);
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
                                Long iii=mDbHelper.createTajers(new String[]{"",name[0],"5",name[1],"no","noo","j"});
                        Refresh();
                        Toast.makeText(Show_Tajers.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                        d.hide();
                    }
                });
                d.show();

            }
        });
    }

    private void Refresh() {
       // String[] nnn=new String[5];
//        mDbHelper.creatTa();
        Cursor db_custom = mDbHelper.fetchAllTAJERS();
        dataModels= new ArrayList<>();
        startManagingCursor(db_custom);
        int n = db_custom.getCount();
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NAME_CUSTOM));
            String phone = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.PHONE_CUSTOM));
            String id = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM));
            String id_outo = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.ID_CUSTOM_OUTO));
            dataModels.add(new DataModel(name, phone, id_outo,id));
            // ere[db_custom.getPosition()] = name;
            db_custom.moveToNext();
        }
//        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1","September 23, 2008"));
//      dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2","February 9, 2009"));
//        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3","April 27, 2009"));
//        dataModels.add(new DataModel("Donut","Android 1.6","4","September 15, 2009"));
//        dataModels.add(new DataModel("Eclair", "Android 2.0", "5","October 26, 2009"));
//        dataModels.add(new DataModel("Froyo", "Android 2.2", "8","May 20, 2010"));
//        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9","December 6, 2010"));
//        dataModels.add(new DataModel("Honeycomb","Android 3.0","11","February 22, 2011"));
//        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14","October 18, 2011"));
//        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16","July 9, 2012"));
//        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19","October 31, 2013"));
//        dataModels.add(new DataModel("Lollipop","Android 5.0","21","November 12, 2014"));
//        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23","October 5, 2015"));

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
