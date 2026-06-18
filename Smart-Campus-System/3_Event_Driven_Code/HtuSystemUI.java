package Event_driven;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;



public class HtuSystemUI extends JFrame{
	   private ArrayList<HtuWorkshop> masterWorkshopList = new ArrayList<>();
	    private HtuStudent currentActiveStudent = null;

	    private CardLayout screenLayout = new CardLayout();
	    private JPanel mainContainerPanel = new JPanel(screenLayout);
	    private JPanel screenLogin, screenStudent, screenAdmin;

	    private JTable tableAvailableWorkshops, tableStudentSchedule, tableAdminReport;
	    private DefaultTableModel modelAvailable, modelSchedule, modelAdminReport;
	    private JTextArea areaParticipantsDisplay;

	    public HtuSystemUI() {
	        loadSystemData();

	        setTitle("HTU - Workshop Management System");
	        setSize(850, 550);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        createLoginScreen();
	        createStudentScreen();
	        createAdminScreen();

	        mainContainerPanel.add(screenLogin, "LoginScreen");
	        mainContainerPanel.add(screenStudent, "StudentScreen");
	        mainContainerPanel.add(screenAdmin, "AdminScreen");

	        add(mainContainerPanel);
	        screenLayout.show(mainContainerPanel, "LoginScreen");
	    }

	    private void loadSystemData() {
	        masterWorkshopList.add(new HtuWorkshop("C Programming", "10am", "S-204", 25));
	        masterWorkshopList.add(new HtuWorkshop("Advanced C", "12pm", "S-204", 25));
	        masterWorkshopList.add(new HtuWorkshop("Java Basics", "10am", "S-205", 25));
	        masterWorkshopList.add(new HtuWorkshop("Advanced Java", "12pm", "S-205", 25));
	        masterWorkshopList.add(new HtuWorkshop("Robotics 1", "9am", "N-203", 15));
	        masterWorkshopList.add(new HtuWorkshop("Robotics 2", "12pm", "N-203", 15));
	    }

