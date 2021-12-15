package healthcaremanagementsystem;

public class Doctor {
    public Doctor() {
        this.ID = "";           // empty ID
        this.name = "";         // empty name
        this.address = "";      // empty address
        this.phone = "";        // empty phone
        this.speciality = "";   // empty speciality
    }

    /* Doctor constructor with a doctor as an arg */
    public Doctor(Doctor that) {
        copy(that);
    }
    
    /* Doctor constructor with an index as an arg */
    public Doctor(int index) {
        this(ManagementSystemUI.msDB.doctor.get(index));
        this.index = index;
    }
    
    /* Copy doctor into current doctor */
    public final void copy(Doctor that) {
        this.ID = that.ID;
        this.name = that.name;
        this.address = that.address;
        this.phone = that.phone;
        this.speciality = that.speciality;
    }
    
    @Override
    public String toString() {
        return ID + " - " + name;
    }
    
    /* Parameters */
    int index = -1;                                 // index to track UI
    String ID, name, address, phone, speciality;    // class components
}
