import java.util.HashSet;

import acm.graphics.GImage;


public class Pawn extends Piece{
	
	public Pawn(int row, int col, boolean color) {
		super(row, col, color, 1);
		if(color){
			image = new GImage("bp.gif");
		}
		else{
			image = new GImage("wp.gif");
		}
		moved = false;
	}
	public Pawn(Pawn p) {
		this(p.row, p.col, p.color);
		attMoves = new HashSet<Move>(p.attMoves);
		possMoves = new HashSet<Move>(p.possMoves);
		protMoves = new HashSet<Move>(p.protMoves);
	}
	@Override
	public void updateMoves(Board b) {
		possMoves.clear();
		attMoves.clear();
		protMoves.clear();
//		HashSet<Move> possMoves = new HashSet<Move>();
		//TODO EnPassant
		Square curSquare = b.board[row][col];
		if(color){
			if(!b.lastMoves.isEmpty()){
				Move lastMove = b.lastMoves.getLast();
				if(lastMove.piece instanceof Pawn && Math.abs(lastMove.piece.col - col) == 1
						&& (lastMove.oldRow - lastMove.newRow) == 2 && row == 4){
					possMoves.add(new Move(this, curSquare, b.board[row + 1][lastMove.newCol]));
					attMoves.add(new Move(this, curSquare, b.board[row + 1][lastMove.newCol]));				
				}
			}
			if(row == 8){
				System.out.println(this);
			}
			if(b.board[row + 1][col].p == null){
				possMoves.add(new Move(this, curSquare, b.board[row + 1][col]));
				
				if(!moved && b.board[row + 2][col].p == null){
					possMoves.add(new Move(this, curSquare, b.board[row + 2][col]));
				}
			}
			if(col > 0 && b.board[row + 1][col - 1].p != null){
				if(b.board[row + 1][col - 1].p.color != color){
					attMoves.add(new Move(this, curSquare, b.board[row + 1][col - 1]));
					possMoves.add(new Move(this, curSquare, b.board[row + 1][col - 1]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b.board[row + 1][col - 1]));
				}
			}
			if(col < 7 && b.board[row + 1][col + 1].p != null){
				if(b.board[row + 1][col + 1].p.color != color){
					attMoves.add(new Move(this, curSquare, b.board[row + 1][col + 1]));
					possMoves.add(new Move(this, curSquare, b.board[row + 1][col + 1]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b.board[row + 1][col + 1]));
				}
			}
			
		}
		else{
			if(!b.lastMoves.isEmpty()){
				Move lastMove = b.lastMoves.getLast();
				if(lastMove.piece instanceof Pawn && Math.abs(lastMove.piece.col - col) == 1
						&& (lastMove.newRow - lastMove.oldRow) == 2 && row == 3){
					possMoves.add(new Move(this, curSquare, b.board[row - 1][lastMove.newCol]));
					attMoves.add(new Move(this, curSquare, b.board[row - 1][lastMove.newCol]));				
				}
			}
			if(b.board[row - 1][col].p == null){
				possMoves.add(new Move(this, curSquare, b.board[row - 1][col]));
				if(!moved && b.board[row - 2][col].p == null){
					possMoves.add(new Move(this, curSquare, b.board[row - 2][col]));
				}
			}
			if(col > 0 && b.board[row - 1][col - 1].p != null){
				if(b.board[row - 1][col - 1].p.color != color){
					attMoves.add(new Move(this, curSquare, b.board[row - 1][col - 1]));
					possMoves.add(new Move(this, curSquare, b.board[row - 1][col - 1]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b.board[row - 1][col - 1]));
				}
			}
			if(col < 7 && b.board[row - 1][col + 1].p != null){
				if(b.board[row - 1][col + 1].p.color != color){
					attMoves.add(new Move(this, curSquare, b.board[row - 1][col + 1]));
					possMoves.add(new Move(this, curSquare, b.board[row - 1][col + 1]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b.board[row - 1][col + 1]));
				}
			}
		}
//		for(Move m : possMoves){
//			Board b1 = new Board(b, m);
//			if(!b1.inCheck(color)){
//				this.possMoves.add(m);
//			}
//		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (color ? "Black " : "White ") + "Pawn (" + row + ", " + col + ")";
	}
}
