package OthelloGame;

import java.util.Scanner;

public class OthelloGame {

    private static final int SIZE = 8;
    private static char[][] board = new char[SIZE][SIZE];
    private static final char EMPTY = '　';
    private static final char BLACK = '\u26AB';
    private static final char WHITE = '\u26AA';

    public static void main(String[] args) {
        
    	// display a board
    	initializeBoard();
        printBoard();
        
        char currentPlayer = BLACK;
        Scanner scanner = new Scanner(System.in);
        
        int count = 0;
        while (count < 60) {
        	// determine the current player
        	System.out.println("Current player: " + currentPlayer);
        	
        	// Availability Check
        	if (isPlaceable(currentPlayer)) {
        	
	        	// ask player a cell position
	            System.out.print("Enter row and column (0-7) separated by space: ");
	            
	            int row = scanner.nextInt();
	            int col = scanner.nextInt();
	            
	            
	            // Verify cell's placeability
	            if (isValidMove(row, col, currentPlayer)) {
	                // Flip the opponent cells
	            	placeMove(row, col, currentPlayer);
	            	// Display the updated board
	                printBoard();
	                
	            	count++;
	            	System.out.println(count);
	                
	                // Turn next
	                currentPlayer = (currentPlayer == BLACK) ? WHITE : BLACK;
	                
	            } else {
	            	// Ask player again
	                System.out.println("Invalid move, try again.");
	            }
        	} else {
        		
        		if (isPlaceable(currentPlayer == BLACK ? WHITE : BLACK)) {
        			
        			System.out.println("There is no spots");
            		printBoard();
            		
            		// Turn next
                    currentPlayer = currentPlayer == BLACK ? WHITE : BLACK;	
        		} else {
        			// End game
        			break;
        		}
        	}
        }
        int black = countDisks(BLACK);
        int white = countDisks(WHITE);
        
        System.out.println("------------ Result ------------");
        System.out.println(BLACK + " : " + black);
        System.out.println(WHITE + " : " + white);
        
        if(black == white) {

        	System.out.println("------------ DRAW ------------");
        
        } else if (black > white) {

        	System.out.println("------------ " + BLACK + " WIN ------------");
        } else {
        	System.out.println("------------ " + WHITE + " WIN ------------");
        }
        
    }

