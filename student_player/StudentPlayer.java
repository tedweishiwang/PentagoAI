package student_player;

import java.util.ArrayList;

import boardgame.Board;
import boardgame.Move;

import pentago_swap.PentagoPlayer;
import student_player.MyTools.Node;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoBoardState.Quadrant;
import pentago_swap.PentagoMove;


/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {
    
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260540022");
    }
    
    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...
        MyTools.getSomething();
        int me= boardState.getTurnPlayer();
        
        ArrayList<PentagoMove> legalmoves = boardState.getAllLegalMoves();
        PentagoBoardState cps = (PentagoBoardState) boardState.clone();
        PentagoMove win = MyTools.checkWin(boardState);
        if(win == null) {
            if(boardState.getTurnNumber()<15) {
                PentagoMove lose = MyTools.checkLose(boardState);
                if(boardState.isLegal(lose)) return lose;
                else return boardState.getRandomMove();
            }
            else {
                Node ans= MyTools.minimax(boardState,me,me,Integer.MIN_VALUE,Integer.MAX_VALUE);
                PentagoMove lose = MyTools.checkLose(boardState);
                if(boardState.isLegal(lose)) return lose;
                else return ans.move;
            }
        }
        else {
            return win;
        }
    }
    
}
