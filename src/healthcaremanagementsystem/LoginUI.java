package healthcaremanagementsystem;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginUI extends javax.swing.JFrame {
    public LoginUI() {
        /* load credentials from DB; if possible */
        credentials = new Credentials();
        credentials.loadData();
        
        /* Init UI components */
        initComponents();
        
        /* Set close behavoir */
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfUser = new javax.swing.JTextField();
        loginButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfPass = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(238, 238, 238));
        setLocation(new java.awt.Point(100, 100));
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(280, 2147483647));
        setMinimumSize(new java.awt.Dimension(280, 230));
        setPreferredSize(new java.awt.Dimension(280, 230));

        tfUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfUser.setForeground(new java.awt.Color(108, 110, 114));
        tfUser.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfUser.setText("Username");
        tfUser.setPreferredSize(new java.awt.Dimension(120, 20));
        tfUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfUserFocusLost(evt);
            }
        });

        loginButton.setBackground(new java.awt.Color(80, 160, 90));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new java.awt.Dimension(70, 24));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("HealthCare Management");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin Panel");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/diu.png"))); // NOI18N

        tfPass.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tfPass.setForeground(new java.awt.Color(108, 110, 114));
        tfPass.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfPass.setText("00000000");
        tfPass.setPreferredSize(new java.awt.Dimension(120, 21));
        tfPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfPassFocusLost(evt);
            }
        });
        tfPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPassKeyPressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/speedout.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(20, 20, 20))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfPass, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                                    .addComponent(tfUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(loginButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(616, 239));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tfUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserFocusGained
        if (tfUser.getText().equals("Username")) {
            tfUser.setText("");
        }
    }//GEN-LAST:event_tfUserFocusGained
    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        login();
    }//GEN-LAST:event_loginButtonMouseClicked
    private void tfUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfUserFocusLost
        if (tfUser.getText().equals("")) {
            tfUser.setText("Username");
        }
    }//GEN-LAST:event_tfUserFocusLost
    private void tfPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPassFocusGained
        if (tfPass.getText().equals("00000000")) {
            tfPass.setText("");
        }
    }//GEN-LAST:event_tfPassFocusGained
    private void tfPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPassFocusLost
        if (tfPass.getText().equals("")) {
            tfPass.setText("00000000");
        }
    }//GEN-LAST:event_tfPassFocusLost
    private void tfPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPassKeyPressed
        Character key = evt.getKeyChar();
        boolean enterIsPressed = key.compareTo('\n') == 0;
        if (enterIsPressed) {
            loginButton.requestFocus();
            login();
        }
    }//GEN-LAST:event_tfPassKeyPressed

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginButtonActionPerformed
    
    /* Check login credentials */
    private void login() {
        /* IF CORRECT */
        if (credentials.check(tfUser.getText(), tfPass.getText())) {
            System.out.println("Login Status: Successful login.");
            Healthcaremanagementsystem.showManagementSystem();  // open UI
            exit();                                             // exit current
        } else { /* IF WRONG */
            System.out.println("Login Status: Incorrect username or password!");
            JOptionPane.showMessageDialog(null,
                                          "Incorrect username or password",
                                          "Login Error",
                                          JOptionPane.ERROR_MESSAGE);
            tfPass.setText("");
            tfPass.requestFocus();
        }
    }
    
    /* Exit function */
    private void exit() {
        super.setVisible(false);
        super.dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField tfPass;
    private javax.swing.JTextField tfUser;
    // End of variables declaration//GEN-END:variables

    /* Parameters */
    Credentials credentials;
}
