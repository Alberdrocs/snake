/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 *
 * @author alu20925322z
 */
public class Node {
    
    public int row;
    public int col;
    
    public Node(int row, int col){
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
    
    
}
