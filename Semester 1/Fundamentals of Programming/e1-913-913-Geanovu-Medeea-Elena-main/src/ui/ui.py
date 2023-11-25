from src.ai.ai import AI
import texttable

class UI:
    def __init__(self):
        self.ai = AI()

    def print_table(self):
        board = self.ai.get_board()
        table = texttable.Texttable()
        for row in board:
            table.add_row(row)
        print(table.draw())

    def print_menu(self):
        print("1. place <pattern> <x,y> pattern: block, tub, blinker, beacon, glider, x,y:integers")
        print("2. tick[n] n-number of generations")
        print("3. save patterns")
        print("4. save iteration.txt")
        print("5. load iteration.txt")
        print("6. Exit?\n")

    def run_game(self):
        patterns = []
        while True:
            try:
                self.print_table()
                self.print_menu()
                choice = input(">> ")
                choice_args = choice.strip().split()
                if choice_args[0] == 'place' and choice_args[1] in ['block', 'tub', 'blinker', 'beacon', 'glider']:
                    numbers = str(choice_args[2]).split(',')
                    if int(numbers[0]) >= 0 and int(numbers[1]) <= 7:
                        self.ai.place_shape(choice_args[1], int(numbers[0]), int(numbers[1]))
                        self.ai.save_patterns(patterns, choice_args[1], int(numbers[0]), int(numbers[1]))
                elif choice_args[0] == 'tick' and int(choice_args[1]) > 0:
                    self.ai.generate(int(choice_args[1]))
                elif choice_args[0] == 'save':
                    self.ai.save_file(choice_args[1])
                elif choice_args[0] == 'load':
                    self.ai.load_file(choice_args[1])
                elif choice == 'Exit':
                    break
                else:
                    print("Invalid command")
            except Exception as e:
                print("Error: ", str(e))
