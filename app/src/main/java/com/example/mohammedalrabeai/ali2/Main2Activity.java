package com.example.mohammedalrabeai.ali2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedalrabeai.ali2.ext.CustomAdapter;
import com.example.mohammedalrabeai.ali2.ext.DataModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm:ss";
    private static String[] taps = null;
    private static Calendar mCalendar;
    public static final String MYPREFS = "mySharedPreferences";
    private float dollar;
    int mode = Activity.MODE_PRIVATE;
    static A_TableDB mDbHelper;
     static ArrayList<DataModel> dataModels;
    static ArrayList<DataModel> phoneModels;
    static ArrayList<DataModel> coverModels;
    static ArrayList<DataModel> tempModel;
    static ArrayList<DataModel> Model_1,Model_2,Model_3,Model_4,Model_5,Model_6,Model_7,Model_8,Model_9,Model_10,Model_11,Model_12;
    ListView listView;
    private static CustomAdapter adapter;
    private static CustomAdapter adapter2;
    private static CustomAdapter  ad_Model_1,ad_Model_2,ad_Model_3,ad_Model_4,ad_Model_5,ad_Model_6,ad_Model_7,ad_Model_8,ad_Model_9,ad_Model_10,ad_Model_11,ad_Model_12;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
TextView customsTxtBx;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navig_1st);
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();
        mCalendar = Calendar.getInstance();
customsTxtBx=(TextView)findViewById(R.id.customsTxt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout v=(TabLayout)findViewById(R.id.tabs);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        loadPreferences();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String s= mDbHelper.getame();
                Toast.makeText(Main2Activity.this, ""+s, Toast.LENGTH_SHORT).show();
                try {
                    mDbHelper.backupDatabase();
                    Toast.makeText(Main2Activity.this, "backup Done..", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Main2Activity.this, "backup fuiler..", Toast.LENGTH_SHORT).show();
                }
//                Menu sss=new MenuBuilder(Main2Activity.this);
//                sss.add("ighhh");
            registerForContextMenu(fab);
                openContextMenu(fab);

//                MenuInflater mi = getMenuInflater();
//                mi.inflate(R.menu.list_menu_item_longpress, sss);
//

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        dataModels= new ArrayList<>();
        coverModels= new ArrayList<>();
        tempModel=new ArrayList<>();

            Cursor db_custom = mDbHelper.fetchTablesInf();

            dataModels= new ArrayList<>();
            startManagingCursor(db_custom);
            int n = db_custom.getCount();
            taps=new String[n];
            db_custom.moveToFirst();
            for (int i = 0; i < n; i++) {
                String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.TABLE_NAME));
                taps[i] = name;
                if(i>3) {
//                    String name = db_custom.getString(db_custom.getColumnIndexOrThrow(A_TableDB.TABLE_NAME));
//                    taps[i - 4] = name;
                    setadapters(i - 3, taps[i]);
                    TabLayout.Tab tapp=  v.newTab();
                    tapp.setText(taps[i]+" "+i);
                    tapp.setContentDescription("discription");
                    v.addTab(tapp);
                }
                db_custom.moveToNext();
            }
        try {

            firetTable();
            towTable();
        }catch (Exception e){
            Dialog d=new Dialog(this);
            Window window=d.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            d.setTitle(" عنوان النافذة");
            d.setContentView(R.layout.sale_dialog);
            String phone="";
            d.show();
            Button okB=(Button)d.findViewById(R.id.yesButton);
            Button cancleB=(Button)d.findViewById(R.id.noButton);
            cancleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });
            okB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(Main2Activity.this ,  MainActivity.class);
                    startActivity(i);

                    d.hide();
                }
            });
        }



        adapter= new CustomAdapter(phoneModels,getApplicationContext());
        adapter2= new CustomAdapter(coverModels,getApplicationContext());
       // ad_Model_1=new CustomAdapter(Model_1,getApplicationContext());
        customsTxtBx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main2Activity.this,Show_Customers.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       // v.newTab();

