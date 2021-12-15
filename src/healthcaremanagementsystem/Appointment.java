package healthcaremanagementsystem;

public class Appointment {
    /* Empty appointment constructor */
    public Appointment() {
        this.time = "";         // empty time
        this.midday = "";       // empty midday
        this.date = "";         // empty date
        this.doctorID = "";     // empty doctor ID
        this.patientID = "";    // empty patient ID
    }
    
    /* Appointment constructor taking another appointment as an arg */
    public Appointment(Appointment that) {
        copy(that);
    }
    
    /* Appointment constructor taking an index to appointment in UI */
    public Appointment(int index) {
        this(ManagementSystemUI.msDB.appointment.get(index));
        this.index = index;     // copy current index to keep track in UI
    }
    
    /* Copy appointment to current appointment */
    public final void copy(Appointment that) {
        this.time = that.time;
        this.midday = that.midday;
        this.date = that.date;
        this.doctorID = that.doctorID;
        this.patientID = that.patientID;
    }
    
    @Override
    public String toString() {
        String status = "";
        if (doctorID.equals("null")) status = "[?] ";
        return status + patientID + " - " + date + " " + time + " " + midday;
    }
    
    /* Parameters */
    int index = -1;                                     // Index
    String time, midday, date, doctorID, patientID;     // Main parameters
}