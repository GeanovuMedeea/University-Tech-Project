from src.ai.valid import Valid
from src.repository.repository import Board

import copy


class AI:
    def __init__(self):
        self.board = Board()

    def in_bounds(self, shape, x, y):
        if shape == 'block':
            if x >= 0 and x <= 6 and y >= 0 and y <= 6:
                if self.board.get_cell(x, y) == 0 and self.board.get_cell(x + 1,
                                                                          y + 1) == 0 and self.board.get_cell(x + 1,
                                                                                                              y) == 0 and self.board.get_cell(
                        x, y + 1) == 0:
                    return True
        elif shape == 'tub':
            if x >= 0 and x <= 5 and y >= 0 and y <= 5:
                if self.board.get_cell(x, y + 1) == 0 and self.board.get_cell(x + 1,
                                                                              y) == 0 and self.board.get_cell(x + 1,
                                                                                                              y + 2) == 0 and self.board.get_cell(
                        x + 2, y + 1) == 0:
                    return True
        elif shape == 'blinker':
            if x >= 0 and x <= 7 and y >= 0 and y <= 5:
                if self.board.get_cell(x, y) == 0 and self.board.get_cell(x, y + 1) == 0 and self.board.get_cell(x,
                                                                                                                 y + 2) == 0:
                    return True
        elif shape == 'beacon':
            if x >= 0 and x <= 4 and y >= 0 and y <= 4:
                if self.board.get_cell(x, y) == 0 and self.board.get_cell(x, +1) == 0 and self.board.get_cell(x + 1,
                                                                                                              y) == 0 and self.board.get_cell(
                        x + 1, y + 1) == 0 and self.board.get_cell(x + 2, y + 2) == 0 and self.board.get_cell(x + 2,
                                                                                                              y + 3) == 0 and self.board.get_cell(
                        x + 3, y + 2) == 0 and self.board.get_cell(x + 3, y + 3) == 0:
                    return True
        elif shape == 'glider':
            if x >= 0 and x <= 4 and y >= 0 and y <= 4:
                if self.board.get_cell(x, y + 1) == 0 and self.board.get_cell(x + 1,
                                                                              y + 2) == 0 and self.board.get_cell(
                        x + 2, y) == 0 and self.board.get_cell(x + 2, y + 1) == 0 and self.board.get_cell(x + 2,
                                                                                                          y + 2) == 0:
                    return True
        return False

    def place_shape(self, shape, x, y):
        if not self.in_bounds(shape, x, y):
            raise ValueError("Coordinates are out of range/ovelap live cells")
        if shape == 'block':
            self.board.place_block(x,y)
        elif shape == 'tub':
            self.board.place_tub(x, y)
        elif shape == 'blinker':
            self.board.place_blinker(x, y)
        elif shape == 'beacon':
            self.board.place_beacon(x, y)
        elif shape == 'glider':
            self.board.place_glider(x, y)

    def generate(self, nr):
        pass

    def save_patterns(self, patterns, shape, x, y):
        patterns.append(str(shape) + ' '+ str(x) + ',' + str(y))
        self.board.save_patterns(patterns)

    def save_file(self, filename):
        self.board.save_file(filename)

    def load_file(self, filename):
        self.board.load_file(filename)

    def get_board(self):
        ui_board = []
        for i in range(8):
            ui_board.append([' ']*8)
        for i in range(8):
            for j in range(8):
                if self.board.get_board()[i][j] == 1:
                    ui_board[i][j] = 'X'
        return ui_board
