package healthcaremanagementsystem;

public class EmergencyPatient extends NormalPatient {
    public EmergencyPatient() {
        this.room = "";     // empty room
    }
    
    /* Copy that ePatient into this ePatient */
    public final void copy(EmergencyPatient that) {
        this.ID = that.ID;
        this.name = that.name;
        this.room = that.room;
        this.phone = that.phone;
        this.gender = that.gender;
        this.address = that.address;
        this.payment = that.payment;
        this.symptom = that.symptom;
        this.diagnosis = that.diagnosis;
    }
    
    /* Emergency Patient Constructor with another one as an arg */
    public EmergencyPatient(EmergencyPatient that) {
        copy(that);
    }
    
    /* Emergency Patient Constructor with flag and index as args */
    public EmergencyPatient(boolean flag, int index) {
        if (flag) copy(ManagementSystemUI.msDB.ePatient.get(index));
        else copy(ManagementSystemUI.msDB.nPatient.get(index));
        this.index = index;     // copy index to keep track in UI
    }
    
    /* Parameters */
    String room;
}
