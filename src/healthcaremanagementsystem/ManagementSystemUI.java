package healthcaremanagementsystem;

import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;


public class ManagementSystemUI extends javax.swing.JFrame {
    public ManagementSystemUI() {
        msDB = new ManagementSystem();
                                            /* Create ManagementSystem Object */
        msDB.loadData();                    /* Load Stored data if available */
        
        initComponents();                   /* Initialize UI components */
        super.setVisible(true);             /* Make UI visible */
        super.setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
                                            /* Disable default exit button */
        /* Set exit button new event */ 
        super.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmExit();              /* Confirm exit with saving data */
            }
        });
        
        /* Create new DefaultListModels for each JList */
        appointmentsList = new DefaultListModel();
        doctorsList = new DefaultListModel();
        ePatientsList = new DefaultListModel();
        nPatientsList = new DefaultListModel();
        
        /* Assign each JList with its corresponding DefaultListModel */
        jListAppointments.setModel(appointmentsList);
        jListDoctors.setModel(doctorsList);
        jListEmergencyPatients.setModel(ePatientsList);
        jListNormalPatients.setModel(nPatientsList);

        /* Update counter of each category */
        updateAppointmentsNumber();
        updateDoctorsNumber();
        updateEmergencyPatientsNumber();
        updateNormalPatientsNumber();
        
        /* Display elements of each category */
        showAppointments();
        showDoctors();
        showEmergencyPatients();
        showNormalPatients();
        
        /* Mouse double click listner (over list) */
        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                /* Get requested list */
                JList theList = (JList) mouseEvent.getSource();
                
                /* Check for double click */
                if (mouseEvent.getClickCount() == 2) {
                    /* Get clicked index of requested list */
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    
                    /* Check if index is a natural number (i.e non negative number) */
                    if (index >= 0) {
                        
                        /* Edit corresponding item */
                        if (jListAppointments == theList) {
                            editAppointment(index);
                        } else if (jListDoctors == theList) {
                            editDoctor(index);
                        } else if (jListEmergencyPatients == theList) {
                            editEmergencyPatient(index);
                        } else if (jListNormalPatients == theList) {
                            editNormalPatient(index);
                        } else {
                            // DO NOTHING
                        }
                    }
                }
            }
        };
      
        /* Add mouse doubleclick listner */
        jListAppointments.addMouseListener(mouseListener);
        jListDoctors.addMouseListener(mouseListener);
        jListEmergencyPatients.addMouseListener(mouseListener);
        jListNormalPatients.addMouseListener(mouseListener);
    }
    
    private static void showErrorNoSelection() {
        JOptionPane.showMessageDialog(null, 
                                      "No item is selected", 
                                      "Remove Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
    private void saveAllData(boolean info) {
        msDB.saveData();        /* Save data to disk */
        
        /* Show info after saving process was done */
        if (info) JOptionPane.showMessageDialog(null, 
                                        "Successful saving", 
                                        "Save Process Info", 
                                        JOptionPane.INFORMATION_MESSAGE);
        
        mustSave = false;       /* save prompt on exit */
    }
    private void confirmExit() {
        int selectedOption;
        
        if (mustSave) {
            /* Get user response from (yes, no & cancel) dialog */
            selectedOption = JOptionPane.showConfirmDialog(null, 
                                      "Save changes?", 
                                      "Choose", 
                                      JOptionPane.YES_NO_CANCEL_OPTION);
        }
        else {
            selectedOption = JOptionPane.NO_OPTION;
        }
        
        /* Consequences of response to the question of 'Save Changes?' */
        switch (selectedOption) {
            case JOptionPane.YES_OPTION:    /* If the user selected 'YES' */
                saveAllData(false);         /* Save data w/o showing message */
                System.exit(0);             /* Close program afterwards */
            case JOptionPane.NO_OPTION:     /* If the user selected 'NO' */
                System.exit(0);             /* Close program w/o saving */
            default:                        /* If the user selected 'CANCEL' */
                break;                      /* Do nothing */
        }
    }
    public final void updateAppointmentsNumber() {
        String number = String.valueOf(msDB.appointment.size());
        jlAppointments.setText(STR_APPOINTMENTS + " (" + number + ")");
    }
    public final void updateDoctorsNumber() {
        String number = String.valueOf(msDB.doctor.size());
        jlDoctors.setText(STR_DOCTORS + " (" + number + ")");
    }
    public final void updateEmergencyPatientsNumber() {
        String number = String.valueOf(msDB.ePatient.size());
        jlEPatients.setText(STR_EMERGENCY_PATIENTS + " (" + number + ")");
    }
    public final void updateNormalPatientsNumber() {
        String number = String.valueOf(msDB.nPatient.size());
        jlNPatients.setText(STR_NORMAL_PATIENTS + " (" + number + ")");
    }
    private void showAppointments() {
        for (Appointment i : msDB.appointment) {
            appointmentsList.addElement(i);
        }
    }
    private void showDoctors() {
        for (Doctor i : msDB.doctor) {
            doctorsList.addElement(i);
        }
    }
    private void showEmergencyPatients() {
        for (EmergencyPatient i : msDB.ePatient) {
            ePatientsList.addElement(i);
        }
    }
    private void showNormalPatients() {
        for (NormalPatient i : msDB.nPatient) {
            nPatientsList.addElement(i);
        }
    }
    private void removeAppointment() {
        /* Get selected item index ('-1' if not selected) */
        int selectedItem = jListAppointments.getSelectedIndex();
        
        /* check if an item is selected */
        if (selectedItem != -1) {
            /* Ask user to confirm */
            int selectedOption = JOptionPane.showConfirmDialog(null, 
                                        "Are you sure you want to remove?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
            
            /* If user selected 'YES' */
            if (selectedOption == JOptionPane.YES_OPTION) {
                msDB.appointment.remove(selectedItem);
                                                /* Remove from db */
                appointmentsList.remove(selectedItem);
                                                /* Remove item (visually) */
                updateAppointmentsNumber();     /* Update number (visually) */
                mustSave = true;
            } else {
                // DO NOTHING
            }
        } else {
            /* Show info after saving process was done */
            showErrorNoSelection();
        }
    }
    private void removeDoctor() {
        /* Get selected item index ('-1' if not selected) */
        int selectedItem = jListDoctors.getSelectedIndex();
        
        /* check if an item is selected */
        if (selectedItem != -1) {
            /* Ask user to confirm */
            int selectedOption = JOptionPane.showConfirmDialog(null, 
                                        "Are you sure you want to remove?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
            
            /* If user selected 'YES' */
            if (selectedOption == JOptionPane.YES_OPTION) {
                String currentID = msDB.doctor.get(selectedItem).ID;
                boolean notifyFlag = false;
                for (int i=0; i<msDB.appointment.size(); ++i) {
                    Appointment a = msDB.appointment.get(i);
                    if (a.doctorID.equals(currentID)) {
                        a.doctorID = "null";
                        appointmentsList.set(i, a.toString());
                        notifyFlag = true;
                    }
                }
                
                msDB.doctor.remove(selectedItem);
                                                /* Remove from db */
                doctorsList.remove(selectedItem);
                                                /* Remove item (visually) */
                updateDoctorsNumber();          /* Update number (visually) */
                
                if (notifyFlag) {
                    JOptionPane.showMessageDialog(null, 
                                        "Due to doctor removal, some appointments are not fully defined!", 
                                        "Remove Info",
                                        JOptionPane.INFORMATION_MESSAGE);
                }
                mustSave = true;
            } else {
                // DO NOTHING
            }
        } else {
            /* Show info after saving process was done */
            showErrorNoSelection();
        }
    }
    private void removeEmergencyPatient() {
        /* Get selected item index ('-1' if not selected) */
        int selectedItem = jListEmergencyPatients.getSelectedIndex();
        
        /* check if an item is selected */
        if (selectedItem != -1) {
            /* Ask user to confirm */
            int selectedOption1 = JOptionPane.showConfirmDialog(null, 
                                        "Are you sure you want to remove?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
            
            /* Get current ID */
            String curID = msDB.ePatient.get(selectedItem).ID;
            
            /* If user selected 'YES' */
            if (selectedOption1 == JOptionPane.YES_OPTION) {
                int length = msDB.appointment.size();
                boolean askedBefore = false,
                        hasBeenRemoved = false,
                        hasLinkedAppointment = false;
                
                for (int i=0; i<length; ) {
                    if (msDB.appointment.get(i).patientID.equals(curID)) {
                        if (!askedBefore) {
                            hasLinkedAppointment = true;
                            askedBefore = true;
                            
                            /* Ask  */
                            int selectedOption2 = JOptionPane.showConfirmDialog(null, 
                                        "All linked appointments will be removed as well\nContinue?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
                            
                            /* If user seleted 'YES' again */
                            if (selectedOption2 == JOptionPane.YES_OPTION) {
                                msDB.ePatient.remove(selectedItem); /* Remove from db */                 
                                ePatientsList.remove(selectedItem); /* Remove item (visually) */
                                updateEmergencyPatientsNumber();    /* Update number (visually) */
                                mustSave = true;                    /* save prompt on exit */
                                hasBeenRemoved = true;
                            } else {
                                break;
                            }
                        }
                        msDB.appointment.remove(i); /* Remove from db */                 
                        appointmentsList.remove(i); /* Remove item (visually) */
                        --length;
                    } else  ++i;
                }
                
                if (!hasBeenRemoved && !hasLinkedAppointment) {
                    msDB.ePatient.remove(selectedItem); /* Remove from db */                 
                    ePatientsList.remove(selectedItem); /* Remove item (visually) */
                    updateEmergencyPatientsNumber();    /* Update number (visually) */
                    mustSave = true;                    /* save prompt on exit */
                }
                
                updateAppointmentsNumber();
            } else {
                // DO NOTHING
            }
        } else {
            /* Show info after saving process was done */
            showErrorNoSelection();
        }
    }
    private void removeNormalPatient() {
        /* Get selected item index ('-1' if not selected) */
        int selectedItem = jListNormalPatients.getSelectedIndex();
        
        /* check if an item is selected */
        if (selectedItem != -1) {
            /* Ask user to confirm */
            int selectedOption = JOptionPane.showConfirmDialog(null, 
                                        "Are you sure you want to remove?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
            
            /* Get current ID */
            String curID = msDB.nPatient.get(selectedItem).ID;
            
            /* If user selected 'YES' */
            if (selectedOption == JOptionPane.YES_OPTION) {
                int length = msDB.appointment.size();
                boolean askedBefore = false,
                        hasBeenRemoved = false,
                        hasLinkedAppointment = false;
                
                for (int i=0; i<length; ) {
                    if (msDB.appointment.get(i).patientID.equals(curID)) {
                        if (!askedBefore) {
                            hasLinkedAppointment = true;
                            askedBefore = true;
                            
                            /* Ask  */
                            int selectedOption2 = JOptionPane.showConfirmDialog(null, 
                                        "All linked appointments will be removed as well\nContinue?", 
                                        "Choose",
                                        JOptionPane.YES_NO_OPTION);
                            
                            /* If user seleted 'YES' again */
                            if (selectedOption2 == JOptionPane.YES_OPTION) {
                                msDB.nPatient.remove(selectedItem); /* Remove from db */                 
                                nPatientsList.remove(selectedItem); /* Remove item (visually) */
                                updateNormalPatientsNumber();       /* Update number (visually) */
                                mustSave = true;                    /* save prompt on exit */
                                hasBeenRemoved = true;
                            } else {
                                break;
                            }
                        }
                        msDB.appointment.remove(i); /* Remove from db */                 
                        appointmentsList.remove(i); /* Remove item (visually) */
                        --length;
                    } else  ++i;
                }
                
                if (!hasBeenRemoved && !hasLinkedAppointment) {
                    msDB.nPatient.remove(selectedItem); /* Remove from db */                 
                    nPatientsList.remove(selectedItem); /* Remove item (visually) */
                    updateNormalPatientsNumber();       /* Update number (visually) */
                    mustSave = true;                    /* save prompt on exit */
                }
                
                updateAppointmentsNumber();
            } else {
                // DO NOTHING
            }
        } else {
            /* Show info after saving process was done */
            showErrorNoSelection();
        }
    }
    private void editAppointment(int index) {
        Appointment a = new Appointment(index);
        appointmentUI = new AppointmentUI(false, a);
    }
    private void editDoctor(int index) {
        Doctor d = new Doctor(index);
        doctorUI = new DoctorUI(false, d);
    }
    private void editEmergencyPatient(int index) {
        EmergencyPatient eP = new EmergencyPatient(true, index);
        patientUI = new PatientUI(false, true, eP);
    }
    private void editNormalPatient(int index) {
        EmergencyPatient nP = new EmergencyPatient(false, index);
        patientUI = new PatientUI(false, false, nP);
    }
    private void addAppointment() {
        msDB.appointment.add(new Appointment());
        appointmentsList.addElement(STR_ENTRY_APPOINTMENT);
        updateAppointmentsNumber();
        new AppointmentUI(true, new Appointment(appointmentsList.size()-1));
    }
    private void addDoctor() {
        msDB.doctor.add(new Doctor());
        doctorsList.addElement(STR_ENTRY_DOCTOR);
        updateDoctorsNumber();
        new DoctorUI(true, new Doctor(doctorsList.size()-1));
    }
    private void addPatient(boolean isEmergent) {
        if (isEmergent) {
            msDB.ePatient.add(new EmergencyPatient());
            ePatientsList.addElement(STR_ENTRY_EPATIENT);
            updateEmergencyPatientsNumber();
            new PatientUI(true, true, new EmergencyPatient(true, ePatientsList.size()-1));
        } else {
            msDB.nPatient.add(new NormalPatient());
            nPatientsList.addElement(STR_ENTRY_NPATIENT);
            updateNormalPatientsNumber();
            new PatientUI(true, false, new EmergencyPatient(false, nPatientsList.size()-1));
        }
    }
    public static boolean uniqueID(String id) {
        /* Check if ID was saved before */
        if (!msDB.doctor.stream().noneMatch(i -> (i.ID.equals(id)))) return false;
        if (!msDB.ePatient.stream().noneMatch(i -> (i.ID.equals(id)))) return false;
        if (!msDB.nPatient.stream().noneMatch(i -> (i.ID.equals(id)))) return false;

        return true;
    }
    public static int getDoctorIndex(String id) {
        for (int i=0; i<msDB.doctor.size(); ++i) {
            if (msDB.doctor.get(i).ID.equals(id)) {
                return i;
            }
        }
        return -1;
    }
    public static int getPatientIndex(String id) {
        for (int i=0; i<msDB.ePatient.size(); ++i) {
            if (msDB.ePatient.get(i).ID.equals(id)) {
                return i+2;
            }
        }
        for (int i=0; i<msDB.nPatient.size(); ++i) {
            if (msDB.nPatient.get(i).ID.equals(id)) {
                return i + msDB.ePatient.size() + 3;
            }
        }
        return -1;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListAppointments = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListDoctors = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListEmergencyPatients = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListNormalPatients = new javax.swing.JList<>();
        jlAppointments = new javax.swing.JLabel();
        jlDoctors = new javax.swing.JLabel();
        jlEPatients = new javax.swing.JLabel();
        jlNPatients = new javax.swing.JLabel();
        bAddApp = new javax.swing.JButton();
        bRemApp = new javax.swing.JButton();
        bAddDoc = new javax.swing.JButton();
        bRemDoc = new javax.swing.JButton();
        bAddEPat = new javax.swing.JButton();
        bRemEPat = new javax.swing.JButton();
        bAddNPat = new javax.swing.JButton();
        bRemNPat = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        miNew = new javax.swing.JMenuItem();
        miSave = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miExit = new javax.swing.JMenuItem();
        mEdit = new javax.swing.JMenu();
        miAddDoctor = new javax.swing.JMenuItem();
        miAddPatient = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        miAddAppointment = new javax.swing.JMenuItem();
        mHelp = new javax.swing.JMenu();
        miAbout = new javax.swing.JMenuItem();
        miGithub = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Healthcare Management System");
        setLocation(new java.awt.Point(100, 100));
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1000, 302));
        setPreferredSize(new java.awt.Dimension(1000, 302));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DIU CSE222 - Healthcare Management System");

        jScrollPane1.setViewportView(jListAppointments);

        jScrollPane2.setViewportView(jListDoctors);

        jScrollPane3.setViewportView(jListEmergencyPatients);

        jScrollPane4.setViewportView(jListNormalPatients);

        jlAppointments.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlAppointments.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlAppointments.setText("Appointments (0)");

        jlDoctors.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlDoctors.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlDoctors.setText("Doctors (0)");

        jlEPatients.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlEPatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlEPatients.setText("Emergency Patients (0)");

        jlNPatients.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlNPatients.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlNPatients.setText("Normal Patients (0)");

        bAddApp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bAddApp.setText("Add");
        bAddApp.setMaximumSize(new java.awt.Dimension(50, 23));
        bAddApp.setMinimumSize(new java.awt.Dimension(50, 23));
        bAddApp.setPreferredSize(new java.awt.Dimension(50, 23));
        bAddApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddAppActionPerformed(evt);
            }
        });

        bRemApp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bRemApp.setText("Remove");
        bRemApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemAppActionPerformed(evt);
            }
        });

        bAddDoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bAddDoc.setText("Add");
        bAddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddDocActionPerformed(evt);
            }
        });

        bRemDoc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bRemDoc.setText("Remove");
        bRemDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemDocActionPerformed(evt);
            }
        });

        bAddEPat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bAddEPat.setText("Add");
        bAddEPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddEPatActionPerformed(evt);
            }
        });

        bRemEPat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bRemEPat.setText("Remove");
        bRemEPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemEPatActionPerformed(evt);
            }
        });

        bAddNPat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bAddNPat.setText("Add");
        bAddNPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAddNPatActionPerformed(evt);
            }
        });

        bRemNPat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        bRemNPat.setText("Remove");
        bRemNPat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRemNPatActionPerformed(evt);
            }
        });

        mFile.setText("File");

        miNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        miNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_new.png"))); // NOI18N
        miNew.setText("New");
        miNew.setToolTipText("");
        miNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miNewActionPerformed(evt);
            }
        });
        mFile.add(miNew);

        miSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        miSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_save.png"))); // NOI18N
        miSave.setText("Save");
        miSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveActionPerformed(evt);
            }
        });
        mFile.add(miSave);
        mFile.add(jSeparator1);

        miExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        miExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_exit.png"))); // NOI18N
        miExit.setText("Exit");
        miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miExitActionPerformed(evt);
            }
        });
        mFile.add(miExit);

        jMenuBar1.add(mFile);

        mEdit.setText("Edit");

        miAddDoctor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_doctor.png"))); // NOI18N
        miAddDoctor.setText("Add Doctor");
        miAddDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddDoctorActionPerformed(evt);
            }
        });
        mEdit.add(miAddDoctor);

        miAddPatient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_patient.png"))); // NOI18N
        miAddPatient.setText("Add Patient");
        miAddPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddPatientActionPerformed(evt);
            }
        });
        mEdit.add(miAddPatient);
        mEdit.add(jSeparator2);

        miAddAppointment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_appointment.png"))); // NOI18N
        miAddAppointment.setText("Make Appointment");
        miAddAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAddAppointmentActionPerformed(evt);
            }
        });
        mEdit.add(miAddAppointment);

        jMenuBar1.add(mEdit);

        mHelp.setText("Help");

        miAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        miAbout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_about.png"))); // NOI18N
        miAbout.setText("About");
        miAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miAboutActionPerformed(evt);
            }
        });
        mHelp.add(miAbout);

        miGithub.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        miGithub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/healthcaremanagementsystem/img_github.png"))); // NOI18N
        miGithub.setText("Github");
        miGithub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGithubActionPerformed(evt);
            }
        });
        mHelp.add(miGithub);

        jMenuBar1.add(mHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bAddApp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(bRemApp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jlAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bAddDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(bRemDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jlDoctors, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlEPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(bAddEPat, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(bRemEPat, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bAddNPat, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(bRemNPat, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jlNPatients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bRemNPat)
                            .addComponent(bAddNPat)
                            .addComponent(bRemEPat)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlAppointments)
                                    .addComponent(jlDoctors)
                                    .addComponent(jlEPatients)
                                    .addComponent(jlNPatients))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jScrollPane2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bAddDoc)
                            .addComponent(bRemDoc)
                            .addComponent(bRemApp)
                            .addComponent(bAddApp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bAddEPat))))
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(816, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void miSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveActionPerformed
        saveAllData(true);
    }//GEN-LAST:event_miSaveActionPerformed
    private void miNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miNewActionPerformed
        /* Get user response from (yes, no) dialog */
        int selectedOption = JOptionPane.showConfirmDialog(null, 
                                  "All data will be lost, are you sure?", 
                                  "Choose", 
                                  JOptionPane.OK_CANCEL_OPTION);
        if (selectedOption == JOptionPane.OK_OPTION) {
            /* Clear database */
            msDB.appointment.clear();
            msDB.doctor.clear();
            msDB.ePatient.clear();
            msDB.nPatient.clear();

            /* Clear data (visually) */
            appointmentsList.clear();
            doctorsList.clear();
            ePatientsList.clear();
            nPatientsList.clear();

            /* Update counter of each category */
            updateAppointmentsNumber();
            updateDoctorsNumber();
            updateEmergencyPatientsNumber();
            updateNormalPatientsNumber();
        }
    }//GEN-LAST:event_miNewActionPerformed
    private void miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miExitActionPerformed
        confirmExit();
    }//GEN-LAST:event_miExitActionPerformed
    private void miAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAboutActionPerformed
        System.out.println("The software is made for CSE222 Project");
        System.out.println("Made & Managed by: >_Speedout");
                /* Show error message */
        JOptionPane.showMessageDialog(null, 
                                      "The software under development for CSE222 Project\nModify by: Team SpeedOut", 
                                      "About",
                                      JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_miAboutActionPerformed
    private void miAddDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddDoctorActionPerformed
        addDoctor();
    }//GEN-LAST:event_miAddDoctorActionPerformed
    private void miAddPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddPatientActionPerformed
        addPatient(false);
    }//GEN-LAST:event_miAddPatientActionPerformed
    private void miAddAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miAddAppointmentActionPerformed
        addAppointment();
    }//GEN-LAST:event_miAddAppointmentActionPerformed
    private void miGithubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGithubActionPerformed
        String uri = GITHUB;
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(uri));
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(ManagementSystemUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_miGithubActionPerformed

    private void bAddAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddAppActionPerformed
        addAppointment();
    }//GEN-LAST:event_bAddAppActionPerformed
    private void bRemAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemAppActionPerformed
        removeAppointment();
    }//GEN-LAST:event_bRemAppActionPerformed

    private void bAddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddDocActionPerformed
        addDoctor();
    }//GEN-LAST:event_bAddDocActionPerformed
    private void bRemDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemDocActionPerformed
        removeDoctor();
    }//GEN-LAST:event_bRemDocActionPerformed

    private void bAddEPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddEPatActionPerformed
        addPatient(true);
    }//GEN-LAST:event_bAddEPatActionPerformed
    private void bRemEPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemEPatActionPerformed
        removeEmergencyPatient();
    }//GEN-LAST:event_bRemEPatActionPerformed

    private void bAddNPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAddNPatActionPerformed
        addPatient(false);
    }//GEN-LAST:event_bAddNPatActionPerformed
    private void bRemNPatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRemNPatActionPerformed
        removeNormalPatient();
    }//GEN-LAST:event_bRemNPatActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddApp;
    private javax.swing.JButton bAddDoc;
    private javax.swing.JButton bAddEPat;
    private javax.swing.JButton bAddNPat;
    private javax.swing.JButton bRemApp;
    private javax.swing.JButton bRemDoc;
    private javax.swing.JButton bRemEPat;
    private javax.swing.JButton bRemNPat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jListAppointments;
    private javax.swing.JList<String> jListDoctors;
    private javax.swing.JList<String> jListEmergencyPatients;
    private javax.swing.JList<String> jListNormalPatients;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel jlAppointments;
    private javax.swing.JLabel jlDoctors;
    private javax.swing.JLabel jlEPatients;
    private javax.swing.JLabel jlNPatients;
    private javax.swing.JMenu mEdit;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mHelp;
    private javax.swing.JMenuItem miAbout;
    private javax.swing.JMenuItem miAddAppointment;
    private javax.swing.JMenuItem miAddDoctor;
    private javax.swing.JMenuItem miAddPatient;
    private javax.swing.JMenuItem miExit;
    private javax.swing.JMenuItem miGithub;
    private javax.swing.JMenuItem miNew;
    private javax.swing.JMenuItem miSave;
    // End of variables declaration//GEN-END:variables
    
    /* PROGRAM VARIABLES */
    /* Main Object that holds all data in it */
    static ManagementSystem msDB;
    static AppointmentUI appointmentUI;
    static DoctorUI doctorUI;
    static PatientUI patientUI;
    static boolean mustSave = false;
    
    /* DefaultListModels for JLists */
    static DefaultListModel appointmentsList;
    static DefaultListModel doctorsList;
    static DefaultListModel ePatientsList;
    static DefaultListModel nPatientsList;
    
    /* Final Variables */
    final static String STR_APPOINTMENTS = "Appointments";
    final static String STR_DOCTORS = "Doctors";
    final static String STR_EMERGENCY_PATIENTS = "Emergency Patients";
    final static String STR_NORMAL_PATIENTS = "Normal Patients";
    final static String STR_ENTRY_APPOINTMENT = "<New Appointment Form>";
    final static String STR_ENTRY_DOCTOR = "<New Doctor Form>";
    final static String STR_ENTRY_EPATIENT = "<New Emergency Patient Form>";
    final static String STR_ENTRY_NPATIENT = "<New Normal Patient Form>";
    final static String GITHUB = "https://github.com/biplobsd/healthcare-management-system";
}
