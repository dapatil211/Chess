import java.util.ArrayList;
import java.util.HashSet;

import acm.graphics.GImage;


public class Knight extends Piece{
	public Knight(int x, int y, boolean color) {
		super(x, y, color, 3);
		if(color){
			image = new GImage("bn.gif");
		}
		else{
			image = new GImage("wn.gif");
		}
	}
	public Knight(Knight p) {
		this(p.row, p.col, p.color);
		attMoves = new HashSet<Move>(p.attMoves);
		possMoves = new HashSet<Move>(p.possMoves);
		protMoves = new HashSet<Move>(p.protMoves);
	}
	@Override
	public void updateMoves(Board bo) {
		possMoves.clear();
		attMoves.clear();
		protMoves.clear();
//		HashSet<Move> possMoves = new HashSet<Move>();
		Square[][] b = bo.board;
		ArrayList<Square> temp = new ArrayList<Square>();
		temp.add(new Square(row + 2, col + 1));
		temp.add(new Square(row - 2, col + 1));
		temp.add(new Square(row + 2, col - 1));
		temp.add(new Square(row - 2, col - 1));
		temp.add(new Square(row + 1, col + 2));
		temp.add(new Square(row - 1, col + 2));
		temp.add(new Square(row - 1, col - 2));
		temp.add(new Square(row + 1, col - 2));
		Square curSquare = b[row][col];
		for(Square s : temp){
			if(s.row >= 0 && s.row < 8 && s.col >= 0 && s.col < 8){
				if(b[s.row][s.col].p != null){
					if(b[s.row][s.col].p.color == color){
						protMoves.add(new Move(this, curSquare, b[s.row][s.col]));
						continue;
					}
					else{
						attMoves.add(new Move(this, curSquare, b[s.row][s.col]));
					}
				}
				possMoves.add(new Move(this, curSquare, b[s.row][s.col]));
			}
		}
//		for(Move m : possMoves){
//			Board b1 = new Board(bo, m);
//			if(!b1.inCheck(color)){
//				this.possMoves.add(m);
//			}
//		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (color ? "Black " : "White ") + "Knight (" + row + ", " + col + ")";
	}
}
