import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private MinMax minmax;

    // Set up the board 
    public Game() {
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
        pScore = new JLabel("Player: 0");
        pScore.setHorizontalAlignment(SwingConstants.CENTER);
        aiScore = new JLabel("AI: 0");
        aiScore.setHorizontalAlignment(SwingConstants.CENTER);
        drawScore = new JLabel("Draw: 0");
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

        // Used for deciding the next AI move
        minmax = new MinMax();
    }

    public void setAl(JButton button, int x, int y) {
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (button.getText().equals("")) {   
                    if (turnNumber % 2 == 0) {
                        button.setText("X");
                        board[x][y] = 1;
                        State s = new State(board);
                        s.setMaxNode();
                        minmax.setBoard(board);
                        minmax.getMax();
                    } else {
                        button.setText("O");
                        board[x][y] = 2;
                        minmax.setBoard(board);
                    }
                    turnNumber += 1;
                    minmax.printBoard();

                    if (checker() == "X") {
                        minmax.setWinner("X");
                        // minmax.winBoard("X");
                        JOptionPane.showMessageDialog(frame, "X wins!");
                        resetFunc();
                    } else if (checker() == "O") {
                        minmax.setWinner("O");
                        // minmax.winBoard("O");
                        JOptionPane.showMessageDialog(frame, "O wins!");
                        resetFunc();
                    } else if (checker() == "DRAW") {
                        minmax.setWinner("DRAW");
                        // minmax.winBoard("DRAW");
                        JOptionPane.showMessageDialog(frame, "Draw");
                        resetFunc();
                    }
                }
            }
        };
        button.addActionListener(al);
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
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText("");
            } 
        }
        turnNumber = 0;
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

    public String checker() {
        int countX = 0;
        int countO = 0;

        /*rows*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText() == "O") {
                    countO++;
                } else if (buttons[i][j].getText() == "X") {
                    countX++;
                }
            }

            if (countX == 3) {
                return "X";
            } else if (countO == 3) {
                return "O";
            }
            
            countX = 0;
            countO = 0;
        }

        /*cols*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[j][i].getText() == "O") {
                    countO++;
                } else if (buttons[j][i].getText() == "X") {
                    countX++;
                }

            }
    
            if (countX == 3) {
                return "X";
            } else if (countO == 3) {
                return "O";
            }

            countX = 0;
            countO = 0;
        }

        /*diagonal to the left*/
        for (int i = 0; i < 3; i++) {
            if (buttons[i][i].getText() == "O") {
                countO++;
            } else if (buttons[i][i].getText() == "X") {
                countX++;
            }
        }
        
        if (countX == 3) {
            return "X";
        } else if (countO == 3) {
            return "O";
        }

        countX = 0;
        countO = 0;

        /*diagonal to the right*/
        for (int i = 0; i < 3; i++) {
            if (buttons[i][2-i].getText() == "O") {
                countO++;
            } else if (buttons[i][2-i].getText() == "X") {
                countX++;
            }
        }
        
        if (countX == 3) {
            return "X";
        } else if (countO == 3) {
            return "O";
        }

        countX = 0;
        countO = 0;

        /*draw*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText() == "O") {
                    countO++;
                } else if (buttons[i][j].getText() == "X") {
                    countX++;
                }
            }

        }

        if (countX == 5) {
            countX = 0;
            countO = 0;
            return "DRAW";
        }

        return "";
    }
}