package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class ScorePanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private ArrayList<ScoreEntry> scores;

    public ScorePanel() {
        scores = new ArrayList<>();
        model = new DefaultTableModel(new Object[]{"Oyuncu", "Zaman"}, 0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);

        table.setBackground(Color.GRAY);
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
    }
    
    // Skorları oyuncu adı ve süreye göre ekleyen ve tabloyu güncelleyen metot
    public void addScore(String playerName, int time) {
        scores.add(new ScoreEntry(playerName, time));
        Collections.sort(scores); 
        updateTable();
        saveScoresToFile(); 
    }
    
    // Skor tablosunu gösteren JFrame'i oluşturan metot
    public void showScoreboard() {
        JFrame scoreFrame = new JFrame("Scoreboard");
        scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scoreFrame.getContentPane().add(scrollPane);
        scoreFrame.pack();
        scoreFrame.setVisible(true);
        scoreFrame.setIconImage(new ImageIcon("icon.png").getImage());
    }

    private void updateTable() {
        model.setRowCount(0); 
        for (ScoreEntry entry : scores) {
            model.addRow(new Object[]{entry.getPlayerName(), entry.getTime()});
        }
    }
    
    // Skorları scores.txt dosyasına kaydeden metot
    private void saveScoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"))) {
            for (ScoreEntry entry : scores) {
                writer.write(entry.getPlayerName() + "," + entry.getTime());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ScoreEntry implements Comparable<ScoreEntry> {
        private String playerName;
        private int time;

        public ScoreEntry(String playerName, int time) {
            this.playerName = playerName;
            this.time = time;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getTime() {
            return time;
        }

        @Override
        public int compareTo(ScoreEntry other) {
            return Integer.compare(time, other.getTime());
        }
    }
}
