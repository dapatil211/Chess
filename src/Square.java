import java.awt.Color;
import java.util.HashSet;

import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;


public class Square {
	GRect r;
	Piece p;
	int col, row;
	boolean color;
	Color c;
	boolean selected;
	boolean possible;
	boolean attacked;
	Board b;
	public static final int squareLength = 72;
	public Square(int row, int col) {
		this.col = col;
		this.row = row;
		color = (col + row) % 2 == 1;
		c = color ? Color.WHITE : Color.GRAY;
//		r = new GRect(col * squareLength, row * squareLength, squareLength, squareLength);
		r = new GRect(squareLength, squareLength);
		r.setFillColor(c);
		r.setFilled(true);
		p = null;
		selected = false;
	}
	public boolean contains(HashSet<Move> hs){
		for(Move m : hs){
			if(m.s2.equals(this)){
				return true;
			}
		}
		return false;
	}
	public Square(Square s){
		this(s.row, s.col);
		this.p = Piece.clonePiece(s.p); 
	}
	public Square(int row, int col, Piece p, Board b){
		this(row, col);
		update(p, b);
	}
	public void place(Piece p, Board b){
		b.pieces.add(p);
		update(p, b);
	}
	public void update(Piece p, Board b){
		if(p == null){
			removeFocus(b);
		}
		if(p != null && this.p != null){
			b.pieces.remove(this.p);
		}
		this.p = p;
	}
	public void removeFocus(Board b){
		selected = false;
		try{
		for(Move s : p.possMoves){
			b.board[s.newRow][s.newCol].setPossible(selected);
		}
		for(Move s : p.attMoves){
			b.board[s.newRow][s.newCol].setAttack(selected);
		}}
		catch(Exception e){
			System.out.println("Help!");
		}
	}
	public boolean isAttacked(boolean b, Board board){
		for(Piece p : board.pieces){
			if(p.color == b && p.possible(this.row, this.col)){
				return true;
			}
		}
		return false;
	}
	private void setAttack(boolean selected) {
		attacked = selected;
		
	}
	public GObject getImage(Boolean player){
		GCompound c = new GCompound();
		int x = 0, y = 0;
		if(player){
			x = (7 - col) * squareLength;
			y = (7 - row) * squareLength;
		}
		else{
			x = col * squareLength;
			y = row * squareLength;
		}
		r.setLocation(x, y);
		c.add(r);
		if(p != null){
			GObject i = p.getImage();
			i.setLocation(x, y);
			c.add(i);
		}
		if(selected){
			r.setFillColor(Color.BLUE);
		}
		if(possible && !attacked){
			r.setFillColor(Color.GREEN);
		}
		if(attacked){
			r.setFillColor(Color.RED);
		}
		else if(!selected && !possible){
			r.setFillColor(this.c);
		}
		return c;
	}
	public boolean giveFocus(Board b, boolean move) {
		if(p == null || p.color != move){
			return false;
		}
		else{
			selected = !selected;
			for(Move s : p.possMoves){
				b.board[s.newRow][s.newCol].setPossible(selected);
			}
			for(Move s : p.attMoves){
				b.board[s.newRow][s.newCol].setAttack(selected);
			}
			return selected;
		}
	}
	private void setPossible(boolean selected) {
		this.possible = selected;
	}
	@Override
	public boolean equals(Object obj) {
		Square s = (Square) obj;
		return s.col == col && s.row == row;
	}
	@Override
	public int hashCode() {
		return row * 8 + col;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return p == null ? "(" + row + ", " + col + ")" : p.toString();
	}
}
