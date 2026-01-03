import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {
    private Stage primaryStage;
    private DataManager dataManager;
    private String currentUserRole;
    private String currentUserId;
    private Scene currentScene;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dataManager = new DataManager();
        
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setMaximized(true);
        
        showLoginPage();
        primaryStage.show();
    }

    private void showLoginPage() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(50));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");

        VBox loginBox = new VBox(15);
        loginBox.setMaxWidth(400);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setPadding(new Insets(40));
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);");

        Label titleLabel = new Label("Hospital Management System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#667eea"));

        Label subtitleLabel = new Label("Please login to continue");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.GRAY);

        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Doctor", "Patient", "Nurse", "Administrative Staff", "Hospital Admin");
        roleCombo.setPromptText("Select Role");
        roleCombo.setMaxWidth(Double.MAX_VALUE);
        roleCombo.setStyle("-fx-font-size: 14px;");

        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");
        userIdField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        Button loginButton = UIComponents.createStyledButton("Login", "#667eea");
        loginButton.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);

        loginButton.setOnAction(e -> {
            String role = roleCombo.getValue();
            String userId = userIdField.getText().trim();

            if (role == null || userId.isEmpty()) {
                errorLabel.setText("Please select role and enter User ID");
                errorLabel.setVisible(true);
                return;
            }

            if (authenticateUser(role, userId)) {
                currentUserRole = role;
                currentUserId = userId;
                showDashboard();
            } else {
                errorLabel.setText("Invalid credentials");
                errorLabel.setVisible(true);
            }
        });

        loginBox.getChildren().addAll(titleLabel, subtitleLabel, roleCombo, userIdField, loginButton, errorLabel);
        root.getChildren().add(loginBox);

        currentScene = new Scene(root, 1200, 800);
        primaryStage.setScene(currentScene);
    }

    private boolean authenticateUser(String role, String userId) {
        switch (role) {
            case "Doctor":
                return dataManager.getClinicians().stream().anyMatch(c -> c.getClinicianId().equals(userId) && (c.getSpecialization().equals("GP") || c.getSpecialization().contains("Consultant")));
            case "Patient":
                return dataManager.getPatients().stream().anyMatch(p -> p.getPatientId().equals(userId));
            case "Nurse":
                return dataManager.getClinicians().stream().anyMatch(c -> c.getClinicianId().equals(userId) && (c.getSpecialization().contains("Nurse") || c.getSpecialization().contains("Sister")));
            case "Administrative Staff":
                return dataManager.getStaff().stream().anyMatch(s -> s.getStaffId().equals(userId));
            case "Hospital Admin":
                return dataManager.getStaff().stream().anyMatch(s -> s.getStaffId().equals(userId) && s.getRole().equals("Hospital Administrator"));
            default:
                return false;
        }
    }

    private void showDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f5f5f5;");

        Label welcomeLabel = new Label("Welcome, " + currentUserRole + " (" + currentUserId + ")");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        welcomeLabel.setTextFill(Color.web("#333333"));

        VBox contentArea = new VBox(20);
        
        switch (currentUserRole) {
            case "Doctor":
                contentArea.getChildren().add(createDoctorDashboard());
                break;
            case "Patient":
                contentArea.getChildren().add(createPatientDashboard());
                break;
            case "Nurse":
                contentArea.getChildren().add(createNurseDashboard());
                break;
            case "Administrative Staff":
                contentArea.getChildren().add(createAdminStaffDashboard());
                break;
            case "Hospital Admin":
                contentArea.getChildren().add(createHospitalDashboard());
                break;
        }

        mainContent.getChildren().addAll(welcomeLabel, contentArea);
        
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f5f5f5;");
        
        root.setCenter(scrollPane);

        currentScene = new Scene(root, 1200, 800);
        primaryStage.setScene(currentScene);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPrefWidth(250);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");

        Label logoLabel = new Label("HMS");
        logoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        logoLabel.setTextFill(Color.WHITE);

        VBox menuItems = new VBox(5);
        
        switch (currentUserRole) {
            case "Doctor":
                menuItems.getChildren().addAll(
                    createMenuButton("Dashboard", e -> showDashboard()),
                    createMenuButton("My Patients", e -> showMyPatients()),
                    createMenuButton("Appointments", e -> showAppointments()),
                    createMenuButton("Prescribe Drug", e -> showPrescribeDrug())
                );
                break;
            case "Patient":
                menuItems.getChildren().addAll(
                    createMenuButton("Dashboard", e -> showDashboard()),
                    createMenuButton("Book Appointment", e -> showBookAppointment()),
                    createMenuButton("My Appointments", e -> showMyAppointments()),
                    createMenuButton("Prescriptions", e -> showMyPrescriptions())
                );
                break;
            case "Nurse":
                menuItems.getChildren().addAll(
                    createMenuButton("Dashboard", e -> showDashboard()),
                    createMenuButton("Monitor Patients", e -> showMonitorPatients()),
                    createMenuButton("Update Reports", e -> showUpdateReports())
                );
                break;
            case "Administrative Staff":
                menuItems.getChildren().addAll(
                    createMenuButton("Dashboard", e -> showDashboard()),
                    createMenuButton("Manage Appointments", e -> showManageAppointments()),
                    createMenuButton("Patient Registration", e -> showPatientRegistration()),
                    createMenuButton("View All Patients", e -> showAllPatients())
                );
                break;
            case "Hospital Admin":
                menuItems.getChildren().addAll(
                    createMenuButton("Dashboard", e -> showDashboard()),
                    createMenuButton("Hospital Details", e -> showHospitalDetails()),
                    createMenuButton("Manage Doctors", e -> showManageDoctors()),
                    createMenuButton("Manage Staff", e -> showManageStaff()),
                    createMenuButton("All Appointments", e -> showAllAppointments())
                );
                break;
        }

        Button logoutButton = createMenuButton("Logout", e -> showLoginPage());
        logoutButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logoLabel, new Separator(), menuItems, spacer, logoutButton);
        return sidebar;
    }

    private Button createMenuButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> handler) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10; -fx-cursor: hand;"));
        button.setOnAction(handler);
        return button;
    }

    private VBox createDoctorDashboard() {
        VBox dashboard = new VBox(20);
        
        Label title = new Label("Doctor Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        FlowPane statsPane = new FlowPane(20, 20);
        statsPane.setAlignment(Pos.CENTER_LEFT);

        long todayAppointments = dataManager.getAppointments().stream()
            .filter(a -> a.getClinicianId().equals(currentUserId) && a.getAppointmentDate().equals(LocalDate.now().toString()))
            .count();

        long totalPatients = dataManager.getAppointments().stream()
            .filter(a -> a.getClinicianId().equals(currentUserId))
            .map(Models.Appointment::getPatientId)
            .distinct()
            .count();

        statsPane.getChildren().addAll(
            UIComponents.createStatCard("Today's Appointments", String.valueOf(todayAppointments), "#3498db"),
            UIComponents.createStatCard("Total Patients", String.valueOf(totalPatients), "#2ecc71")
        );

        TableView<Models.Appointment> appointmentTable = createAppointmentTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getClinicianId().equals(currentUserId) && a.getAppointmentDate().equals(LocalDate.now().toString()))
                .collect(Collectors.toList())
        );

        VBox tableContainer = new VBox(10);
        Label tableTitle = new Label("Today's Appointments");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableContainer.getChildren().addAll(tableTitle, appointmentTable);

        dashboard.getChildren().addAll(title, statsPane, tableContainer);
        return dashboard;
    }

    private VBox createPatientDashboard() {
        VBox dashboard = new VBox(20);
        
        Label title = new Label("Patient Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        FlowPane statsPane = new FlowPane(20, 20);

        long myAppointments = dataManager.getAppointments().stream()
            .filter(a -> a.getPatientId().equals(currentUserId))
            .count();

        long activePrescriptions = dataManager.getPrescriptions().stream()
            .filter(p -> p.getPatientId().equals(currentUserId) && p.getStatus().equalsIgnoreCase("Active"))
            .count();

        statsPane.getChildren().addAll(
            UIComponents.createStatCard("My Appointments", String.valueOf(myAppointments), "#9b59b6"),
            UIComponents.createStatCard("Active Prescriptions", String.valueOf(activePrescriptions), "#e67e22")
        );

        TableView<Models.Appointment> upcomingTable = createAppointmentTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getPatientId().equals(currentUserId))
                .sorted((a1, a2) -> a2.getAppointmentDate().compareTo(a1.getAppointmentDate()))
                .limit(5)
                .collect(Collectors.toList())
        );

        VBox tableContainer = new VBox(10);
        Label tableTitle = new Label("Recent Appointments");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableContainer.getChildren().addAll(tableTitle, upcomingTable);

        dashboard.getChildren().addAll(title, statsPane, tableContainer);
        return dashboard;
    }

    private VBox createNurseDashboard() {
        VBox dashboard = new VBox(20);
        
        Label title = new Label("Nurse Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        FlowPane statsPane = new FlowPane(20, 20);

        long totalPatients = dataManager.getPatients().size();
        long todayAppointments = dataManager.getAppointments().stream()
            .filter(a -> a.getAppointmentDate().equals(LocalDate.now().toString()))
            .count();

        statsPane.getChildren().addAll(
            UIComponents.createStatCard("Total Patients", String.valueOf(totalPatients), "#1abc9c"),
            UIComponents.createStatCard("Today's Appointments", String.valueOf(todayAppointments), "#f39c12")
        );

        TableView<Models.Patient> patientTable = createPatientTable(dataManager.getPatients().stream().limit(10).collect(Collectors.toList()));

        VBox tableContainer = new VBox(10);
        Label tableTitle = new Label("Patients to Monitor");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableContainer.getChildren().addAll(tableTitle, patientTable);

        dashboard.getChildren().addAll(title, statsPane, tableContainer);
        return dashboard;
    }

    private VBox createAdminStaffDashboard() {
        VBox dashboard = new VBox(20);
        
        Label title = new Label("Administrative Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        FlowPane statsPane = new FlowPane(20, 20);

        long pendingAppointments = dataManager.getAppointments().stream()
            .filter(a -> a.getStatus().equalsIgnoreCase("Scheduled"))
            .count();

        long totalPatients = dataManager.getPatients().size();

        statsPane.getChildren().addAll(
            UIComponents.createStatCard("Pending Appointments", String.valueOf(pendingAppointments), "#e74c3c"),
            UIComponents.createStatCard("Total Patients", String.valueOf(totalPatients), "#34495e")
        );

        TableView<Models.Appointment> appointmentTable = createAppointmentTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getStatus().equalsIgnoreCase("Scheduled"))
                .limit(10)
                .collect(Collectors.toList())
        );

        VBox tableContainer = new VBox(10);
        Label tableTitle = new Label("Pending Appointments");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableContainer.getChildren().addAll(tableTitle, appointmentTable);

        dashboard.getChildren().addAll(title, statsPane, tableContainer);
        return dashboard;
    }

    private VBox createHospitalDashboard() {
        VBox dashboard = new VBox(20);
        
        Label title = new Label("Hospital Administration Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        FlowPane statsPane = new FlowPane(20, 20);

        statsPane.getChildren().addAll(
            UIComponents.createStatCard("Total Doctors", String.valueOf(dataManager.getClinicians().size()), "#3498db"),
            UIComponents.createStatCard("Total Patients", String.valueOf(dataManager.getPatients().size()), "#2ecc71"),
            UIComponents.createStatCard("Total Staff", String.valueOf(dataManager.getStaff().size()), "#9b59b6"),
            UIComponents.createStatCard("Facilities", String.valueOf(dataManager.getFacilities().size()), "#e67e22")
        );

        TableView<Models.Facility> facilityTable = createFacilityTable(dataManager.getFacilities());

        VBox tableContainer = new VBox(10);
        Label tableTitle = new Label("Facilities Overview");
        tableTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        tableContainer.getChildren().addAll(tableTitle, facilityTable);

        dashboard.getChildren().addAll(title, statsPane, tableContainer);
        return dashboard;
    }

    private void showMyPatients() {
        updateMainContent("My Patients", createPatientTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getClinicianId().equals(currentUserId))
                .map(a -> dataManager.getPatients().stream()
                    .filter(p -> p.getPatientId().equals(a.getPatientId()))
                    .findFirst()
                    .orElse(null))
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList())
        ));
    }

    private void showAppointments() {
        updateMainContent("My Appointments", createAppointmentTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getClinicianId().equals(currentUserId))
                .collect(Collectors.toList())
        ));
    }

    private void showPrescribeDrug() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label title = new Label("Prescribe Drug");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ComboBox<String> patientCombo = new ComboBox<>();
        patientCombo.setPromptText("Select Patient");
        patientCombo.setMaxWidth(Double.MAX_VALUE);
        dataManager.getAppointments().stream()
            .filter(a -> a.getClinicianId().equals(currentUserId))
            .map(Models.Appointment::getPatientId)
            .distinct()
            .forEach(patientCombo.getItems()::add);

        TextField drugNameField = new TextField();
        drugNameField.setPromptText("Drug Name");

        TextField dosageField = new TextField();
        dosageField.setPromptText("Dosage (e.g., 500mg)");

        TextField frequencyField = new TextField();
        frequencyField.setPromptText("Frequency (e.g., Twice daily)");

        TextField durationField = new TextField();
        durationField.setPromptText("Duration (days)");

        TextArea instructionsArea = new TextArea();
        instructionsArea.setPromptText("Instructions");
        instructionsArea.setPrefRowCount(3);

        Button prescribeButton = UIComponents.createStyledButton("Prescribe", "#27ae60");
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.GREEN);

        prescribeButton.setOnAction(e -> {
            if (patientCombo.getValue() != null && !drugNameField.getText().isEmpty()) {
                String prescriptionId = "RX" + System.currentTimeMillis();
                Models.Prescription prescription = new Models.Prescription(
                    prescriptionId,
                    patientCombo.getValue(),
                    currentUserId,
                    drugNameField.getText(),
                    dosageField.getText(),
                    frequencyField.getText(),
                    durationField.getText().isEmpty() ? 0 : Integer.parseInt(durationField.getText()),
                    instructionsArea.getText(),
                    LocalDate.now().toString(),
                    "Active"
                );
                dataManager.addPrescription(prescription);
                messageLabel.setText("Prescription created successfully!");
                
                // Clear fields
                patientCombo.setValue(null);
                drugNameField.clear();
                dosageField.clear();
                frequencyField.clear();
                durationField.clear();
                instructionsArea.clear();
            }
        });

        form.getChildren().addAll(title, patientCombo, drugNameField, dosageField, frequencyField, durationField, instructionsArea, prescribeButton, messageLabel);
        updateMainContent("Prescribe Drug", form);
    }

    private void showBookAppointment() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label title = new Label("Book Appointment");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ComboBox<String> doctorCombo = new ComboBox<>();
        doctorCombo.setPromptText("Select Doctor");
        doctorCombo.setMaxWidth(Double.MAX_VALUE);
        dataManager.getClinicians().forEach(c -> 
            doctorCombo.getItems().add(c.getClinicianId() + " - Dr. " + c.getFirstName() + " " + c.getLastName() + " (" + c.getSpecialization() + ")")
        );

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Appointment Date");
        datePicker.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> timeCombo = new ComboBox<>();
        timeCombo.setPromptText("Select Time");
        timeCombo.setMaxWidth(Double.MAX_VALUE);
        timeCombo.getItems().addAll("09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00");

        TextArea reasonArea = new TextArea();
        reasonArea.setPromptText("Reason for Visit");
        reasonArea.setPrefRowCount(3);

        Button bookButton = UIComponents.createStyledButton("Book Appointment", "#3498db");
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.GREEN);

        bookButton.setOnAction(e -> {
            if (doctorCombo.getValue() != null && datePicker.getValue() != null && timeCombo.getValue() != null) {
                String doctorId = doctorCombo.getValue().split(" - ")[0];
                String appointmentId = "APT" + System.currentTimeMillis();
                
                Models.Appointment appointment = new Models.Appointment(
                    appointmentId,
                    currentUserId,
                    doctorId,
                    dataManager.getClinicians().stream().filter(c -> c.getClinicianId().equals(doctorId))
                        .map(Models.Clinician::getFacilityId).findFirst().orElse("FAC001"),
                    datePicker.getValue().toString(),
                    timeCombo.getValue(),
                    30,
                    "Consultation",
                    "Scheduled",
                    reasonArea.getText(),
                    "",
                    LocalDate.now().toString(),
                    LocalDate.now().toString()
                );
                
                dataManager.addAppointment(appointment);
                messageLabel.setText("Appointment booked successfully!");
                
                doctorCombo.setValue(null);
                datePicker.setValue(null);
                timeCombo.setValue(null);
                reasonArea.clear();
            }
        });

        form.getChildren().addAll(title, doctorCombo, datePicker, timeCombo, reasonArea, bookButton, messageLabel);
        updateMainContent("Book Appointment", form);
    }

    private void showMyAppointments() {
        updateMainContent("My Appointments", createAppointmentTable(
            dataManager.getAppointments().stream()
                .filter(a -> a.getPatientId().equals(currentUserId))
                .collect(Collectors.toList())
        ));
    }

    private void showMyPrescriptions() {
        updateMainContent("My Prescriptions", createPrescriptionTable(
            dataManager.getPrescriptions().stream()
                .filter(p -> p.getPatientId().equals(currentUserId))
                .collect(Collectors.toList())
        ));
    }

    private void showMonitorPatients() {
        updateMainContent("Monitor Patients", createPatientTable(dataManager.getPatients()));
    }

    private void showUpdateReports() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label title = new Label("Update Patient Report");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        ComboBox<String> patientCombo = new ComboBox<>();
        patientCombo.setPromptText("Select Patient");
        patientCombo.setMaxWidth(Double.MAX_VALUE);
        dataManager.getPatients().forEach(p -> 
            patientCombo.getItems().add(p.getPatientId() + " - " + p.getFirstName() + " " + p.getLastName())
        );

        TextArea reportArea = new TextArea();
        reportArea.setPromptText("Enter patient report/notes");
        reportArea.setPrefRowCount(5);

        Button updateButton = UIComponents.createStyledButton("Update Report", "#16a085");
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.GREEN);

        updateButton.setOnAction(e -> {
            if (patientCombo.getValue() != null && !reportArea.getText().isEmpty()) {
                messageLabel.setText("Report updated successfully! (Saved: " + LocalDate.now() + ")");
                reportArea.clear();
                patientCombo.setValue(null);
            }
        });

        form.getChildren().addAll(title, patientCombo, reportArea, updateButton, messageLabel);
        updateMainContent("Update Reports", form);
    }

    private void showManageAppointments() {
        VBox container = new VBox(15);
        
        Button addButton = UIComponents.createStyledButton("Schedule New Appointment", "#3498db");
        addButton.setOnAction(e -> showBookAppointment());
        
        TableView<Models.Appointment> table = createAppointmentTable(dataManager.getAppointments());
        
        container.getChildren().addAll(addButton, table);
        updateMainContent("Manage Appointments", container);
    }

    private void showPatientRegistration() {
        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setMaxWidth(600);
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label title = new Label("Patient Registration");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");

        ComboBox<String> genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other");
        genderCombo.setPromptText("Gender");
        genderCombo.setMaxWidth(Double.MAX_VALUE);

        DatePicker dobPicker = new DatePicker();
        dobPicker.setPromptText("Date of Birth");
        dobPicker.setMaxWidth(Double.MAX_VALUE);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField addressField = new TextField();
        addressField.setPromptText("Address");

        ComboBox<String> bloodGroupCombo = new ComboBox<>();
        bloodGroupCombo.getItems().addAll("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        bloodGroupCombo.setPromptText("Blood Group");
        bloodGroupCombo.setMaxWidth(Double.MAX_VALUE);

        TextField emergencyContactField = new TextField();
        emergencyContactField.setPromptText("Emergency Contact");

        Button registerButton = UIComponents.createStyledButton("Register Patient", "#27ae60");
        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.GREEN);

        registerButton.setOnAction(e -> {
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()) {
                String patientId = "PAT" + System.currentTimeMillis();
                
                Models.Patient patient = new Models.Patient(
                    patientId,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    genderCombo.getValue(),
                    dobPicker.getValue() != null ? dobPicker.getValue().toString() : "",
                    phoneField.getText().isEmpty() ? 0 : Long.parseLong(phoneField.getText()),
                    emailField.getText(),
                    addressField.getText(),
                    bloodGroupCombo.getValue(),
                    emergencyContactField.getText().isEmpty() ? 0 : Long.parseLong(emergencyContactField.getText()),
                    LocalDate.now().toString(),
                    ""
                );
                
                dataManager.addPatient(patient);
                messageLabel.setText("Patient registered successfully! ID: " + patientId);
                
                // Clear fields
                firstNameField.clear();
                lastNameField.clear();
                genderCombo.setValue(null);
                dobPicker.setValue(null);
                phoneField.clear();
                emailField.clear();
                addressField.clear();
                bloodGroupCombo.setValue(null);
                emergencyContactField.clear();
            }
        });

        form.getChildren().addAll(title, firstNameField, lastNameField, genderCombo, dobPicker, 
            phoneField, emailField, addressField, bloodGroupCombo, emergencyContactField, registerButton, messageLabel);
        updateMainContent("Patient Registration", form);
    }

    private void showAllPatients() {
        updateMainContent("All Patients", createPatientTable(dataManager.getPatients()));
    }

    private void showHospitalDetails() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        
        Label title = new Label("Hospital Facilities");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        
        TableView<Models.Facility> table = createFacilityTable(dataManager.getFacilities());
        
        container.getChildren().addAll(title, table);
        updateMainContent("Hospital Details", container);
    }

    private void showManageDoctors() {
        VBox container = new VBox(15);
        
        TableView<Models.Clinician> table = createClinicianTable(dataManager.getClinicians());
        
        container.getChildren().add(table);
        updateMainContent("Manage Doctors", container);
    }

    private void showManageStaff() {
        updateMainContent("Manage Staff", createStaffTable(dataManager.getStaff()));
    }

    private void showAllAppointments() {
        updateMainContent("All Appointments", createAppointmentTable(dataManager.getAppointments()));
    }

    private TableView<Models.Appointment> createAppointmentTable(List<Models.Appointment> appointments) {
        TableView<Models.Appointment> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(appointments));
        
        TableColumn<Models.Appointment, String> idCol = new TableColumn<>("Appointment ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        idCol.setPrefWidth(120);
        
        TableColumn<Models.Appointment, String> patientCol = new TableColumn<>("Patient ID");
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        patientCol.setPrefWidth(100);
        
        TableColumn<Models.Appointment, String> doctorCol = new TableColumn<>("Doctor ID");
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("clinicianId"));
        doctorCol.setPrefWidth(100);
        
        TableColumn<Models.Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        dateCol.setPrefWidth(100);
        
        TableColumn<Models.Appointment, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        timeCol.setPrefWidth(80);
        
        TableColumn<Models.Appointment, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        typeCol.setPrefWidth(120);
        
        TableColumn<Models.Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);
        
        TableColumn<Models.Appointment, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reasonForVisit"));
        reasonCol.setPrefWidth(200);
        
        table.getColumns().addAll(idCol, patientCol, doctorCol, dateCol, timeCol, typeCol, statusCol, reasonCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private TableView<Models.Patient> createPatientTable(List<Models.Patient> patients) {
        TableView<Models.Patient> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(patients));
        
        TableColumn<Models.Patient, String> idCol = new TableColumn<>("Patient ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        idCol.setPrefWidth(100);
        
        TableColumn<Models.Patient, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(120);
        
        TableColumn<Models.Patient, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(120);
        
        TableColumn<Models.Patient, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderCol.setPrefWidth(80);
        
        TableColumn<Models.Patient, String> dobCol = new TableColumn<>("Date of Birth");
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        dobCol.setPrefWidth(120);
        
        TableColumn<Models.Patient, Long> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneCol.setPrefWidth(120);
        
        TableColumn<Models.Patient, String> bloodCol = new TableColumn<>("Blood Group");
        bloodCol.setCellValueFactory(new PropertyValueFactory<>("bloodGroup"));
        bloodCol.setPrefWidth(100);
        
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, genderCol, dobCol, phoneCol, bloodCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private TableView<Models.Clinician> createClinicianTable(List<Models.Clinician> clinicians) {
        TableView<Models.Clinician> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(clinicians));
        
        TableColumn<Models.Clinician, String> idCol = new TableColumn<>("Clinician ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("clinicianId"));
        idCol.setPrefWidth(120);
        
        TableColumn<Models.Clinician, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(120);
        
        TableColumn<Models.Clinician, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(120);
        
        TableColumn<Models.Clinician, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        specCol.setPrefWidth(150);
        
        TableColumn<Models.Clinician, Long> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        phoneCol.setPrefWidth(120);
        
        TableColumn<Models.Clinician, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setPrefWidth(200);
        
        TableColumn<Models.Clinician, Integer> expCol = new TableColumn<>("Experience");
        expCol.setCellValueFactory(new PropertyValueFactory<>("yearsOfExperience"));
        expCol.setPrefWidth(100);
        
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, specCol, phoneCol, emailCol, expCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private TableView<Models.Prescription> createPrescriptionTable(List<Models.Prescription> prescriptions) {
        TableView<Models.Prescription> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(prescriptions));
        
        TableColumn<Models.Prescription, String> idCol = new TableColumn<>("Prescription ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));
        idCol.setPrefWidth(120);
        
        TableColumn<Models.Prescription, String> drugCol = new TableColumn<>("Drug Name");
        drugCol.setCellValueFactory(new PropertyValueFactory<>("drugName"));
        drugCol.setPrefWidth(150);
        
        TableColumn<Models.Prescription, String> dosageCol = new TableColumn<>("Dosage");
        dosageCol.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        dosageCol.setPrefWidth(100);
        
        TableColumn<Models.Prescription, String> freqCol = new TableColumn<>("Frequency");
        freqCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        freqCol.setPrefWidth(120);
        
        TableColumn<Models.Prescription, Integer> durationCol = new TableColumn<>("Duration (Days)");
        durationCol.setCellValueFactory(new PropertyValueFactory<>("durationDays"));
        durationCol.setPrefWidth(120);
        
        TableColumn<Models.Prescription, String> instructionsCol = new TableColumn<>("Instructions");
        instructionsCol.setCellValueFactory(new PropertyValueFactory<>("instructions"));
        instructionsCol.setPrefWidth(200);
        
        TableColumn<Models.Prescription, String> dateCol = new TableColumn<>("Prescribed Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("prescribedDate"));
        dateCol.setPrefWidth(120);
        
        TableColumn<Models.Prescription, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);
        
        table.getColumns().addAll(idCol, drugCol, dosageCol, freqCol, durationCol, instructionsCol, dateCol, statusCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private TableView<Models.Facility> createFacilityTable(List<Models.Facility> facilities) {
        TableView<Models.Facility> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(facilities));
        
        TableColumn<Models.Facility, String> idCol = new TableColumn<>("Facility ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("facilityId"));
        idCol.setPrefWidth(100);
        
        TableColumn<Models.Facility, String> nameCol = new TableColumn<>("Facility Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("facilityName"));
        nameCol.setPrefWidth(200);
        
        TableColumn<Models.Facility, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("facilityType"));
        typeCol.setPrefWidth(150);
        
        TableColumn<Models.Facility, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        cityCol.setPrefWidth(120);
        
        TableColumn<Models.Facility, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        stateCol.setPrefWidth(100);
        
        TableColumn<Models.Facility, Integer> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capacityCol.setPrefWidth(100);
        
        TableColumn<Models.Facility, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);
        
        table.getColumns().addAll(idCol, nameCol, typeCol, cityCol, stateCol, capacityCol, statusCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private TableView<Models.Staff> createStaffTable(List<Models.Staff> staff) {
        TableView<Models.Staff> table = new TableView<>();
        table.setItems(FXCollections.observableArrayList(staff));
        
        TableColumn<Models.Staff, String> idCol = new TableColumn<>("Staff ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        idCol.setPrefWidth(100);
        
        TableColumn<Models.Staff, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setPrefWidth(120);
        
        TableColumn<Models.Staff, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameCol.setPrefWidth(120);
        
        TableColumn<Models.Staff, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        roleCol.setPrefWidth(150);
        
        TableColumn<Models.Staff, String> deptCol = new TableColumn<>("Department");
        deptCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        deptCol.setPrefWidth(150);
        
        TableColumn<Models.Staff, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("employmentStatus"));
        statusCol.setPrefWidth(120);
        
        table.getColumns().addAll(idCol, firstNameCol, lastNameCol, roleCol, deptCol, statusCol);
        table.setStyle("-fx-background-color: white;");
        
        return table;
    }

    private void updateMainContent(String title, javafx.scene.Node content) {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setStyle("-fx-background-color: #f5f5f5;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#333333"));

        mainContent.getChildren().addAll(titleLabel, content);

        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #f5f5f5;");

        BorderPane root = (BorderPane) currentScene.getRoot();
        root.setCenter(scrollPane);
    }

    public static void main(String[] args) {
        launch(args);
    }
}