import java.util.HashSet;

import acm.graphics.GImage;


public class Bishop extends Piece{
	public Bishop(int x, int y, boolean color) {
		super(x, y, color, 3);
		if(color){
			image = new GImage("bb.gif");
		}
		else{
			image = new GImage("wb.gif");
		}
	}
	public Bishop(Bishop p) {
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
		Square curSquare = b[row][col];
		int r = row + 1;
		int c = col + 1;
		while(c < 8 && r < 8){
			if(b[r][c].p != null){
				if(b[r][c].p.color != color){
					possMoves.add(new Move(this, curSquare, b[r][c]));
					attMoves.add(new Move(this, curSquare, b[r][c]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b[r][c]));
				}
				break;
			}
			possMoves.add(new Move(this, curSquare, b[r][c]));
			r++;
			c++;
		}
		r = row + 1;
		c = col - 1;
		while(c >= 0 && r < 8){
			if(b[r][c].p != null){
				if(b[r][c].p.color != color){
					possMoves.add(new Move(this, curSquare, b[r][c]));
					attMoves.add(new Move(this, curSquare, b[r][c]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b[r][c]));
				}
				break;
			}
			possMoves.add(new Move(this, curSquare, b[r][c]));
			r++;
			c--;
		}
		r = row - 1;
		c = col + 1;
		while(c < 8 && r >= 0){
			if(b[r][c].p != null){
				if(b[r][c].p.color != color){
					possMoves.add(new Move(this, curSquare, b[r][c]));
					attMoves.add(new Move(this, curSquare, b[r][c]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b[r][c]));
				}
				break;
			}
			possMoves.add(new Move(this, curSquare, b[r][c]));
			r--;
			c++;
		}
		r = row - 1;
		c = col - 1;
		while(c >= 0 && r >= 0){
			if(b[r][c].p != null){
				if(b[r][c].p.color != color){
					possMoves.add(new Move(this, curSquare, b[r][c]));
					attMoves.add(new Move(this, curSquare, b[r][c]));
				}
				else{
					protMoves.add(new Move(this, curSquare, b[r][c]));
				}
				break;
			}
			possMoves.add(new Move(this, curSquare, b[r][c]));
			r--;
			c--;
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
		return (color ? "Black " : "White ") + "Bishop (" + row + ", " + col + ")";
	}
}
