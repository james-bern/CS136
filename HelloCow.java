class Hello136 extends Cow {
    final static int PLAYER_NONE = 0;
    final static int PLAYER_X    = 1;
    final static int PLAYER_O    = 2;

    // state
    static int board[][] = new int[3][3]; // board[row (y)][column (x)]
    static int current_player = PLAYER_X;
    static int winner = PLAYER_NONE;
    static int turn = 0;
    static double win_line_x1;
    static double win_line_x2;
    static double win_line_y1;
    static double win_line_y2;

    static boolean game_is_over() {
        return (winner != PLAYER_NONE) || (turn == 9);
    }

    public static void main(String[] arguments) {

        // configure
        canvasConfig(0, 0, 3, 3);

        // loop
        while (beginFrame()) {
            int hot_row = (int) Math.floor(mouseY);
            int hot_column = (int) Math.floor(mouseX);
           

            if (!game_is_over()) { // update
                if (mousePressed && (board[hot_row][hot_column] == PLAYER_NONE)) { // make move
                    ++turn;
                    board[hot_row][hot_column] = current_player;
                    current_player = (current_player == PLAYER_X) ? PLAYER_O : PLAYER_X;
                }

                { // check for game over

                    // rows
                    for (int row = 0; row < 3; ++row) {
                        if ((board[row][0] != PLAYER_NONE) && ((board[row][0] == board[row][1]) && (board[row][0] == board[row][2]))) {
                            winner = board[row][0];
                            win_line_x1 = 0.0;
                            win_line_x2 = 3.0;
                            win_line_y1 = row + 0.5;
                            win_line_y2 = win_line_y1;
                        }
                    }

                    // columns
                    for (int column = 0; column < 3; ++column) {
                        if ((board[0][column] != PLAYER_NONE) && ((board[0][column] == board[1][column]) && (board[0][column] == board[2][column]))) {
                            winner = board[0][column];
                            win_line_x1 = column + 0.5;
                            win_line_x2 = win_line_x1;
                            win_line_y1 = 0.0;
                            win_line_y2 = 3.0;
                        }
                    }

                    // diagonals
                    if ((board[0][0] != PLAYER_NONE) && ((board[0][0] == board[1][1]) && (board[0][0] == board[2][2]))) {
                        winner = board[0][0];
                        win_line_x1 = 0.0;
                        win_line_y1 = 0.0;
                        win_line_x2 = 3.0;
                        win_line_y2 = 3.0;
                    }
                    if ((board[0][2] != PLAYER_NONE) && ((board[0][2] == board[1][1]) && (board[0][2] == board[2][0]))) {
                        winner = board[0][2];
                        win_line_x1 = 0.0;
                        win_line_y1 = 3.0;
                        win_line_x2 = 3.0;
                        win_line_y2 = 0.0;
                    }

                }
            }

            { // draw
                if (!game_is_over()) { // hot square
                    _draw_set_color(YELLOW, 0.5);
                    fill_rectangle(hot_column, hot_row, hot_column + 1, hot_row + 1);
                }

                { // board lines
                    _draw_set_line_thickness(2.0);
                    drawLine(1, 0, 1, 3, BLACK);
                    drawLine(2, 0, 2, 3, BLACK);
                    drawLine(0, 1, 3, 1, BLACK);
                    drawLine(0, 2, 3, 2, BLACK);
                }

                { // X's and O's
                    double epsilon = 0.1;
                    for (int row = 0; row < 3; ++row) {
                        for (int column = 0; column < 3; ++column) {
                            if (board[row][column] == PLAYER_X) {
                                drawLine(column + epsilon, row + epsilon, column + 1 - epsilon, row + 1 - epsilon, BLACK);
                                drawLine(column + epsilon, row + 1 - epsilon, column + 1 - epsilon, row + epsilon, BLACK);
                            } else if (board[row][column] == PLAYER_O) {
                                outline_circle(column + 0.5, row + 0.5, 0.5 - epsilon);
                            }
                        }
                    }
                }

                if (winner != PLAYER_NONE) { // winning line
                    drawLine(win_line_x1, win_line_y1, win_line_x2, win_line_y2, BLUE, 6.0);
                }
            }
        }
    }
}
