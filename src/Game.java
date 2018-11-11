import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	Grid grid; // HANDLE THIS menu cli gui
	private boolean skipped;
	private String selectedMove;
	public static char PLAYER1_PIECE;
	public static char PLAYER2_PIECE;
	public static char EMPTY_SQUARE;
	public static char VALID_SQUARE;
	public static String DIFFICULTY;
	public Game() {
		PLAYER1_PIECE='1';
		PLAYER2_PIECE='2';
		EMPTY_SQUARE='-';
		VALID_SQUARE='x';
		DIFFICULTY="easy";
	}
	static {
		//static initializer block
	}
	public final static Scanner scanner=new Scanner(System.in);
	public boolean playTurn(Player player, Player opponent, Grid grid, int turnNumber) {
		ArrayList<ArrayList<String>> list=grid.determineValidMoves(grid.grid,player,opponent);
		if (!list.isEmpty()) {
			skipped=false;
			grid.displayGrid(grid.gridWithValidMoves(grid.grid,list),player,opponent,turnNumber);
			selectedMove=player.readMove(list);
			grid.turnPieces(grid.findMove(list,selectedMove),player,opponent);
			return true;
		}
		else if (!skipped){
			skipped=true;
			return true;
		}
		else {
			return false;
		}
	}
	public void start() {
		Player[] players=new Player[2];
		players[0]=new Computer(PLAYER1_PIECE);
		players[1]=new Computer(PLAYER2_PIECE);
		Grid grid=new Grid(players[0],players[1]);
		int i;
		for (i=0;playTurn(players[i % 2],players[(i+1)%2],grid,i+1);i++);
		grid.displayWinner(grid.grid, players, i);
	}
	public static void main(String args[]) {
		Game game=new Game();
		game.start();
	}
}
