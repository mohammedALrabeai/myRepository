package com.example.mohammedalrabeai.ali2;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ScrollingView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowTable extends AppCompatActivity {
    A_TableDB mDbHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScrollView scrollView=new ScrollView(this);
        TableLayout table = new TableLayout(this);
        TableRow.LayoutParams llp = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 2, 0);//2px right-margin
        mDbHelper = new A_TableDB(this);
        mDbHelper.open();
//        TableRow tr = new TableRow(this);
//        tr.setBackgroundColor(Color.BLACK);
//        tr.setPadding(0, 0, 0, 2); //Border between rows
        String name=null;
        int cul=0;
        if(getIntent()!=null){
            Bundle extr=getIntent().getExtras();
            name=extr.getString("name");
            cul=extr.getInt("cul",0);
        }
        Cursor db_custom = mDbHelper.fetchCustomTable(name);
        startManagingCursor(db_custom);
        db_custom.moveToFirst();
        TableRow tr2 = new TableRow(this);
        tr2.setBackgroundColor(Color.BLACK);
        tr2.setPadding(0, 0, 0, 2); //Border between rows
        for(int k=0;k<db_custom.getColumnCount();k++){
//New Cell
            LinearLayout cell = new LinearLayout(this);
            cell.setBackgroundColor(Color.CYAN);
            cell.setLayoutParams(llp);//2px border on the right for the cell
            TextView tv = new TextView(this);
            tv.setText(db_custom.getColumnName(k));
            tv.setPadding(0, 0, 4, 3);
            cell.addView(tv);
            tr2.addView(cell);}
        table.addView(tr2);

            int n = db_custom.getCount();
//        db_custom.moveToNext();

        for (int i = 0; i < n; i++) {


        TableRow tr = new TableRow(this);
        tr.setBackgroundColor(Color.BLACK);
        tr.setPadding(0, 0, 0, 2); //Border between rows
    for(int j=0;j<db_custom.getColumnCount();j++) {
//New Cell
    LinearLayout cell = new LinearLayout(this);
    cell.setBackgroundColor(Color.WHITE);
    cell.setLayoutParams(llp);//2px border on the right for the cell


    TextView tv = new TextView(this);
    tv.setText(db_custom.getString(j));
    tv.setPadding(0, 0, 4, 3);

    cell.addView(tv);
    tr.addView(cell);
}
//add as many cells you want to a row, using the same approach

        table.addView(tr);
            db_custom.moveToNext();
        }
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(table);
      //  scrollView.addView(linearLayout);
       // scrollView.isEnabled();
        HorizontalScrollView sss2=new HorizontalScrollView(this);
        NestedScrollView sss=new NestedScrollView(this);
        sss.addView(linearLayout);
        sss2.addView(sss);
      //  scrollView.setEnabled(true);
        //scrollView.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        setContentView(sss2);

    }
}
