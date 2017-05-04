/*
    John Edward Pascual
    Andrea Marie Santos
    U-7L
    May 4, 2017
        Update: Tic tac toe implements "Alpha-beta pruning" algorithm
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
    /* Elements used to build the board */
    private JFrame frame;
    private Container container;
    private JPanel mainPanel, northPanel, scoresPanel, buttonPanel;
    private JLabel pScore, aiScore, drawScore;
    private JButton reset;
    private JButton[][] buttons;
    private int[][] board;
    private int pScoreInt = 0, aiScoreInt = 0, drawScoreInt = 0;

    /* Used to choose the next AI move */
    private MinMax minmax;
    private Boolean playerFirst;
    private int turnNumber;

    /* Settings for player/AI turn */
    private int player; // Used to check if player goes first or not
    private String playerMarker, aiMarker;
    private int playerGridMarker, aiGridMarker;
    private Boolean aiMaxNode;

    private int alpha = -99;
    private int beta = 99;

    // Set up the board 
    public Game() {
        Random rand = new Random();
        // Turn initialize to 0
        turnNumber = 0;
        // Frame setup
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        container = frame.getContentPane();
        // Main panel setup
        mainPanel = new JPanel(new BorderLayout());
        // North Panel setup - holds scores and reset button
        northPanel = new JPanel(new GridLayout(2,1));
        scoresPanel = new JPanel(new GridLayout(1, 3));
        // Score labels setup
        pScore = new JLabel("Player: " + pScoreInt);
        pScore.setHorizontalAlignment(SwingConstants.CENTER);
        aiScore = new JLabel("AI: " + aiScoreInt);
        aiScore.setHorizontalAlignment(SwingConstants.CENTER);
        drawScore = new JLabel("Draw: " + drawScoreInt);
        drawScore.setHorizontalAlignment(SwingConstants.CENTER);
        // Reset button setup
        reset = new JButton("RESET GAME");
        // Add "reset" actionListener
        setResetAL(reset);

        // Buttons setup
        buttonPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        // Build buttons
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j] = new JButton();
                // Set ActionListeners
                setAl(buttons[i][j], i, j);
                // Put this button into the board
                buttonPanel.add(buttons[i][j]);
            }
        }

        colorize();
        // Combining scoresPanel
        scoresPanel.add(pScore);
        scoresPanel.add(aiScore);
        scoresPanel.add(drawScore);
        northPanel.add(scoresPanel);
        northPanel.add(reset);
        // Combining mainPanel
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Putting into the frame
        container.add(mainPanel);
        frame.pack();
        frame.setVisible(true);


        // Create a copy of the board (used for States)
        board = new int[3][3]; // 1 = x, 2 = z0

        selectPlayer();
        xFirst(board);

    }

    /* Allows the player to choose whether or not they make the first move */
    public void selectPlayer() {
        String[] buttons = { "X", "O" };

        player = JOptionPane.showOptionDialog(null, "Choose: ", "Confirmation",
        JOptionPane.WARNING_MESSAGE, 1, null, buttons, buttons[1]);

        if (player == 0) { // Player goes first
            playerMarker = "X";
            playerGridMarker = 1;
            aiMaxNode = false;
            aiMarker = "O";
            aiGridMarker = 2;
        } else if (player == 1) { // AI goes first
            playerMarker = "O";
            playerGridMarker = 2;
            aiMaxNode = true;
            aiMarker = "X";
            aiGridMarker = 1;
        } else {
            System.exit(0);
        }
    }

    /* Creates settings for the game if the player chooses the AI to go first */
    public void xFirst(int[][] board) {
        if (player == 1) { // Player chose AI to go first
            State state = new State(board);
            state.maxNode = true; // AI maximizes chances of winning

            // System.out.println("out " + alpha + " " + beta);
            // Do MinMax on the current state
            MinMax mm = new MinMax(state, true);
            // System.out.println("out " + alpha + " " + beta);
            int[] nextMove = mm.getNextMove(); // Tile coordinates for X move
            board[nextMove[0]][nextMove[1]] = 1;
            buttons[nextMove[0]][nextMove[1]].setText("X");
        }

    }

    /*
        The decision of the next AI move is activated on click.
        Using settings derived from selectPlayer(), this puts on the board the
        icons of the player or AI. They are used to set the AI's playing style
        (if it should minimize chances of player wins/maximize chances of it
        winning). Checkers for draw or wins are also present here.
    */
    public void setAl(JButton button, int x, int y) {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("")) {  // Empty tile
                    if (turnNumber % 2 == 0) { // Player moves first
                        // Use settings from selectPlayer()
                        button.setText(playerMarker);
                        board[x][y] = playerGridMarker;

                        // Create a new State class with the current setup
                        State current = new State(board);
                        current.maxNode = aiMaxNode;

                        if (checkIfDraw(current)) // game is a draw
                            return ;
                        // System.out.println("in " + alpha + " " + beta);

                        // Do MinMax on the current state
                        MinMax mm = new MinMax(current, aiMaxNode);

                        // System.out.println("in " + alpha + " " + beta);
                        // Get the next move from MinMax
                        int[] nextMove = mm.getNextMove();
                        // Set the board according to next move
                        board[nextMove[0]][nextMove[1]] = aiGridMarker;
                        buttons[nextMove[0]][nextMove[1]].setText(aiMarker);
                        turnNumber += 1;

                        if (checkIfDraw(current))
                            return ;
                    }
                    turnNumber += 1;

                    State current = new State(board);

                    // Checker for wins/draws are in the State class
                    if (current.checker() == "X") {
                        JOptionPane.showMessageDialog(frame, "X wins!");
                        State finished = new State(board);
                        finished.setFinishedState("X");

                        if (player == 1) {
                            aiScoreInt += 1;
                            aiScore.setText("AI: " + aiScoreInt);
                        } else {
                            pScoreInt += 1;
                            pScore.setText("Player: " + pScoreInt);
                        }

                        resetFunc();
                    } else if (current.checker() == "O") {
                        JOptionPane.showMessageDialog(frame, "O wins!");
                        State finished = new State(board);
                        finished.setFinishedState("O");
                        pScoreInt += 1;
                        
                        if (player == 0) {
                            aiScoreInt += 1;
                            aiScore.setText("AI: " + aiScoreInt);
                        } else {
                            pScoreInt += 1;
                            pScore.setText("Player: " + pScoreInt);
                        }

                        // Resets the game and allows players to choose X or O
                        resetFunc();
                    }
                }
            }
        };
        button.addActionListener(al);
    }

    /* Checks if the game resulted in a draw */
    public boolean checkIfDraw(State current) {
        if (current.checker() == "DRAW") {
            JOptionPane.showMessageDialog(frame, "Draw");
            State finished = new State(board);
            finished.setFinishedState("DRAW");
            drawScoreInt += 1;
            drawScore.setText("Draw: " + drawScoreInt);
            resetFunc();
            return true;
        }
        return false;
    }

    /* For resetting the board */
    public void setResetAL(JButton reset) {
        ActionListener resetAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFunc();
            }
        };
        reset.addActionListener(resetAL);
    }

    public void resetFunc() {
        Random rand = new Random();
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                board[i][j] = 0;
                buttons[i][j].setText("");
            } 
        }
        turnNumber = 0;

        // Player chooses if they are X or O
        selectPlayer();
        xFirst(board);
    }

    /* Sets style and color of the Tic Tac Toe game */
    public void colorize() {
        pScore.setForeground(new Color(111, 156, 235));
        aiScore.setForeground(new Color(111, 156, 235));
        drawScore.setForeground(new Color(111, 156, 235));
        scoresPanel.setBackground(new Color(20, 27, 65));
        reset.setBackground(new Color(31, 86, 115));
        reset.setForeground(new Color(111, 156, 235));
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setBackground(new Color(20, 27, 65));
                buttons[i][j].setForeground(new Color(111, 156, 235));
            }
        }
    }

    
}