package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Random;

public class gui extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private JLayeredPane layeredPane;
    private JLabel imageLabel, resultLabel;
    private JButton rockBtn, paperBtn, scissorsBtn;
    private ImageIcon icon;
    private Player player;
    private Player computer;
    private JLabel playerGifLabel; 
    private JLabel computerGifLabel; 
	private String newGifPath;
	private JLabel newGifLabel;

    public gui() {
        player = new Player("");
        computer = new Player("");
        homeGUI();
    }

    public void homeGUI() {
        frame = new JFrame("Bato-Bato Pick!");
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1400, 900));
        frame.add(layeredPane);

        // Buttons
        rockBtn = new JButton("");
        rockBtn.setBounds(65, 690, 355, 70);
        buttonLayout(rockBtn);
        rockBtn.addActionListener(this);
        layeredPane.add(rockBtn, JLayeredPane.MODAL_LAYER);

        paperBtn = new JButton("");
        paperBtn.setBounds(520, 690, 355, 70);
        buttonLayout(paperBtn);
        paperBtn.addActionListener(this);
        layeredPane.add(paperBtn, JLayeredPane.MODAL_LAYER);

        scissorsBtn = new JButton("");
        scissorsBtn.setBounds(990, 690, 355, 70);
        buttonLayout(scissorsBtn);
        scissorsBtn.addActionListener(this);
        layeredPane.add(scissorsBtn, JLayeredPane.MODAL_LAYER);

        // Frame background image
        icon = resizeImage("src/game/images/jackNpoy (1).png", 1400, 900);
        imageLabel = new JLabel(icon);
        imageLabel.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
       
        // New GIF path
        newGifPath = "src/game/images/2.gif";

        // New GIF label
        newGifLabel = new JLabel(new ImageIcon(newGifPath));
        newGifLabel.setBounds(1100, 0, 220, 220);  // Position and size of the GIF
        layeredPane.add(newGifLabel, JLayeredPane.PALETTE_LAYER);

        // Result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setBounds(400, 100, 600, 100);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 36));
        resultLabel.setForeground(Color.BLACK);
        layeredPane.add(resultLabel, JLayeredPane.PALETTE_LAYER);

        frame.setContentPane(layeredPane);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	resultLabel.setText("");  // Clear the result label
    	
        if (e.getSource() == rockBtn) {
            player.setChoice(new Rock());
            updatePlayerGIF("src/game/images/uRock0.gif");
        } else if (e.getSource() == paperBtn) {
            player.setChoice(new Paper());
            updatePlayerGIF("src/game/images/uPaper0.gif");
        } else if (e.getSource() == scissorsBtn) {
            player.setChoice(new Scissors());
            updatePlayerGIF("src/game/images/uScissors0.gif");
        }

        // Generate computer choice and update its GIF
        computer.setChoice(generateRandomChoice());
        updateComputerGIF(getGifPathForChoice(computer.getChoice().getName())); //setter & getter
        
   /*     // Display the result after GIFs are removed
        resultLabel.setBounds(400, 0, 600, 200); // Set bounds for result label
        resultLabel.setFont(new Font("Arial", Font.BOLD, 60)); // Increase the font size here
        resultLabel.setText("<html><span style='color:black;'>" 
        	    + player.getChoice().getName() + " vs " 
        	    + computer.getChoice().getName() + "</span></html>");
    */    
        // First Timer: Show the match-up for 2.1 seconds
        Timer timer1 = new Timer(1600, event -> {
            // Remove the GIFs after the first delay
            if (playerGifLabel != null) {
                layeredPane.remove(playerGifLabel);
            }
            if (computerGifLabel != null) {
                layeredPane.remove(computerGifLabel);
            }
            
            // Second Timer: Delay before showing the final result
            Timer timer2 = new Timer(1300, event2 -> {
                // Display the final result after a 1-second delay
                String result = player.getChoice().interact(computer.getChoice());
                resultLabel.setBounds(210, 300, 1000, 250); // Set bounds for result label
                resultLabel.setFont(new Font("Arial", Font.BOLD, 60)); // Set font size
                resultLabel.setText("<html><span style='color:white;'>"
                        + result + "</span></html>");
                
                // Refresh the layered pane to update the UI
                layeredPane.repaint();
            });
            
            timer2.setRepeats(false);  // Ensure the second timer only runs once
            timer2.start();  // Start the second timer
        });

        timer1.setRepeats(false);  // Ensure the first timer only runs once
        timer1.start();  // Start the first timer
    }
    
    
    
    private Hand generateRandomChoice() {
        Hand[] choices = {new Rock(), new Paper(), new Scissors()};
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    private String getGifPathForChoice(String choice) {
        switch (choice) {
            case "Rock":
                return "src/game/images/cRock0.gif"; // Path to the Rock GIF
            case "Paper":
                return "src/game/images/cPaper0.gif"; // Path to the Paper GIF
            case "Scissors":
                return "src/game/images/cScissors0.gif"; // Path to the Scissors GIF
            default:
                return ""; // Return empty if choice is invalid
        }
    }
    
    private void updatePlayerGIF(String gifPath) {
        if (playerGifLabel != null) {
            layeredPane.remove(playerGifLabel); // Remove existing player GIF
        }

        File gifFile = new File(gifPath);
        if (gifFile.exists()) {
            ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
            playerGifLabel = new JLabel(gifIcon);
            playerGifLabel.setBounds(0, 0, gifIcon.getIconWidth(), gifIcon.getIconHeight());
            layeredPane.add(playerGifLabel, JLayeredPane.PALETTE_LAYER);
            layeredPane.repaint(); // Refresh the layered pane
        } else {
            System.out.println("Player GIF file not found: " + gifPath);
        }
    }

    private void updateComputerGIF(String gifPath) {
        if (computerGifLabel != null) {
            layeredPane.remove(computerGifLabel); // Remove existing computer GIF
        }

        File gifFile = new File(gifPath);
        if (gifFile.exists()) {
            ImageIcon gifIcon = new ImageIcon(gifFile.getAbsolutePath());
            computerGifLabel = new JLabel(gifIcon);
            computerGifLabel.setBounds(585, 0, gifIcon.getIconWidth(), gifIcon.getIconHeight()); // Position it separately
            layeredPane.add(computerGifLabel, JLayeredPane.PALETTE_LAYER);
            layeredPane.repaint(); // Refresh the layered pane
        } else {
            System.out.println("Computer GIF file not found: " + gifPath);
        }
    }
	
    public void buttonLayout(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.BOLD, 50));
        button.setForeground(Color.WHITE);
    }

    public static ImageIcon resizeImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public static void main(String[] args) {
        new gui();
    }
}
