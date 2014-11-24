import java.util.HashSet;

import acm.graphics.GObject;


public abstract class Piece {
	boolean moved;
	int row, col;
	boolean taken = false;
	boolean color;
	GObject image;
	HashSet<Move> possMoves = new HashSet<Move>();
	HashSet<Move> attMoves = new HashSet<Move>();
	HashSet<Move> protMoves = new HashSet<Move>();
	public double score;
	public Piece(int row, int col, boolean color, double score) {
		this.color = color;
		this.row = row; 
		this.col = col;
		this.score = score;
	}
	public static Piece clonePiece(Piece p){
		if(p instanceof King){
			return new King((King) p);
		}
		else if(p instanceof Queen){
			return new Queen((Queen) p);
		}
		else if(p instanceof Rook){
			return new Rook((Rook) p);
		}
		else if(p instanceof Bishop){
			return new Bishop((Bishop) p);
		}
		else if(p instanceof Knight){
			return new Knight((Knight) p);
		}
		else if(p instanceof Pawn){
			return new Pawn((Pawn) p);
		}
		else return null;
	}
	public Piece(Piece p) {
		this(p.row, p.col, p.color, p.score);
	}
	public GObject getImage() {
		return image;
	}
	public boolean possible(int row, int col) {
		return possMoves.contains(new Move(this, this.row, this.col, row, col));
	}
	public boolean isAttacked(Board b){
		Square s = new Square(row, col);
		for(Piece p : b.pieces){
			if(p.color != color && (s.contains(p.possMoves) || s.contains(p.protMoves))){
				return true;
			}
		}
		return false;
	}
	public void update(int row, int col, Board b) {
		this.row = row;
		this.col = col;
		moved = true;
	}
	public void updateMoves(Board b) {
		possMoves.clear();	
	}
	public boolean pieceEquals(Piece p) {
		if(p.color != color){
			return false;
		}
		if(p instanceof Pawn && this instanceof Pawn){
			return true;
		}
		else if(p instanceof King && this instanceof King){
			return true;
		}
		else if(p instanceof Queen && this instanceof Queen){
			return true;
		}
		else if(p instanceof Rook && this instanceof Rook){
			return true;
		}
		else if(p instanceof Bishop && this instanceof Bishop){
			return true;
		}
		else if(p instanceof Knight && this instanceof Knight){
			return true;
		}
		else return false;
	}
	@Override
	public String toString() {
		return getClass().getName();
	}
}
