package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONWriter {

    private String basicJSON(String str, String val) {
        return "\"" + str + "\"" + ":" + "\"" + val + "\"";
    }

    public String patientJSON(Patient patient) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("id", patient.getId());
        m.put("birthdate", patient.getBirthdate());
        m.put("deathdate", patient.getDeathdate());
        m.put("ssn", patient.getSsn());
        m.put("drivers", patient.getDrivers());
        m.put("passport", patient.getPassport());
        m.put("prefix", patient.getPrefix());
        m.put("first", patient.getFirst());
        m.put("last", patient.getLast());
        m.put("suffix", patient.getSuffix());
        m.put("maiden", patient.getMaiden());
        m.put("marital", patient.getMarital());
        m.put("race", patient.getRace());
        m.put("ethicity", patient.getEthnicity());
        m.put("gender", patient.getGender());
        m.put("birthplace", patient.getBirthplace());
        m.put("address", patient.getAddress());
        m.put("city", patient.getCity());
        m.put("state", patient.getState());
        m.put("zip", patient.getZip());

        StringBuilder output = new StringBuilder();
        output.append("{");
        output.append("\n");
        for (Map.Entry<String, String> entry : m.entrySet()) {
            output.append(basicJSON(entry.getKey(), entry.getValue()));
            if (!entry.getKey().equals("zip")) {
                output.append(",");
            }
            output.append("\n");
        }
        output.append("}");

        return output.toString();
    }

    public String patientsJSON(ArrayList<Patient> patientsList) {
        StringBuilder output = new StringBuilder();
        int cnt = 0;
        output.append("{");
        output.append("\n");
        output.append("\"");
        output.append("patients");
        output.append("\"");
        output.append(":");
        output.append("[");
        output.append("\n");
        for (Patient p : patientsList) {
            output.append(patientJSON(p));
            cnt++;
            if (cnt < patientsList.size()) {
                output.append(",");
            }
            output.append("\n");
        }
        output.append("]");
        output.append("\n");
        output.append("}");
        return output.toString();
    }
}
