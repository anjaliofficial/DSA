import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class game extends JPanel implements ActionListener {
    private static final int ROWS = 20, COLS = 10, BLOCK_SIZE = 30;
    private Timer timer;
    private Queue<Tetromino> blockQueue;
    private Stack<int[][]> boardStack;
    private Tetromino currentBlock;
    private boolean gameOver;
    private int[][] board;
    private int score;

    public game() {
        this.setPreferredSize(new Dimension(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE));
        this.setBackground(Color.BLACK);
        this.timer = new Timer(500, this);
        this.blockQueue = new LinkedList<>();
        this.boardStack = new Stack<>();
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
        return new Tetromino(Tetromino.SHAPES[shapeIndex], Color.CYAN);
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
                    g.setColor(Color.CYAN);
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
        game game = new game();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Tetromino {
    static final int[][][] SHAPES = {
            { { 1, 1, 1, 1 } },
            { { 1, 1 }, { 1, 1 } },
            { { 0, 1, 0 }, { 1, 1, 1 } },
            { { 1, 1, 0 }, { 0, 1, 1 } },
            { { 0, 1, 1 }, { 1, 1, 0 } }
    };

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
        // Add rotation logic here
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
