import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class HangmanGame {
    private JFrame frame;
    private JLabel wordLabel, errorLabel, titleLabel, imageLabel;
    private JTextField inputField;
    private JButton guessButton, resetButton;
    private JPanel mainPanel, inputPanel, buttonPanel;
    private static final String[] WORDS = {"APFEL", "BANANE", "KIRSCHE", "TRAUBE", "MELONE", "MANGO", "KIWI", "ORANGE", "ZITRONE"};
    private String word;
    private StringBuffer guessedWord;
    private int errors = 0;
    private final int MAX_ERRORS = 9;

    public HangmanGame() {
        frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Titel-Label
        titleLabel = new JLabel("Hangman - Errate das Wort", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Hauptpanel für das Wort, Fehler und Bild
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Courier", Font.BOLD, 24));

        errorLabel = new JLabel("Fehler: 0", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        imageLabel = new JLabel("", SwingConstants.CENTER);

        mainPanel.add(wordLabel);
        mainPanel.add(errorLabel);
        mainPanel.add(imageLabel);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Eingabefeld + Button
        inputPanel = new JPanel();
        inputField = new JTextField(2);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        guessButton = new JButton("Prüfen");
        guessButton.setFont(new Font("Arial", Font.PLAIN, 14));
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkLetter();
            }
        });

        inputPanel.add(new JLabel("Buchstabe: "));
        inputPanel.add(inputField);
        inputPanel.add(guessButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Reset-Button
        buttonPanel = new JPanel();
        resetButton = new JButton("Neues Spiel");
        resetButton.setFont(new Font("Arial", Font.PLAIN, 14));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.EAST);

        resetGame();
        frame.setVisible(true);
    }

    private void checkLetter() {
        String input = inputField.getText().toUpperCase();
        inputField.setText("");
        if (input.length() != 1) return;

        char letter = input.charAt(0);
        boolean found = false;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                guessedWord.setCharAt(i * 2, letter);
                found = true;
            }
        }

        if (!found) {
            errors++;
            errorLabel.setText("Fehler: " + errors);
            updateImage();
        }

        wordLabel.setText(guessedWord.toString());

        if (errors >= MAX_ERRORS) {
            JOptionPane.showMessageDialog(frame, "Verloren! Das Wort war: " + word);
            resetGame();
        } else if (guessedWord.toString().indexOf('_') == -1) {
            JOptionPane.showMessageDialog(frame, "Gewonnen!");
            resetGame();
        }
    }

    private void resetGame() {
        errors = 0;
        word = WORDS[new Random().nextInt(WORDS.length)];
        guessedWord = new StringBuffer();
        for (int i = 0; i < word.length(); i++) {
            guessedWord.append("_ ");
        }
        wordLabel.setText(guessedWord.toString());
        errorLabel.setText("Fehler: 0");
        imageLabel.setIcon(null);
    }

    private void updateImage() {
        String imageName = "/hangman/hangman" + errors + ".png";
        java.net.URL imgURL = getClass().getResource(imageName);

        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        }
    }

    public static void main(String[] args) {
        new HangmanGame();
    }
}
