package healthcaremanagementsystem;

public class NormalPatient {
    public NormalPatient() {
        this.ID = "";               // empty ID            
        this.name = "";             // empty name
        this.address = "";          // empty address
        this.phone = "";            // empty phone
        this.gender = "";           // empty gender
        this.payment = "";          // empty payment
        this.symptom = "";          // empty symptoms
        this.diagnosis = "";        // empty diagnosis      
    }
    
    /* copy that patient into this patient */ 
    public final void copy(NormalPatient that) {
        this.ID = that.ID;
        this.name = that.name;
        this.address = that.address;
        this.phone = that.phone;
        this.gender = that.gender;
        this.payment = that.payment;
        this.symptom = that.symptom;
        this.diagnosis = that.diagnosis;
    }

    /* Patient constructor with another patient */ 
    public NormalPatient(NormalPatient that) {
        copy(that);
    }
    
    /* Patient constructor with index */ 
    public NormalPatient(int index) {
        this(ManagementSystemUI.msDB.nPatient.get(index));
        this.index = index;
    }
    
    @Override
    public String toString() {
        return ID + " - " + name;
    }
    
    /* Parameters */
    int index = -1;             // index in UI
    String ID, name, address, phone, gender, payment, symptom, diagnosis;
}
