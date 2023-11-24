from Point.point import Point
from Board.board import Board


class Service:
    def __init__(self):
        self.__board = Board()

    @property
    def get_row(self):
        """
        Returns the number of rows
        :return:
        """
        return self.get_board().get_row

    @property
    def get_column(self):
        """
        Returns the nr of columns
        :return: 
        """""
        return self.get_board().get_column

    def set_row(self, row):
        """
        Sets the row number of the board
        :param row:
        :return:
        """
        self.__board.set_row(row)

    def set_column(self, column):
        """
        Sets the column nr of the board
        :param column: 
        :return: 
        """""
        self.__board.set_column(column)

    def get_board(self):
        """
        Returns the board
        :return:
        """
        return self.__board

    def available_move(self):
        """
        Returns an array consisting of the points that can be used for future moves by checking the board array for
        any zero values
        :return:
        """
        move = []
        for x in range(self.__board.get_row):
            for y in range(self.__board.get_column):
                if self.__board.get_board()[x][y] == 0:
                    move.append(Point(x, y))
        return move

    def board_move(self, point):
        """
        Borders the immediate vecinity of the point on the board
        :param point:
        :return:
        """
        x = point.get_x
        y = point.get_y
        if x - 1 >= 0 and y - 1 >= 0 and self.__board.get_board()[x - 1][y - 1] == 0:
            self.__board.get_board()[x - 1][y - 1] = -1
        if x - 1 >= 0 and self.__board.get_board()[x - 1][y] == 0:
            self.__board.get_board()[x - 1][y] = -1
        if x - 1 >= 0 and y + 1 < self.__board.get_column and self.__board.get_board()[x - 1][y + 1] == 0:
            self.__board.get_board()[x - 1][y + 1] = -1
        if y - 1 >= 0 and self.__board.get_board()[x][y - 1] == 0:
            self.__board.get_board()[x][y - 1] = -1
        if y + 1 < self.__board.get_column and self.__board.get_board()[x][y + 1] == 0:
            self.__board.get_board()[x][y + 1] = -1
        if x + 1 < self.__board.get_row and y + 1 < self.__board.get_column and self.__board.get_board()[x + 1][y + 1] == 0:
            self.__board.get_board()[x + 1][y + 1] = -1
        if x + 1 < self.__board.get_row and self.__board.get_board()[x + 1][y] == 0:
            self.__board.get_board()[x + 1][y] = -1
        if x + 1 < self.__board.get_row and y - 1 >= 0 and self.__board.get_board()[x + 1][y - 1] == 0:
            self.__board.get_board()[x + 1][y - 1] = -1

    def create_board(self):
        """
        This function creates the board with the corresponding dimension.
        The board will be a list of lists, each list will represent a row, and each element of these lists will
        represent a column.
        The board will have self.__row lists and each list will have self.__column elements. Each cell contains "0"
        which represents it being empty.
        :return:
        """
        self.__board.create_board()

    def destroy_board(self):
        """
        Destroys the existing board
        :return:
        """
        self.__board.destroy_board()
