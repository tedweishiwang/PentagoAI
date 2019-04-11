package student_player;

import java.util.ArrayList;
import java.util.Random;

import boardgame.Board;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoMove;
import student_player.MyTools.Node;

public class MyTools {
    public static double getSomething() {
        return Math.random();
    }
    
    public static class Node{
        public PentagoMove move;
        public int score;
        /*public moveandscore(PentagoMove move, int score) {
         this.move=move;
         this.score=score;
         }*/
        
        public void setMove(PentagoMove move) {
            this.move=move;
        }
        public void setScore(int score) {
            this.score=score;
        }
    }
    
    
    public static Node minimax(PentagoBoardState boardState,int player,int current,int alpha,int beta) {
        
        ArrayList<PentagoMove> legalmoves = boardState.getAllLegalMoves();
        int Best;
        if (player==current) {
            Best= -(Integer.MAX_VALUE);
        }
        else{
            Best= (Integer.MAX_VALUE);
        }
        
        PentagoMove initmove=null;
        Node nextnode = new Node();
        nextnode.setMove(initmove);
        
        if(boardState.getWinner()!=Board.NOBODY) {
            nextnode.setScore(Best);
            if (boardState.getWinner()==current) {
                Best=100000 ;
                nextnode.setScore(Best);
            }
            else if (boardState.getWinner()==Board.DRAW){
                Best= 0;
                nextnode.setScore(Best);
            }
            else {
                Best=-100000;
                nextnode.setScore(Best);
            }
        }
        
        else {
            for(PentagoMove v: legalmoves) {
                PentagoBoardState newBoard= (PentagoBoardState) boardState.clone();
                newBoard.processMove(v);
                int p_now=player;
                if(player==0) player=1;
                else player=0;
                int score= minimax(newBoard,player,current,alpha,beta).score;
                
                if(p_now==current) {
                    
                    if(score>alpha) {
                        alpha=score;
                        nextnode.setScore(score);
                        nextnode.setMove(v);
                    }
                }
                
                else {
                    if(score<beta) {
                        beta=score;
                        nextnode.setMove(v);
                        nextnode.setScore(score);
                    }
                }
                if (alpha >= beta) break;
            }
        }
        
        if(player==current){
            nextnode.setScore(alpha);
        }
        else {
            nextnode.setScore(beta);
        }
        return nextnode;
    }
    
    
    
    public static PentagoMove checkWin(PentagoBoardState boardState) {
        ArrayList<PentagoMove> legalmoves = boardState.getAllLegalMoves();
        for(PentagoMove m: legalmoves) {
            PentagoBoardState cps = (PentagoBoardState) boardState.clone();
            cps.processMove(m);
            if(cps.getWinner()==boardState.getTurnPlayer()) {
                System.out.print(cps.getTurnPlayer() + " I will win in the next move." + m.getMoveCoord().getX() + "," + m.getMoveCoord().getY());
                return m;
            }
        }
        return null;
    }
    
    public static PentagoMove checkLose(PentagoBoardState boardState) {
        ArrayList<PentagoMove> legalmoves_pre = boardState.getAllLegalMoves();
        PentagoBoardState cps = (PentagoBoardState) boardState.clone();
        Random r = new Random();
        //int rand = 0;
        //int rand = r.nextInt((legalmoves_pre.size()-1 - 0) + 1) + 0;
        //PentagoMove rand_move = legalmoves_pre.get(rand);
        for(PentagoMove k: legalmoves_pre) {
            cps = (PentagoBoardState) boardState.clone();
            cps.processMove(k);
            ArrayList<PentagoMove> legalmoves = cps.getAllLegalMoves();
            int valid = 1;
            for(PentagoMove m: legalmoves) {
                PentagoBoardState cps2 = (PentagoBoardState) cps.clone();
                cps2.processMove(m);
                if(cps2.getWinner()==cps.getTurnPlayer()) {
                    valid = 0;
                    System.out.println("cant play here " + m.getMoveCoord().getX() + "," + m.getMoveCoord().getY() );
                }
            }
            if (valid==1) {
                System.out.println("Defensive play");
                return k;
            }
        }
        return (PentagoMove) boardState.getRandomMove();
    }
    
}
