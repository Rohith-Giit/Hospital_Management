import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIComponents {

    /**
     * Creates a styled button with custom background color
     * @param text Button text
     * @param color Background color in hex format
     * @return Styled Button
     */
    public static Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 12 24 12 24;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        
        // Hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle(
                "-fx-background-color: derive(" + color + ", -10%);" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 12 24 12 24;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);"
            );
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(
                "-fx-background-color: " + color + ";" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-padding: 12 24 12 24;" +
                "-fx-background-radius: 5;" +
                "-fx-cursor: hand;"
            );
        });
        
        return button;
    }

    /**
     * Creates a statistics card with title, value and color
     * @param title Card title
     * @param value Numeric value to display
     * @param color Card accent color
     * @return VBox containing the stat card
     */
    public static VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(25));
        card.setMinWidth(200);
        card.setMinHeight(120);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 12;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);" +
            "-fx-border-color: " + color + ";" +
            "-fx-border-width: 0 0 4 0;" +
            "-fx-border-radius: 12;"
        );

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        valueLabel.setTextFill(Color.web(color));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        titleLabel.setTextFill(Color.web("#666666"));
        titleLabel.setWrapText(true);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(180);

        card.getChildren().addAll(valueLabel, titleLabel);

        // Hover effect
        card.setOnMouseEntered(e -> {
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 5);" +
                "-fx-border-color: " + color + ";" +
                "-fx-border-width: 0 0 4 0;" +
                "-fx-border-radius: 12;" +
                "-fx-scale-x: 1.02;" +
                "-fx-scale-y: 1.02;"
            );
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);" +
                "-fx-border-color: " + color + ";" +
                "-fx-border-width: 0 0 4 0;" +
                "-fx-border-radius: 12;"
            );
        });

        return card;
    }

    /**
     * Creates a styled form label
     * @param text Label text
     * @return Styled Label
     */
    public static Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#333333"));
        return label;
    }

    /**
     * Creates a section header label
     * @param text Header text
     * @return Styled Label
     */
    public static Label createSectionHeader(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        label.setTextFill(Color.web("#2c3e50"));
        return label;
    }

    /**
     * Creates an info card with icon and text
     * @param title Card title
     * @param description Card description
     * @param color Card color
     * @return VBox containing the info card
     */
    public static VBox createInfoCard(String title, String description, String color) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);"
        );

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.WHITE);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        descLabel.setTextFill(Color.WHITE);
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(250);

        card.getChildren().addAll(titleLabel, descLabel);
        return card;
    }

    /**
     * Creates a status badge
     * @param status Status text
     * @return Styled Label as badge
     */
    public static Label createStatusBadge(String status) {
        Label badge = new Label(status);
        badge.setPadding(new Insets(5, 12, 5, 12));
        badge.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        
        String color;
        switch (status.toLowerCase()) {
            case "active":
            case "scheduled":
            case "available":
                color = "#27ae60";
                break;
            case "completed":
            case "done":
                color = "#3498db";
                break;
            case "pending":
            case "waiting":
                color = "#f39c12";
                break;
            case "cancelled":
            case "inactive":
                color = "#e74c3c";
                break;
            default:
                color = "#95a5a6";
        }
        
        badge.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 12;"
        );
        
        return badge;
    }

    /**
     * Creates a card container for grouping content
     * @return VBox styled as a card
     */
    public static VBox createCard() {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10, 0, 0, 2);"
        );
        return card;
    }

    /**
     * Creates a styled alert/notification box
     * @param message Alert message
     * @param type Alert type (success, error, warning, info)
     * @return VBox containing the alert
     */
    public static VBox createAlert(String message, String type) {
        VBox alert = new VBox(10);
        alert.setPadding(new Insets(15));
        alert.setAlignment(Pos.CENTER_LEFT);
        
        String color;
        String icon;
        
        switch (type.toLowerCase()) {
            case "success":
                color = "#d4edda";
                icon = "✓";
                break;
            case "error":
                color = "#f8d7da";
                icon = "✗";
                break;
            case "warning":
                color = "#fff3cd";
                icon = "⚠";
                break;
            case "info":
            default:
                color = "#d1ecf1";
                icon = "ℹ";
        }
        
        alert.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-background-radius: 5;" +
            "-fx-border-color: derive(" + color + ", -20%);" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 5;"
        );

        Label messageLabel = new Label(icon + " " + message);
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        messageLabel.setWrapText(true);

        alert.getChildren().add(messageLabel);
        return alert;
    }

    /**
     * Creates a separator line
     * @return Styled VBox as separator
     */
    public static VBox createSeparator() {
        VBox separator = new VBox();
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: #e0e0e0;");
        return separator;
    }

    /**
     * Creates a loading indicator message
     * @param message Loading message
     * @return Label with loading text
     */
    public static Label createLoadingLabel(String message) {
        Label label = new Label("⟳ " + message);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        label.setTextFill(Color.web("#666666"));
        return label;
    }

    /**
     * Creates an empty state message
     * @param message Empty state message
     * @return VBox containing empty state
     */
    public static VBox createEmptyState(String message) {
        VBox emptyState = new VBox(15);
        emptyState.setAlignment(Pos.CENTER);
        emptyState.setPadding(new Insets(50));

        Label iconLabel = new Label("📋");
        iconLabel.setFont(Font.font("Arial", 48));

        Label messageLabel = new Label(message);
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        messageLabel.setTextFill(Color.web("#999999"));
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);

        emptyState.getChildren().addAll(iconLabel, messageLabel);
        return emptyState;
    }

    /**
     * Creates a primary action button
     * @param text Button text
     * @return Styled primary button
     */
    public static Button createPrimaryButton(String text) {
        return createStyledButton(text, "#3498db");
    }

    /**
     * Creates a success action button
     * @param text Button text
     * @return Styled success button
     */
    public static Button createSuccessButton(String text) {
        return createStyledButton(text, "#27ae60");
    }

    /**
     * Creates a danger action button
     * @param text Button text
     * @return Styled danger button
     */
    public static Button createDangerButton(String text) {
        return createStyledButton(text, "#e74c3c");
    }

    /**
     * Creates a warning action button
     * @param text Button text
     * @return Styled warning button
     */
    public static Button createWarningButton(String text) {
        return createStyledButton(text, "#f39c12");
    }

    /**
     * Creates a secondary action button
     * @param text Button text
     * @return Styled secondary button
     */
    public static Button createSecondaryButton(String text) {
        return createStyledButton(text, "#95a5a6");
    }
}