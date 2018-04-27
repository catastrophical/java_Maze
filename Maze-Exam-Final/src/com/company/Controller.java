package com.company;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
/**
*Controller klassen
 */

public class Controller {
    @FXML
    Button drawButton;

    @FXML
    Canvas canvas;


    Maze maze = new Maze();

    // laver en maze da den extender
    MazeKruskal mazeKruskal = new MazeKruskal();

    @FXML
    int offset = 28;
    int start = 3;

    public Controller() {

    }

    public void drawKruskals(ActionEvent event)
    {
        // kalder generateKruskalsMaze funktionen fra MazeKruskal klassen
        mazeKruskal.generateKruskalsMaze();
        System.out.println("Generated Kruskals Maze");
        // saet nye dimensioner for canvasset baseret paa antallet af felter i vores grid:
        canvas.setHeight(maze.Ydimension * 30);
        canvas.setWidth(maze.Xdimension * 30);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        // saetter backgrunden paa canvas til sort
        gc.setFill(Color.BLACK);
        // laver en rect der fylder hele canvas width og height
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Tegner streger efter drawCell metoden
        for (int i = 0; i < mazeKruskal.grid.length; i++) {
            for (int j = 0; j < mazeKruskal.grid.length; j++) {

                drawCell(i, j);
            }

        }



    }


    /**
     * metoden der koeres naar man trykker paa Generate Maze knappen i GUI.
     * Den kalder generateMaze funktionen i maze klassen. Derefter tegnes cellerne efter drawCell metoden
     * @param event
     */
    public void doDraw(ActionEvent event) {
        // kalder generateMaze funktionen fra maze klassen
        maze.generateMaze();
        System.out.println("Generated Maze");
        /**
         * saet nye dimensioner for canvasset baseret paa antallet af felter i vores grid:
         */
        canvas.setHeight(maze.Ydimension * 30);
        canvas.setWidth(maze.Xdimension * 30);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        // saetter backgrunden paa canvas til sort
        gc.setFill(Color.BLACK);
        // laver en rect der fylder hele canvas width og height
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //Tegner streger efter drawCell metoden
        for (int i = 0; i < maze.grid.length; i++) {
            for (int j = 0; j < maze.grid.length; j++) {

                drawCell(i, j);
            }

        }
    }

    /**
     *
     * @param event skal tegne solution naar der trykkes paa knappen i GUI men goer det ikke endnu
     */
    public void drawSolution(ActionEvent event) {
        // kalder drawSolution funktionen fra maze klassen
        maze.drawSolution();
        System.out.println("Drawing solution ");
        //Tegner streger efter drawCell metoden
        for (int i = 0; i < maze.grid.length; i++) {
            for (int j = 0; j < maze.grid.length; j++) {

                drawSolutionCell(i, j);
            }

        }

    }

    /**
     *
     * @param barrier bruger Color til at returne farverne groen eller sort til at tegne mazen
     * @return
     */
    private Color wallColor(boolean barrier) {
       // hvis den er true saa return groen
        if (barrier) {
            return Color.GREEN;
        }
        return Color.BLACK;
    }

    /**
     *  hvid farve til at tegne solution stien
      */

    private Color solutionColor() {

            return Color.WHITE;

    }

    /**
     * DrawCell metoden med parametrene i og j.
     * @param i
     * @param j
     */
    public void drawCell(int i, int j) {
        Cell cell = maze.grid[i][j];


        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setStroke(wallColor(cell.bottomWall));
        graphicsContext.strokeLine(cell.x * 30 + offset, cell.y * 30 + offset, cell.x * 30 + start, cell.y * 30 + offset);

        graphicsContext.setStroke(wallColor(cell.topWall));
        graphicsContext.strokeLine(cell.x * 30 + start, cell.y * 30 + start, cell.x * 30 + offset, cell.y * 30 + start);

        graphicsContext.setStroke(wallColor(cell.leftWall));
        graphicsContext.strokeLine(cell.x * 30 + start, cell.y * 30 + offset, cell.x * 30 + start, cell.y * 30 + start);

        graphicsContext.setStroke(wallColor(cell.rightWall));
        graphicsContext.strokeLine(cell.x * 30 + offset, cell.y * 30 + offset, cell.x * 30 + offset, cell.y * 30 + start);


    }

    /**
     * Denne metode skal tegne solution cellerne fra stack solution rutecellerne
     * @param i
     * @param j
     */
    public void drawSolutionCell(int i, int j) {
        Cell cell = maze.grid[i][j];

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        graphicsContext.setStroke(solutionColor());
        // cell.solution.x ??

        graphicsContext.strokeLine(cell.x * 30 + offset/2, cell.y * 30 + offset/2, cell.x * 30 + start, cell.y * 30 + offset/2);

        graphicsContext.setStroke(solutionColor());
        graphicsContext.strokeLine(cell.x * 30 + start, cell.y * 30 + start, cell.x * 30 + offset/2, cell.y * 30 + start);

        graphicsContext.setStroke(solutionColor());
        graphicsContext.strokeLine(cell.x * 30 + start, cell.y * 30 + offset/2, cell.x * 30 + start, cell.y * 30 + start);

        graphicsContext.setStroke(solutionColor());
        graphicsContext.strokeLine(cell.x * 30 + offset/2, cell.y * 30 + offset/2, cell.x * 30 + offset/2, cell.y * 30 + start);






    }

}
