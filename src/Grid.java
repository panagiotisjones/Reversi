import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Grid {
	public char[][] grid;
	public Grid(Player player1, Player player2) {
		grid=new char[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				grid[i][j]=Game.EMPTY_SQUARE;
			}
		}
		grid[3][3]=Game.PLAYER1_PIECE;
		player1.addToPieces("33");
		grid[4][4]=Game.PLAYER1_PIECE;
		player1.addToPieces("44");
		grid[3][4]=Game.PLAYER2_PIECE;
		player2.addToPieces("34");
		grid[4][3]=Game.PLAYER2_PIECE;
		player2.addToPieces("43");
	}
	/**
	 * Formats and displays grid
	 * @param grid Grid to display
	 * @param name Name of player who selects a move next
	 */
	public void displayGrid(char[][] grid, Player player, Player opponent, int turnNumber) {
		System.out.println();
		System.out.println("Turn "+turnNumber+": " + player.getUsername());
		System.out.println();
		for (int i=0;i<8;i++) {
			System.out.print(i+1 + " |\t");
			for (int j=0;j<8;j++) {
				System.out.print(grid[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("  |____________________");
		System.out.println("   \ta b c d e f g h");
		System.out.println(player.getUsername()+": "+player.getPieces().size());
		System.out.println(opponent.getUsername()+": "+opponent.getPieces().size());
	}
	public void displayWinner(char[][] grid, Player[] player, int turnNumber) {
		displayGrid(grid,player[0],player[1],turnNumber);
		if (player[0].getPieces().size()==player[1].getPieces().size()) {
			System.out.println("Tie!");
		}
		else {
			System.out.println("Winner: " + (player[0].getPieces().size()>player[1].getPieces().size()? player[0].getUsername() : player[1].getUsername()) );
		}
	}
	/**
	 * Determines valid moves based on gridState, creates and returns essential information
	 * @param gridState Grid state from which valid moves are determined
	 * @param player The player for which valid moves are determined
	 * @return ArrayList of valid moves, which are ArrayLists of valid move (zeroth index), followed by captured pieces
	 */
	public ArrayList<ArrayList<String>> determineValidMoves(char[][] gridState, Player player, Player opponent) {
		ArrayList<ArrayList<String>> informationList=new ArrayList<ArrayList<String>>();
		ArrayList<String> temporaryList=new ArrayList<String>();
		int gridRow, gridColumn;
		for (int counter=0;counter<player.getPieces().size();counter++) {
			gridRow=Integer.parseInt(String.valueOf(player.getPieces().get(counter).charAt(0)));
			gridColumn=Integer.parseInt(String.valueOf(player.getPieces().get(counter).charAt(1)));
			if (player.getTeam()==gridState[gridRow][gridColumn]) {
				for (int horizontal=-1;horizontal<=1;horizontal++) {
					for (int vertical=-1;vertical<=1;vertical++) {
						int step=1;
						boolean move_found=false;
						boolean opposing_found=false;
						try {
							while (gridState[gridRow+vertical*step][gridColumn+horizontal*step]==opponent.getTeam()) {
								opposing_found=true;
								temporaryList.add(String.valueOf(gridRow+vertical*step)+String.valueOf(gridColumn+horizontal*step));
								step++;
							}
							if (gridState[gridRow+vertical*step][gridColumn+horizontal*step]==Game.EMPTY_SQUARE && opposing_found) {
								temporaryList.add(0,String.valueOf(gridRow+vertical*step)+String.valueOf(gridColumn+horizontal*step));
								move_found=false;
								for (int i=0;i<informationList.size();i++) {
									if (temporaryList.get(0).equals(informationList.get(i).get(0))) {
										for (int j=0;j<temporaryList.size();j++) {
											if (!(informationList.get(i).contains(temporaryList.get(j)))) {
												informationList.get(i).add(temporaryList.get(j));
											}
										}
										move_found=true;
									}
								}
								if (!(move_found)) {
									informationList.add((ArrayList<String>)temporaryList.clone()); //maybe I should check the exception lol
								}
							}
							if (!(temporaryList.isEmpty())) {
								temporaryList.clear();
							}
						}
						catch (Exception e) {
							//do nothing if out of bounds
						}
					}
				}
			}
		}
		return informationList;
	}
	/**
	 * Receives list with piece positions and team, sets piece positions to team
	 * @param list ArrayList with positions to turn
	 * @param player Player team (1 or 2)
	 */
	public void turnPieces(ArrayList<String> list, Player player, Player opponent) {
		String col, row;
		for (int i=0;i<list.size();i++) {
			grid[Integer.parseInt(col=String.valueOf(list.get(i).charAt(0)))][Integer.parseInt(row=String.valueOf(list.get(i).charAt(1)))]=player.getTeam();
			player.addToPieces(col.concat(row));
			if (i>0) {
				opponent.removeFromPieces(col.concat(row));
			}
		}
	}
	public ArrayList<String> findMove(ArrayList<ArrayList<String>> informationList, String move) {
		for (ArrayList<String> list : informationList) {
			if (list.get(0).equals(move)) {
				return list;
			}
		}
		return null;
	}
	/**
	 * Creates new grid with valid moves displayed
	 * @param grid Current state of grid
	 * @param informationList ArrayList with ArrayLists of which zeroth index is a valid move
	 * @return New grid with valid moves
	 */
	public char[][] gridWithValidMoves(char[][] grid, ArrayList<ArrayList<String>> informationList) {
		char[][] newGrid=new char[8][8];
		for (int i=0;i<8;i++) {
			for (int j=0;j<8;j++) {
				newGrid[i][j]=grid[i][j];
			}
		}
		for (ArrayList<String> position : informationList) {
			newGrid[Integer.parseInt(String.valueOf(position.get(0).charAt(0)))][Integer.parseInt(String.valueOf(position.get(0).charAt(1)))]=Game.VALID_SQUARE;
		}
		return newGrid;
	}
}
