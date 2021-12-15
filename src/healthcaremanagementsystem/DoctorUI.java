package healthcaremanagementsystem;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DoctorUI extends javax.swing.JFrame {

    public DoctorUI() {
        this(true, new Doctor());
        /* Enable edit for new doctor */

    }

    public DoctorUI(boolean editFlag, Doctor doctor) {
        initComponents();

        /* Show jForm window and set default Exit button behavior */
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DoctorUI.doctor = doctor;

        loadData();

        if (!editFlag) {
            setEnableAll(false);
            /* Disable edit all */
            jlTitle.requestFocus();
            /* remove focus from any jTextFields */
        } else {
            chbEdit.doClick();
            /* Enable edit all */
            clear();
            /* Clear form for new entry */
        }

    }

    private int FormValidity() {
        String curID = ManagementSystemUI.msDB.doctor.get(doctor.index).ID;

        /* Checking form to be free of errors */
        if (doctor.index == -1) {
            return FORM_E_DB;
        } else if (tfID.getText().equals(CLEARED_ID)) {
            return FORM_E_ID;
        } else if (!curID.equals(tfID.getText())
                && !ManagementSystemUI.uniqueID(tfID.getText())) {
            return FORM_E_UID;
        } else if (tfName.getText().equals(CLEARED_NAME)) {
            return FORM_E_NAME;
        } else if (tfAddress.getText().equals(CLEARED_ADDRESS)) {
            return FORM_E_ADDRESS;
        } else if (tfPhone.getText().equals(CLEARED_PHONE)) {
            return FORM_E_PHONE;
        } else if (tfSpeciality.getText().equals(CLEARED_SPECIALITY)) {
            return FORM_E_SPECIALITY;
        } else {
            return FORM_E_OK;
        }
    }

    private void clear() {
        tfID.setText(CLEARED_ID);
        tfName.setText(CLEARED_NAME);
        tfAddress.setText(CLEARED_ADDRESS);
        tfPhone.setText(CLEARED_PHONE);
        tfSpeciality.setText(CLEARED_SPECIALITY);
    }

    private void setEnableAll(boolean flag) {
        /* Change enable status for all components */
 /* All doctor jTextFields for data */
        tfID.setEditable(flag);
        tfName.setEditable(flag);
        tfAddress.setEditable(flag);
        tfPhone.setEditable(flag);
        tfSpeciality.setEditable(flag);

        /* Clear and save jButtons */
        bClear.setEnabled(flag);
        bSave.setEnabled(flag);

        /* Change title */
        String formStatus;
        if (flag) {
            formStatus = "(Edit)";
        } else {
            formStatus = "(View Only)";
        }
        jlTitle.setText("Doctor " + formStatus);
    }

    private void updateData() {
        doctor.ID = tfID.getText();
        doctor.name = tfName.getText();
        doctor.address = tfAddress.getText();
        doctor.phone = tfPhone.getText();
        doctor.speciality = tfSpeciality.getText();
    }

    private void loadData() {
        tfID.setText(doctor.ID);
        tfName.setText(doctor.name);
        tfAddress.setText(doctor.address);
        tfPhone.setText(doctor.phone);
        tfSpeciality.setText(doctor.speciality);
    }

    private void saveForm() {
        /* Update doctor components by extracting it from form */
        updateData();

        /* Update item (DB) */
        ManagementSystemUI.msDB.doctor.get(doctor.index).copy(doctor);

        /* Update item (visually) */
        ManagementSystemUI.doctorsList.set(doctor.index, doctor.toString());

        /* Close form window */
        super.dispose();
        log("Doctor form is saved!");

        /* Save changes flag */
        ManagementSystemUI.mustSave = true;

        /* Show info message */
        JOptionPane.showMessageDialog(null,
                "Successful saving",
                "Save Process Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showErrorSaving(String msg) {
        /* Show error message */
        JOptionPane.showMessageDialog(null,
                msg,
                "Save Process Error",
                JOptionPane.ERROR_MESSAGE);
    }

    static private void log(String str) {
        System.out.println(str);
        /* Out to console */
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlTitle = new javax.swing.JLabel();
        jlID = new javax.swing.JLabel();
        jlName = new javax.swing.JLabel();
        jlAddress = new javax.swing.JLabel();
        jlPhone = new javax.swing.JLabel();
        jlSpeciality = new javax.swing.JLabel();
        tfID = new javax.swing.JTextField();
        tfName = new javax.swing.JTextField();
        tfAddress = new javax.swing.JTextField();
        tfPhone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tfSpeciality = new javax.swing.JTextArea();
        chbEdit = new javax.swing.JCheckBox();
        bClear = new javax.swing.JButton();
        bSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Doctor");
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(320, 348));
        setPreferredSize(new java.awt.Dimension(320, 348));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jlTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitle.setText("Doctor");

        jlID.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlID.setText("ID:");

        jlName.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlName.setText("Name:");

        jlAddress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlAddress.setText("Address:");

        jlPhone.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlPhone.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlPhone.setText("Phone:");

        jlSpeciality.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlSpeciality.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlSpeciality.setText("Speciality:");

        tfID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfID.setText("xxSxxxx");
        tfID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfIDFocusGained(evt);
            }
        });

        tfName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfName.setText("first middle last");
        tfName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfNameFocusGained(evt);
            }
        });
        tfName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfNameActionPerformed(evt);
            }
        });

        tfAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfAddress.setText("apt., st., city");
        tfAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfAddressFocusGained(evt);
            }
        });

        tfPhone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfPhone.setText("01x-xxxx-xxx");
        tfPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfPhoneFocusGained(evt);
            }
        });

        tfSpeciality.setColumns(20);
        tfSpeciality.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfSpeciality.setRows(5);
        tfSpeciality.setText("specialities");
        tfSpeciality.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfSpecialityFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(tfSpeciality);

        chbEdit.setText("Edit");
        chbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbEditActionPerformed(evt);
            }
        });

        bClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bClear.setText("Clear");
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });

        bSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bSave.setText("Save");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jlSpeciality, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                        .addComponent(jlPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                        .addComponent(jlAddress, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                                        .addComponent(jlName, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                                    .addComponent(chbEdit)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jlID, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(tfID)
                            .addComponent(tfName)
                            .addComponent(tfAddress)
                            .addComponent(tfPhone)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlID)
                    .addComponent(tfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlName)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlAddress)
                    .addComponent(tfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPhone)
                    .addComponent(tfPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlSpeciality)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chbEdit)
                    .addComponent(bClear)
                    .addComponent(bSave))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(469, 329));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbEditActionPerformed
        setEnableAll(chbEdit.isSelected());
        /* Based on Edit jCheckBox status */
    }//GEN-LAST:event_chbEditActionPerformed
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        /* Get last doctor index */
        int index = ManagementSystemUI.msDB.doctor.size() - 1;

        /* Check if current opened doctor is last */
        if (index == doctor.index) {
            if (ManagementSystemUI.doctorsList.get(index).equals("<New Doctor Form>")) {
                ManagementSystemUI.msDB.doctor.remove(index);
                ManagementSystemUI.doctorsList.remove(index);
                Healthcaremanagementsystem.msUI.updateDoctorsNumber();
            }
        }
    }//GEN-LAST:event_formWindowClosed
    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        /* Make user confirm */
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Are you sure?",
                "Choose",
                JOptionPane.YES_NO_OPTION);

        /* Check user's selection */
        if (selectedOption == JOptionPane.YES_OPTION) {
            clear();
            log("Doctor form is cleared!");
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_bClearActionPerformed
    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        /* Make sure appointment form is valid */
        switch (FormValidity()) {
            case FORM_E_OK:
                saveForm();
                break;
            case FORM_E_DB:
                showErrorSaving(EMSG_DB);
                break;
            case FORM_E_ID:
                showErrorSaving(EMSG_ID);
                break;
            case FORM_E_UID:
                showErrorSaving(EMSG_UID);
                break;
            case FORM_E_NAME:
                showErrorSaving(EMSG_NAME);
                break;
            case FORM_E_ADDRESS:
                showErrorSaving(EMSG_ADDRESS);
                break;
            case FORM_E_PHONE:
                showErrorSaving(EMSG_PHONE);
                break;
            case FORM_E_SPECIALITY:
                showErrorSaving(EMSG_SPECIALITY);
                break;
        }
    }//GEN-LAST:event_bSaveActionPerformed

    /* jTextFields focus gained events */
    private void tfIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfIDFocusGained
        tfID.selectAll();
    }//GEN-LAST:event_tfIDFocusGained
    private void tfNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNameFocusGained
        tfName.selectAll();
    }//GEN-LAST:event_tfNameFocusGained
    private void tfAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAddressFocusGained
        tfAddress.selectAll();
    }//GEN-LAST:event_tfAddressFocusGained
    private void tfPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPhoneFocusGained
        tfPhone.selectAll();
    }//GEN-LAST:event_tfPhoneFocusGained
    private void tfSpecialityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfSpecialityFocusGained
        tfSpeciality.selectAll();
    }//GEN-LAST:event_tfSpecialityFocusGained

    private void tfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClear;
    private javax.swing.JButton bSave;
    private javax.swing.JCheckBox chbEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlAddress;
    private javax.swing.JLabel jlID;
    private javax.swing.JLabel jlName;
    private javax.swing.JLabel jlPhone;
    private javax.swing.JLabel jlSpeciality;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextArea tfSpeciality;
    // End of variables declaration//GEN-END:variables

    /* Form variables */
    static Doctor doctor;

    /* Form errors */
    private final static int FORM_E_OK = 0;
    private final static int FORM_E_DB = -1;
    private final static int FORM_E_ID = -2;
    private final static int FORM_E_UID = -3;
    private final static int FORM_E_NAME = -4;
    private final static int FORM_E_ADDRESS = -5;
    private final static int FORM_E_PHONE = -6;
    private final static int FORM_E_SPECIALITY = -7;

    /* Cleared text */
    private final static String CLEARED_ID = "xxXxxxx";
    private final static String CLEARED_NAME = "First Middle Last";
    private final static String CLEARED_ADDRESS = "apt., st., city";
    private final static String CLEARED_PHONE = "01x xxxx xxx";
    private final static String CLEARED_SPECIALITY = "No speciality added yet.";

    /* Error messages */
    private final static String EMSG_DB = "DB Error!";
    private final static String EMSG_ID = "ID Error";
    private final static String EMSG_UID = "ID is already taken!";
    private final static String EMSG_NAME = "Name Error!";
    private final static String EMSG_ADDRESS = "Address Error!";
    private final static String EMSG_PHONE = "Phone Error!";
    private final static String EMSG_SPECIALITY = "Speciality Error!";
}
