package project;


import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class myFrame {
    private JFrame frame;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private JButton restartButton;

    public myFrame() {
    	// JFrame oluşturma
        frame = new JFrame("Sıralama Oyunu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("icon.png").getImage());
        
        // Oyun paneli ve skor paneli oluşturma
        gamePanel = new GamePanel(this);
        scorePanel = new ScorePanel();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(gamePanel, BorderLayout.CENTER);
        contentPanel.add(scorePanel, BorderLayout.EAST);

        frame.getContentPane().add(contentPanel);
        
        // Buton paneli oluşturma
        JPanel buttonPanel = new JPanel();
        restartButton = new JButton("Yeniden Başlat");

        // Yeniden Başlat butonuna tıklayınca restartGame metodu çağrılıyor.
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
        
        buttonPanel.add(restartButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.setPreferredSize(new Dimension(400, 350));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    // Oyunun sonunda çağrılan endGame metodu
    public void endGame(int score) {
        gamePanel.stopTimer();
        // Puan için kullanıcıdan isim alınıyor
        String playerName = JOptionPane.showInputDialog(frame, "Puanınız için isminizi girin:");
        
        // İsim boş değilse ve girildiyse skor paneline ekleniyor
        if (playerName != null && !playerName.isEmpty()) {
            scorePanel.addScore(playerName, score);
        }
        scorePanel.showScoreboard();
        gamePanel.resetGame();
    }
    
    // Oyunu yeniden başlatan metot
    public void restartGame() {
        gamePanel.stopTimer();
        gamePanel.resetGame();
        gamePanel.startGame();
    }
}
