class Board:
    def __init__(self):
        self.cells = []
        if len(self.cells) == 0:
            for i in range(8):
                self.cells.append([0]*8)

    def place_block(self, x, y):
        self.cells[x][y] = 1
        self.cells[x+1][y] = 1
        self.cells[x][y+1] = 1
        self.cells[x+1][y+1] = 1

    def place_tub(self, x, y):
        self.cells[x][y+1] = 1
        self.cells[x+1][y] = 1
        self.cells[x+1][y+2] = 1
        self.cells[x+2][y+1] = 1

    def place_blinker(self, x, y):
        self.cells[x][y] = 1
        self.cells[x][y+1] = 1
        self.cells[x][y+2] = 1

    def place_beacon(self, x, y):
        self.cells[x][y] = 1
        self.cells[x][y+1] = 1
        self.cells[x+1][y] = 1
        self.cells[x+1][y+1] = 1
        self.cells[x+2][y+2] = 1
        self.cells[x+2][y+3] = 1
        self.cells[x+3][y+2] = 1
        self.cells[x+3][y+3] = 1

    def place_glider(self, x, y):
        self.cells[x][y+1] = 1
        self.cells[x+1][y+2] = 1
        self.cells[x+2][y] = 1
        self.cells[x+2][y+1] = 1
        self.cells[x+2][y+2] = 1

    def get_cell(self, x, y):
        return self.cells[x][y]

    def clear_board(self):
        self.cells.clear()

    def get_board(self):
        return self.cells

    def set_row(self, l):
        self.cells.append(l)

    def save_patterns(self, patterns):
        try:
            f = open(r'C:\Users\Dell\PycharmProjects\pythonProject\e1-913-913-Geanovu-Medeea-Elena\src\files\cell_patterns.txt','w')
            for line in patterns:
                f.write(line + '\n')
            f.close()
        except Exception as e:
            print("Error: ", str(e))


    def save_file(self, filename):
        try:
            f = open(str(filename),'x')
            board = self.get_board()
            for row in board:
                for i in row:
                    f.write(str(i) + " ")
                f.write('\n')
            f.close()
        except Exception as e:
            print("Error: ", str(e))

    def load_file(self, filename):
        self.clear_board()
        try:
            f = open(str(filename), 'r')
            line = f.readline().strip()
            while len(line) > 0:
                line_split = line.split()
                l = []
                for i in line_split:
                    if i == '1':
                        l.append(1)
                    else:
                        l.append(0)
                self.set_row(l)
                line = f.readline().strip()
            f.close()
        except Exception as e:
            print("Error: ", str(e))