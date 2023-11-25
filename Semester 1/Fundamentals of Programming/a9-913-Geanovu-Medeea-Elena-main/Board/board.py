from Point.point import Point


class Board:
    def __init__(self, row=0, column=0):
        self.__row = row
        self.__column = column
        self.__board = []

    @property
    def get_row(self):
        return self.__row

    @property
    def get_column(self):
        return self.__column

    def get_board(self):
        return self.__board

    def set_row(self, row):
        self.__row = row

    def set_column(self, column):
        self.__column = column

    def create_board(self):
        """
        This function creates the board with the corresponding dimension.
        The board will be a list of lists, each list will represent a row, and each element of these lists will
        represent a column.
        The board will have self.__row lists and each list will have self.__column elements. Each cell contains "0"
        which represents it being empty.
        :return:
        """
        for x in range(self.get_row):
            array = []
            for y in range(self.get_column):
                array.append(0)
            self.__board.append(array)

    def destroy_board(self):
        """
        Destroys the existing board
        :return:
        """
        self.__board = []
        self.set_column(0)
        self.set_row(0)

    def __len__(self):
        """Overriding the len function"""
        return len(self.__board)

    def __repr__(self):
        """
        print the board in a str appearance
        :return:
        """
        string = "\n   "
        for x in range(self.__column):
            string += str(x) + '   '  # table column index
        for x in range(self.__row):
            string += "\n "
            string += "-" * (4 * self.__column + 1)
            string += "\n"
            string += str(x) + '|'  # table row index
            for y in range(self.__column):
                if self.__board[x][y] == 1:  # 1 = the player
                    string += ' ' + '0' + ' ' + "|"
                elif self.__board[x][y] == 2:  # 2 = the computer
                    string += ' ' + 'X' + ' ' + "|"
                elif self.__board[x][y] == -1:  # -1 = nonempty neighbours
                    string += ' # ' + "|"
                else:
                    string += ' ' + ' ' + ' ' + "|"
        string += "\n "
        string += "-" * (4 * self.__column + 1) + "\n"
        return string