//        TabLayout.Tab tapp2=  v.newTab();
//        tapp2.setText("new2");
//        tapp2.setContentDescription("discription");
//        v.addTab(tapp2);

    }



    private void towTable() {
        Cursor db_custom = mDbHelper.fetchCustomTable(dataModels.get(3).getName());


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
            coverModels.add(new DataModel(name, id_table_outo, cul_numb,note));
            db_custom.moveToNext();
        }

    }

    private void firetTable() {
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
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("إضافة ...");
        menu.add(Menu.NONE,1,Menu.NONE,"مصروف");
        menu.add(Menu.NONE,2,Menu.NONE,"دين");
        menu.add(Menu.NONE,3,Menu.NONE,"مردود مبيعات");
        menu.add(Menu.NONE,4,Menu.NONE,"فاتورة");
        menu.add(Menu.NONE,5,Menu.NONE,"صنف");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case 1:{
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Main2Activity.this ,  ShowOut.class);
                i.putExtra("isNew",true);

                startActivity(i);
            }
            break;
            case 2:{
                Intent i=new Intent(Main2Activity.this ,  ShowDibet.class);
                i.putExtra("isNew",true);

                startActivity(i);
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3:{
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
            }
            break;
            case 4:{
                Intent i=new Intent(Main2Activity.this ,  Add_List.class);

                startActivity(i);
            }
            break;

        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        Menu s=new MenuBuilder(this);
//        MenuInflater mi = getMenuInflater();
//        mi.inflate(R.menu.list_menu_item_longpress, s);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(Main2Activity.this ,  ShowSale.class);

            startActivity(i);


            return true;
        }
        if (id == R.id.action_add_list) {
            Intent i=new Intent(Main2Activity.this ,  Add_List.class);

            startActivity(i);


            return true;
        }
        if (id == R.id.dolar_cost) {

            Dialog d=new Dialog(this);
            Window window=d.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            d.setTitle(" عنوان النافذة");
            d.setContentView(R.layout.dollar_dialog);
            String phone="";
            d.show();
            Button okB=(Button)d.findViewById(R.id.yesButton);
            TextView chang=(TextView)d.findViewById(R.id.chang_dolar);
            EditText dollarTxt=(EditText) d.findViewById(R.id.textView4);
            dollarTxt.setText(dollar+"");
            Button cancleB=(Button)d.findViewById(R.id.noButton);
            chang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   dollar= Float.parseFloat(dollarTxt.getText().toString());
                    Toast.makeText(Main2Activity.this, ""+dollar, Toast.LENGTH_SHORT).show();
                    SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS,mode);
                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                    editor.putFloat("dollar", dollar);
                     editor.commit();
                }
            });
            okB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.hide();
                }
            });


           return true;
        }
        if (id == R.id.other) {
            Intent i=new Intent(Main2Activity.this ,  MainActivity555.class);

            startActivity(i);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void searshgo(View view) {
        Intent i=new Intent(this,Show_Customers_Detile.class);
        startActivity(i);
    }

    public void addgo(View view) {
        Intent i=new Intent(this,Show_Customers_Detile.class);
        startActivity(i);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent i=new Intent(Main2Activity.this ,  ShowSale.class);

            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(Main2Activity.this ,  Daily_Reckoning.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(Main2Activity.this ,  ShowOut.class);
            i.putExtra("isNew",false);
            startActivity(i);

        } else if (id == R.id.nav_dibet){
            Intent i=new Intent(Main2Activity.this ,  ShowDibet.class);
            i.putExtra("isNew",false);
            startActivity(i);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if(id==R.id.chang_table){



        }else if (id == R.id.media_player) {
            Intent i=new Intent(Main2Activity.this ,  MainActivity.class);
            startActivity(i);

        }else if (id == R.id.recorder) {


        }else if (id == R.id.set_recorder) {


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("ValidFragment")
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;

                       rootView = inflater.inflate(R.layout.fragment_phones, container, false);

            ListView listView=(ListView)rootView.findViewById(R.id.list_phone);
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                listView.setAdapter(adapter);
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2) {
                listView.setAdapter(adapter2);
            }else {listView.setAdapter(getAdapterr(getArguments().getInt(ARG_SECTION_NUMBER)));
//                rootView = inflater.inflate(R.layout.fragment_main2, container, false);
//                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//                textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Dothing();
                    DataModel dataModel;
                    if (getArguments().getInt(ARG_SECTION_NUMBER)==1) {
                         dataModel = phoneModels.get(position);
                    }else {
                         dataModel = coverModels.get(position);
                    }
//                        long id_oto = Long.parseLong(dataModel.getid_table_outo());
                    String name55 = dataModel.getName();
                    String culN = dataModel.getcul_numb();
                    double price=Double.parseDouble(dataModel.getid_table_outo());
//                        Toast.makeText(M, name55+"  "+culN, Toast.LENGTH_SHORT).show();
                    Log.d("hhg",name55+"  "+culN+"  "+dataModel.getid_table_outo());
                    Dialog d=new Dialog(getContext());
                    Window window=d.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    d.setTitle(" عنوان النافذة");
                    d.setContentView(R.layout.sale_dialog);
                    String phone="";
                    d.show();
                    Button okB=(Button)d.findViewById(R.id.yesButton);
                    Button cancleB=(Button)d.findViewById(R.id.noButton);
                    cancleB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.hide();
                        }
                    });
                    okB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
                            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                            String timeFor=timeFormat.format(mCalendar.getTime());
                            String dateForButton = dateFormat.format(mCalendar.getTime());
                            Long iii=mDbHelper.createPIECES(new String[]{"1","1","model",""+price,name55,dateForButton,"yes",timeFor," "," "});
                            Snackbar.make(view, name55+"   ID: "+culN+"  insert: "+dateForButton+"  insert: "+timeFor
                                    , Snackbar.LENGTH_LONG).setAction("No action", null).show();
                            // Long iii=mDbHelper.createCustom(new String[]{"",name[0],"5",name[1],"no","noo","j"});
                            // Refresh();
                            // Toast.makeText(Main2Activity.this, iii+" : "+name[0]+"  "+name[1], Toast.LENGTH_SHORT).show();
                            d.hide();
                        }
                    });
                }
            });
            return rootView;
        }

        private CustomAdapter getAdapterr(int anInt) {
          //  Toast.makeText(getApplicationContext(), ""+anInt, Toast.LENGTH_SHORT).show();
            Log.d("DDDDDD","number adabter="+anInt);
            switch (anInt-2){
                case 1:
                    return ad_Model_1;
                case 2:return ad_Model_2;
                case 3:return ad_Model_3;
                case 4:return ad_Model_4;
                case 5:return ad_Model_5;
                case 6:return ad_Model_6;
                case 7:return ad_Model_7;
                case 8:return ad_Model_8;
                case 9:return ad_Model_9;
                case 10:return ad_Model_10;
                case 11:return ad_Model_11;
                case 12:return ad_Model_12;




            }

            return null;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 15;
        }
    }
    public void loadPreferences() {
// Get the stored preferences
        int mode = Activity.MODE_PRIVATE;
        SharedPreferences mySharedPreferences = getSharedPreferences(MYPREFS,
                mode);
// Retrieve the saved values.

        dollar = mySharedPreferences.getFloat("dollar", 550);
        // String stringPreference;
        // stringPreference = mySharedPreferences.getString(“textEntryValue”,  “”);
    }
    private void setadapters(int Count, String nameTa) {
        Cursor db_custom = mDbHelper.fetchCustomTable(nameTa);
        tempModel=new ArrayList<>();

        // dataModels= new ArrayList<>();
//        phoneModels=new ArrayList<>();
        startManagingCursor(db_custom);
        int n = db_custom.getCount();
        Log.d("DDDDDD2","name= "+nameTa+"  count = "+Count);
        db_custom.moveToFirst();
        for (int i = 0; i < n; i++) {
            String name = db_custom.getString(db_custom.getColumnIndexOrThrow("discription"));
            String id_table_outo = db_custom.getString(db_custom.getColumnIndexOrThrow("price"));
            String cul_numb = db_custom.getString(db_custom.getColumnIndexOrThrow("num"));
            String note = db_custom.getString(db_custom.getColumnIndexOrThrow("num"));
            tempModel.add(new DataModel(name, id_table_outo, cul_numb,note));
            db_custom.moveToNext();
        }
        switch (Count){
            case 1:
              // Model_1=new ArrayList<>();
                Model_1=tempModel;
                Log.d("DDDDDD3","Model_1 size = "+Model_1.size());
               ad_Model_1= new CustomAdapter(Model_1,getApplicationContext());
               break;
            case 2:Model_2=tempModel;
                Log.d("DDDDDD3","Model_2 size = "+Model_2.size());
                ad_Model_2= new CustomAdapter(Model_2,getApplicationContext());
                break;
            case 3:Model_3=tempModel;
                Log.d("DDDDDD3","Model_3 size = "+Model_3.size());
                ad_Model_3= new CustomAdapter(Model_3,getApplicationContext());
                break;
            case 4:Model_4=tempModel;
                Log.d("DDDDDD3","Model_4 size = "+Model_4.size());
                ad_Model_4= new CustomAdapter(Model_4,getApplicationContext());
                break;
            case 5:Model_5=tempModel;
                Log.d("DDDDDD3","Model_5 size = "+Model_5.size());
                ad_Model_5= new CustomAdapter(Model_5,getApplicationContext());
                break;
            case 6:Model_6=tempModel;
                Log.d("DDDDDD3","Model_6 size = "+Model_6.size());
                ad_Model_6= new CustomAdapter(Model_6,getApplicationContext());
                break;
                case 7:Model_7=tempModel;
                    Log.d("DDDDDD3","Model_7 size = "+Model_7.size());
                    ad_Model_7= new CustomAdapter(Model_7,getApplicationContext());
                    break;
            case 8:Model_8=tempModel;
                Log.d("DDDDDD3","Model_8 size = "+Model_8.size());
                ad_Model_8= new CustomAdapter(Model_8,getApplicationContext());
                break;
                case 9:Model_9=tempModel;
                    Log.d("DDDDDD3","Model_9 size = "+Model_9.size());
                    ad_Model_9= new CustomAdapter(Model_9,getApplicationContext());
                    break;
            case 10:Model_10=tempModel;
                Log.d("DDDDDD3","Model_10 size = "+Model_10.size());
                ad_Model_10= new CustomAdapter(Model_10,getApplicationContext());
                break;
            case 11:Model_11=tempModel;
                Log.d("DDDDDD3","Model_11 size = "+Model_11.size());
                ad_Model_11= new CustomAdapter(Model_11,getApplicationContext());
                break;
            case 12:Model_12=tempModel;
                Log.d("DDDDDD3","Model_12 size = "+Model_12.size());
                ad_Model_12= new CustomAdapter(Model_12,getApplicationContext());
                break;




        }


    }
}
