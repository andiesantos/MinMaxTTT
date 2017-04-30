import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;

public class Game {
    /* Elements used to build the board */
    private JFrame frame;
    private Container container;
    private JPanel mainPanel;
    private JPanel northPanel;
    private JPanel scoresPanel;
    private JLabel pScore, aiScore, drawScore;
    private JButton reset;
    private JPanel buttonPanel;
    private JButton[][] buttons;
    private int turnNumber;
    private int[][] board;
    private int pScoreInt = 0, aiScoreInt = 0, drawScoreInt = 0;
    private MinMax minmax;
    private Boolean playerFirst;
    private int player;
    String a, nota;
    int b, notb;
    boolean c;

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
        // North Panel setup
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
        
        container.add(mainPanel);
        frame.pack();
        frame.setVisible(true);


        // Create a copy of the board (used for state)
        board = new int[3][3]; // 1 = x, 2 = z0

        selectPlayer();
        xFirst(board);

    }

    public void xFirst(int[][] board) {
        // For testing purposes only
        if (player == 1) {
            State state = new State(board);
            // state.setFinishedState("X");

            // Used for deciding the next AI move
            //minmax = new MinMax(state);
            // select random tile to put AI's first move in
            state.maxNode = true;
            // Do MinMax on the current state
            MinMax mm = new MinMax(state, true);
            // Get the next move from MinMax
            int[] nextMove = mm.getNextMove();
            // Set the board according to next move
            board[nextMove[0]][nextMove[1]] = 1;
            buttons[nextMove[0]][nextMove[1]].setText("X");
        }

    }

    public void setAl(JButton button, int x, int y) {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("")) {   
                    if (turnNumber % 2 == 0) {
                        button.setText(a);
                        board[x][y] = b;
                        // Create a new State class with the current setup
                        State current = new State(board);
                        current.maxNode = c;

                        if (checkIfDraw(current))
                            return ;

                        // Do MinMax on the current state
                        MinMax mm = new MinMax(current, c);

                        // Get the next move from MinMax
                        int[] nextMove = mm.getNextMove();
                        // Set the board according to next move
                        board[nextMove[0]][nextMove[1]] = notb;
                        buttons[nextMove[0]][nextMove[1]].setText(nota);
                        turnNumber += 1;

                        if (checkIfDraw(current))
                            return ;
                    }
                    turnNumber += 1;

                    State current = new State(board);

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

                        resetFunc();
                    }
                }
            }
        };
        button.addActionListener(al);
    }

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

    // For resetting the board
    public void setResetAL(JButton reset) {
        ActionListener resetAL = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                turnNumber = 0;
                for (int i=0; i<3; i++) {
                    for (int j=0; j<3; j++) {
                        buttons[i][j].setText("");
                    } 
                }
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

        selectPlayer();
        xFirst(board);
    }

    public void selectPlayer() {
        String[] buttons = { "X", "O" };

        player = JOptionPane.showOptionDialog(null, "Choose: ", "Confirmation",
        JOptionPane.WARNING_MESSAGE, 1, null, buttons, buttons[1]);

        if (player == 0) {
            a = "X";
            b = 1;
            c = false;
            nota = "O";
            notb = 2;
        } else if (player == 1) {
            a = "O";
            b = 2;
            c = true;
            nota = "X";
            notb = 1;
        } else {
            System.exit(0);
        }
    }

    // Temporary colors until PRETTIER NOTICE
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