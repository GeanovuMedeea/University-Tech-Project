class Validate:
    @staticmethod
    def valid_input(x, y, board):
        """
        Function that validates the coordinates of a point given
        :param x:
        :param y:
        :param board:
        :return:
        """
        try:
            x = int(x)
            y = int(y)
        except ValueError:
            raise Exception("Please give integers!")

        if y < 0 or x < 0 or y >= board.get_column or x >= board.get_row:
            raise Exception("Point out of border!")

        if board.get_board()[x][y] != 0:
            raise Exception("Square already taken!")