package model;

import java.util.*;

public class Model {
    private DataLoader dataLoader;
    private DataFrame dataFrame;
    private ArrayList<Patient> patientList;
    private String filePath;
    private JSONWriter jsonWriter;

    public Model(){
        dataLoader = new DataLoader();
        dataFrame = new DataFrame();
        patientList = new ArrayList<>();
        filePath = null;
        jsonWriter = new JSONWriter();
    }

    public void readFile(String filePath){
        setFilePath(filePath);
        this.dataFrame = dataLoader.dataLoadedToDataFrame(filePath);
        this.patientList = dataLoader.loadDataToPatientList(filePath);
    }

    public ArrayList<Patient> getPatientList(){
        return patientList;
    }

    public String[][] getStringPatientList(){
        String[][] patientList = new String[this.patientList.size()][20];
        for (int i=0 ; i<this.patientList.size() ; i++){
            patientList[i][0] = this.patientList.get(i).getId();
            patientList[i][1] = this.patientList.get(i).getBirthdate();
            patientList[i][2] = this.patientList.get(i).getDeathdate();
            patientList[i][3] = this.patientList.get(i).getSsn();
            patientList[i][4] = this.patientList.get(i).getDrivers();
            patientList[i][5] = this.patientList.get(i).getPassport();
            patientList[i][6] = this.patientList.get(i).getPrefix();
            patientList[i][7] = this.patientList.get(i).getFirst();
            patientList[i][8] = this.patientList.get(i).getLast();
            patientList[i][9] = this.patientList.get(i).getSuffix();
            patientList[i][10] = this.patientList.get(i).getMaiden();
            patientList[i][11] = this.patientList.get(i).getMarital();
            patientList[i][12] = this.patientList.get(i).getRace();
            patientList[i][13] = this.patientList.get(i).getEthnicity();
            patientList[i][14] = this.patientList.get(i).getGender();
            patientList[i][15] = this.patientList.get(i).getBirthplace();
            patientList[i][16] = this.patientList.get(i).getAddress();
            patientList[i][17] = this.patientList.get(i).getCity();
            patientList[i][18] = this.patientList.get(i).getState();
            patientList[i][19] = this.patientList.get(i).getZip();
        }
        return patientList;
    }

    public DataFrame getDataFrame() {
        return dataFrame;
    }

    public String writeToJSON(ArrayList<Patient> patients){
        return jsonWriter.patientsJSON(patients);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private int getAge(Patient patient){
        int age;
        String birthdate = patient.getBirthdate();
        String deathdate = patient.getDeathdate();
        String birthyear = birthdate.substring(0,4);
        int intBirthYear = Integer.parseInt(birthyear);
        if (!deathdate.equals("")){
            String deathyear = deathdate.substring(0,4);
            int intDeathYear = Integer.parseInt(deathyear);
            age = intDeathYear - intBirthYear;
        } else {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            age = currentYear - intBirthYear;
        }
        return age;
    }

    private ArrayList<Integer> getAgeList(ArrayList<Patient> patients){
        ArrayList<Integer> ageList = new ArrayList<>();
        for(Patient patient : patients){
            ageList.add(getAge(patient));
        }
        return ageList;
    }

    public String getOldest(){
        ArrayList<Integer> ageList = getAgeList(patientList);
        int maxAge = Collections.max(ageList);
        StringBuilder name = new StringBuilder();
        for(Patient patient : patientList){
            if (getAge(patient) == maxAge)
                name.append(patient.getFirst()).append(" ").append(patient.getLast()).append("\n");
        }
        return name.toString();
    }

    public int getAverageAge(){
        ArrayList<Integer> ageList = getAgeList(patientList);
        int ageTotal = 0;
        for(int age : ageList) {
            ageTotal += age;
        }
        return ageTotal/patientList.size();
    }

    private HashMap<String, Integer> getBirthyearMap(ArrayList<Patient> patients){
        HashMap<String, Integer> birthyearMap = new HashMap<>();
        for (Patient patient: patients) {
            String birthYear = patient.getBirthdate().substring(0,4);
            if(birthyearMap.containsKey(birthYear)){
                birthyearMap.put(birthYear, birthyearMap.get(birthYear) + 1);
            } else {
                birthyearMap.put(birthYear, 1);
            }
        }
        return birthyearMap;
    }

    // Find most common key of map
    private <T> Map.Entry <T, Integer> getMostCommonEntry(Map<T, Integer> map) {
        Map.Entry<T, Integer> mostCommonEntry = null;
        for (Map.Entry<T, Integer> entry : map.entrySet()) {
            mostCommonEntry = entry;
        }
        return mostCommonEntry;
    }

    public String getCommonYear() {
        HashMap<String, Integer> birthYearMap = getBirthyearMap(patientList);
        Map.Entry<String, Integer> mostCommonYear = getMostCommonEntry(birthYearMap);
        return mostCommonYear == null ? null : mostCommonYear.getKey();
    }

    public String getCommonAddress() {
        Map<String, Integer> addressMap = new HashMap<>();
        for(Patient patient : patientList) {
            String address = patient.getAddress();
            addressMap.computeIfPresent(address, (key, value) -> value++);
            addressMap.putIfAbsent(address, 1);
        }
        Map.Entry<String, Integer> mostCommonCity = getMostCommonEntry(addressMap);
        return mostCommonCity == null ? null : mostCommonCity.getKey();
    }

    public String getCommonCity() {
        Map<String, Integer> cityMap = new HashMap<>();
        for(Patient patient : patientList) {
            String city = patient.getCity();
            cityMap.computeIfPresent(city, (key, value) -> value++);
            cityMap.putIfAbsent(city, 1);
        }
        Map.Entry<String, Integer> mostCommonCity = getMostCommonEntry(cityMap);
        return mostCommonCity == null ? null : mostCommonCity.getKey();
    }

    public String getGenderProportion(){
        int m = 0;
        int f = 0;
        for (Patient patient : patientList) {
            String gender = patient.getGender();
            if (gender.equals("F"))  f += 1;
            else if (gender.equals("M"))  m += 1;
        }
        return "Male : Female = " + m + " : " + f;
    }
}