    // Initialize The Board
    private static void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }

    
    // Display the current Board
    private static void printBoard() {
        System.out.print("    3");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("  " + i + " ");
        }
        System.out.println();
        System.out.println("   -----------------------------------");
        
        for (int i = 0; i < SIZE; i++) {
            System.out.print(" " + i + " | ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(
                		board[i][j] == EMPTY ?
                				board[i][j] + " | "
                				: board[i][j] + "| "
                				);
            }
            System.out.println();
            System.out.println("   -----------------------------------");
        }
    }

    // Determine there is any spot to place
    private static boolean isPlaceable(char player) {
    	for (int i = 0; i < SIZE; i++) {
    		for (int j = 0; j < SIZE; j++) {
    			if (isValidMove(i,j,player)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    // determine placeability
    private static boolean isValidMove(int row, int col, char player) {
    	// Verifying cell's placeability
        if (
        		row < 0 || row >= SIZE ||
        		col < 0 || col >= SIZE ||
        		board[row][col] != EMPTY
        	) {
            return false;
        }
        
        // Verifying Flippability (Up)
        if (isValidFlip(row, col, -1, 0, player)) {
        	return true;
        }
        
        // Verifying Flippability (Down)
        if (isValidFlip(row, col, 1, 0, player)) {
        	return true;
        }
         
        // Verifying Flippability (Right)
        if (isValidFlip(row, col, 0, 1, player)) {
        	return true;
        }
        
        // Verifying Flippability (Left)
        if (isValidFlip(row, col, 0, -1, player)) {
        	return true;
        }

        // Verifying Flippability (Up-Right)
        if (isValidFlip(row, col, -1, 1, player)) {
        	return true;
        }
            
        // Verifying Flippability (Up-Left)
        if (isValidFlip(row, col, -1, -1, player)) {
        	return true;
        }
        // Verifying Flippability (Down-Right)
        if (isValidFlip(row, col, 1, 1, player)) {
        	return true;
        }
        
        // Verifying Availability (Down-Left)
        if (isValidFlip(row, col, 1, -1, player)) {
        	return true;
        }
        
        // No Pleaceability Verified
    	return false;
    }

    // Flip the opponent's discs
    private static void placeMove(int row, int col, char player) {
        // place a disc on the cell
    	board[row][col] = player;

        // Flip opponent's discs (Up)
        if (isValidFlip(row, col, -1, 0, player)) {
        	flipDiscs(row, col, -1, 0, player);
        }
        
        // Flip opponent's discs (Down)
        if (isValidFlip(row, col, 1, 0, player)) {
        	flipDiscs(row, col, 1, 0, player);
        }
        
        // Flip opponent's discs (Right)
        if (isValidFlip(row, col, 0, 1, player)) {
        	flipDiscs(row, col, 0, 1, player);
        }
        
        // Flip opponent's discs (Left)
        if (isValidFlip(row, col, 0, -1, player)) {
        	flipDiscs(row, col, 0, -1, player);
        }
        
        // Flip opponent's discs (Up-Right)
        if (isValidFlip(row, col, -1, 1, player)) {
        	flipDiscs(row, col, -1, 1, player);
        }
        
        // Flip opponent's discs (Up-Left)
        if (isValidFlip(row, col, -1, -1, player)) {
        	flipDiscs(row, col, -1, -1, player);
        }
     
        // Flip opponent's discs (Down-Right)
        if (isValidFlip(row, col, 1, 1, player)) {
        	flipDiscs(row, col, 1, 1, player);
        }
        
        // Flip opponent's discs (Down-Left)
        if (isValidFlip(row, col, 1, -1, player)) {
        	flipDiscs(row, col, 1, -1, player);
        }  
    }
    
    // determine flippability in a given direction
    private static boolean isValidFlip(int row, int col, int dirRow, int dirCol, char player) {
    	char opponent = (player == BLACK) ? WHITE : BLACK;
    	// is there opponent's disc next to the cell?
    	if (
    			row + dirRow >= 0 && row + dirRow < SIZE &&
    			col + dirCol >= 0 && col + dirCol < SIZE &&
    			board[row + dirRow][col + dirCol] == opponent &&
    			row + dirRow * 2 >= 0 && row + dirRow * 2 < SIZE &&
    			col + dirCol * 2 >= 0 && col + dirCol * 2 < SIZE
    		) {
    		
    		/**
    		 * Is there player's disc next to opponent's one?
    		 * 
    		 * check1: cell is not empty
    		 * check2: row and col are within 0-7
    		 */
    		for (
    				int i = 2;
    				row + dirRow * i >= 0 && row + dirRow * i < SIZE &&
    				col + dirCol * i >= 0 && col + dirCol * i < SIZE &&
					board[row + dirRow * i][col + dirCol * i] != EMPTY;
    				i++ 
    			) {
    			// is there player's disc next to opponent's disc?
    			if (board[row + dirRow * i][col + dirCol * i] == player) {
    				return true;
    			} 
    		}
    		
    		// there is no player's discs beyond its direction
    		return false;
    	
    	} else {
    		
    		// there is no opponent's disc next to the cell
    		return false;
    	}
    }

    // Flip the opponent's discs in a given direction
    private static void flipDiscs(int row, int col, int dirRow, int dirCol, char player) {
    	for (
    			int i = 1;
    			row + dirRow * i >= 0 && row + dirRow * i < SIZE &&
    	    	col + dirCol * i >= 0 && col + dirCol * i < SIZE &&
    	    	board[row + dirRow * i][col + dirCol * i] != player;
    			i ++
    	    ) {
    		
    		// Flip the disc
    		board[row + dirRow * i][col + dirCol * i] = player;	
    	}
	}

    // Count player's total discs on the board
    private static int countDisks(char player) {
    	int count = 0;
    	for (int i = 0; i < SIZE; i++) {
    		for (int j = 0; j < SIZE; j++) {
    			if (board[i][j] == player) {
    				count++;
    			}
    		}
    	}
    	
    	return count;
    }
}


	