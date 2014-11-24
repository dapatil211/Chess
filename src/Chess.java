import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import acm.program.GraphicsProgram;


public class Chess extends GraphicsProgram{
	//TODO: CheckMate
	
	Board b;
	Square[][] squares;
	boolean player;
	int selRow, selCol;
	Piece selPiece;
	boolean move;
	private boolean moved;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Chess().start(args);
	}
	
	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return new Dimension(650, 650);
	}
	
	@Override
	public void init() {	// draw board
		squares = new Square[8][8];
		b = new Board(squares);
		String[] selectionValues = {"White", "Black"};
		player = JOptionPane.showInputDialog(this, "Select a side", "Chess", JOptionPane.QUESTION_MESSAGE, 
				null, selectionValues, "White").equals("Black");
		initBoard();
		b.addKings();
//		b = new Board();
		b.update();
		add(b.makeImage(player));
		addMouseListeners(this);
		selRow = -1;
		selCol = -1;
		selPiece = null;
		move = false;
	}
	public int xToRow(double x){
		int row = (int) (x / 72);
		return player ? 7 - row : row;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int col = xToRow(e.getX());
		int row = xToRow(e.getY());
		if(col < 8 && row < 8){
			remove(b.getImage());
			if(selPiece == null){
				giveFocus(row, col);
			}
			else{
				
				movePiece(row, col);
				if(moved){
					add(b.makeImage(player));
					Move m = b.findBestMove(!player);
					movePiece(m);
					remove(b.getImage());
//					add(b.makeImage(player));
				}
			}
			System.out.println(col + ", " + row);
			add(b.makeImage(player));
		}
	}
	

	private void movePiece(Move m) {
		selPiece = m.piece;
		selRow = m.oldRow;
		selCol = m.oldCol;
		movePiece(m.newRow, m.newCol);
		moved = false;
	}

	private void movePiece(int row, int col) {
		if(selPiece.possible(row, col)){
			if(selPiece instanceof Pawn && (row == 7 || row == 0)){ // Promotion
				promote(row, col);
			}
			else if(selPiece instanceof Pawn && Math.abs(row - selRow) == 1 && Math.abs(col - selCol) == 1 //enPassant
					&& b.board[row][col].p == null){
				enPassant(row, col);
			}
			else if(selPiece instanceof King && ((col == selCol + 2) || (col == selCol - 2))){ // castle
				castle(row, col);
			}
			else{
				move(selPiece, row, col);
			}
			if(b.checkMate(b.move)){
				if(b.move){
					JOptionPane.showConfirmDialog(this, "White Won", "Game Over", JOptionPane.OK_OPTION);
				}
				else{
					JOptionPane.showConfirmDialog(this, "Black Won", "Game Over", JOptionPane.OK_OPTION);
				}
				removeMouseListener(this);
				init();
				return;
			}
			selRow = -1;
			selCol = -1;
			selPiece = null;
			move = !move;
			moved = true;
		}
		else{
			b.board[selRow][selCol].removeFocus(b);
			selRow = -1;
			selCol = -1;
			selPiece = null;
		}
	}

	private void enPassant(int row, int col) {
		b.pieces.remove(b.board[selRow][col].p);
		b.board[selRow][col].update(null, b);
		move(selPiece, row, col);
	}

	private void castle(int row, int col){
		move(selPiece, row, col);
		if(col == selCol + 2){
			move(b.board[row][7].p, row, col - 1);
		}
		else{
			move(b.board[row][0].p, row, col + 1);
		}
	}
	private void move(Piece p, int row, int col){
		b.move(p, row, col);
	}
	private void promote(int row, int col){
		b.board[selRow][selCol].update(null, b);
		String[] choices = {"Queen", "Rook", "Knight", "Bishop"};
		int choice = JOptionPane.showOptionDialog(this, "What would you like to promote your pawn to?",
				"PROMOTION!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, "Queen");
		b.pieces.remove(selPiece);
		if(choice == 0){
			b.board[row][col].update(new Queen(row, col, selPiece.color), b);
		}
		else if(choice == 1){
			b.board[row][col].update(new Rook(row, col, selPiece.color), b);
		}
		else if(choice == 2){
			b.board[row][col].update(new Knight(row, col, selPiece.color), b);
		}
		else{
			b.board[row][col].update(new Bishop(row, col, selPiece.color), b);
		}
		b.pieces.add(b.board[row][col].p);
	}
	private void giveFocus(int row, int col) {
		System.out.println("giveFocus");
		if(!b.board[row][col].giveFocus(b, move)){
			selRow = -1;
			selCol = -1;
			selPiece = null;
		}
		else{
			selRow = row;
			selCol = col;
			selPiece = b.board[row][col].p;
		}
		
	}
	private void initBoard() {
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				b.board[i][j] = new Square(i, j);
			}
		}
		lastRow(true);
		for(int i = 0; i < 8; i ++){
			b.board[1][i].place(new Pawn(1, i, true), b);
		}
		for(int i = 0; i < 8; i ++){
			b.board[6][i].place(new Pawn(6, i, false), b);
		}
		lastRow(false);		
	}
	private void lastRow(boolean col) {
		if(col){
			b.board[0][0].place(new Rook(0, 0, col), b);
			b.board[0][1].place(new Knight(0, 1, col), b);
			b.board[0][2].place(new Bishop(0, 2, col), b);
			b.board[0][4].place(new King(0, 4, col), b);
			b.board[0][3].place(new Queen(0, 3, col), b);
			b.board[0][5].place(new Bishop(0, 5, col), b);
			b.board[0][6].place(new Knight(0, 6, col), b);
			b.board[0][7].place(new Rook(0, 7, col), b);
		}
		else{
			b.board[7][0].place(new Rook(7, 0, col), b);
			b.board[7][1].place(new Knight(7, 1, col), b);
			b.board[7][2].place(new Bishop(7, 2, col), b);
			b.board[7][4].place(new King(7, 4, col), b);
			b.board[7][3].place(new Queen(7, 3, col), b);
			b.board[7][5].place(new Bishop(7, 5, col), b);
			b.board[7][6].place(new Knight(7, 6, col), b);
			b.board[7][7].place(new Rook(7, 7, col), b);
		}
	}
		
}