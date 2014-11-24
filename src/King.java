import java.util.ArrayList;
import java.util.HashSet;

import acm.graphics.GImage;


public class King extends Piece{
	boolean inCheck = false;
	public King(int x, int y, boolean color) {
		super(x, y, color, Double.POSITIVE_INFINITY);
		if(color){
			image = new GImage("bk.gif");
		}
		else{
			image = new GImage("wk.gif");
		}
	}
	public King(King p) {
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
		if(b == null){
			return;
		}
		Square[][] board = b.board;
		ArrayList<Square> temp = new ArrayList<Square>();
		temp.add(new Square(row, col + 1));
		temp.add(new Square(row - 1, col + 1));
		temp.add(new Square(row + 1, col + 1));
		temp.add(new Square(row - 1, col));
		temp.add(new Square(row + 1, col));
		temp.add(new Square(row - 1, col - 1));
		temp.add(new Square(row, col - 1));
		temp.add(new Square(row + 1, col - 1));
		Square curSquare = board[row][col];
		for(Square s : temp){
			if(s.row >= 0 && s.row < 8 && s.col >= 0 && s.col < 8){
				if(board[s.row][s.col].p != null){
					if(board[s.row][s.col].p.color == color){
						protMoves.add(new Move(this, curSquare, board[s.row][s.col]));
					}
					else{
						attMoves.add(new Move(this, curSquare, board[s.row][s.col]));
						possMoves.add(new Move(this, curSquare, board[s.row][s.col]));
					}
				}
				else{
					possMoves.add(new Move(this, curSquare, board[s.row][s.col]));
				}
			}
		}
		if(!moved && board[row][0].p instanceof Rook && !board[row][0].p.moved){
			boolean castle = true;
			for(int i = 0; i <= col; i ++){
				if(board[row][i].isAttacked(!color, b)){
					castle = false;
					break;
				}
			}
			for(int i = 1; i < col; i ++){
				if(board[row][i].p != null){
					castle = false;
					break;
				}
			}
			if(castle){
				possMoves.add(new Move(this, curSquare, board[row][col - 2]));
			}
		}
		if(!moved && board[row][7].p instanceof Rook && !board[row][7].p.moved){
			boolean castle = true;
			for(int i = col; i < 8; i ++){
				if(board[row][i].isAttacked(!color, b)){
					castle = false;
					break;
				}
			}
			for(int i = col + 1; i < 7; i ++){
				if(board[row][i].p != null){
					castle = false;
					break;
				}
			}
			if(castle){
				possMoves.add(new Move(this, curSquare, board[row][col + 2]));
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
		return (color ? "Black " : "White ") + "King (" + row + ", " + col + ")";
	}
}
