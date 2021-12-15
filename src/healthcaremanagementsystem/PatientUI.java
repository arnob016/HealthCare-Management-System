package healthcaremanagementsystem;

import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PatientUI extends javax.swing.JFrame {
    static EmergencyPatient patient;
    static boolean isEmergent = false;
    
    public PatientUI() {
        this(true, false, new EmergencyPatient());
    }
    public PatientUI(boolean editFlag, boolean type, EmergencyPatient eP) {
        initComponents();               /* Initialize jForm components */
        
        /* Add radio buttons to corresponding groups */
        /* Type jRadioButtonGroup */
        rbgType.add(rbTypeN);
        rbgType.add(rbTypeE);
        
        /* Gender jRadioButtonGroup */
        rbgGender.add(rbGenderM);
        rbgGender.add(rbGenderF);
        
        /* Payment jRadioButtonGroup */
        rbgPayment.add(rbPaymentH);
        rbgPayment.add(rbPaymentC);
        rbgPayment.add(rbPaymentV);
        rbgPayment.add(rbPaymentI);
        
        /* Show jForm window and set default Exit button behavior */
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        PatientUI.patient = eP;
        isEmergent = type;
        
        if (isEmergent) {
            rbTypeE.doClick();
        }
        loadData();
        
        /* Check edit flag to enable/disable edit features (show/edit) */
        if (!editFlag) {
            setEnableAll(false);        /* Disable edit all */
            jlTitle.requestFocus();     /* remove focus from any jTextFields */
        } else {
            chbEdit.doClick();          /* Enable edit all */
            clear();                    /* Clear form for new entry */
        }
    }

    private int FormValidity() {
        String curID;
        if (isEmergent) {
            curID = ManagementSystemUI.msDB.ePatient.get(patient.index).ID;
            if (tfRoom.getText().equals(CLEARED_ROOM)) return FORM_E_ROOM;
        } else {
            curID = ManagementSystemUI.msDB.nPatient.get(patient.index).ID;
        }
        
        
        /* Checking form to be free of errors */
        if (patient.index == -1) return FORM_E_DB;
        else if (tfID.getText().equals(CLEARED_ID)) return FORM_E_ID;
        else if (!curID.equals(tfID.getText()) &&
                !ManagementSystemUI.uniqueID(tfID.getText())) return FORM_E_UID;
        else if (tfName.getText().equals(CLEARED_NAME)) return FORM_E_NAME;
        else if (tfAddress.getText().equals(CLEARED_ADDRESS)) return FORM_E_ADDRESS;
        else if (tfPhone.getText().equals(CLEARED_PHONE)) return FORM_E_PHONE;
        else if (taSymptoms.getText().equals(CLEARED_SYMPTOMS)) return FORM_E_SYMPTOMS;
        else if (taDiagnosis.getText().equals(CLEARED_DIAGNOSIS)) return FORM_E_DIAGNOSIS;
        else return FORM_E_OK;
    }
    private void updateData() {
        patient.ID = tfID.getText();
        patient.name = tfName.getText();
        patient.address = tfAddress.getText();
        patient.phone = tfPhone.getText();
        patient.gender = getButtonGroupValue(rbgGender);
        patient.payment = getButtonGroupValue(rbgPayment);
        patient.symptom = taSymptoms.getText();
        patient.diagnosis = taDiagnosis.getText();
        patient.room = tfRoom.getText();
    }
    private void loadData() {
        tfID.setText(patient.ID);
        tfName.setText(patient.name);
        tfAddress.setText(patient.address);
        tfPhone.setText(patient.phone);
        
        if (isEmergent) {
            tfRoom.setText(patient.room);
        } else tfRoom.setText(" none");
        
        if (patient.gender.equals("Male")) rbGenderM.doClick();
        else rbGenderF.doClick();
        
        if (patient.payment.equals("Health Insurance")) rbPaymentH.doClick();
        else if (patient.payment.equals("Cash")) rbPaymentC.doClick();
        else if (patient.payment.equals("VISA")) rbPaymentV.doClick();
        else rbPaymentI.doClick();
        
        taSymptoms.setText(patient.symptom);
        taDiagnosis.setText(patient.diagnosis);
    }
    private void saveForm() {
        /* Update patient's components by extracting it from form */
        updateData();
        
        
        if (isEmergent) {
            /* Update item (DB) */
            ManagementSystemUI.msDB.ePatient.get(patient.index).copy(patient);

            /* Update item (visually) */
            ManagementSystemUI.ePatientsList.set(patient.index, patient.toString());
        } else {
            /* Update item (DB) */
            ManagementSystemUI.msDB.nPatient.get(patient.index).copy(patient);

            /* Update item (visually) */
            ManagementSystemUI.nPatientsList.set(patient.index, patient.toString());
        }
        

        /* Close form window */
        super.dispose();
        log("Patient form is saved!");
        
        /* Save changes flag */
        ManagementSystemUI.mustSave = true;
        
        /* Show info message */
        JOptionPane.showMessageDialog(null, 
                                      "Successful saving", 
                                      "Save Process Info",
                                      JOptionPane.INFORMATION_MESSAGE);
    }
    private void updateTitle() {
        String formStatus, formType;
        if (chbEdit.isSelected()) formStatus = "(Edit)";
        else formStatus = "(View Only)";
        if (rbTypeN.isSelected()) formType = "Normal";
        else formType = "Emergency";
        jlTitle.setText(formType + " Patient " + formStatus);
    }
    private void clear() {
        tfID.setText(CLEARED_ID);
        tfName.setText(CLEARED_NAME);
        tfAddress.setText(CLEARED_ADDRESS);
        tfPhone.setText(CLEARED_PHONE);
        tfRoom.setText(CLEARED_ROOM);
        taSymptoms.setText(CLEARED_SYMPTOMS);
        taDiagnosis.setText(CLEARED_DIAGNOSIS);
        
        rbGenderM.doClick();
        rbPaymentH.doClick();
    }
    private void setEnableAll(boolean flag) {
        /* Change enable status for all components */
        /* jRadiobuttons */
        rbTypeN.setEnabled(flag);
        rbTypeE.setEnabled(flag);
        
        /* Gender jRadioButtonGroup */
        rbGenderM.setEnabled(flag);
        rbGenderF.setEnabled(flag);
        
        /* Payment jRadioButtonGroup */
        rbPaymentH.setEnabled(flag);
        rbPaymentC.setEnabled(flag);
        rbPaymentV.setEnabled(flag);
        rbPaymentI.setEnabled(flag);
        
        /* Time jTextfields */
        if (rbTypeE.isSelected()) tfRoom.setEditable(flag);
        else tfRoom.setEditable(false);
        tfID.setEditable(flag);
        tfName.setEditable(flag);
        tfAddress.setEditable(flag);
        tfPhone.setEditable(flag);
        
        /* Clear, save, add & remove jButtons */
        bClear.setEnabled(flag);
        bSave.setEnabled(flag);
        
        /* jTextAreas */
        taSymptoms.setEditable(flag);
        taDiagnosis.setEditable(flag);
        
        /* Change title */
        updateTitle();
    }
    private String getButtonGroupValue(ButtonGroup jrbg) {
        /* Get enumeration over jRadioButtons */
        Enumeration<AbstractButton> elements = jrbg.getElements();
        
        /* Iterate over them */
        for (; elements.hasMoreElements();) {
            /* Get next element */
            AbstractButton button = (AbstractButton) elements.nextElement();
            
            /* Check if selected */
            if (button.isSelected()) return button.getText();
        }
        
        return "null";
    }
    private static void showErrorSaving(String msg) {
        /* Show error message */
        JOptionPane.showMessageDialog(null, 
                                      msg, 
                                      "Save Process Error",
                                      JOptionPane.ERROR_MESSAGE);
    } 
    static private void log(String str) {
        System.out.println(str);        /* Out to console */
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgType = new javax.swing.ButtonGroup();
        rbgGender = new javax.swing.ButtonGroup();
        rbgPayment = new javax.swing.ButtonGroup();
        jlTitle = new javax.swing.JLabel();
        jlType = new javax.swing.JLabel();
        jlRoom = new javax.swing.JLabel();
        jlID = new javax.swing.JLabel();
        jlName = new javax.swing.JLabel();
        jlAddress = new javax.swing.JLabel();
        jlPhone = new javax.swing.JLabel();
        jlGender = new javax.swing.JLabel();
        jlPayment = new javax.swing.JLabel();
        jlSymptoms = new javax.swing.JLabel();
        jlDiagnosis = new javax.swing.JLabel();
        chbEdit = new javax.swing.JCheckBox();
        bSave = new javax.swing.JButton();
        bClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taSymptoms = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDiagnosis = new javax.swing.JTextArea();
        tfRoom = new javax.swing.JTextField();
        tfID = new javax.swing.JTextField();
        tfName = new javax.swing.JTextField();
        tfAddress = new javax.swing.JTextField();
        tfPhone = new javax.swing.JTextField();
        rbTypeN = new javax.swing.JRadioButton();
        rbTypeE = new javax.swing.JRadioButton();
        rbGenderM = new javax.swing.JRadioButton();
        rbGenderF = new javax.swing.JRadioButton();
        rbPaymentH = new javax.swing.JRadioButton();
        rbPaymentC = new javax.swing.JRadioButton();
        rbPaymentV = new javax.swing.JRadioButton();
        rbPaymentI = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Patient");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(550, 550));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jlTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitle.setText("Patient");

        jlType.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlType.setText("Type:");

        jlRoom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlRoom.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlRoom.setText("Room:");

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

        jlGender.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlGender.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlGender.setText("Gender:");

        jlPayment.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlPayment.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlPayment.setText("Payment:");

        jlSymptoms.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlSymptoms.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlSymptoms.setText("Symptoms:");

        jlDiagnosis.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlDiagnosis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlDiagnosis.setText("Diagnosis:");

        chbEdit.setText("Edit");
        chbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbEditActionPerformed(evt);
            }
        });

        bSave.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bSave.setText("Save");
        bSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bSaveActionPerformed(evt);
            }
        });

        bClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bClear.setText("Clear");
        bClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bClearActionPerformed(evt);
            }
        });

        taSymptoms.setColumns(20);
        taSymptoms.setRows(5);
        taSymptoms.setText("Symptom #1");
        taSymptoms.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                taSymptomsFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(taSymptoms);

        taDiagnosis.setColumns(20);
        taDiagnosis.setRows(5);
        taDiagnosis.setText("Diagnosis #1");
        taDiagnosis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                taDiagnosisFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(taDiagnosis);

        tfRoom.setText("666");
        tfRoom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfRoomFocusGained(evt);
            }
        });

        tfID.setText("xxSxxxx");
        tfID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfIDFocusGained(evt);
            }
        });

        tfName.setText("first middle last");
        tfName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfNameFocusGained(evt);
            }
        });

        tfAddress.setText("Apt., Bldg., St., City");
        tfAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfAddressFocusGained(evt);
            }
        });

        tfPhone.setText("01x-xxxx-xxx");
        tfPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfPhoneFocusGained(evt);
            }
        });

        rbTypeN.setSelected(true);
        rbTypeN.setText("Normal");
        rbTypeN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTypeNActionPerformed(evt);
            }
        });

        rbTypeE.setText("Emergency");
        rbTypeE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTypeEActionPerformed(evt);
            }
        });

        rbGenderM.setSelected(true);
        rbGenderM.setText("Male");

        rbGenderF.setText("Female");

        rbPaymentH.setSelected(true);
        rbPaymentH.setText("Health Insurance");

        rbPaymentC.setText("Cash");

        rbPaymentV.setText("VISA");

        rbPaymentI.setText("Installments");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDiagnosis, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(chbEdit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlType, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlGender, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlPayment, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jlSymptoms, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(tfName)
                            .addComponent(tfAddress)
                            .addComponent(tfPhone)
                            .addComponent(tfID)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbGenderM)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbGenderF))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbPaymentH)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPaymentC)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPaymentV)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbPaymentI)))
                                .addGap(0, 14, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rbTypeE)
                                .addGap(18, 18, 18)
                                .addComponent(rbTypeN)
                                .addGap(59, 59, 59)
                                .addComponent(jlRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfRoom)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlType)
                    .addComponent(rbTypeE)
                    .addComponent(tfRoom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlRoom)
                    .addComponent(rbTypeN))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlGender)
                    .addComponent(rbGenderM)
                    .addComponent(rbGenderF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPayment)
                    .addComponent(rbPaymentH)
                    .addComponent(rbPaymentC)
                    .addComponent(rbPaymentV)
                    .addComponent(rbPaymentI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlSymptoms)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(jlDiagnosis))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chbEdit)
                        .addComponent(bClear))
                    .addComponent(bSave, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        /* Make user confirm */
        int selectedOption = JOptionPane.showConfirmDialog(null, 
                                  "Are you sure?", 
                                  "Choose", 
                                  JOptionPane.YES_NO_OPTION);
        
        /* Check user's selection */
        if (selectedOption == JOptionPane.YES_OPTION) {
            clear();
            log("Patient form is cleared!");
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_bClearActionPerformed
    private void chbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbEditActionPerformed
        setEnableAll(chbEdit.isSelected()); /* Based on Edit jCheckBox status */
    }//GEN-LAST:event_chbEditActionPerformed
    private void rbTypeNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTypeNActionPerformed
        tfRoom.setEditable(rbTypeE.isSelected());
        updateTitle();
        if (isEmergent == true) {
            int newIndex = ManagementSystemUI.msDB.nPatient.size();
            int curIndex = patient.index;
            patient.index = newIndex;
            
            ManagementSystemUI.msDB.nPatient.add(new EmergencyPatient());
            ManagementSystemUI.msDB.nPatient.get(newIndex).copy(patient);
            
            if (!ManagementSystemUI.msDB.ePatient.get(curIndex).ID.equals("")) {
                ManagementSystemUI.nPatientsList.addElement(ManagementSystemUI.msDB.nPatient.get(newIndex).toString());
            } else {
                ManagementSystemUI.nPatientsList.addElement("<New Normal Patient Form>");
            }
            
            Healthcaremanagementsystem.msUI.updateNormalPatientsNumber();
            
            ManagementSystemUI.msDB.ePatient.remove(curIndex);
            ManagementSystemUI.ePatientsList.remove(curIndex);
            Healthcaremanagementsystem.msUI.updateEmergencyPatientsNumber();
        }
        isEmergent = false;
    }//GEN-LAST:event_rbTypeNActionPerformed
    private void rbTypeEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTypeEActionPerformed
        tfRoom.setEditable(rbTypeE.isSelected());
        updateTitle();
        if (isEmergent == false) {
            int newIndex = ManagementSystemUI.msDB.ePatient.size();
            int curIndex = patient.index;
            patient.index = newIndex;

            ManagementSystemUI.msDB.ePatient.add(new EmergencyPatient());
            ManagementSystemUI.msDB.ePatient.get(newIndex).copy(patient);
            if (!ManagementSystemUI.msDB.nPatient.get(curIndex).ID.equals("")) {
                ManagementSystemUI.ePatientsList.addElement(ManagementSystemUI.msDB.ePatient.get(newIndex).toString());
            } else {
                ManagementSystemUI.ePatientsList.addElement("<New Emergency Patient Form>");
            }
            
            Healthcaremanagementsystem.msUI.updateEmergencyPatientsNumber();
            
            ManagementSystemUI.msDB.nPatient.remove(curIndex);
            ManagementSystemUI.nPatientsList.remove(curIndex);
            Healthcaremanagementsystem.msUI.updateNormalPatientsNumber();
        }
        isEmergent = true;
    }//GEN-LAST:event_rbTypeEActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    int index;
    
        /* Get last appointment index */
        if (isEmergent) {
            index = ManagementSystemUI.msDB.ePatient.size() - 1;
        } else {
            index = ManagementSystemUI.msDB.nPatient.size() - 1;
        }
        
        /* Check if current opened patient is last */
        if (index == patient.index) {
            if (isEmergent) {
                if (ManagementSystemUI.ePatientsList.get(index).equals("<New Emergency Patient Form>")) {
                    ManagementSystemUI.msDB.ePatient.remove(index);
                    ManagementSystemUI.ePatientsList.remove(index);
                    Healthcaremanagementsystem.msUI.updateEmergencyPatientsNumber();
                }
            } else {
                if (ManagementSystemUI.nPatientsList.get(index).equals("<New Normal Patient Form>")) {
                    ManagementSystemUI.msDB.nPatient.remove(index);
                    ManagementSystemUI.nPatientsList.remove(index);
                    Healthcaremanagementsystem.msUI.updateNormalPatientsNumber();
                }                
            }
        }
    }//GEN-LAST:event_formWindowClosed

    private void tfRoomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfRoomFocusGained
        tfRoom.selectAll();
    }//GEN-LAST:event_tfRoomFocusGained
    private void tfNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNameFocusGained
        tfName.selectAll();
    }//GEN-LAST:event_tfNameFocusGained
    private void tfAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfAddressFocusGained
        tfAddress.selectAll();
    }//GEN-LAST:event_tfAddressFocusGained
    private void tfPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfPhoneFocusGained
        tfPhone.selectAll();
    }//GEN-LAST:event_tfPhoneFocusGained
    private void taSymptomsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taSymptomsFocusGained
        taSymptoms.selectAll();
    }//GEN-LAST:event_taSymptomsFocusGained
    private void taDiagnosisFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taDiagnosisFocusGained
        taDiagnosis.selectAll();
    }//GEN-LAST:event_taDiagnosisFocusGained
    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        /* Make sure appointment form is valid */
        switch(FormValidity()) {
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
            case FORM_E_SYMPTOMS:
                showErrorSaving(EMSG_SYMPTOMS);
                break;
            case FORM_E_DIAGNOSIS:
                showErrorSaving(EMSG_DIAGNOSIS);
                break;
            case FORM_E_ROOM:
                showErrorSaving(EMSG_ROOM);
                break;
        }
    }//GEN-LAST:event_bSaveActionPerformed
    private void tfIDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfIDFocusGained
        tfID.selectAll();
    }//GEN-LAST:event_tfIDFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClear;
    private javax.swing.JButton bSave;
    private javax.swing.JCheckBox chbEdit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlAddress;
    private javax.swing.JLabel jlDiagnosis;
    private javax.swing.JLabel jlGender;
    private javax.swing.JLabel jlID;
    private javax.swing.JLabel jlName;
    private javax.swing.JLabel jlPayment;
    private javax.swing.JLabel jlPhone;
    private javax.swing.JLabel jlRoom;
    private javax.swing.JLabel jlSymptoms;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JLabel jlType;
    private javax.swing.JRadioButton rbGenderF;
    private javax.swing.JRadioButton rbGenderM;
    private javax.swing.JRadioButton rbPaymentC;
    private javax.swing.JRadioButton rbPaymentH;
    private javax.swing.JRadioButton rbPaymentI;
    private javax.swing.JRadioButton rbPaymentV;
    private javax.swing.JRadioButton rbTypeE;
    private javax.swing.JRadioButton rbTypeN;
    private javax.swing.ButtonGroup rbgGender;
    private javax.swing.ButtonGroup rbgPayment;
    private javax.swing.ButtonGroup rbgType;
    private javax.swing.JTextArea taDiagnosis;
    private javax.swing.JTextArea taSymptoms;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JTextField tfID;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfRoom;
    // End of variables declaration//GEN-END:variables

    /* Form errors */
    private final static int FORM_E_OK = 0;
    private final static int FORM_E_DB = -1;
    private final static int FORM_E_ID = -2;
    private final static int FORM_E_UID = -3;
    private final static int FORM_E_NAME = -4;
    private final static int FORM_E_ADDRESS = -5;
    private final static int FORM_E_PHONE = -6;
    private final static int FORM_E_SYMPTOMS = -7;
    private final static int FORM_E_DIAGNOSIS = -8;
    private final static int FORM_E_ROOM = -9;
    
    /* Cleared text */
    private final static String CLEARED_ID = "xxXxxxx";
    private final static String CLEARED_NAME = "First Middle Last";
    private final static String CLEARED_ADDRESS = "apt., st., city";
    private final static String CLEARED_PHONE = "01x xxxx xxx";
    private final static String CLEARED_SYMPTOMS = "No symptoms added yet.";
    private final static String CLEARED_DIAGNOSIS = "No diagnosis added yet.";
    private final static String CLEARED_ROOM = "none";
    
    /* Error messages */
    private final static String EMSG_DB = "DB Error!";
    private final static String EMSG_ID = "ID Error";
    private final static String EMSG_UID = "ID is already taken!";
    private final static String EMSG_NAME = "Name Error!";
    private final static String EMSG_ADDRESS = "Address Error!";
    private final static String EMSG_PHONE = "Phone Error!";
    private final static String EMSG_SYMPTOMS = "Symptoms Error!";
    private final static String EMSG_DIAGNOSIS = "Diagnosis Error!";
    private final static String EMSG_ROOM = "Room Error!";
}
