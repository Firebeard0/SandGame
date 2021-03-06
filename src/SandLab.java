import java.awt.*;
import java.util.*;

public class SandLab
{
  //Step 4,6
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  public static final int ACID = 4;


  public Color[] displayColor; 
  
  //do not add any more fields below
  private int[][] grid;
  private SandDisplay display;
  
  
  /**
   * Constructor for SandLab
   * @param numRows The number of rows to start with
   * @param numCols The number of columns to start with;
   */
  public SandLab(int numRows, int numCols)
  {
    String[] names;
    // Change this value to add more buttons
    //Step 4,6
    names = new String[5];
    // Each value needs a name for the button
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    names[ACID] = "Acid";
 
    displayColor = new Color[5];
    displayColor[0] = Color.BLACK;
    displayColor[1] = Color.GRAY;
    displayColor[2] = Color.YELLOW;
    displayColor[3] = Color.BLUE;
    displayColor[4] = Color.GREEN;
    
  
    //1. Add code to initialize the data member grid with same dimensions
    
    
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
    
    grid = new int [numRows] [numCols];
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
    //2. Assign the values associated with the parameters to the grid
	  
	  grid[row][col] = tool;
   
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
      //Step 3
   //Hint - use a nested for loop
	  

	  
	  for(int row = 0; row < grid.length; row++) {
		  for(int col = 0; col < grid[row].length; col++)
		  {
			  display.setColor(row, col, displayColor[grid[row][col]]);
			  
//			  if(grid[row][col] == METAL)
//			  {
//				  display.setColor(row, col, Color.GRAY);
//			  }
//			  
//			  else
//			  {
//				  display.setColor(row, col, Color.BLACK);
//			  }
		  }
	  }
    
  }

  //Step 5,7
  //called repeatedly.
  //causes one random particle in grid to maybe do something.
  public void step()
  {
    //Remember, you need to access both row and column to specify a spot in the array
    //The scalar refers to how big the value could be
    int someRandomRow = (int) (Math.random() * grid.length);
    int someRandomCol = (int) (Math.random() * grid[someRandomRow].length);
    //remember that you need to watch for the edges of the array
	  
	  
	  if(grid[someRandomRow][someRandomCol] == SAND)
	  {
		  moveCell(someRandomRow, someRandomCol, SAND, someRandomRow+1, someRandomCol);

	  }
	  if(grid[someRandomRow][someRandomCol] == ACID)
	  {
		  moveCell(someRandomRow, someRandomCol, ACID, someRandomRow+1, someRandomCol);

	  }
	  else if(grid[someRandomRow][someRandomCol] == WATER)
	  {
		  int waterDest = (int) (Math.random() * 3);
		  int destCol = someRandomCol;
		  int destRow = someRandomRow;
		  
		  if (waterDest == 0)
			  destCol = someRandomCol - 1; 
		  else if (waterDest == 2)
		  	destCol = someRandomCol + 1;
		  else
			  destRow = someRandomRow + 1;
		  			  
		  moveCell(someRandomRow, someRandomCol, WATER, destRow, destCol);
	  }
			
	  
 
  }
  
  private void moveCell(int row, int col, int type, int destRow, int destCol)
  {
	  if(destRow < grid.length && destCol < grid[destRow].length && destCol >= 0)  //is the destination on the grid?
	  {
		  if(grid[destRow][destCol] == EMPTY)  //is the destination empty?
		  {			  
			  grid[destRow][destCol] = type;  //draw the new cell
			  grid[row][col] = EMPTY;   // empty the old cell
		  }
		  if(grid[row][col] == SAND && grid[row + 1][col] == WATER)
		  {
			  grid[row][col] = WATER;
			  grid[row+1][col]= SAND;
		  }
		  if(grid[row][col] == ACID)
		  {
			  if (grid[row + 1][col] == WATER) {
				  grid[row][col]= EMPTY;
			  }
			  else
			  {
			  grid[row][col] = EMPTY;
			  grid[row +1][col]= ACID;
			  }
		  }
		  
	  }
	  else
		  if (grid[row][col] == ACID)
			  grid[row][col] = EMPTY;
  
  }
  

  
  //do not modify this method!
  public void run()
  {
    while (true) // infinite loop
    {
      for (int i = 0; i < display.getSpeed(); i++)
      {
        step();
      }
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
      {
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
      }
    }
  }
}