	    private void createLoginScreen() {
	        screenLogin = new JPanel(null);
	        screenLogin.setBackground(new Color(245, 247, 250));

	        JLabel lblTitle = new JLabel("Workshop Registration System", SwingConstants.CENTER);
	        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
	        lblTitle.setBounds(150, 40, 550, 40);
	        screenLogin.add(lblTitle);

	        JLabel lblName = new JLabel("Name:");
	        lblName.setBounds(250, 150, 80, 30);
	        JTextField txtName = new JTextField();
	        txtName.setBounds(340, 150, 200, 30);
	        screenLogin.add(lblName);
	        screenLogin.add(txtName);

	        JLabel lblId = new JLabel("ID:");
	        lblId.setBounds(250, 200, 80, 30);
	        JTextField txtId = new JTextField();
	        txtId.setBounds(340, 200, 200, 30);
	        screenLogin.add(lblId);
	        screenLogin.add(txtId);

	        JLabel lblPass = new JLabel("Admin Password (If Admin):");
	        lblPass.setBounds(150, 250, 180, 30);
	        JPasswordField txtPass = new JPasswordField();
	        txtPass.setBounds(340, 250, 200, 30);
	        screenLogin.add(lblPass);
	        screenLogin.add(txtPass);

	        JButton btnLogin = new JButton("Login");
	        btnLogin.setBounds(340, 320, 200, 40);
	        screenLogin.add(btnLogin);

	        btnLogin.addActionListener(e -> {
	            try {
	                String name = txtName.getText().trim();
	                int id = Integer.parseInt(txtId.getText().trim());
	                String password = new String(txtPass.getPassword());

	                if (name.isEmpty()) {
	                    JOptionPane.showMessageDialog(this, "Please enter your name.", "Input Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }

	                if (id >= 1 && id <= 100) {
	                    if (password.equals("admin123")) {
	                        updateAdminReportData();
	                        screenLayout.show(mainContainerPanel, "AdminScreen");
	                    } else {
	                        JOptionPane.showMessageDialog(this, "Wrong Admin Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
	                    }
	                } 
	                else if (id > 100 && id < 26000000) {
	                    currentActiveStudent = new HtuStudent(name, id);
	                    
	                    txtName.setText("");
	                    txtId.setText("");
	                    txtPass.setText("");
	                    
	                    updateAvailableWorkshopsTable();
	                    updateStudentScheduleTable();
	                    screenLayout.show(mainContainerPanel, "StudentScreen");
	                } else {
	                    JOptionPane.showMessageDialog(this, "ID out of range!", "Input Error", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (NumberFormatException ex) {
	                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });
	    }

	    private void createStudentScreen() {
	        screenStudent = new JPanel(new BorderLayout(10, 10));
	        screenStudent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        String[] colsAvailable = {"#", "Workshop Title", "Time", "Location", "Seats Left"};
	        modelAvailable = new DefaultTableModel(colsAvailable, 0);
	        tableAvailableWorkshops = new JTable(modelAvailable);
	        JScrollPane scrollAvailable = new JScrollPane(tableAvailableWorkshops);
	        scrollAvailable.setBorder(BorderFactory.createTitledBorder("Available Workshops (Select to Register)"));

	        String[] colsSchedule = {"Workshop Title", "Time", "Location"};
	        modelSchedule = new DefaultTableModel(colsSchedule, 0);
	        tableStudentSchedule = new JTable(modelSchedule);
	        JScrollPane scrollSchedule = new JScrollPane(tableStudentSchedule);
	        scrollSchedule.setBorder(BorderFactory.createTitledBorder("My Current Schedule (Select to Unregister)"));

	        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
	        centerPanel.add(scrollAvailable);
	        centerPanel.add(scrollSchedule);
	        screenStudent.add(centerPanel, BorderLayout.CENTER);

	        JPanel bottomControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	        JButton btnRegister = new JButton("Register Selected");
	        JButton btnUnregister = new JButton("Unregister Selected");
	        JButton btnLogout = new JButton("Logout System");
	        
	        bottomControlPanel.add(btnRegister);
	        bottomControlPanel.add(btnUnregister);
	        bottomControlPanel.add(btnLogout);
	        screenStudent.add(bottomControlPanel, BorderLayout.SOUTH);

	        btnRegister.addActionListener(e -> {
	            int selectedRow = tableAvailableWorkshops.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(this, "Please select a workshop from the top table first.");
	                return;
	            }

	            HtuWorkshop selected = masterWorkshopList.get(selectedRow);

	            for (HtuWorkshop w : currentActiveStudent.getEnrolledWorkshops()) {
	                if (w.getSessionTime().equals(selected.getSessionTime())) {
	                    JOptionPane.showMessageDialog(this, "Time conflict! You already have a workshop at " + selected.getSessionTime(), "Registration Failed", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	            }

	            if (currentActiveStudent.getEnrolledWorkshops().contains(selected)) {
	                JOptionPane.showMessageDialog(this, "You are already registered in this workshop!", "Registration Failed", JOptionPane.WARNING_MESSAGE);
	                return;
	            }

	            if (selected.registerStudent(currentActiveStudent)) {
	                currentActiveStudent.getEnrolledWorkshops().add(selected);
	                JOptionPane.showMessageDialog(this, "Successfully registered in: " + selected.getSubject());
	                updateAvailableWorkshopsTable();
	                updateStudentScheduleTable();
	            } else {
	                JOptionPane.showMessageDialog(this, "Sorry, this workshop is full!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        btnUnregister.addActionListener(e -> {
	            int selectedRow = tableStudentSchedule.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(this, "Please select a workshop from your schedule to unregister.");
	                return;
	            }

	            HtuWorkshop selected = currentActiveStudent.getEnrolledWorkshops().get(selectedRow);
	            selected.unregisterStudent(currentActiveStudent.getUserCardId());
	            currentActiveStudent.getEnrolledWorkshops().remove(selected);
	            
	            JOptionPane.showMessageDialog(this, "Successfully removed from: " + selected.getSubject());
	            updateAvailableWorkshopsTable();
	            updateStudentScheduleTable();
	        });

	        btnLogout.addActionListener(e -> {
	            currentActiveStudent = null;
	            screenLayout.show(mainContainerPanel, "LoginScreen");
	        });
	    }

	    private void createAdminScreen() {
	        screenAdmin = new JPanel(new BorderLayout(10, 10));
	        screenAdmin.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        String[] colsAdmin = {"#", "Workshop Title", "Time", "Location", "Registered Students", "Capacity"};
	        modelAdminReport = new DefaultTableModel(colsAdmin, 0);
	        tableAdminReport = new JTable(modelAdminReport);
	        JScrollPane scrollAdmin = new JScrollPane(tableAdminReport);
	        scrollAdmin.setBorder(BorderFactory.createTitledBorder("System Workshops Report"));

	        areaParticipantsDisplay = new JTextArea();
	        areaParticipantsDisplay.setEditable(false);
	        areaParticipantsDisplay.setFont(new Font("Monospaced", Font.PLAIN, 13));
	        JScrollPane scrollPanelArea = new JScrollPane(areaParticipantsDisplay);
	        scrollPanelArea.setPreferredSize(new Dimension(280, 0));
	        scrollPanelArea.setBorder(BorderFactory.createTitledBorder("Registered Students List"));

	        screenAdmin.add(scrollAdmin, BorderLayout.CENTER);
	        screenAdmin.add(scrollPanelArea, BorderLayout.EAST);

	        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	        JButton btnView = new JButton("View Participants");
	        JButton btnAdminExit = new JButton("Exit Admin Panel");
	        panelButtons.add(btnView);
	        panelButtons.add(btnAdminExit);
	        screenAdmin.add(panelButtons, BorderLayout.SOUTH);

	        btnView.addActionListener(e -> {
	            int selectedRow = tableAdminReport.getSelectedRow();
	            if (selectedRow == -1) {
	                JOptionPane.showMessageDialog(this, "Please select a workshop from the report table.");
	                return;
	            }

	            HtuWorkshop w = masterWorkshopList.get(selectedRow);
	            ArrayList<HtuStudent> list = w.getStudentList();
	            
	            heapSort(list);

	            StringBuilder sb = new StringBuilder();
	            sb.append("Workshop: ").append(w.getSubject()).append("\n");
	            sb.append("============================\n");
	            if (list.isEmpty()) {
	                sb.append("No registered students yet.");
	            } else {
	                for (HtuStudent s : list) {
	                    sb.append("• ID: ").append(s.getUserCardId()).append("\n  Name: ").append(s.getFullName()).append("\n---\n");
	                }
	            }
	            areaParticipantsDisplay.setText(sb.toString());
	        });

	        btnAdminExit.addActionListener(e -> {
	            areaParticipantsDisplay.setText("");
	            screenLayout.show(mainContainerPanel, "LoginScreen");
	        });
	    }

	    private void heapSort(ArrayList<HtuStudent> list) {
	        int n = list.size();

	        for (int i = n / 2 - 1; i >= 0; i--) {
	            heapify(list, n, i);
	        }

	        for (int i = n - 1; i > 0; i--) {
	            HtuStudent temp = list.get(0);
	            list.set(0, list.get(i));
	            list.set(i, temp);

	            heapify(list, i, 0);
	        }
	    }

	    
	    private void heapify(ArrayList<HtuStudent> list, int n, int i) {
	        int max = i; 
	        int l = 2 * i + 1; 
	        int r = 2 * i + 2; 

	        if (l < n && list.get(l).getUserCardId() > list.get(max).getUserCardId()) {
	            max = l;
	        }

	        if (r < n && list.get(r).getUserCardId() > list.get(max).getUserCardId()) {
	            max = r;
	        }

	        if (max != i) {
	            HtuStudent swap = list.get(i);
	            list.set(i, list.get(max));
	            list.set(max, swap);

	            heapify(list, n, max);
	        }
	    }

	    private void updateAvailableWorkshopsTable() {
	        modelAvailable.setRowCount(0);
	        for (int i = 0; i < masterWorkshopList.size(); i++) {
	            HtuWorkshop w = masterWorkshopList.get(i);
	            modelAvailable.addRow(new Object[]{ (i + 1), w.getSubject(), w.getSessionTime(), w.getLabLocation(), w.getFreeSeats() });
	        }
	    }

	    private void updateStudentScheduleTable() {
	        modelSchedule.setRowCount(0);
	        if (currentActiveStudent != null) {
	            for (HtuWorkshop w : currentActiveStudent.getEnrolledWorkshops()) {
	                modelSchedule.addRow(new Object[]{ w.getSubject(), w.getSessionTime(), w.getLabLocation() });
	            }
	        }
	    }

	    private void updateAdminReportData() {
	        modelAdminReport.setRowCount(0);
	        for (int i = 0; i < masterWorkshopList.size(); i++) {
	            HtuWorkshop w = masterWorkshopList.get(i);
	            modelAdminReport.addRow(new Object[]{ (i + 1), w.getSubject(), w.getSessionTime(), w.getLabLocation(), w.getStudentList().size(), w.getMaxCapacity() });
	        }
	    }
}
