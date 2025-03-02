/*
3b) 
A Game of Tetris 
Functionality: 
Queue: Use a queue to store the sequence of falling blocks. 
Stack: Use a stack to represent the current state of the game board. 
GUI: 
A game board with grid cells. 
A preview area to show the next block. 
Buttons for left, right, and rotate. 
Implementation: 
Initialization: 
 Create an empty queue to store the sequence of falling blocks. 
 Create an empty stack to represent the game board. 
 Initialize the game board with empty cells. 
 Generate a random block and enqueue it. 
Game Loop: 
While the game is not over: 
 Check for game over: If the top row of the game board is filled, the game is over. 
 Display the game state: Draw the current state of the game board and the next block in the 
preview area. 
Handle user input: 
 If the left or right button is clicked, move the current block horizontally if possible. 
 If the rotate button is clicked, rotate the current block if possible. 
 Move the block: If the current block can move down without colliding, move it down. Otherwise: 
 Push the current block onto the stack, representing its placement on the game board. 
 Check for completed rows: If a row is filled, pop it from the stack and add a new empty row at the 
top. 
 Generate a new random block and enqueue it. 
Game Over: 
 Display a game over message and the final score. 
Data Structures: 
Block: A class or struct to represent a Tetris block, including its shape, color, and current position. 
GameBoard: A 2D array or matrix to represent the game board, where each cell can be empty or filled 
with a block. 
Queue: A queue to store the sequence of falling blocks. 
Stack: A stack to represent the current state of the game board. 
Additional Considerations: 
Collision detection: Implement a function to check if a block can move or rotate without colliding with 
other blocks or the game board boundaries. 
Scoring: Implement a scoring system based on factors like completed rows, number of blocks placed, and 
other game-specific rules. 
Leveling: Increase the speed of the falling blocks as the player's score increases. 
Power-ups: Add power-ups like clearing lines, adding extra rows, or changing the shape of the current 
block. 
*/ import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.*;

public class TetrisGame extends JPanel implements ActionListener {
    private static final int ROWS = 20, COLS = 10, BLOCK_SIZE = 30;
    private Timer timer;
    private Queue<Tetromino> blockQueue;
    private Tetromino currentBlock;
    private boolean gameOver;
    private int[][] board;
    private int score;

    public TetrisGame() {
        this.setPreferredSize(new Dimension(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE));
        this.setBackground(Color.BLACK);
        this.timer = new Timer(500, this);
        this.blockQueue = new LinkedList<>();
        this.board = new int[ROWS][COLS];
        this.score = 0;
        this.gameOver = false;

        initializeGame();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleInput(e.getKeyCode());
            }
        });
        setFocusable(true);
        timer.start();
    }

    private void initializeGame() {
        for (int i = 0; i < 3; i++) {
            blockQueue.add(generateRandomBlock());
        }
        currentBlock = blockQueue.poll();
    }

    private Tetromino generateRandomBlock() {
        Random random = new Random();
        int shapeIndex = random.nextInt(Tetromino.SHAPES.length);
        Color randomColor = Tetromino.COLORS[random.nextInt(Tetromino.COLORS.length)];
        return new Tetromino(Tetromino.SHAPES[shapeIndex], randomColor);
    }

    private void handleInput(int keyCode) {
        if (gameOver)
            return;
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                if (currentBlock.canMove(-1, 0, board))
                    currentBlock.move(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                if (currentBlock.canMove(1, 0, board))
                    currentBlock.move(1, 0);
                break;
            case KeyEvent.VK_DOWN:
                moveBlockDown();
                break;
            case KeyEvent.VK_UP:
                currentBlock.rotate(board);
                break;
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveBlockDown();
            repaint();
        }
    }

    private void moveBlockDown() {
        if (currentBlock.canMove(0, 1, board)) {
            currentBlock.move(0, 1);
        } else {
            placeBlock();
            clearRows();
            currentBlock = blockQueue.poll();
            blockQueue.add(generateRandomBlock());
            if (!currentBlock.canMove(0, 0, board))
                gameOver = true;
        }
    }

    private void placeBlock() {
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] == 1) {
                    board[currentBlock.y + i][currentBlock.x + j] = 1;
                }
            }
        }
    }

    private void clearRows() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean full = true;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 0) {
                    full = false;
                    break;
                }
            }
            if (full) {
                score += 100;
                for (int k = i; k > 0; k--) {
                    board[k] = board[k - 1].clone();
                }
                board[0] = new int[COLS];
                i++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 1) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                }
            }
        }
        currentBlock.draw(g);
        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Score: " + score, COLS * BLOCK_SIZE / 4, ROWS * BLOCK_SIZE / 2);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Tetromino {
    static final int[][][] SHAPES = {
            { { 1, 1, 1, 1 } },                 // I Shape
            { { 1, 1 }, { 1, 1 } },             // O Shape
            { { 0, 1, 0 }, { 1, 1, 1 } },       // T Shape
            { { 1, 1, 0 }, { 0, 1, 1 } },       // Z Shape
            { { 0, 1, 1 }, { 1, 1, 0 } },       // S Shape
            { { 1, 1, 1 }, { 1, 0, 0 } },       // L Shape
            { { 1, 1, 1 }, { 0, 0, 1 } },       // J Shape
            { { 1, 1, 1 }, { 0, 1, 0 } },       // New Unique Piece
            { { 1, 0 }, { 1, 1 }, { 1, 0 } }    // Another Unique Piece
    };

    static final Color[] COLORS = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN };

    int[][] shape;
    Color color;
    int x, y;

    public Tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.x = 4;
        this.y = 0;
    }

    public boolean canMove(int dx, int dy, int[][] board) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    int newX = x + j + dx;
                    int newY = y + i + dy;
                    if (newX < 0 || newX >= board[0].length || newY >= board.length || board[newY][newX] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void rotate(int[][] board) {
        int[][] rotatedShape = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                rotatedShape[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        if (canMove(0, 0, board)) {
            shape = rotatedShape;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    g.fillRect((x + j) * 30, (y + i) * 30, 30, 30);
                }
            }
        }
    }
}
