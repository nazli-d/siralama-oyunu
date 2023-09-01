package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

class GamePanel extends JPanel {
    private ArrayList<JButton> buttons;
    private Timer timer;
    private int secondsPassed;
    private boolean gameStarted;
    private myFrame MyFrame;
    private JLabel timerLabel;
    private int expectedNumber;
    private JLabel instructionLabel;
    
    public GamePanel(myFrame MyFrame) {
        setLayout(new BorderLayout());
        buttons = new ArrayList<>();
        setBackground(Color.WHITE);
        
        // Sıralama yapma talimatı için JLabel oluşturma
        instructionLabel = new JLabel("1'den 10'a kadar sıralama yapınız.");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(instructionLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        for (int i = 1; i <= 10; i++) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(80, 80));
            buttons.add(button);
            buttonPanel.add(button);
        }

        // Her bir düğmeye numara atanıyor ve stil ayarları yapılıyor
        for (int i = 0; i < buttons.size(); i++) {
            JButton button = buttons.get(i);
            int number = i + 1;
            button.setText(Integer.toString(number));      
            button.setBackground(Color.MAGENTA);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 20));
        }

        timerLabel = new JLabel();
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        topPanel.add(timerLabel, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(buttonPanel, BorderLayout.CENTER);
        contentPanel.add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        this.MyFrame = MyFrame;
        startGame();
    }
    
    // Oyunu başlatan metot
    public void startGame() {
        Collections.shuffle(buttons);
        gameStarted = true;
        secondsPassed = 0;
        expectedNumber = 1;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                timerLabel.setText("Time: " + secondsPassed);

                if (expectedNumber > 10) {
                    endGame(secondsPassed);
                }
            }
        });
        timer.start();

        for (int i = 0; i < buttons.size(); i++) {
            JButton button = buttons.get(i);
            int number = i + 1;
            button.setText(Integer.toString(number));

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gameStarted && button.getText().equals(Integer.toString(expectedNumber))) {
                        button.setEnabled(false);
                        expectedNumber++;

                        if (expectedNumber <= 10) {
                            buttons.get(expectedNumber - 1).setEnabled(true);
                        } else {
                            endGame(secondsPassed);
                        }
                    }
                }
            });
        }
    }
    
    // Zamanlayıcıyı başlatan metot
    public void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsPassed++;
                timerLabel.setText("Time: " + secondsPassed);

                if (expectedNumber > 10) {
                    endGame(secondsPassed);
                }
            }
        });
        timer.start();
    }
    
    // Oyunu bitiren metot
    private void endGame(int time) {
        gameStarted = false;
        timer.stop();
        MyFrame.endGame(time);
    }

    // Oyunu sıfırlayan metot
    public void resetGame() {
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setText("");
        }
        secondsPassed = 0;
        timerLabel.setText("");
    }

	// Zamanlayıcıyı durduran metot
    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

}
