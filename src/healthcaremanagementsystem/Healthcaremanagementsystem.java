package healthcaremanagementsystem;

public class Healthcaremanagementsystem {
    static LoginUI msLUI;               // LoginUI object
    static ManagementSystemUI msUI;     // ManagementSystemUI object
    
    public static void main(String[] args) {
        showLoginWindow();              // Show login window
    }
    
    public static void showLoginWindow() {
        msLUI = new LoginUI();
    }
    
    public static void showManagementSystem() {
        msUI = new ManagementSystemUI();
    }
}
