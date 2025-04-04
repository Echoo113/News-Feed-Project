
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientGUI implements IClientGUI {
    private Socket socket;
    private JFrame frame;
    private JDialog parentDialog;
    private JDialog commentDialog;
    private JDialog blockDialog;
    private BufferedReader input;
    private PrintWriter output;
    private static final int PORT_NUMBER = 2001;
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);


    public ClientGUI() {
        connectToServer();
        initializeGUI();

    }

    private void initializeGUI() {
        frame = new JFrame("Client GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        cardPanel.add(makeCheckInPanel(), "CheckIn");
        cardPanel.add(makeLoginPage(), "Login");
        cardPanel.add(makeCreateAccountPage(), "CreateAccount");
        frame.add(cardPanel);
        frame.setVisible(true);

        cardLayout.show(cardPanel, "CheckIn");
    }

    private JPanel makeCheckInPanel() {
        JPanel checkInPanel = new JPanel();

        checkInPanel.setBackground(new Color(230, 240, 255));
        checkInPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to Our Platform");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(102, 153, 255));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(200, 50));

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 18));
        createAccountButton.setForeground(new Color(255, 255, 255));
        createAccountButton.setBackground(new Color(102, 204, 153));
        createAccountButton.setOpaque(true);
        createAccountButton.setBorderPainted(false);
        createAccountButton.setPreferredSize(new Dimension(200, 50));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        checkInPanel.add(welcomeLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        checkInPanel.add(loginButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        checkInPanel.add(createAccountButton, constraints);

        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "Login"));
        createAccountButton.addActionListener(e -> cardLayout.show(cardPanel, "CreateAccount"));

        return checkInPanel;
    }

    private JPanel makeLoginPage() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        loginPanel.add(usernameLabel, constraints);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 1;
        constraints.gridy = 0;
        loginPanel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        loginPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 1;
        constraints.gridy = 1;
        loginPanel.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        loginPanel.add(loginButton, constraints);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            try {
                sendRequest("1," + username + "," + password);
                String line = input.readLine();
                if ("Valid".equals(line)) {
                    cardPanel.add(welcomePanel(), "Welcome");
                    cardLayout.show(cardPanel, "Welcome");
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username " +
                            "or password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error communicating" +
                        " with server: " + ex.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return loginPanel;
    }


    private JPanel makeCreateAccountPage() {
        JPanel createAccountPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        createAccountPanel.add(usernameLabel, constraints);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 1;
        constraints.gridy = 0;
        createAccountPanel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 1;
        createAccountPanel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 1;
        constraints.gridy = 1;
        createAccountPanel.add(passwordField, constraints);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 2;
        createAccountPanel.add(confirmPasswordLabel, constraints);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));
        constraints.gridx = 1;
        constraints.gridy = 2;
        createAccountPanel.add(confirmPasswordField, constraints);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        createAccountPanel.add(createAccountButton, constraints);

        createAccountButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Passwords " +
                        "do not match. Please try again.");
                passwordField.setText("");
                confirmPasswordField.setText("");
            } else {
                try {

                    sendRequest("2," + username + "," + password);
                    String line = input.readLine();
                    if ("Account created".equals(line)) {
                        cardPanel.add(welcomePanel(), "Welcome");
                        cardLayout.show(cardPanel, "Welcome");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Username " +
                                "already exists. Please try again.");
                        usernameField.setText("");
                        passwordField.setText("");
                        confirmPasswordField.setText("");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error " +
                            "sending request: " + ex.getMessage());
                }
            }
        });
        cardPanel.add(welcomePanel(), "Welcome");
        cardLayout.show(cardPanel, "Welcome");
        return createAccountPanel;
    }


    public JPanel welcomePanel() {
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new GridBagLayout());
        welcomePanel.setBackground(new Color(239, 231, 178));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        JLabel welcomeLabel = new JLabel("Welcome to the Social Media Platform!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel, gbc);

        // Define button labels
        String[] buttonLabels = {
                "Search up a User",
                "Add or Delete a User",
                "Check or modify a Post",
                "Block or Unblock a User",
                "Change your username or password",
                "Make comment on a post",
                "Logout"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setBackground(new Color(100, 149, 237));
            button.setForeground(Color.WHITE);  // White text
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setPreferredSize(new Dimension(200, 40));
            button.addActionListener(e -> {
                try {
                    handleMenuAction(e.getActionCommand());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(welcomePanel, "Error: "
                            + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            welcomePanel.add(button, gbc);
        }

        return welcomePanel;
    }


    public JPanel addDeletePane() {
        JPanel addDeletePane = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        addDeletePane.setBackground(new Color(188, 214, 248));

        JLabel headerLabel = new JLabel("Add or Delete a User");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        addDeletePane.add(headerLabel, gbc);

        String[] operations = {"Add a User", "Delete a User"};
        for (String operation : operations) {
            JButton button = new JButton(operation);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(e -> {
                try {
                    handleAddDeleteAction(operation);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            addDeletePane.add(button, gbc);
        }
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Welcome");
            parentDialog.dispose();
        });
        addDeletePane.add(backButton, gbc);

        parentDialog = new JDialog(frame, "Add or Delete a " +
                "User", Dialog.ModalityType.APPLICATION_MODAL);
        parentDialog.setContentPane(addDeletePane);
        parentDialog.setSize(500, 600);
        parentDialog.setLocationRelativeTo(null);
        parentDialog.setVisible(true);
        return addDeletePane;
    }

    public void handleAddDeleteAction(String operation) throws IOException {
        switch (operation) {
            case "Add a User":
                sendRequest("1");
                JPanel addUser = addUserPanel();
                parentDialog.setContentPane(addUser);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "Delete a User":
                sendRequest("2");
                JPanel deleteUser = deleteUserPanel();
                parentDialog.setContentPane(deleteUser);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Feature not implemented yet.");
                break;
        }
    }

    public JPanel deleteUserPanel() {
        JPanel deleteUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Enter the username of the user you want to delete:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        deleteUserPanel.add(label, gbc);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        deleteUserPanel.add(textField, gbc);

        JButton button = new JButton("Delete User");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        deleteUserPanel.add(button, gbc);

        button.addActionListener(e -> {
            String username = textField.getText();
            try {
                sendRequest(username);
                String response = input.readLine();
                if ("User is not found".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "User is not found.");
                } else if ("User is not your friend".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "User is not your friend.");
                } else {
                    JOptionPane.showMessageDialog(frame, "User deleted successfully.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error" +
                        " sending request: " + ex.getMessage());
            }
        });
        return deleteUserPanel;
    }

    public JPanel addUserPanel() {
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel label = new JLabel("Enter the username of the user you want to add:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        addUserPanel.add(label, gbc);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addUserPanel.add(textField, gbc);

        JButton button = new JButton("Add User");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        addUserPanel.add(button, gbc);

        button.addActionListener(e -> {
            String username = textField.getText();
            try {
                sendRequest(username);
                String response = input.readLine();
                if ("User not found".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "User not found.");
                } else if ("User is already a friend".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "User is already a friend.");
                } else if ("You cannot add yourself as a friend".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "You cannot add yourself as a friend.");
                } else if ("The user is in your block list".equals(response)) {
                    JOptionPane.showMessageDialog(frame, "The user is in your block list.");
                } else {
                    JOptionPane.showMessageDialog(frame, "User added successfully.");
                }
                cardLayout.show(cardPanel, "Welcome");
                parentDialog.dispose();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error " +
                        "sending request: " + ex.getMessage());
            }
        });
        return addUserPanel;
    }

    public void handleMenuAction(String actionCommand) throws IOException {
        switch (actionCommand) {
            case "Search up a User":
                sendRequest("1");
                cardLayout.addLayoutComponent(searchPanel(), "Search");
                cardLayout.show(cardPanel, "Search");
                break;
            case "Add or Delete a User":
                sendRequest("2");
                cardLayout.addLayoutComponent(addDeletePane(), "AddDelete");
                cardLayout.show(cardPanel, "AddDelete");
                break;
            case "Check or modify a Post":
                sendRequest("3");
                cardLayout.addLayoutComponent(postManagementPanel(), "PostManagement");
                cardLayout.show(cardPanel, "PostManagement");
                break;

            case "Block or Unblock a User":
                sendRequest("4");
                cardLayout.addLayoutComponent(blockManagePanel(), "BlockManagement");
                cardLayout.show(cardPanel, "BlockManagement");

                break;
            case "Change your username or password":
                sendRequest("5");
                cardLayout.addLayoutComponent(changeNamePassword(), "ChangeCredentials");
                cardLayout.show(cardPanel, "ChangeCredentials");
                break;
            case "Make comment on a post":
                sendRequest("6");
                cardLayout.addLayoutComponent(commentManagementPanel(), "PostManagement");
                cardLayout.show(cardPanel, "CommentManagement");
                break;
            case "Logout":
                confirmAndLogout();
                return;
            default:
                JOptionPane.showMessageDialog(null,
                        "No action defined for: " + actionCommand);
                break;
        }
        cardLayout.show(cardPanel, "Welcome");
    }


    public JPanel postManagementPanel() {
        JPanel postPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        postPanel.setBackground(new Color(188, 214, 248));

        JLabel headerLabel = new JLabel("Post    Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        postPanel.add(headerLabel, gbc);

        String[] operations = {"Add a Post", "Delete a Post", "View all Posts",
                "View Friend's Posts", "Upvote/Downvote a Post", "View Your Posts"};
        for (String operation : operations) {
            JButton button = new JButton(operation);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(e -> {
                try {
                    handlePostAction(operation);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            postPanel.add(button, gbc);
        }

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Welcome");
            parentDialog.dispose();
        });
        postPanel.add(backButton, gbc);

        parentDialog = new JDialog(frame, "Post Management", Dialog.ModalityType.APPLICATION_MODAL);
        parentDialog.setContentPane(postPanel);
        parentDialog.setSize(500, 600);
        parentDialog.setLocationRelativeTo(null);
        parentDialog.setVisible(true);
        return postPanel;
    }

    public JPanel searchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.setBackground(new Color(188, 214, 248));

        JLabel headerLabel = new JLabel("Search for a User");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(headerLabel, gbc);

        JLabel userLabel = new JLabel("Enter Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(userField, gbc);

        JButton searchButton = new JButton("Search User");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.addActionListener(e -> {
            String username = userField.getText().trim();
            if (!username.isEmpty()) {
                try {
                    output.println(username);
                    output.flush();

                    String response = input.readLine();
                    if ("User found".equals(response)) {
                        String name = input.readLine();
                        String id = input.readLine();
                        String friends = input.readLine();
                        JOptionPane.showMessageDialog(panel, "Name: "
                                        + name + "\nID: " + id + "\nFriends: " + friends,
                                "User Found", JOptionPane.INFORMATION_MESSAGE);
                        parentDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "User not found",
                                "Search Result", JOptionPane.ERROR_MESSAGE);
                        parentDialog.dispose();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Error communicating" +
                            " with the server: " + ex.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
                    parentDialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Please enter a username.",
                        "Input Error", JOptionPane.WARNING_MESSAGE);
                parentDialog.dispose();
            }
        });
        panel.add(searchButton, gbc);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Welcome");
            parentDialog.dispose();
        });
        panel.add(backButton, gbc);

        parentDialog = new JDialog(frame, "Search a User", Dialog.ModalityType.APPLICATION_MODAL);
        parentDialog.setContentPane(panel);
        parentDialog.setSize(500, 600);
        parentDialog.setLocationRelativeTo(null);
        parentDialog.setVisible(true);

        return panel;
    }


    //-----------------for post management
    public void handlePostAction(String operation) throws IOException {
        switch (operation) {
            case "Add a Post":
                sendRequest("1");
                JPanel addPost = addPostPanel();
                parentDialog.setContentPane(addPost);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "Delete a Post":
                sendRequest("2");
                JPanel deletePost = deletePostPanel();
                parentDialog.setContentPane(deletePost);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "View all Posts":
                sendRequest("3");
                JPanel viewAllPosts = viewAllPostsPanel();
                parentDialog.setContentPane(viewAllPosts);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "View Friend's Posts":
                sendRequest("4");
                JPanel viewFriendPosts = viewFriendPostsPanel(parentDialog);
                parentDialog.setContentPane(viewFriendPosts);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "Upvote/Downvote a Post":
                sendRequest("5");
                JPanel voteOnFriendsPost = createVotePostPanel();
                parentDialog.setContentPane(voteOnFriendsPost);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            case "View Your Posts":
                sendRequest("6");
                JPanel viewYourPosts = viewYourPostsPanel(parentDialog);
                parentDialog.setContentPane(viewYourPosts);
                parentDialog.revalidate();
                parentDialog.repaint();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Feature not implemented yet.");
                break;
        }
    }

    public JPanel addPostPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel label = new JLabel("Enter your post content:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        JTextArea textArea = new JTextArea(5, 20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // Submit button with enhanced visuals
        JButton submitButton = new JButton("Submit Post");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(100, 149, 237));
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> {
            String postContent = textArea.getText().trim();
            if (!postContent.isEmpty()) {
                output.println(postContent);
                output.flush();
                JOptionPane.showMessageDialog(panel,
                        "Post added successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                parentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(panel,
                        "Post content cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(submitButton, gbc);

        return panel;
    }

    public JPanel deletePostPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Label to instruct user
        JLabel label = new JLabel("Enter the ID of the post you wish to delete:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        // Text field for entering the post ID
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);

        JButton deleteButton = new JButton("Delete Post");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setBackground(new Color(175, 106, 81));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            String postID = textField.getText().trim();
            if (!postID.isEmpty()) {
                try {
                    int id = Integer.parseInt(postID);
                    output.println(id);
                    output.flush();
                    String response = input.readLine();
                    if ("Post deleted".equals(response)) {
                        JOptionPane.showMessageDialog(panel, "Post deleted successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        parentDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(panel, "Post not found. Please" +
                                        " enter a valid post ID.",
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid post ID.",
                            "Validation Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Error communicating with server: "
                            + ex.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Post ID cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);

        return panel;
    }

    public JPanel viewAllPostsPanel() throws IOException {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.setBackground(new Color(150, 144, 236));
        // Label for guidance
        JLabel label = new JLabel("View All Posts");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        JTextArea textArea = new JTextArea(15, 50);
        textArea.setEditable(false);  // Make text area non-editable
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);
        String line = "";
        String post = "";
        while ((line = input.readLine()) != null) {

            if (line.contains("end----")) {
                break;
            }
            post += line + "\n";
        }
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setText(post);
        textArea.setCaretPosition(0);
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> parentDialog.dispose());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(closeButton, gbc);
        return panel;
    }

    public JPanel viewFriendPostsPanel(JDialog parentDialog) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.setBackground(new Color(188, 214, 248));
        // Label for guidance
        JLabel label = new JLabel("Enter your friend's username:");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        // Text field for entering the friend's username
        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        // Text area for displaying posts
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // Button to fetch posts

        JButton fetchPostsButton = new JButton("View Posts");
        fetchPostsButton.setFont(new Font("Arial", Font.BOLD, 16));
        fetchPostsButton.addActionListener(e -> {
            String friendUsername = usernameField.getText().trim();
            if (!friendUsername.isEmpty()) {
                String posts = "";
                String line = "";
                try {
                    sendRequest(friendUsername);
                    while ((line = input.readLine()) != null) {
                        if (line.equals("User not found")) {
                            JOptionPane.showMessageDialog(panel,
                                    "This user is not found",
                                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                            parentDialog.dispose();
                            break;
                        }
                        if (line.equals("User is not your friend")) {
                            JOptionPane.showMessageDialog(panel, "This user is not your friend",
                                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                            parentDialog.dispose();
                            break;
                        }
                        if (line.contains("end----")) {
                            break;
                        }
                        posts += line + "\n";
                    }

                    textArea.setText(posts);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Username field cannot be empty.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(fetchPostsButton, gbc);


        // Close button to close the dialog
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> parentDialog.dispose());
        panel.add(closeButton, gbc);

        return panel;
    }

    public JPanel viewYourPostsPanel(JDialog parentDialog) throws IOException {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 20);
        panel.setBackground(new Color(150, 200, 236));

        JLabel label = new JLabel("View Your Posts");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setHorizontalAlignment(JLabel.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(label, gbc);

        JTextArea textArea = new JTextArea(15, 50);
        textArea.setEditable(false);  // Make text area non-editable
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        String yourPosts = "";
        String line = "";
        while ((line = input.readLine()) != null) {
            if (line.contains("end----")) {
                break;
            }
            yourPosts += line + "\n";
        }
        textArea.setText(yourPosts);
        textArea.setCaretPosition(0);  // Scroll to the top of the text area
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.addActionListener(e -> parentDialog.dispose());
        panel.add(closeButton, gbc);

        return panel;
    }

    public JPanel createVotePostPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Friend's username input
        JLabel friendLabel = new JLabel("Enter your friend's username:");
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField friendField = new JTextField(20);
        friendField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(friendLabel, gbc);
        panel.add(friendField, gbc);

        // Post ID input
        JLabel postIdLabel = new JLabel("Enter the Post ID:");
        postIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField postIdField = new JTextField(20);
        postIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(postIdLabel, gbc);
        panel.add(postIdField, gbc);

        // Upvote button
        JButton upvoteButton = new JButton("Upvote Post");
        upvoteButton.setFont(new Font("Arial", Font.BOLD, 16));
        upvoteButton.addActionListener(e -> {
            if (!friendField.getText().trim().isEmpty() && !postIdField.getText().trim().isEmpty()) {
                sendVote(friendField.getText().trim(), postIdField.getText().trim(), "1");
            } else {
                JOptionPane.showMessageDialog(panel, "Please " +
                        "fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(upvoteButton, gbc);

        // Downvote button
        JButton downvoteButton = new JButton("Downvote Post");
        downvoteButton.setFont(new Font("Arial", Font.BOLD, 16));
        downvoteButton.addActionListener(e -> {
            if (!friendField.getText().trim().isEmpty() && !postIdField.getText().trim().isEmpty()) {
                sendVote(friendField.getText().trim(), postIdField.getText().trim(), "2");
            } else {
                JOptionPane.showMessageDialog(panel, "Please " +
                        "fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(downvoteButton, gbc);

        return panel;
    }

    public void sendVote(String friendName, String postId, String voteType) {
        try {
            sendRequest(friendName);
            sendRequest(postId);
            sendRequest(voteType);
            String response = input.readLine();
            JOptionPane.showMessageDialog(null, response);
            parentDialog.dispose();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: "
                    + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    //for block management----------------

    public JPanel blockManagePanel() {
        JPanel blockPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        blockPanel.setBackground(new Color(188, 214, 248));

        JLabel headerLabel = new JLabel("User Block Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        blockPanel.add(headerLabel, gbc);

        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        blockPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        blockPanel.add(usernameField, gbc);

        JButton blockButton = new JButton("Block a User");
        JButton unblockButton = new JButton("Unblock a User");
        blockButton.setFont(new Font("Arial", Font.PLAIN, 14));
        unblockButton.setFont(new Font("Arial", Font.PLAIN, 14));

        blockButton.addActionListener(e -> {
            if (!usernameField.getText().trim().isEmpty()) {
                handleBlockUnblock(usernameField.getText(), true, blockPanel);
            } else {
                JOptionPane.showMessageDialog(blockPanel, "Please enter a username.");
            }
        });
        unblockButton.addActionListener(e -> {
            if (!usernameField.getText().trim().isEmpty()) {
                handleBlockUnblock(usernameField.getText(), false, blockPanel);
            } else {
                JOptionPane.showMessageDialog(blockPanel, "Please enter a username.");
            }
        });

        blockPanel.add(blockButton, gbc);
        blockPanel.add(unblockButton, gbc);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Welcome");
            blockDialog.dispose();
        });
        blockPanel.add(backButton, gbc);

        blockDialog = new JDialog(frame, "User Block Management",
                Dialog.ModalityType.APPLICATION_MODAL);
        blockDialog.setContentPane(blockPanel);
        blockDialog.setSize(500, 600);
        blockDialog.setLocationRelativeTo(null);
        blockDialog.setVisible(true);
        return blockPanel;
    }

    public void handleBlockUnblock(String username, boolean blockOperation, JPanel blockPanel) {
        try {

            if (blockOperation) {
                sendRequest("1");
                sendRequest(username);
                String feedback = input.readLine();
                JOptionPane.showMessageDialog(blockPanel, feedback);
            } else {
                sendRequest("2");
                sendRequest(username);
                String feedback = input.readLine();
                JOptionPane.showMessageDialog(blockPanel, feedback);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(blockPanel, "Error: " + ex.getMessage());
        }
    }


    //for comment management----------------

    public JPanel commentManagementPanel() {
        JPanel commentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        commentPanel.setBackground(new Color(117, 83, 70));

        JLabel headerLabel = new JLabel("Comment Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.anchor = GridBagConstraints.CENTER;
        commentPanel.add(headerLabel, gbc);

        // Button setup for different comment operations
        JButton addButton = new JButton("Add a Comment");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.addActionListener(e -> {
            try {
                handleCommentAction("Add a Comment");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        commentPanel.add(addButton, gbc);

        JButton deleteButton = new JButton("Delete a Comment");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 14));
        deleteButton.addActionListener(e -> {
            try {
                handleCommentAction("Delete a Comment");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        commentPanel.add(deleteButton, gbc);

        JButton upButton = new JButton("Upvote a comment");
        upButton.setFont(new Font("Arial", Font.PLAIN, 14));
        upButton.addActionListener(e -> {
            try {
                handleCommentAction("Upvote a comment");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        JButton downButton = new JButton("Downvote a comment");
        downButton.setFont(new Font("Arial", Font.PLAIN, 14));
        downButton.addActionListener(e -> {
            try {
                handleCommentAction("Downvote a comment");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        commentPanel.add(upButton, gbc);
        commentPanel.add(downButton, gbc);

        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "Welcome");  // Switch to the main menu view
            commentDialog.dispose();  // Close the dialog
        });
        commentPanel.add(backButton, gbc);

        commentDialog = new JDialog(frame, "Comment Management", Dialog.ModalityType.APPLICATION_MODAL);
        commentDialog.setContentPane(commentPanel);
        commentDialog.setSize(500, 600);
        commentDialog.setLocationRelativeTo(null);
        commentDialog.setVisible(true);

        return commentPanel;
    }

    public void handleCommentAction(String operation) throws IOException {
        commentDialog.getContentPane().removeAll();
        switch (operation) {
            case "Add a Comment":
                sendRequest("1");
                commentDialog.add(addCommentPanel());
                commentDialog.revalidate();
                commentDialog.repaint();
                break;
            case "Delete a Comment":
                sendRequest("2");
                commentDialog.add(deleteCommentPanel());
                commentDialog.revalidate();
                commentDialog.repaint();
                break;
            case "Upvote a comment":
                sendRequest("3");
                commentDialog.add(upvoteCommentPanel());
                commentDialog.revalidate();
                commentDialog.repaint();
                break;
            case "Downvote a comment":
                sendRequest("4");
                commentDialog.add(downvoteCommentPanel());
                commentDialog.revalidate();
                commentDialog.repaint();
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Invalid operation " +
                        "or feature not implemented yet.");
                break;
        }
    }

    public JPanel addCommentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        // Label and text field for the friend's username
        JLabel friendLabel = new JLabel("Enter your friend's username:");
        friendLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField friendField = new JTextField(20);
        friendField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(friendLabel, gbc);
        panel.add(friendField, gbc);

        // Label and text field for the post ID
        JLabel postIdLabel = new JLabel("Enter the Post ID:");
        postIdLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField postIdField = new JTextField(20);
        postIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(postIdLabel, gbc);
        panel.add(postIdField, gbc);

        // Text area for entering the comment
        JLabel commentLabel = new JLabel("Enter your comment:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextArea commentArea = new JTextArea(5, 20);
        commentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(commentArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(commentLabel, gbc);
        panel.add(scrollPane, gbc);

        // Submit button for adding the comment
        JButton submitButton = new JButton("Add Comment");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.setBackground(Color.GRAY);  // A pleasing blue color
        submitButton.setForeground(Color.WHITE);  // Text color set to white
        submitButton.setOpaque(true);
        submitButton.setBorderPainted(false);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> {
            String username = friendField.getText().trim();
            String postId = postIdField.getText().trim();
            String comment = commentArea.getText().trim();
            if (!username.isEmpty() && !postId.isEmpty() && !comment.isEmpty()) {
                try {
                    output.println(username);  // Send the friend's username
                    output.println(postId);    // Send the post ID
                    output.println(comment);   // Send the comment text
                    output.flush();

                    // Handle server response
                    String response = input.readLine();
                    if ("Comment added".equals(response)) {
                        JOptionPane.showMessageDialog(panel, "Comment added successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, response, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    commentDialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Error communicating with the server: "
                            + ex.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
                    commentDialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(panel, "All fields must be filled.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(submitButton, gbc);

        return panel;
    }

    public JPanel deleteCommentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel friendLabel = new JLabel("Enter your friend's username:");
        friendLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField friendField = new JTextField(20);
        friendField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(friendLabel, gbc);
        panel.add(friendField, gbc);

        JLabel postIdLabel = new JLabel("Enter the Post ID:");
        postIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField postIdField = new JTextField(20);
        postIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(postIdLabel, gbc);
        panel.add(postIdField, gbc);

        JLabel commentIdLabel = new JLabel("Enter the Comment ID:");
        commentIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField commentIdField = new JTextField(20);
        commentIdField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(commentIdLabel, gbc);
        panel.add(commentIdField, gbc);

        JButton deleteButton = new JButton("Delete Comment");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        panel.setBackground(Color.GRAY);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setBackground(new Color(11, 51, 13));
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            String friendUsername = friendField.getText().trim();
            String postId = postIdField.getText().trim();
            String commentId = commentIdField.getText().trim();
            if (!friendUsername.isEmpty() && !postId.isEmpty() && !commentId.isEmpty()) {
                try {
                    output.println(friendUsername);
                    output.println(postId);
                    output.println(commentId);
                    output.flush();
                    // Handle server response
                    String response = input.readLine();
                    if ("Comment deleted".equals(response)) {
                        JOptionPane.showMessageDialog(panel, "Comment deleted successfully.",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel, response, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    commentDialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Error communicating with the " +
                            "server: " + ex.getMessage(), "Network Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(panel, "All fields must be " +
                        "filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(deleteButton, gbc);

        return panel;
    }

    public JPanel upvoteCommentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel userLabel = new JLabel("Enter the username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(userLabel, gbc);
        panel.add(userField, gbc);

        JLabel postLabel = new JLabel("Enter the Post ID:");
        postLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField postField = new JTextField(20);
        postField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(postLabel, gbc);
        panel.add(postField, gbc);

        JLabel commentLabel = new JLabel("Enter the Comment ID:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField commentField = new JTextField(20);
        commentField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(commentLabel, gbc);
        panel.add(commentField, gbc);

        JButton submitButton = new JButton("Upvote Comment");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String postId = postField.getText().trim();
            String commentId = commentField.getText().trim();

            if (!username.isEmpty() && !postId.isEmpty() && !commentId.isEmpty()) {
                try {
                    output.println(username);
                    output.println(postId);
                    output.println(commentId);
                    output.flush();

                    String response = input.readLine();
                    JOptionPane.showMessageDialog(null, response, "Server " +
                            "Response", JOptionPane.INFORMATION_MESSAGE);
                    commentDialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error " +
                                    "communicating with the server: " + ex.getMessage(),
                            "Network Error", JOptionPane.ERROR_MESSAGE);
                    commentDialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "All fields must " +
                        "be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                commentDialog.dispose();
            }
        });
        panel.add(submitButton, gbc);

        panel.setBackground(Color.LIGHT_GRAY);
        return panel;
    }

    public JPanel downvoteCommentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 20, 20, 20);


        JLabel userLabel = new JLabel("Enter the username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField userField = new JTextField(20);
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(userLabel, gbc);
        panel.add(userField, gbc);

        JLabel postLabel = new JLabel("Enter the Post ID:");
        postLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField postField = new JTextField(20);
        postField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(postLabel, gbc);
        panel.add(postField, gbc);

        JLabel commentLabel = new JLabel("Enter the Comment ID:");
        commentLabel.setFont(new Font("Arial", Font.BOLD, 18));
        JTextField commentField = new JTextField(20);
        commentField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(commentLabel, gbc);
        panel.add(commentField, gbc);

        JButton submitButton = new JButton("Downvote Comment");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(e -> {
            String username = userField.getText().trim();
            String postId = postField.getText().trim();
            String commentId = commentField.getText().trim();

            if (!username.isEmpty() && !postId.isEmpty() && !commentId.isEmpty()) {
                try {
                    output.println(username);
                    output.println(postId);
                    output.println(commentId);
                    output.flush();

                    String response = input.readLine();
                    JOptionPane.showMessageDialog(null, response, "Server" +
                            " Response", JOptionPane.INFORMATION_MESSAGE);
                    commentDialog.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error " +
                                    "communicating with the server: " + ex.getMessage(),
                            "Network Error", JOptionPane.ERROR_MESSAGE);
                    commentDialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "All fields" +
                        " must be filled.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                commentDialog.dispose();
            }
        });
        panel.add(submitButton, gbc);

        panel.setBackground(Color.LIGHT_GRAY);
        return panel;
    }


    //logout----------------
    public void confirmAndLogout() throws IOException {
        int response = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response == JOptionPane.YES_OPTION) {
            sendRequest("7");
            socket.close();  // Close the socket connection
            System.exit(0);  // Terminate the application
        } else {
            cardPanel.add(welcomePanel(), "Welcome");
            cardLayout.show(cardPanel, "Welcome");
        }
    }

    //change username and password----------------

    public JPanel changeNamePassword() {
        // Creating a JPanel to hold all components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.setBackground(new Color(243, 209, 175));

        // Username components
        JLabel usernameLabel = new JLabel("Enter your new username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField usernameField = new JTextField(20);
        panel.add(usernameLabel, gbc);
        panel.add(usernameField, gbc);

        // Password components
        JLabel passwordLabel = new JLabel("Enter your new password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel, gbc);
        panel.add(passwordField, gbc);

        // Submit button setup
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.addActionListener(e -> {
            String newUsername = usernameField.getText().trim();
            String newPassword = new String(passwordField.getPassword()).trim();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill in both fields.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    sendRequest(newUsername);
                    sendRequest(newPassword);

                    output.println(newUsername);
                    output.flush();
                    output.println(newPassword);
                    output.flush();

                    JOptionPane.showMessageDialog(panel, "Credentials updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // After successful update, close the dialog
                    Window window = SwingUtilities.getWindowAncestor(panel);
                    if (window instanceof JDialog) {
                        window.dispose();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(panel, "Failed to update credentials: "
                            + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        // Show this panel in a JDialog for modal behavior
        JDialog dialog = new JDialog(frame, "Change Username/Password",
                Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setContentPane(panel);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);

        return panel;
    }


    public void connectToServer() {
        try {
            socket = new Socket("localhost", PORT_NUMBER);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Cannot connect to server: " + e.getMessage());
        }
    }

    public void sendRequest(String text) throws IOException {
        output.println(text);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientGUI::new);
    }
}
