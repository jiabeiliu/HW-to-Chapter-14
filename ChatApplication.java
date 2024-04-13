import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ChatApplication {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cards; // a panel that uses CardLayout
    private JTextField signUpUsernameField, loginUsernameField, chatMessageField;
    private JPasswordField signUpPasswordField, loginPasswordField;
    private JTextArea chatArea;
    private Map<String, String> userAccounts = new HashMap<>();

    public ChatApplication() {
        // Create and set up the window.
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);

        // Create the cards.
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        JPanel card1 = createSignUpPanel();
        JPanel card2 = createLoginPanel();
        JPanel card3 = createChatPanel();

        // Add the cards to the main panel.
        cards.add(card1, "SignUpPanel");
        cards.add(card2, "LoginPanel");
        cards.add(card3, "ChatPanel");

        // Display the window.
        frame.add(cards, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        signUpUsernameField = new JTextField();
        signUpPasswordField = new JPasswordField();
        JButton signUpButton = new JButton("Sign Up");

        signUpButton.addActionListener(e -> {
            String username = signUpUsernameField.getText().trim();
            String password = new String(signUpPasswordField.getPassword()).trim();
            if (!username.isEmpty() && !password.isEmpty() && !userAccounts.containsKey(username)) {
                userAccounts.put(username, password);
                JOptionPane.showMessageDialog(frame, "Signup successful. Please login.");
                cardLayout.show(cards, "LoginPanel");
            } else {
                JOptionPane.showMessageDialog(frame, "Signup failed. User already exists or fields are empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Username:"));
        panel.add(signUpUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(signUpPasswordField);
        panel.add(new JLabel()); // Spacer
        panel.add(signUpButton);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        loginUsernameField = new JTextField();
        loginPasswordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText().trim();
            String password = new String(loginPasswordField.getPassword()).trim();
            if (userAccounts.containsKey(username) && userAccounts.get(username).equals(password)) {
                JOptionPane.showMessageDialog(frame, "Login successful.");
                cardLayout.show(cards, "ChatPanel");
            } else {
                JOptionPane.showMessageDialog(frame, "Login failed. Incorrect username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Username:"));
        panel.add(loginUsernameField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);
        panel.add(new JLabel()); // Spacer
        panel.add(loginButton);

        return panel;
    }

    private JPanel createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatMessageField = new JTextField();
        JButton sendButton = new JButton("Send");

        sendButton.addActionListener(e -> {
            String message = chatMessageField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append("You: " + message + "\n");
                chatMessageField.setText("");
            }
        });

        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(chatMessageField, BorderLayout.CENTER);
        southPanel.add(sendButton, BorderLayout.EAST);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatApplication::new);
    }
}
