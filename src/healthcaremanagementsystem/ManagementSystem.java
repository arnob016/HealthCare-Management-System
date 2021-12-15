package healthcaremanagementsystem;

import java.util.ArrayList;

public class ManagementSystem {
    public ManagementSystem() {}

    /* Load data from DB (saved file on disk */
    public void loadData() {
        ManagementSystem ms;        /* temp management system */
        
        /* Load data into temp management system */
        ms = (ManagementSystem)DB.loadData(ManagementSystem.class, DB.DBFILE);
        
        /* Check if data was loaded */
        if (ms != null) {
            this.doctor = ms.doctor;
            this.nPatient = ms.nPatient;
            this.ePatient = ms.ePatient;
            this.appointment = ms.appointment;
        }
    }
    
    /* Save all data */
    public void saveData() {
        DB.saveData(this, DB.DBFILE);
    }
    
    /* Paramters */
    ArrayList<Appointment> appointment = new ArrayList<>();     // appointments
    ArrayList<Doctor> doctor = new ArrayList<>();               // doctors
    ArrayList<EmergencyPatient> ePatient = new ArrayList<>();   // ePatients
    ArrayList<NormalPatient> nPatient = new ArrayList<>();      // nPatients
}
