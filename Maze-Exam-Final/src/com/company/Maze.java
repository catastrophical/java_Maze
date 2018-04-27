package com.company;
import java.util.*;
/**
* Indeholder logikken til at genere recusrive backtracker maze
 */
public class Maze {
    // int variabler til dynamisk at regulere storrelsen på grid
    public int Xdimension = 15;
    public int Ydimension = 15;
    // intitialisere random
    private Random random = new Random();

    // 2D array called grid
    Cell[][] grid = new Cell[Xdimension][Ydimension];

    // enum type der indeholder predefinerede konstanter
    private enum Retning {
        OP, NED, HOJRE, VENSTRE
    }
    // Cell constructor med argumenterne cell og direction
    private Cell cellFromDirection(Cell cell, Retning direction) {
        // switch statement med retning som execution paths
        switch (direction) {

                case NED:
                return grid[cell.x][cell.y + 1];
                case OP:
                return grid[cell.x][cell.y - 1];
                case HOJRE:
                return grid[cell.x + 1][cell.y];
                case VENSTRE:
                return grid[cell.x - 1][cell.y];

           // default
            default:
                throw new RuntimeException();
        }
    }

    /**
     funktionen der generere recursive backtracker mazen
     */
    public void generateMaze() {

        //for loop der laver grid med walls

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        // Laver indgangspunktet på grid
        Cell initialCell = grid[0][0];
        initialCell.topWall = false;

        // dynamisk exit point der tilpasser sig grid dimensionerne
        Cell goalCell = grid[Xdimension - 1][Ydimension - 1];
        goalCell.bottomWall = false;

        // laver en tom stack LIFO af typen cell
        Stack<Cell> stack = new Stack<com.company.Cell>();

        // * 1.Make the initial cell the first cell on the stack
        // Laver den celle man starter i til den første cell i stack
        stack.push(grid[0][0]);

        // tjek recursion https://introcs.cs.princeton.edu/java/23recursion/

        // 2.While there are unvisited cells
        //
        while (stack.size() > 0) {

            // set current cell (that we are working on) to the top element of the stack,
            // removing it from the stack. (it will be re-added if there are multiple options from here)
            Cell current = (Cell) stack.pop();


            // hvis current cell er den samme som goalCell er vi gennem mazen
            //
            if (current == goalCell) {
                // laver en kopi af stack som den er nu
                Stack<Cell> copiedStack = (Stack<Cell>)stack.clone();
                System.out.print("solution found:");

                /**stack.forEach((Cell move) ->
                        System.out.printf(" -> [x:%d, y:%d]", move.x, move.y)

                );
                 **/
                System.out.println("");


                // skal bruge den poppede x og y til at tegne på
                // så længe stack-kopien er > end 0 så pop den og send til en celle med x og y

                while (copiedStack.size() > 0) {
                    // kopien af stacken bliver toemt i en Cell der kaldes solution
                    Cell solution = copiedStack.pop();
                    System.out.printf(" x:%d, y:%d ",solution.x, solution.y);

                }

            }
            // saetter current cell til at vaere visited
            current.visited = true;
            // alt er muligt
            // arraylist af typen retning som hedder muligheder
            ArrayList<Retning> muligheder = new ArrayList();
            // tilfojer retningerne til muligheder arrayList
            muligheder.add(Retning.OP);
            muligheder.add(Retning.NED);
            muligheder.add(Retning.HOJRE);
            muligheder.add(Retning.VENSTRE);
            // filtrere retninger som faar os uden for grid fra
            if (current.x == 0) { // fjern mulighed for VENSTRE hvis vi er paa raekken laengst mod venstre
                muligheder.remove(Retning.VENSTRE);
            }
            if (current.y == 0) { // fjern mulighed for NED hvis vi er paa oeverste raekke
                muligheder.remove(Retning.OP);
            }
            if (current.y == Ydimension - 1) { // fjern mulighed for NED hvis vi er paa nederste raekke
                muligheder.remove(Retning.NED);
            }
            if (current.x == Xdimension - 1) { // fjern mulighed for HOEJRE hvis vi er paa raekken laengst mod hoejre
                muligheder.remove(Retning.HOJRE);
            }

            // sorter tilstoedende celler som er visited fra:
            // loop over alle tilbagevaerende muligheder:
            for (Iterator<Retning> looper = muligheder.iterator(); looper.hasNext(); ) {
                //   - find cellen i den mulige retning:
                Retning mulig_retning = looper.next();
                if (cellFromDirection(current, mulig_retning).visited) {
                    //   - fjern muligheden hvis den celle den peger paa allerede er visited:
                    looper.remove();
                }
            }

            // nu har [muligheder] KUN retninger som peger paa ikke-visited celler inden for vores grid.

            System.out.printf("muligheder: %d [%d, %d]\n", muligheder.size(), current.x, current.y);

            if (muligheder.isEmpty()) {
                System.out.println("Backtracing...");
                continue;
            } else {
                // der er stadig mindst een mulighed herfra, saa gem den paa stacken saa vi kan backtracke senere
                stack.push(current);
            }

            //  vaelg tilfaeldig gyldig mulighed
            Retning vej = muligheder.get(random.nextInt(muligheder.size()));
            Cell naeste = cellFromDirection(current, vej);

            assert (naeste.visited == false);
            switch (vej) {
                case OP:
                    System.out.print("OP:");
                    break;
                case NED:
                    System.out.print("NED:");
                    break;
                case VENSTRE:
                    System.out.print("VENSTRE:");
                    break;
                case HOJRE:
                    System.out.print("HOJRE:");
                    break;
            }
            System.out.printf("[x:%d,y:%d] -> [%d,%d]\n", current.x, current.y, naeste.x, naeste.y);

            switch (vej) { // fjern barriere den vej vi har valgt at gaa
                case OP:
                    current.topWall = naeste.bottomWall = false;
                    break;
                case NED:
                    current.bottomWall = naeste.topWall = false;
                    break;
                case HOJRE:
                    current.rightWall = naeste.leftWall = false;
                    break;
                case VENSTRE:
                    current.leftWall = naeste.rightWall = false;
                    break;
            }

            stack.push(naeste);
        }
    }
    // tegn solution med indholdet fra stack
    public void drawSolution()
    {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

    }
}