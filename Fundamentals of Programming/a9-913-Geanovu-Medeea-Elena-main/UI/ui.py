class Console:
    def __init__(self, ai_controller):
        self.__game = ai_controller
        self.__start_with_ai = False
        self.__in_turn = 1 #the player makes the first move

    def start(self):
        """
        Begin the game
        :return:
        """
        continue_game = True
        while continue_game:
            try:
                print("Please input the board dimension:")
                x = int(input("Height = "))
                y = int(input("Width = "))
                if x < 1 or y < 1:
                    raise ValueError

                print("Who starts the game?")
                print("a) Player")
                print("b) AI")
                set_player = input(">> ")
                # Setting the size of the board
                self.__game.set_row(x)
                self.__game.set_column(y)
                self.__game.create_board()

                if set_player == 'a':
                    self.__in_turn = 1  # 1 will be the player
                    self.__start_with_ai = False
                elif set_player == 'b':
                    self.__in_turn = 2  # 2 will be the computer
                    self.__start_with_ai = True

                while self.__game.game_over() is True:  # cycles through player - ai turns
                    if self.__in_turn == 1:
                        try:
                            print(self.__game.get_board())
                            x = input("x = ")
                            y = input("y = ")
                            # player_occupy = self.__game.player_occupy(x, y)
                            self.__game.make_move_player(x, y)
                            self.__in_turn = 2
                        except Exception as error:
                            print(error)
                    elif self.__in_turn == 2:
                        self.__game.make_move_ai(self.__start_with_ai, x, y)
                        self.__in_turn = 1

                print(self.__game.get_board())

                if self.__in_turn == 2:
                    print("Player has won!")
                else:
                    print("Computer has won!")

                print("Do you want to play again?")
                print("a) Yes")
                print("b) No")
                choice = input()

                if choice == 'a':
                    self.__game.destroy_board()
                elif choice == 'b':
                    print("Thank you for playing!")
                    continue_game = False
                else:
                    print("Invalid option!")

            except ValueError:
                print("Invalid coordinates!")
