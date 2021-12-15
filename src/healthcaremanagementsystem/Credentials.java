package healthcaremanagementsystem;

public class Credentials {
    public Credentials() {}

    public void loadData() {
        Credentials c;      // temp credentials holder
        
        /* Load data */
        c = (Credentials)DB.loadData(Credentials.class, DB.DBADMIN);
        
        /* Check if c is loaded correctly */
        if (c != null) {
            System.out.println("DB Status: Credentials database was found.");
            this.user = c.user;
            this.pass = c.pass;
        } else {            
            System.out.print("DB Status: Database not found. ");
            System.out.println("Default admin credentials loaded.");
            this.user = "admin";
            this.pass = "admin";
        }
    }
    
    /* Check username and password */
    public boolean check(String user, String pass) {
        return this.user.equals(user) && this.pass.equals(pass);
    }
    
    /* Parameters */
    private String user, pass;
}
