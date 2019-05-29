/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import snake.Direction;
import snake.Snake;

/**
 *
 * @author alu20925322z
 */
public class Board extends javax.swing.JPanel {

    public static int num_rows = 20;
    public static int num_cols = 20;
    
    private ScoreBoard score;
    
    private Timer timer;
    private Snake snake;
    private static Food food;
    private static SpecialFood specialFood;
    private boolean girando = false;
    private boolean paused = false;
    private HashMap<String, String> playersScore = new HashMap<>();
    private List<String> topPlayers = new ArrayList<String>();
    
    
    public void setScoreBoard(ScoreBoard score){
        this.score = score;
    }
    
    
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        snake = new Snake(5);
        food = new Food(4, 4);
        specialFood = new SpecialFood(11, 11);

        
        MyKeyAdapter keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        setFocusable(true);
        
        int deltaTime = 125;
        timer = new Timer (deltaTime, new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                mainLoop();
            }
        });
        timer.start();
    }
    
    public void mainLoop(){
        if(snake.move()){
            gameOver();
        } else{
            score.setScore(snake.getSnakeSize());
            repaint();
            girando = false;
        }
    }
    
    public static Food getFood(){
        return food;
    }
    
    public static SpecialFood getSpecialFood(){
        return specialFood;
    }
    
    public static int getRows(){
        return num_rows;
    }
    
    public static int getCols(){
        return num_cols;
    }
    
    private int squareWidth(){
        return getWidth() / num_cols;
    }
    
    private int squareHeight() {
        return getHeight() / num_rows;
    }
    
    public static void drawSquare(Graphics g, int row, int col, int squareWidth, int squareHeight, Color color) {
        int x = col * squareWidth;
        int y = row * squareHeight;
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth - 2,
        squareHeight - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight - 1, x, y);
        g.drawLine(x, y, x + squareWidth - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight - 1,
        x + squareWidth - 1, y + squareHeight - 1);
        g.drawLine(x + squareWidth - 1,
        y + squareHeight - 1,
        x + squareWidth - 1, y + 1);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawBoard(g2d);
        snake.paintSnake(g2d, squareWidth(), squareHeight());
        food.paintFood(g2d, squareWidth(), squareHeight());
        specialFood.paintSpecialFood(g2d, squareWidth(), squareHeight());
    }


    private void drawBoard(Graphics2D g2d) {
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                drawSquare(g2d, row, col, squareWidth(), squareHeight(), Color.DARK_GRAY);
            }
        }
    }
    
    private void gameOver() {
        int score1 = 0;
        int score2 = 0;
        int score3 = 0;
        timer.stop();
        if(playersScore.size() < 3){
            JOptionPane.showMessageDialog(null,
            "Game Over.\nYour size has been " + score.getScore());
            String nombre = JOptionPane.showInputDialog("Write your name.");
            System.out.println("Nombre: " + nombre);
            if(nombre == null || nombre.equals("")){
                System.out.println("Nombre null o vacio");
                startNewGame();
                return;
            }
            playersScore.put(nombre, Integer.toString(score.getScore()));
            topPlayers.add(nombre);
            System.out.println("player añadidio");
            startNewGame();
        } else {
            JOptionPane.showMessageDialog(null,
            "Game Over.\nYour size has been " + score.getScore());
            String nombre = JOptionPane.showInputDialog("Write your name.");
            playersScore.put(nombre, Integer.toString(score.getScore()));
            topPlayers.add(nombre);
            System.out.println("Tamaño: " + topPlayers.size());
            for (Map.Entry<String, String> en : playersScore.entrySet()) {
                String key = en.getKey();
                String val = en.getValue();
                int valInt = Integer.parseInt(val);
                if(valInt > score1){
                    score2 = score1;
                    score1 = valInt;
                    topPlayers.set(2, topPlayers.get(1));
                    topPlayers.set(1, topPlayers.get(0));
                    topPlayers.set(0, key + ": " + score1);
                    
                } else if(valInt > score2){
                    score3 = score2;
                    score2 = valInt;
                    topPlayers.set(2, topPlayers.get(1));
                    topPlayers.set(1, key + ": " + score2);
                } else if(valInt > score3){
                    score3 = valInt;
                    topPlayers.set(2, key + ": " + score3);
                }
            }
            JOptionPane.showMessageDialog(null,"High Scores:\n1. " + topPlayers.get(0) + "\n2. " + 
                    topPlayers.get(1) + "\n3. " + topPlayers.get(2));
            startNewGame();
        }
        
    }

    private void startNewGame() {
        snake = new Snake(5);
        food = new Food(4, 4);
        specialFood = new SpecialFood(11, 11);
        timer.restart();
    }
    
    private void pauseGame() {
        if(paused){
            timer.start();
            paused = !paused;
        } else {
            timer.stop();
            paused = !paused;
        }
            
        }
    
    class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if(girando != true){
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(snake.getDirection() == Direction.up || snake.getDirection() == Direction.down){
                        girando = true;
                    }
                    if(snake.getDirection() != Direction.right){
                        snake.changeDirection(Direction.left);
                    } 
                break;
                case KeyEvent.VK_RIGHT:
                    if(snake.getDirection() == Direction.up || snake.getDirection() == Direction.down){
                        girando = true;
                    }
                    if(snake.getDirection() != Direction.left){
                        snake.changeDirection(Direction.right);
                    }  
                break;
                case KeyEvent.VK_UP:
                    if(snake.getDirection() == Direction.right || snake.getDirection() == Direction.left){
                        girando = true;
                    }
                    if(snake.getDirection() != Direction.down){
                        snake.changeDirection(Direction.up);
                    }
                break;
                case KeyEvent.VK_DOWN:
                    if(snake.getDirection() == Direction.right || snake.getDirection() == Direction.left){
                        girando = true;
                    }
                    if(snake.getDirection() != Direction.up){
                        snake.changeDirection(Direction.down);
                    }
                break;
                case KeyEvent.VK_P:
                    
                    pauseGame();
                break;
                case KeyEvent.VK_SPACE:
                default:
                break;
            }
        }
        
        repaint();
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
