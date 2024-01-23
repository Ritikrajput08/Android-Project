package com.example.n_queen_checker;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int size = 4;
    private GridLayout chessboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the chessboard
        chessboard = findViewById(R.id.chessboard);
        createChessboard();

        // Increase button click listener
        Button increaseButton = findViewById(R.id.increaseButton);
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseSize();
            }
        });

        // Decrease button click listener
        Button decreaseButton = findViewById(R.id.decreaseButton);
        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseSize();
            }
        });

        // Check button click listener
        Button checkButton = findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValidPosition = checkPosition();
                if (isValidPosition) {
                    Toast.makeText(MainActivity.this, "Valid position!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Invalid position!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createChessboard() {
        chessboard.removeAllViews(); // Clear the chessboard

        chessboard.setColumnCount(size);
        chessboard.setRowCount(size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                View cell = getCellView(row, col);
                chessboard.addView(cell);
            }
        }
    }

    private View getCellView(int row, int col) {
        View cell;
        cell = new View(this);
        int cellSize = (int) getResources().getDimension(R.dimen.cell_size);
        cell.setLayoutParams(new GridLayout.LayoutParams(
                GridLayout.spec(row, 1f),
                GridLayout.spec(col, 1f)));
        cell.getLayoutParams().width = cellSize;
        cell.getLayoutParams().height = cellSize;
        updateCellBackground(cell, row, col);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleQueenPosition(v, row, col);
            }
        });
        return cell;
    }

    private void updateCellBackground(View cell, int row, int col) {
        if ((row + col) % 2 == 0) {
            cell.setBackgroundColor(Color.WHITE);
        } else {
            cell.setBackgroundColor(Color.GREEN);
        }
    }

    private void toggleQueenPosition(View cell, int row, int col) {
        boolean hasQueen = cell.getTag() != null && (boolean) cell.getTag();
        cell.setTag(!hasQueen);
        updateCellBackground(cell, row, col);

        if (hasQueen) {
            // Queen is removed, so reset the cell color
            cell.setBackgroundColor(getCellColor(row, col));
        } else {
            // Queen is added, so set the cell color to red
            cell.setBackgroundColor(Color.RED);
        }
    }

    private int getCellColor(int row, int col) {
        if ((row + col) % 2 == 0) {
            return Color.WHITE;
        } else {
            return Color.GREEN;
        }
    }


    private void increaseSize() {
        size++;
        createChessboard();
    }

    private void decreaseSize() {
        if (size > 1) {
            size--;
            createChessboard();
        }
    }

    private boolean checkPosition() {
        // Check row and column conflicts
        for (int i = 0; i < size; i++) {
            if (hasConflictInRow(i) || hasConflictInColumn(i)) {
                return false;
            }
        }

        // Check diagonal conflicts
        for (int i = 0; i < size; i++) {
            if (hasConflictInDiagonal(i, 0) || hasConflictInDiagonal(0, i)
                    || hasConflictInDiagonal(i, size - 1) || hasConflictInDiagonal(0, i + size - 1)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasConflictInRow(int row) {
        int queenCount = 0;
        for (int col = 0; col < size; col++) {
            View cell = chessboard.getChildAt(row * size + col);
            boolean hasQueen = cell != null && cell.getTag() != null && (boolean) cell.getTag();
            if (hasQueen) {
                queenCount++;
            }
        }
        return queenCount > 1;
    }

    private boolean hasConflictInColumn(int col) {
        int queenCount = 0;
        for (int row = 0; row < size; row++) {
            View cell = chessboard.getChildAt(row * size + col);
            boolean hasQueen = cell != null && cell.getTag() != null && (boolean) cell.getTag();
            if (hasQueen) {
                queenCount++;
            }
        }
        return queenCount > 1;
    }

    private boolean hasConflictInDiagonal(int startRow, int startCol) {
        int queenCount = 0;
        int row = startRow;
        int col = startCol;
        while (row < size && col < size) {
            View cell = chessboard.getChildAt(row * size + col);
            boolean hasQueen = cell != null && cell.getTag() != null && (boolean) cell.getTag();
            if (hasQueen) {
                queenCount++;
            }
            row++;
            col++;
        }
        return queenCount > 1;
    }
}