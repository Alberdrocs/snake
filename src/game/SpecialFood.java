/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import snake.Node;

/**
 *
 * @author alu20925322z
 */
public class SpecialFood {
    
    public int row = -1;
    public int col = -1;
    
    public SpecialFood(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    public int getRow(){
        return row;
    }
    
    public void setRow(int row){
        this.row = row;
    }
    
    public int getCol(){
        return col;
    }
    
    public void setCol(int col){
        this.col = col;
    }
    
    public void paintSpecialFood(Graphics g, int squareWidth, int squareHeight){
        Board.drawSquare(g, getRow(), getCol(), squareWidth, squareHeight, Color.BLUE);
    }
}