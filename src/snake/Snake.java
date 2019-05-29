/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import game.Board;
import game.Food;
import game.ScoreBoard;
import game.SpecialFood;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author alu20925322z
 */
public class Snake{
    
    private List<Node> body;
    private int remainGrow;
    private Direction direction = Direction.left;
    long tInit;
    
    
    public Snake(int numNodes){
        body = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            body.add(new Node(Board.getRows()/2, i + (Board.getCols()/2)));
        }
    }
    
    public int getSnakeSize(){
        return body.size();
    }

    public Direction getDirection() {
        return direction;
    }
    
    public boolean move(){
        
        tInit = System.currentTimeMillis();
        float t;
        t = (System.currentTimeMillis() - tInit) / 100.0f;
        Toolkit.getDefaultToolkit().sync();
//        Node firstNode = body.get(0);
//        Node node;
//        Node nextNode;
//        int nextPiecePosition[] = {firstNode.getRow(), firstNode.getCol()};
//        switch(direction){ 
//        case right:
//            firstNode.setCol(firstNode.getCol() + 1);
//            body.set(0, firstNode);
//            break;
//        case up:
//            firstNode.setRow(firstNode.getRow() - 1);
//            body.set(0, firstNode);
//            break;
//        case down:
//            firstNode.setRow(firstNode.getRow() + 1);
//            body.set(0, firstNode);
//            break;
//        case left:
//            firstNode.setCol(firstNode.getCol() - 1);
//            body.set(0, firstNode);
//            break;
//        }
//        node = new Node(nextPiecePosition[0], nextPiecePosition[1]);
//        nextNode = body.get(1);
//        body.set(1, node);
//        for (int i = 2; i < body.size(); i++) {
//            nextPiecePosition[0] = nextNode.getRow();
//            nextPiecePosition[1] = nextNode.getCol();
//            node = new Node(nextPiecePosition[0], nextPiecePosition[1]);
//            nextNode = body.get(i);
//            body.set(i, node);
//        }

        int row = body.get(0).getRow();
        int col = body.get(0).getCol();
        switch (direction) {
            case up:
                if (direction != Direction.down) {
                    moveTo(row - 1, col);
                }
                break;
            case down:
                if (direction != Direction.up) {
                    moveTo(row + 1, col);
                }
                break;
            case left:
                if (direction != Direction.right) {
                    moveTo(row, col - 1);
                }
                break;
            case right:
                if (direction != Direction.left) {
                    moveTo(row, col + 1);
                }
                break;
        }


        detectFood();
        detectSpecialFood();
        return checkColision(body.get(0));
    }
    
    private void moveTo(int row, int col) {
        body.add(0, new Node(row, col));
        if (remainGrow == 0) {
            body.remove(body.size() - 1);
        } else {
            remainGrow --;
        }
}
    
    public boolean checkColision(Node firstNode){
        if(firstNode.getCol() < 0 || firstNode.getCol() >= Board.num_cols || 
                firstNode.getRow() < 0 || firstNode.getRow() >= Board.num_rows){
            return true;
        } else {
            Node node;
            for (int i = 1; i < body.size(); i++) {
                node = body.get(i);
                if(node.getCol() == firstNode.getCol() && node.getRow() == firstNode.getRow()){
                    return true;
                }
            }
            return false;
        }
    }
    
    public void changeDirection(Direction direction){
        this.direction = direction;
    }
    
    public void paintSnake(Graphics g, int squareWidth, int squareHeight){
        for (Node node: body) {
            Board.drawSquare(g, node.getRow(), node.getCol(), squareWidth, squareHeight, Color.RED);
        }
    }

    private void detectFood() {
        Food food = Board.getFood();
        if(food.getRow()==body.get(0).getRow() && food.getCol()==body.get(0).getCol()){
            System.out.println("Food eated");
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            Node node;
            boolean validPosition;
            Random random = new Random();
            do{
                validPosition = true;
                food.setCol(random.nextInt(Board.getCols()));
                food.setRow(random.nextInt(Board.getRows()));
                for (int i = 0; i < body.size(); i++) {
                    node = body.get(i);
                    if(node.getCol() == food.getCol() && node.getRow() == food.getRow()){
                        validPosition = false;
                        System.out.println("Dentro del bucle de detect food");
                    }
                }
            } while(!validPosition);
        }
    }
    
    private void detectSpecialFood() {
        Random random = new Random();
        SpecialFood specialFood = Board.getSpecialFood();
        if(specialFood.getRow()==body.get(0).getRow() && specialFood.getCol()==body.get(0).getCol()){
            System.out.println("Food eated");
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            body.add(body.get(body.size() - 1));
            specialFood.setCol(-1);
            specialFood.setRow(-1);
            
            
        } else if(random.nextInt(15) == 1 && specialFood.getCol() == -1 && specialFood.getRow() == -1){
            Node node;
            boolean validPosition;
            int numeroRandom = random.nextInt(5);
            System.out.println(numeroRandom);
            if(random.nextInt(5) == 1){
                do{
                    validPosition = true;
                    specialFood.setCol(random.nextInt(Board.getCols()));
                    specialFood.setRow(random.nextInt(Board.getRows()));
                    for (int i = 0; i < body.size(); i++) {
                        node = body.get(i);
                        if(node.getCol() == specialFood.getCol() && node.getRow() == specialFood.getRow()){
                            validPosition = false;
                            System.out.println("Dentro del bucle de detect special food");
                        }
                    }
                } while(!validPosition);
            }
            
        } else if(random.nextInt(15) == 2){
            specialFood.setCol(-1);
            specialFood.setRow(-1);
        }
    }
    
}