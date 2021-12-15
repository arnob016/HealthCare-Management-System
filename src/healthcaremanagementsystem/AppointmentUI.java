package healthcaremanagementsystem;

import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.Caret;

public class AppointmentUI extends javax.swing.JFrame {

    public AppointmentUI() {
        this(true, new Appointment());
        /* Enable edit for new appointment */
    }

    public AppointmentUI(boolean editFlag, Appointment appoint) {
        initComponents();
        /* Initialize jForm components */

 /* Add radio buttons to group to affect each other */
        rbMiddayGroup.add(rbAM);
        rbMiddayGroup.add(rbPM);

        /* Show jForm window and set default Exit button behavior */
        super.setVisible(true);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Copy data and set dateTime visually */
        AppointmentUI.appoint = appoint;
        setDateTime(appoint.time,
                appoint.midday,
                appoint.date);

        /* Check edit flag to enable/disable edit features (show/edit) */
        if (!editFlag && !appoint.doctorID.equals("null")) {
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

        /* Add doctors and patients entries */
        addDoctorsInsideComboBox();
        addPatientsIndiseComboBox();

        /* Select doctor & patient in jComboBox */
        selectDoctor();
        selectPatient();
    }

    private String getPatientID(int index) {
        /* Get ePatients number */
        int eNum = ManagementSystemUI.msDB.ePatient.size();

        if (index < eNum) {
            return ManagementSystemUI.msDB.ePatient.get(index).ID;
        } else {
            /* index > eNum */
            index -= eNum + 1;
            return ManagementSystemUI.msDB.nPatient.get(index).ID;
        }
    }

    private int FormValidity() {
        /* Get time as numbers */
        int hours = Integer.parseInt("0" + tfHours.getText());
        int minutes = Integer.parseInt("0" + tfMinutes.getText());

        /* Current date calendar and remove time */
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.MILLISECOND, 0);

        /* Checking form to be free of errors */
        if (appoint.index == -1) {
            return FORM_E_DB;
        } else if (!((hours > 0 && hours < 13) && (minutes >= 0 && minutes < 60))) {
            return FORM_E_TIME;
        } else if (dateChooser.getCalendar() == null) {
            return FORM_E_DATE_FORMAT;
        } else if (Duration.between(currentDate.toInstant(),
                dateChooser.getCalendar().toInstant()).toDays() < 0) {
            return FORM_E_DATE_VALID;
        } else if (cbDoctors.getSelectedIndex() < 1) {
            return FORM_E_NO_DOCTOR;
        } else if (cbPatients.getSelectedIndex() < 2
                || cbPatients.getSelectedIndex() == (ManagementSystemUI.msDB.ePatient.size() + 2)) {
            return FORM_E_NO_PATIENT;
        } else {
            return FORM_E_OK;
        }
    }

    private void addDoctorsInsideComboBox() {
        /* Add doctors to doctors jComboBox */
        for (Doctor i : ManagementSystemUI.msDB.doctor) {
            cbDoctors.addItem(i.toString());
        }
    }

    private void addPatientsIndiseComboBox() {
        /* Add emergency category to patients jComboBox */
        cbPatients.addItem("----------- Emergency Patients -----------");

        /* Add emergency patients to patients jComboBox */
        for (EmergencyPatient i : ManagementSystemUI.msDB.ePatient) {
            cbPatients.addItem(i.toString());
        }
        /* Add normal category to patients jComboBox */
        cbPatients.addItem("-------------- Normal Patients --------------");

        /* Add normal patients to patients jComboBox */
        for (NormalPatient i : ManagementSystemUI.msDB.nPatient) {
            cbPatients.addItem(i.toString());
        }
    }

    private void updateData() {
        /* Get selected index */
        int dIndex = cbDoctors.getSelectedIndex() - 1;
        int pIndex = cbPatients.getSelectedIndex() - 2;

        /* Copy appointment form data to appointment data holder */
        appoint.time = timeString();
        appoint.midday = middayString();
        appoint.date = dateString();
        appoint.doctorID = ManagementSystemUI.msDB.doctor.get(dIndex).ID;
        appoint.patientID = getPatientID(pIndex);
    }

