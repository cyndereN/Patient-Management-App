package view;

import controller.Controller;
import model.Model;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GUI extends JFrame {

    private DefaultTableModel tableModel;
    private JPanel midPanel;
    private JPanel CheckBoxPanel;
    private Model model;
    private JMenuBar menuBar;
    private JMenu file;
    private JMenu stats;
    private JMenuItem load, save, oldest, averageAge, commonBirth, commonAddress, commonCity,genderProp;
    private JTable table;
    private JScrollPane tablePanel;
    private JCheckBox id, birthdate, deathdate, ssn, drivers, passport,
            prefix, first, last, suffix, maiden,
            marital, race, ethnicity, gender,
            birthplace, address, city, state, zip;
    private JPanel searchPanel;
    private JTextField searchKeyword;
    private JButton searchButton;
    private TableRowSorter<TableModel> sorter;
    private final String[] columnNames = {"ID", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "FIRST", "LAST",
            "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE", "ZIP"};
    private final String[] searchStrings = {"ID", "BIRTHDATE", "DEATHDATE", "SSN", "DRIVERS", "PASSPORT", "PREFIX", "FIRST", "LAST",
            "SUFFIX", "MAIDEN", "MARITAL", "RACE", "ETHNICITY", "GENDER", "BIRTHPLACE", "ADDRESS", "CITY", "STATE", "ZIP", null};
    private JComboBox<String> searchList;


    private void createMenuBar(){
        menuBar = new JMenuBar();
        getContentPane().add(menuBar, BorderLayout.NORTH);
        file = new JMenu("File");
        load = new JMenuItem("Load"); load.addActionListener((ActionEvent e) -> loadFile());
        save = new JMenuItem("Convert to JSON"); save.addActionListener((ActionEvent e) -> saveFile());
        file.add(load);
        file.add(save);
        menuBar.add(file);

        stats = new JMenu("Stats");
        oldest = new JMenuItem("Oldest"); oldest.addActionListener((ActionEvent e) -> getOldest());
        averageAge = new JMenuItem("Avg. Age"); averageAge.addActionListener((ActionEvent e) -> getAverage());
        commonBirth = new JMenuItem("Com. Birth"); commonBirth.addActionListener((ActionEvent e) -> getCommonBirth());
        commonAddress = new JMenuItem("Com. Address"); commonAddress.addActionListener((ActionEvent e) -> getCommonAddress());
        commonCity = new JMenuItem("Com. City"); commonCity.addActionListener((ActionEvent e) -> getCommonCity());
        genderProp = new JMenuItem("Gender Prop."); genderProp.addActionListener((ActionEvent e) -> getGenderProp());
        stats.add(oldest);
        stats.add(averageAge);
        stats.add(commonBirth);
        stats.add(commonAddress);
        stats.add(commonCity);
        stats.add(genderProp);
        menuBar.add(stats);
    }

    private void loadFile()
    {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            String selectPath = file.getPath();
            model.readFile(selectPath);
            tableModel = new DefaultTableModel(model.getStringPatientList(), model.getDataFrame().getStringColumnNames());
            table.setModel(tableModel);
            tableModel.fireTableDataChanged();
        }
    }

    private void saveFile()
    {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = fc.getSelectedFile();
            try
            {
                String json = model.writeToJSON(model.getPatientList());
                BufferedWriter writer = new BufferedWriter(new FileWriter(file + ".json"));
                writer.write(json);
                writer.close();
                JOptionPane.showMessageDialog(save,"File Saved!");
            }
            catch (IOException exp)
            {
                JOptionPane.showMessageDialog(this, "Unable to save the file", "File Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getOldest(){
        try {
            String names = model.getOldest();
            JOptionPane.showMessageDialog(this, "The oldest people is(are): " + names);
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void getAverage(){
        try {
            int averageAge = model.getAverageAge();
            JOptionPane.showMessageDialog(this, "The average age is: " + averageAge);
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void getCommonBirth(){
        try {
            String commonYear = model.getCommonYear();
            if (commonYear != null)
                JOptionPane.showMessageDialog(this, "The most common birth year is: " + commonYear);
            else
                JOptionPane.showMessageDialog(this, "Not Available!");
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void getCommonAddress(){
        try {
            String commonAddress = model.getCommonAddress();
            if (commonAddress!= null)
                JOptionPane.showMessageDialog(this, "The most common address is: " + commonAddress);
            else
                JOptionPane.showMessageDialog(this, "Not Available!");
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void getCommonCity(){
        try {
            String commonCity = model.getCommonCity();
            if (commonCity != null)
                JOptionPane.showMessageDialog(this, "The most common city is: " + commonCity);
            else
                JOptionPane.showMessageDialog(this, "Not Available!");
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void getGenderProp(){
        try {
            String prop = model.getGenderProportion();
                JOptionPane.showMessageDialog(this, "Gender proportion: " + prop);
        }
        catch (Exception exception){
            JOptionPane.showMessageDialog(this, "Not Available!");
        }
    }

    private void createTablePanel(){
        tableModel = new DefaultTableModel(null, columnNames);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true);
        tablePanel = new JScrollPane(table);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void hideTableColumn(JTable table, int column)
    {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setMinWidth(0);
        tc.setMaxWidth(0);
    }

    private void showTableColumn(JTable table, int column)
    {
        int width = 80;
        TableColumnModel columns = table.getColumnModel();
        TableColumn column_id_data = columns.getColumn(column);

        column_id_data.setMaxWidth(width+100);
        column_id_data.setPreferredWidth(width);
        column_id_data.setMinWidth(width);

        TableColumn column_id_header = table.getTableHeader().getColumnModel().getColumn(column);
        column_id_header.setMaxWidth(width+100);
        column_id_header.setPreferredWidth(width);
        column_id_header.setMinWidth(width);

    }

    private void createCheckBoxPanel() {
        CheckBoxPanel = new JPanel(new GridLayout(20, 1, 10, 3));
        CheckBoxPanel.setBorder(BorderFactory.createEtchedBorder());
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        id = new JCheckBox("ID", true); checkBoxes.add(id);
        id.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 0);
            } else {
                hideTableColumn(table, 0);
            }
        });
        birthdate = new JCheckBox("BIRTHDATE", true); checkBoxes.add(birthdate);
        birthdate.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 1);
            } else {
                hideTableColumn(table, 1);
            }
        });
        deathdate = new JCheckBox("DEATHDATE", true); checkBoxes.add(deathdate);
        deathdate.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 2);
            } else {
                hideTableColumn(table, 2);
            }
        });
        ssn = new JCheckBox("SSN", true); checkBoxes.add(ssn);
        ssn.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 3);
            } else {
                hideTableColumn(table, 3);
            }
        });
        drivers = new JCheckBox("DRIVERS", true); checkBoxes.add(drivers);
        drivers.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 4);
            } else {
                hideTableColumn(table, 4);
            }
        });
        passport = new JCheckBox("PASSPORT", true); checkBoxes.add(passport);
        passport.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 5);
            } else {
                hideTableColumn(table, 5);
            }
        });
        prefix = new JCheckBox("PREFIX", true); checkBoxes.add(prefix);
        prefix.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 6);
            } else {
                hideTableColumn(table, 6);
            }
        });
        first = new JCheckBox("FIRST", true); checkBoxes.add(first);
        first.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 7);
            } else {
                hideTableColumn(table, 7);
            }
        });
        last = new JCheckBox("LAST", true); checkBoxes.add(last);
        last.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 8);
            } else {
                hideTableColumn(table, 8);
            }
        });
        suffix = new JCheckBox("SUFFIX", true); checkBoxes.add(suffix);
        suffix.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 9);
            } else {
                hideTableColumn(table, 9);
            }
        });
        maiden = new JCheckBox("MAIDEN", true); checkBoxes.add(maiden);
        maiden.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 10);
            } else {
                hideTableColumn(table, 10);
            }
        });
        marital = new JCheckBox("MARTIAL", true); checkBoxes.add(marital);
        marital.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 11);
            } else {
                hideTableColumn(table, 11);
            }
        });
        race = new JCheckBox("RACE", true); checkBoxes.add(race);
        race.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 12);
            } else {
                hideTableColumn(table, 12);
            }
        });
        ethnicity = new JCheckBox("ETHNICITY", true); checkBoxes.add(ethnicity);
        ethnicity.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 13);
            } else {
                hideTableColumn(table, 13);
            }
        });
        gender = new JCheckBox("GENDER", true); checkBoxes.add(gender);
        gender.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 14);
            } else {
                hideTableColumn(table, 14);
            }
        });
        birthplace = new JCheckBox("BIRTHPLACE", true); checkBoxes.add(birthplace);
        birthplace.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 15);
            } else {
                hideTableColumn(table, 15);
            }
        });
        address = new JCheckBox("ADDRESS", true); checkBoxes.add(address);
        address.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 16);
            } else {
                hideTableColumn(table, 16);
            }
        });
        city = new JCheckBox("CITY", true); checkBoxes.add(city);
        city.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 17);
            } else {
                hideTableColumn(table, 17);
            }
        });
        state = new JCheckBox("STATE", true); checkBoxes.add(state);
        state.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 18);
            } else {
                hideTableColumn(table, 18);
            }
        });
        zip = new JCheckBox("ZIP", true); checkBoxes.add(zip);
        zip.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                showTableColumn(table, 19);
            } else {
                hideTableColumn(table, 19);
            }
        });

        for (JCheckBox checkBox : checkBoxes)  CheckBoxPanel.add(checkBox);
    }

    private void createMidPanel()
    {
        midPanel = new JPanel(new BorderLayout());
        midPanel.add(CheckBoxPanel, BorderLayout.WEST);
        midPanel.add(tablePanel, BorderLayout.CENTER);
        add(midPanel, BorderLayout.CENTER);
    }

    private static class JTextFieldHintListener implements FocusListener {
        private final String hintText;
        private final JTextField textField;
        public JTextFieldHintListener(JTextField jTextField,String hintText) {
            this.textField = jTextField;
            this.hintText = hintText;
            jTextField.setText(hintText);
            jTextField.setForeground(Color.GRAY);
        }

        @Override
        public void focusGained(FocusEvent e) {
            String temp = textField.getText();
            if(temp.equals(hintText)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }

        }

        @Override
        public void focusLost(FocusEvent e) {
            String temp = textField.getText();
            if(temp.equals("")) {
                textField.setForeground(Color.GRAY);
                textField.setText(hintText);
            }
        }
    }

    private void search(){
        if(table == null) {
            return;
        }
        String choice = (String)searchList.getSelectedItem();
        if (choice == null) {
            sorter = (TableRowSorter<TableModel>) table.getRowSorter();
            if (sorter == null) {
                sorter = new TableRowSorter<>(table.getModel());
                table.setRowSorter(sorter);
            }
            String text = searchKeyword.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(text));
            }
        } else {
            int i = model.getDataFrame().getColumnNames().indexOf(choice);
            sorter = (TableRowSorter<TableModel>) table.getRowSorter();
            if (sorter == null) {
                sorter = new TableRowSorter<>(table.getModel());
                table.setRowSorter(sorter);
            }
            String text = searchKeyword.getText();
            if (text.length() == 0) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter(text, i));
            }
        }
    }


    private void createSearchPanel(){
        searchKeyword = new JTextField();
        searchKeyword.addFocusListener(new JTextFieldHintListener(searchKeyword,
                "Please choose a column and specify a word to match... "));
        searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e) -> search());
        searchList = new JComboBox<>(searchStrings);
        searchList.setPreferredSize(new Dimension(95, 30));
        searchList.setSelectedItem(null);
        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchPanel.add(searchList,BorderLayout.WEST);
        searchPanel.add(searchKeyword, BorderLayout.CENTER);
        searchPanel.add(searchButton,BorderLayout.EAST);
        add(searchPanel, BorderLayout.SOUTH);
    }

    private void createGUI(){
        createMenuBar();
        createCheckBoxPanel();
        createTablePanel();
        createMidPanel();
        createSearchPanel();
    }

    private void init(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600,900);
        setLocationRelativeTo(null);
        createGUI();
        setVisible(true);
    }

    public GUI (Controller controller){
        super("Patient Management App");
        model = controller.getModel();
        this.init();
    }
}
