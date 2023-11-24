from Service.validators import Validate
from Service.service_board import Service
from Point.point import Point


class AIController:
    def __init__(self):
        self.__board_service = Service()
        self.__valid = Validate()
        self.__start_odd = False  # If AI starts the game on odd board (oddxodd)

    def set_row(self, row):
        """
        Sets the row of the board
        :param row:
        :return:
        """
        self.__board_service.get_board().set_row(row)

    def set_column(self, column):
        """
        Sets the column of the board
        :param column:
        :return:
        """
        self.__board_service.get_board().set_column(column)

    def get_board(self):
        """
        returns the board
        :return:
        """
        return self.__board_service.get_board()

    def create_board(self):
        """
        Creates the board and sets start_odd to False (default value)
        :return:
        """
        self.__board_service.create_board()
        self.__start_odd = False

    def destroy_board(self):
        """
        Destroys nthe board
        :return:
        """
        self.__board_service.destroy_board()

    def make_move_player(self, x, y):
        """
        If the player move is valid it will make the move, otherwise it raises an exception if or returns if the move
        is unavailable.
        :param x:
        :param y:
        :return:
        """
        self.__valid.valid_input(x, y, self.__board_service.get_board())
        point = Point(x, y)
        self.__board_service.get_board().get_board()[point.get_x][point.get_y] = 1
        self.__board_service.board_move(point)

    def _check_odd(self, x, y):
        """
        Checks if the board has odd dimensions (oddxodd)
        :param x:
        :param y:
        :return:
        """
        if x % 2 and y % 2:
            return True
        return False

    def _get_mirror_move(self, x, y):
        """
        Returns the point representing the mirrored move of the player
        AI strategy when it starts the game and the board is odd (oddxodd)
        :param x:
        :param y:
        :return:
        """
        row = self.__board_service.get_board().get_row
        col = self.__board_service.get_board().get_column

        if self.__board_service.get_board().get_board()[row - x - 1][col - y - 1] == 0:
            return Point(row - x - 1, col - y - 1)

        if self.__board_service.get_board().get_board()[x][col - y - 1] == 0:
            return Point(x, col - y - 1)

        if self.__board_service.get_board().get_board()[row - x - 1][y] == 0:
            return Point(row - x - 1, y)

    def _decide_move(self, ai_turn, row, column, moves):
        """
        Decides if the AI will use the odd board strategy (if it is the one to start the game) or the strategy of
        filling in as many cells as possible
        :param ai_turn:
        :param row:
        :param column:
        :param moves:
        :return:
        """

        # If the AI starts we check if the board has odd coordinates
        if ai_turn is True and len(moves) == row * column:
            if self._check_odd(row, column):
                return 1

        # If the board is odd and AI started we continue with 1
        if self.__start_odd is True and len(moves) != row * column:
            return 1

        # If the above criteria wasn't met, the AI will just make its move randomly/even board or evenxodd board
        return 2

    def _first_odd(self, row, column, moves, x, y):
        """
        Function that follows the next strategy:
        If the board is odd and AI makes the first move, then the first move will be in the center of the board.
        In order to win, next the AI will only mirror the player's moves.
        :param row:
        :param column:
        :param moves:
        :param x:
        :param y:
        :return:
        """
        # If AI started the game, it will make a move on the board centre point
        if len(moves) == row * column:
            self.__start_odd = True
            row_x = row // 2
            column_y = column // 2
            self.__board_service.get_board().get_board()[int(row_x)][int(column_y)] = 2
            move = Point(row_x, column_y)
            self.__board_service.board_move(move)
            return move

        # If AI started the game, it will mirror the player's every move
        if len(moves) != row * column:
            move = self._get_mirror_move(int(x), int(y))
            self.__board_service.get_board().get_board()[int(move.get_x)][int(move.get_y)] = 2
            self.__board_service.board_move(move)
            return move

    def _best_move(self, moves):
        """
        Function that makes AI move randomly
        :param moves:
        :return:
        """
        best_move = 0
        best_move_empty = 0

        for move in range(0, len(moves) - 1):
            if self.best_board_move(moves[move]) > best_move_empty:
                best_move_empty = self.best_board_move(moves[move])
                best_move = move
        self.__board_service.get_board().get_board()[moves[best_move].get_x][moves[best_move].get_y] = 2
        self.__board_service.board_move(moves[best_move])

        return moves[best_move]

    def player_occupy(self, x, y):
        """
        Returns the cells the player move occupied
        :param x:
        :param y:
        :return:
        """
        s = self.best_board_move(Point(x,y))
        return s

    def best_board_move(self, point):
        """
        It returns the number of empty cells in board surrounding the given point for computing the best possible move
        for the AI
        :param point:
        :return:
        """
        s = 0
        x = point.get_x
        y = point.get_y
        if x - 1 >= 0 and y - 1 >= 0 and self.__board_service.get_board().get_board()[x - 1][y - 1] == 0:
            s = s + 1
        if x - 1 >= 0 and self.__board_service.get_board().get_board()[x - 1][y] == 0:
            s = s + 1
        if x - 1 >= 0 and y + 1 < self.__board_service.get_board().get_column and self.__board_service.get_board().get_board()[x - 1][y + 1] == 0:
            s = s + 1
        if y - 1 >= 0 and self.__board_service.get_board().get_board()[x][y - 1] == 0:
            s = s + 1
        if y + 1 < self.__board_service.get_board().get_column and self.__board_service.get_board().get_board()[x][y + 1] == 0:
            s = s + 1
        if x + 1 < self.__board_service.get_board().get_row and y + 1 < self.__board_service.get_board().get_column and self.__board_service.get_board().get_board()[x + 1][y + 1] == 0:
            s = s + 1
        if x + 1 < self.__board_service.get_board().get_row and self.__board_service.get_board().get_board()[x + 1][y] == 0:
            s = s + 1
        if x + 1 < self.__board_service.get_board().get_row and y - 1 >= 0 and self.__board_service.get_board().get_board()[x + 1][y - 1] == 0:
            s = s + 1
        return s

    def make_move_ai(self, ai_turn, x, y):
        """
        Function that makes the AI's move
        :param ai_turn:
        :param x:
        :param y:
        :return:
        """
        moves = self.__board_service.available_move()
        row = self.__board_service.get_board().get_row
        column = self.__board_service.get_board().get_column
        next_move = self._decide_move(ai_turn, row, column, moves)

        if next_move == 1:  # the AI will use the centre cell strategy for odd board
            return self._first_odd(row, column, moves, x, y)
        elif next_move == 2:
            # If the above criteria wasn't met, the AI will look for the move covering most cells
            return self._best_move(moves)

    def game_over(self):
        """
        Returns True if there can still be made moves, and False if not (FALSE MEANS GAME OVER)
        :return:
        """
        if self.__board_service.available_move():
            return True
        return False