    private void setEnableForComponentButton(Component[] cmp, boolean flag) {
        /* Loop over components */
        for (Component component : cmp) {
            if (component instanceof JButton) {
                /* Check component is jButton */
                component.setEnabled(flag);
                /* Set enable of jButton */
            }
        }
    }

    private void setEnableAll(boolean flag) {
        /* Change enable status for all components */
 /* jRadiobuttons */
        rbAM.setEnabled(flag);
        rbPM.setEnabled(flag);

        /* Time jTextfields */
        tfHours.setEditable(flag);
        tfMinutes.setEditable(flag);

        /* Clear and save jButtons */
        bClear.setEnabled(flag);
        bSave.setEnabled(flag);

        /* Comboboxes jButtons */
        setEnableForComponentButton(cbDoctors.getComponents(), flag);
        setEnableForComponentButton(cbPatients.getComponents(), flag);

        /* DateChooser jButton */
        setEnableForComponentButton(dateChooser.getComponents(), flag);

        /* Change title */
        String formStatus;
        if (flag) {
            formStatus = "(Edit)";
        } else {
            formStatus = "(View Only)";
        }
        jlTitle.setText("Appointment " + formStatus);
    }

    private void clear() {
        /* Get current calendar date */
        Calendar appointmentTime = Calendar.getInstance();

        /* Set time and format it to be (hh:mm) */
        tfHours.setText(String.valueOf(appointmentTime.get(Calendar.HOUR)));
        tfMinutes.setText(String.valueOf(appointmentTime.get(Calendar.MINUTE)));
        formatHours();
        formatMinutes();

        /* Set jDateChooser date to current date */
        dateChooser.setCalendar(appointmentTime);

        /* Initialize jComboBoxes by selecting 'select...' entry */
        cbDoctors.setSelectedIndex(0);
        cbPatients.setSelectedIndex(0);

        /* Chooser midday to 'AM' by default */
        rbAM.doClick();

        /* Focus hours jTextField */
        tfHours.requestFocus();
    }

    private void setDateTime(String time, String Midday, String date) {
        /* Create SimpleDateFormat parser and a temporal Calendar w/o time */
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm d/M/yyyy", Locale.ENGLISH);
        Calendar tCalendar = Calendar.getInstance();
        tCalendar.set(Calendar.MILLISECOND, 0);

        /* Try parsing, then set data (visually) */
        try {
            /* Parsing time and date */
            if (!time.equals("") && !date.equals("")) {
                tCalendar.setTime(sdf.parse(time + " " + date));

                /* Set time and format to (hh:mm) */
                tfHours.setText(String.valueOf(tCalendar.get(Calendar.HOUR)));
                tfMinutes.setText(String.valueOf(tCalendar.get(Calendar.MINUTE)));
                formatHours();
                formatMinutes();

                /* Set midday jRadioButton accordingly */
                if (Midday.equals("AM")) {
                    rbAM.doClick();
                } else {
                    rbPM.doClick();
                }

                /* Set jDateChooser */
                dateChooser.setCalendar(tCalendar);
            }
        } catch (ParseException ex) {
            log(ex.toString());
        }
    }

    private void formatHours() {
        /* Get hours length as string */
        int length = 2 - tfHours.getText().length();

        /* Add leading zeros */
        String r = new String(new char[length]).replace("\0", "0");
        tfHours.setText(r + tfHours.getText());
    }

    private void formatMinutes() {
        /* Get minutes length as string */
        int length = 2 - tfMinutes.getText().length();

        /* Add leading zeros */
        String r = new String(new char[length]).replace("\0", "0");
        tfMinutes.setText(r + tfMinutes.getText());
    }

    private void selectDoctor() {
        /* Get selected doctor index */
        int index = ManagementSystemUI.getDoctorIndex(appoint.doctorID);

        /* safety block & selection in jComboBox */
        if (index != -1) {
            cbDoctors.setSelectedIndex(index + 1);
        }
    }

    private void selectPatient() {
        /* Get selected patient index */
        int index = ManagementSystemUI.getPatientIndex(appoint.patientID);

        /* safety block & selection in jComboBox */
        if (index != -1) {
            cbPatients.setSelectedIndex(index);
        }
    }

