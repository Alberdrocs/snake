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
    private boolean girando = false;
    
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

        
        
        MyKeyAdapter keyAdepter = new MyKeyAdapter();
        addKeyListener(keyAdepter);
        setFocusable(true);
        
        int deltaTime = 100;
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
    }


    private void drawBoard(Graphics2D g2d) {
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_cols; col++) {
                drawSquare(g2d, row, col, squareWidth(), squareHeight(), Color.DARK_GRAY);
            }
        }
    }
    
    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(null,
            "Game Over.\nYour size has been " + score.getScore());
        startNewGame();
    }

    private void startNewGame() {
        snake = new Snake(5);
        food = new Food(4, 4);
        timer.restart();
    }
    
    private void pauseGame() {
            timer.stop();
            JOptionPane.showMessageDialog(null,
            "Paused.\nPress OK to resume");
            timer.start();
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
