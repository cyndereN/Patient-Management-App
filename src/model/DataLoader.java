package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

    public DataFrame dataLoadedToDataFrame(String filePath){
        DataFrame patients = new DataFrame();
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            String line = buffer.readLine();
            int row = -1; // Flag of loading process, row[-1] are names
            while (line != null) {
                String[] data = line.split(",", -1); // Empty string at end of line won't be discarded
                patients.loadDataInRowsHelper(data, row);
                row += 1;
                line = buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return patients;
    }

    private Patient loadDataToPatientListHelper(String[] data) {
        String id = data[0];
        String birthdate = data[1];
        String deathdate = data[2];
        String ssn = data[3];
        String drivers = data[4];
        String passport = data[5];
        String prefix = data[6];
        String first = data[7];
        String last = data[8];
        String suffix = data[9];
        String maiden = data[10];
        String marital = data[11];
        String race = data[12];
        String ethnicity = data[13];
        String gender = data[14];
        String birthplace = data[15];
        String address = data[16];
        String city = data[17];
        String state = data[18];
        String zip = data[19];

        Patient patient = new Patient(id, birthdate, deathdate, ssn, drivers, passport,
                prefix, first, last, suffix, maiden,
                marital, race, ethnicity, gender,
                birthplace, address, city, state, zip);

        return patient;
    }

    public ArrayList<Patient> loadDataToPatientList(String filePath) {
        ArrayList<Patient> patients = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath))) {
            String line = buffer.readLine();
            line = buffer.readLine();
            while (line != null) {
                String[] data = line.split(",", -1);
                Patient patient = loadDataToPatientListHelper(data);
                patients.add(patient);
                line = buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return patients;
    }

}
