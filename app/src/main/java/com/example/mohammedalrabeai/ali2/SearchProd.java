package com.example.mohammedalrabeai.ali2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.util.ArrayList;

import static com.example.mohammedalrabeai.ali2.Main2Activity.dataModels;
import static com.example.mohammedalrabeai.ali2.Main2Activity.mDbHelper;

public class SearchProd extends AppCompatActivity implements SearchView.OnQueryTextListener {
    static ArrayList<DataModel> phoneModels;
    String[] ere;
    // Declare Variables
    ListView list;
    SearchListViewAdapter adapter;
    SearchView editsearch;

    //    String[] animalNameList;
    ArrayList<AnimalNames> arraylist = new ArrayList<AnimalNames>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_prod);
        String[] animalNameList=null;
        // Generate sample data
//        if(getIntent()!=null){
//            Bundle extr=getIntent().getExtras();
//            animalNameList=extr.getStringArray("names");
//        }
//        Intent iii=this.getIntent();
        // String[] animalNameList=iii.getStringArrayExtra("name");
//        animalNameList = new String[]{"Lion", "Tiger", "Dog",
//                "Cat", "Tortoise", "Rat","محمد", "Elephant", "Fox",
//                "Cow","Donkey","Monkey"};

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
        getTables();

//        for (int i = 0; i < animalNameList.length; i++) {
//            AnimalNames animalNames = new AnimalNames(animalNameList[i]);
//            // Binds all strings into an array
//            arraylist.add(animalNames);
//        }

        // Pass results to ListViewAdapter Class
        adapter = new SearchListViewAdapter(this,arraylist );

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchProd.this, "i="+adapter.getItem(i)
                        +" l="+arraylist.get(i).getAnimalName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(SearchProd.this, ""+phoneModels.get(i).getName()+" \n"+phoneModels.get(i).getcul_numb()
                        +" \n"+phoneModels.get(i).getid_table_outo(), Toast.LENGTH_SHORT).show();
//adapter.getItem(arraylist.get(i).getAnimalName();
                onnameClick(arraylist.get(i).getAnimalName());
            }
        });

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);
    }

    private void getTables() {
        Cursor db_custom = mDbHelper.fetchTablesInf();
        startManagingCursor(db_custom);
        int n = db_custom.getCount();
         ere = new String[n];
        Toast.makeText(this, "" + n, Toast.LENGTH_SHORT).show();
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.TABLE_NAME));
           // String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.NAME_CUSTOM));
            ere[db_custom.getPosition()] = name;
            AnimalNames animalNames = new AnimalNames(name);
            // Binds all strings into an array
           // arraylist.add(animalNames);
            db_custom.moveToNext();
        }


        fillDatafromTables(ere);
    }

    private void fillDatafromTables(String[] nameTabl) {
        phoneModels=new ArrayList<>();
        // adapter= new CustomAdapter(dataModels,getApplicationContext());
        for(int oo =0;oo<nameTabl.length;oo++){
            Cursor db_custom = mDbHelper.fetchCustomTable(nameTabl[oo]);


            // dataModels= new ArrayList<>();
//        phoneModels=new ArrayList<>();
            startManagingCursor(db_custom);
            int n = db_custom.getCount();
            db_custom.moveToFirst();
            for (int i = 0; i < n; i++) {
                String name = db_custom.getString(db_custom.getColumnIndexOrThrow("discription"));
                String id_table_outo = db_custom.getString(db_custom.getColumnIndexOrThrow("price"));
                String cul_numb = db_custom.getString(db_custom.getColumnIndexOrThrow("num"));
                String note =nameTabl[oo];
                phoneModels.add(new DataModel(name, id_table_outo, cul_numb,note));
                AnimalNames animalNames = new AnimalNames(name);
                arraylist.add(animalNames);
                db_custom.moveToNext();
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
    private void onnameClick(String o)
    {
        DataModel dataModel=searshData(o);

        //Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
//        intent.putExtra("GetPath",currentDir.toString());
        intent.putExtra("GetName",dataModel.getName());
        intent.putExtra("Getprice",dataModel.getid_table_outo());
        intent.putExtra("GetTableN",dataModel.getnote());
        intent.putExtra("GetNum",dataModel.getcul_numb());
        setResult(RESULT_OK, intent);
        finish();
    }

    private DataModel searshData(String o) {
        for (int i=0;i<phoneModels.size();i++) {
            DataModel dataModel =phoneModels.get(i);
                    String name55 = dataModel.getName();
                    if (name55==o){
                        return dataModel;
                    }
        }
        return null;
    }


}