    private String timeString() {
        /* Convert jTextFields of time to string */
        return tfHours.getText() + ":" + tfMinutes.getText();
    }

    private String middayString() {
        /* Get midday from jRadioButtons selection */
        if (rbAM.isSelected()) {
            return "AM";
        } else {
            return "PM";
        }
    }

    private String dateString() {
        /* Convert jDateChooser date to string */
        return dateChooser.getCalendar().get(Calendar.DAY_OF_MONTH) + "/"
                + (dateChooser.getCalendar().get(Calendar.MONTH) + 1) + "/"
                + dateChooser.getCalendar().get(Calendar.YEAR);
    }

    private void showErrorSaving(String msg) {
        /* Show error message */
        JOptionPane.showMessageDialog(null,
                msg,
                "Save Process Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void saveForm() {
        /* Update appointment components by extracting it from form */
        updateData();

        /* Update item (DB) */
        ManagementSystemUI.msDB.appointment.get(appoint.index).copy(appoint);

        /* Update item (visually) */
        ManagementSystemUI.appointmentsList.set(appoint.index, appoint.toString());

        /* Close form window */
        super.dispose();
        log("Appointment form is saved!");

        /* Save changes flag */
        ManagementSystemUI.mustSave = true;

        /* Show info message */
        JOptionPane.showMessageDialog(null,
                "Successful saving",
                "Save Process Info",
                JOptionPane.INFORMATION_MESSAGE);

    }

    static private void log(String str) {
        System.out.println(str);
        /* Out to console */
    }

    private boolean isCharNumber(int key) {
        /* Check if key is a number */
        if (key >= 48 || key < 58) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbMiddayGroup = new javax.swing.ButtonGroup();
        chbEdit = new javax.swing.JCheckBox();
        jlSeparator = new javax.swing.JLabel();
        jlTime = new javax.swing.JLabel();
        jlDate = new javax.swing.JLabel();
        jlTitle = new javax.swing.JLabel();
        jlDoctors = new javax.swing.JLabel();
        jlPatients = new javax.swing.JLabel();
        tfHours = new javax.swing.JTextField();
        tfMinutes = new javax.swing.JTextField();
        cbDoctors = new javax.swing.JComboBox<>();
        cbPatients = new javax.swing.JComboBox<>();
        rbAM = new javax.swing.JRadioButton();
        rbPM = new javax.swing.JRadioButton();
        bSave = new javax.swing.JButton();
        bClear = new javax.swing.JButton();
        dateChooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Make Appointment");
        setLocationByPlatform(true);
        setMaximumSize(new java.awt.Dimension(1366, 768));
        setMinimumSize(new java.awt.Dimension(320, 270));
        setPreferredSize(new java.awt.Dimension(320, 270));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        chbEdit.setText("Edit");
        chbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbEditActionPerformed(evt);
            }
        });

        jlSeparator.setText(":");

        jlTime.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlTime.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlTime.setText("Time:");

        jlDate.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlDate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlDate.setText("Date:");

        jlTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jlTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitle.setText("Appointment");

        jlDoctors.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlDoctors.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlDoctors.setText("Doctor:");

        jlPatients.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlPatients.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jlPatients.setText("Patient:");

        tfHours.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tfHours.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfHours.setText("12");
        tfHours.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfHoursCaretUpdate(evt);
            }
        });
        tfHours.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfHoursFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfHoursFocusLost(evt);
            }
        });
        tfHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfHoursActionPerformed(evt);
            }
        });
        tfHours.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfHoursKeyTyped(evt);
            }
        });

        tfMinutes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tfMinutes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfMinutes.setText("59");
        tfMinutes.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                tfMinutesCaretUpdate(evt);
            }
        });
        tfMinutes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfMinutesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfMinutesFocusLost(evt);
            }
        });
        tfMinutes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfMinutesKeyTyped(evt);
            }
        });

        cbDoctors.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "select..." }));
        cbDoctors.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cbDoctors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbDoctorsMousePressed(evt);
            }
        });

        cbPatients.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "select..." }));
        cbPatients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbPatientsMousePressed(evt);
            }
        });

        rbAM.setSelected(true);
        rbAM.setText("AM");

        rbPM.setText("PM");

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
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jlTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jlDoctors, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chbEdit)
                                .addComponent(jlPatients, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPatients, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbDoctors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfHours, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jlSeparator)
                                .addGap(4, 4, 4)
                                .addComponent(tfMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(rbAM)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbPM)
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(bClear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(bSave, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jlTitle)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfHours, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlSeparator)
                    .addComponent(jlTime)
                    .addComponent(rbAM)
                    .addComponent(rbPM))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlDate, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDoctors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlDoctors))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPatients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlPatients))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bSave)
                    .addComponent(chbEdit)
                    .addComponent(bClear))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(336, 244));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bSaveActionPerformed
        /* Make sure appointment form is valid */
        switch (FormValidity()) {
            case FORM_E_OK:
                saveForm();
                break;
            case FORM_E_DB:
                showErrorSaving(EMSG_DB);
                break;
            case FORM_E_TIME:
                showErrorSaving(EMSG_TIME);
                break;
            case FORM_E_DATE_FORMAT:
                showErrorSaving(EMSG_DATE_FORMAT);
                break;
            case FORM_E_DATE_VALID:
                showErrorSaving(EMSG_DATE_VALID);
                break;
            case FORM_E_NO_DOCTOR:
                showErrorSaving(EMSG_NO_DOCTOR);
                break;
            case FORM_E_NO_PATIENT:
                showErrorSaving(EMSG_NO_PATIENT);
                break;
        }
    }//GEN-LAST:event_bSaveActionPerformed
    private void tfHoursKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfHoursKeyTyped
        /* Get current caret in hours jTextField */
        Caret curCaret = tfHours.getCaret();

        /* Make sure user do not exceed the limit of two digits */
        if ((tfHours.getText().trim().length() == 2
                && (curCaret.getMark() - curCaret.getDot()) == 0)
                || evt.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
            tfHours.selectAll();
            /* Select all */
            evt.consume();
            /* Consume Event */
        }

        /* Make sure user ONLY type numbers */
        if (isCharNumber(evt.getKeyChar()) == false) {
            evt.consume();
        }
    }//GEN-LAST:event_tfHoursKeyTyped
    private void tfMinutesKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfMinutesKeyTyped
        /* Get current caret in hours jTextField */
        Caret curCaret = tfMinutes.getCaret();

        /* Make sure user do not exceed the limit of two digits */
        if ((tfMinutes.getText().trim().length() == 2
                && (curCaret.getMark() - curCaret.getDot()) == 0)
                || evt.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE) {
            tfMinutes.selectAll();
            /* Select all */
            evt.consume();
            /* Consume Event */
        }

        /* Make sure user ONLY type numbers */
        if (isCharNumber(evt.getKeyChar()) == false) {
            evt.consume();
        }
    }//GEN-LAST:event_tfMinutesKeyTyped
    private void bClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bClearActionPerformed
        /* Make user confirm */
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Are you sure?",
                "Choose",
                JOptionPane.YES_NO_OPTION);

        /* Check user's selection */
        if (selectedOption == JOptionPane.YES_OPTION) {
            clear();
            log("Appointment form is cleared!");
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_bClearActionPerformed
    private void chbEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbEditActionPerformed
        setEnableAll(chbEdit.isSelected());
        /* Based on Edit jCheckBox status */
    }//GEN-LAST:event_chbEditActionPerformed
    private void cbDoctorsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbDoctorsMousePressed
        /* Hide menu if no Edit */
        if (!chbEdit.isSelected()) {
            jlTitle.requestFocus();
            /* Remove focus from jComboBox */
            cbDoctors.hidePopup();
            /* Hide popup menu */
        }
    }//GEN-LAST:event_cbDoctorsMousePressed
    private void cbPatientsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbPatientsMousePressed
        /* Hide menu if no Edit */
        if (!chbEdit.isSelected()) {
            jlTitle.requestFocus();
            /* Remove focus from jComboBox */
            cbPatients.hidePopup();
            /* Hide popup menu */
        }
    }//GEN-LAST:event_cbPatientsMousePressed
    private void tfHoursFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfHoursFocusGained
        tfHours.selectAll();
    }//GEN-LAST:event_tfHoursFocusGained
    private void tfMinutesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfMinutesFocusGained
        tfMinutes.selectAll();
        /* Select minutes text */
    }//GEN-LAST:event_tfMinutesFocusGained
    private void tfHoursCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfHoursCaretUpdate
        /* Get hours as a number */
        int hours = Integer.parseInt("0" + tfHours.getText());

        /* Check if in limits and update colors accordingly  */
        if (hours >= 0 && hours < 13) {
            tfHours.setForeground(Color.black);
            tfHours.setBackground(Color.white);
        } else {
            /* Out boundaries */
            tfHours.setForeground(Color.white);
            tfHours.setBackground(ERRORCOLOR);
        }
    }//GEN-LAST:event_tfHoursCaretUpdate
    private void tfMinutesCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_tfMinutesCaretUpdate
        /* Get minutes as a number */
        int minutes = Integer.parseInt("0" + tfMinutes.getText());

        /* Check if in limits and update colors accordingly */
        if (minutes >= 0 && minutes < 60) {
            tfMinutes.setForeground(Color.black);
            tfMinutes.setBackground(Color.white);
        } else {
            /* Out boundaries */
            tfMinutes.setForeground(Color.white);
            tfMinutes.setBackground(ERRORCOLOR);
        }
    }//GEN-LAST:event_tfMinutesCaretUpdate
    private void tfHoursFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfHoursFocusLost
        formatHours();
        /* Add leading zeros */
    }//GEN-LAST:event_tfHoursFocusLost
    private void tfMinutesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfMinutesFocusLost
        formatMinutes();
        /* Add leading zeros */
    }//GEN-LAST:event_tfMinutesFocusLost
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        /* Get last appointment index */
        int index = ManagementSystemUI.msDB.appointment.size() - 1;

        /* Check if current opened appointment is last */
        if (index == appoint.index) {
            if (ManagementSystemUI.appointmentsList.get(index).equals("<New Appointment Form>")) {
                ManagementSystemUI.msDB.appointment.remove(index);
                ManagementSystemUI.appointmentsList.remove(index);
                Healthcaremanagementsystem.msUI.updateAppointmentsNumber();
            }
        }
    }//GEN-LAST:event_formWindowClosing

    private void tfHoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfHoursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfHoursActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bClear;
    private javax.swing.JButton bSave;
    private javax.swing.JComboBox<String> cbDoctors;
    private javax.swing.JComboBox<String> cbPatients;
    private javax.swing.JCheckBox chbEdit;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JLabel jlDate;
    private javax.swing.JLabel jlDoctors;
    private javax.swing.JLabel jlPatients;
    private javax.swing.JLabel jlSeparator;
    private javax.swing.JLabel jlTime;
    private javax.swing.JLabel jlTitle;
    private javax.swing.JRadioButton rbAM;
    private javax.swing.ButtonGroup rbMiddayGroup;
    private javax.swing.JRadioButton rbPM;
    private javax.swing.JTextField tfHours;
    private javax.swing.JTextField tfMinutes;
    // End of variables declaration//GEN-END:variables

    /* Form variables */
    static Appointment appoint;
    final static Color ERRORCOLOR = new Color(255, 100, 100);
    final static String NOSELECTEION = "select...";

    private final static int FORM_E_OK = 0;
    private final static int FORM_E_DB = -1;
    private final static int FORM_E_TIME = -2;
    private final static int FORM_E_DATE_FORMAT = -3;
    private final static int FORM_E_DATE_VALID = -4;
    private final static int FORM_E_NO_DOCTOR = -5;
    private final static int FORM_E_NO_PATIENT = -6;

    private final static String EMSG_DB = "DB Error!";
    private final static String EMSG_TIME = "Incorrect time!";
    private final static String EMSG_DATE_FORMAT = "Incorrect date format!";
    private final static String EMSG_DATE_VALID = "Cannot set an appointment in the past!";
    private final static String EMSG_NO_DOCTOR = "No doctor is selected!";
    private final static String EMSG_NO_PATIENT = "No Patient is selected!";
}
