package model;

import java.util.ArrayList;

public class Column {
    private String name;
    private ArrayList<String> rows;

    public Column(String name){
        this.name = name;
        rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize(){
        return rows.size();
    }

    public String getRowValue(int index){
        return rows.get(index);
    }

    public void setRowValue(int index, String val){
        rows.set(index, val);
    }

    public void addRowValue(String val){
        rows.add(val);
    }
}
