from src.repository.repository import Board


class Valid():
    def __init__(self):
        self.board = Board()

    def in_bounds(self, shape, x, y):
        if shape == 'block':
            if x >= 0 and x <= 6 and y >= 0 and y <= 6:
                if self.board.get_cell(x,y) == 0 and self.board.get_cell(x+1, y+1) == 0 and self.board.get_cell(x+1, y) == 0 and self.board.get_cell(x, y+1) == 0:
                    return True
        elif shape == 'tub':
            if x >= 0 and x <= 5 and y > 0 and y <= 5:
                if self.board.get_cell(x, y+1) == 0 and self.board.get_cell(x+1, y) == 0 and self.board.get_cell(x+1, y+2) and self.board.get_cell(x+2, y+1) == 0:
                    return True
        elif shape == 'blinker':
            if x >= 0 and x <= 7 and y >=0 and y <= 5:
                if self.board.get_cell(x,y) == 0 and self.board.get_cell(x,y+1) == 0 and self.board.get_cell(x,y+2) == 0:
                    return True
        elif shape == 'beacon':
            if x >= 0 and x <= 4 and y >= 0 and y <= 4:
                if self.board.get_cell(x,y) == 0 and self.board.get_cell(x,+1) == 0 and self.board.get_cell(x+1,y) == 0 and self.board.get_cell(x+1,y+1) == 0 and self.board.get_cell(x+2,y+2) == 0 and self.board.get_cell(x+2,y+3) == 0 and self.board.get_cell(x+3,y+2) == 0 and self.board.get_cell(x+3,y+3) == 0:
                    return True
        elif shape == 'glider':
            if x >= 0 and x <= 4 and y >= 0 and y <= 4:
                if self.board.get_cell(x,y+1) == 0 and self.board.get_cell(x+1,y+2) == 0 and self.board.get_cell(x+2,y) == 0 and self.board.get_cell(x+2,y+1) == 0 and self.board.get_cell(x+2,y+2) == 0:
                    return True
        return False


