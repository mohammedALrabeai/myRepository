package com.example.mohammedalrabeai.ali2.ext;

/**
 * Created by anupamchugh on 09/02/16.
 */
public class DataModel {

    String name;
    String id_table_outo;
    String cul_numb;
    String note;


    public DataModel(String name, String id_table_outo, String cul_numb, String note ) {
        this.name=name;
        this.id_table_outo=id_table_outo;
        this.cul_numb=cul_numb;
        this.note=note;

    }


    public String getName() {
        return name;
    }


    public String getid_table_outo() {
        return id_table_outo;
    }


    public String getcul_numb() {
        return cul_numb;
    }


    public String getnote() {
        return note;
    }

}
