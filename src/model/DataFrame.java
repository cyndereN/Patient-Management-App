package model;

import com.sun.jdi.Value;

import java.util.ArrayList;

public class DataFrame {
    private ArrayList<Column> columns;

    public DataFrame(){
        columns = new ArrayList<Column>();
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column){
        columns.add(column);
    }

    public ArrayList<String> getColumnNames(){
        ArrayList<String> columnNames = new ArrayList<>();
        for (Column column: columns){
            columnNames.add(column.getName());
        }
        return columnNames;
    }

    public int getRowCount(){
        return columns.get(0).getSize();
    }

    public String getValue(String columnName, int row){
        String val = null;
        for (Column column: columns){
            if (column.getName().equals(columnName)) {
                val = column.getRowValue(row);
                break;
            }
        }
        return val;
    }

    public void putValue(String columnName, int row, String Value) {
        for (Column column : columns) {
            if (column.getName().equals(columnName)) {
                column.setRowValue(row, Value);
                break;
            }
        }
    }

    public void addValue(String columnName, String value){
        for (Column column : columns) {
            if (column.getName().equals(columnName)) {
                column.addRowValue(value);
                break;
            }
        }
    }

    // Assume rows are input in order, row[-1] are column names
    public void loadDataInRowsHelper(String[] data, int row){
        if (row == -1) {
            for (String columnName : data) {
                Column column = new Column(columnName);
                addColumn(column);
            }
        } else {
            for(int i=0 ; i<data.length ; i++){
                Column column = columns.get(i);
                column.addRowValue(data[i]);
                columns.set(i, column);
            }
        }
    }

    public String[] getStringColumnNames(){
        int size = getColumnNames().size();
        String[] list = getColumnNames().toArray(new String[size]);
        return list;
    }
}
