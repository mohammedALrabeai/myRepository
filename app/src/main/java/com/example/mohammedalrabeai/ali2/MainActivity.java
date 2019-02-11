package com.example.mohammedalrabeai.ali2;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;

    Button btnUpDirectory,btnSDCard;

    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;

    ArrayList<XYValue> uploadData;

    ListView lvInternalStorage;

    String [] TABLE_NAME;
    int num_Table=0;
    A_TableDB mDbHelper;

    private static final String UNIQUE = " UNIQUE ";
    private static final String INTEGER = " INTEGER ";
    private static final String TEXT = " TEXT ";
    private static final String INTEGERC = " INTEGER, ";
    private static final String TEXTC = " TEXT, ";
    private static final String PARENTHSE_LEFT = " ( ";
    private static final String PARENTHSE_RIGHT = " ) ";
    private static final String CREATE_TABLE_IF_NOT_EXISTS = "CREATE TABLE IF NOT EXISTS ";
    private static final String INTEGER_PRIMARY_KEY = " INTEGER PRIMARY KEY, ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvInternalStorage = (ListView) findViewById(R.id.lvInternalStorage);
        btnUpDirectory = (Button) findViewById(R.id.btnUpDirectory);
        btnSDCard = (Button) findViewById(R.id.btnViewSDCard);
        uploadData = new ArrayList<>();
        TABLE_NAME=new String[15];
        mDbHelper=new A_TableDB(this);
        mDbHelper.open();
        //need to check the permissions
        checkFilePermissions();

        lvInternalStorage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lastDirectory = pathHistory.get(count);
                if(lastDirectory.equals(adapterView.getItemAtPosition(i))){
                    Log.d(TAG, "lvInternalStorage: Selected a file for upload: " + lastDirectory);

                    //Execute method for reading the excel data.
                    readExcelData(lastDirectory);

                }else
                {
                    count++;
                    pathHistory.add(count,(String) adapterView.getItemAtPosition(i));
                    checkInternalStorage();
                    Log.d(TAG, "lvInternalStorage: " + pathHistory.get(count));
                }
            }
        });

        //Goes up one directory level
        btnUpDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    Log.d(TAG, "btnUpDirectory: You have reached the highest level directory.");
                }else{
                    pathHistory.remove(count);
                    count--;
                    checkInternalStorage();
                    Log.d(TAG, "btnUpDirectory: " + pathHistory.get(count));
                }
            }
        });

        //Opens the SDCard or phone memory
        btnSDCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                pathHistory = new ArrayList<String>();
                pathHistory.add(count,System.getenv("EXTERNAL_STORAGE"));
                Log.d(TAG, "btnSDCard: " + pathHistory.get(count));
                checkInternalStorage();
            }
        });

    }

    /**
     *reads the excel file columns then rows. Stores data as ExcelUploadData object
     * @return
     */
    private void readExcelData(String filePath) {
        Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);
        int[] d=new int[15];

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            toastMessage(""+workbook.getNumberOfSheets());
            Log.d(TAG,""+workbook.getNumberOfSheets());
            XSSFSheet sheet = workbook.getSheetAt(3);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//            StringBuilder sb = new StringBuilder();
//extact tables name
            Row row2=sheet.getRow(2);
            for(int c=0;c<100;c++){
                String value = getCellAsString(row2, c, formulaEvaluator);
                if(value!=""){
                    d[num_Table] = c;
                    TABLE_NAME[num_Table++] = value;
                }
            }
            //extract table columen name for each table then create
            for(int t=0;t<num_Table;t++) {
                String nameT=TABLE_NAME[t];
                Row row3=sheet.getRow(3);
                int cul=d[t];
                String value = getCellAsString(row3, cul++, formulaEvaluator);
                String [] cul_name=new String[15];
                int cul_n=0;
                while (value!=""&&value!=null){
                    cul_name[cul_n++]=value;
                    value = getCellAsString(row3, cul++, formulaEvaluator);

                }
                mDbHelper.createTable(nameT,cul_name);
             Long L=   mDbHelper.insertTable(nameT,cul_n-1," ");
Log.d("INSERT_TABLE","L=  "+L);

                //outter loop, loops through rows
                for (int r = 4; r < 166; r++) {
                    Row row = sheet.getRow(r);
                    String test = getCellAsString(row, d[t], formulaEvaluator);
                    String test2 = getCellAsString(sheet.getRow(r + 1), d[t], formulaEvaluator);
                    if (test == null || test == "") {

                        if (test2 != null && test2 != "") {
                            continue;
                        }else {
                            r=166;
                        }
                    } else {

                        StringBuilder sb = new StringBuilder();
                        String[] data = new String[cul_n];
                        //int cellsCount = row.getPhysicalNumberOfCells();
                        //inner loop, loops through columns
                        for (int c = d[t]; c < d[t] + cul_n; c++) {
                            //handles if there are to many columns on the excel sheet.
                            if (c > 2000) {
                                Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! ");
                                toastMessage("ERROR: Excel File Format is incorrect!");
                                break;
                            } else {
//                            if (r == 2) {
//                                String value = getCellAsString(row, c, formulaEvaluator);
//                                if (value != "") {
//                                    d[num_Table] = c;
//                                    TABLE_NAME[num_Table++] = value;
//                                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
//                                    Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
//                                    sb.append(value + ", ");
//                                }
//                            }
//                            if (r == 3) {
//
//                            }
                                String value2 = getCellAsString(row, c, formulaEvaluator);
//                            String cellInfo = "r:" + r + "; c:" + c + "; v:" + value2;
//                            Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                                sb.append(value2 + ", ");
                                data[c - d[t]] = value2;
                            }
                        }
                        Log.d(TAG, " STRINGBUILDER: cul_name=" + cul_name.length + "data  =" + data.length + "cul  =" + cul_n);
                        Long rW = mDbHelper.insertRowCustomTable(nameT, cul_name, cul_n, data);
                        Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString() + " rW=" + rW);

                        // sb.append(":");
                    }
                }
            }
      //      Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());

         //   parseStringBuilder(sb);

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");

        //Add to the ArrayList<XYValue> row by row
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                double x = Double.parseDouble(columns[0]);
                double y = Double.parseDouble(columns[1]);

                String cellInfo = "(x,y): (" + x + "," + y + ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                uploadData.add(new XYValue(x,y));

            }catch (NumberFormatException e){

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }

        printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){
            double x = uploadData.get(i).getX();
            double y = uploadData.get(i).getY();
            Log.d(TAG, "printDataToLog: (x,y): (" + x + "," + y + ")");
        }
    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

    private void checkInternalStorage() {
        Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, FilePathStrings);
            lvInternalStorage.setAdapter(adapter);

        }catch(NullPointerException e){
            Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


    public void gostatistics(View view) {
        Intent i=new Intent(this,Show_Customers_Detile.class);
        startActivity(i);
    }

    public void gotodoogle(View view) {
        Intent i=new Intent(this,BaseDemoActivity.class);
        startActivity(i);
    }

    public void gogo(View view) {
        Intent i=new Intent(this,Main2Activity.class);
        startActivity(i);
    }
}

