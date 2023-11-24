import unittest
from Board.board import Board
from AI.ai import AIController
from Point.point import Point


class MyTestCase(unittest.TestCase):
    def test_set_row(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        self.assertEqual(self.bo.get_board().get_row, 5)

    def test_set_column(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        self.assertEqual(self.bo.get_board().get_column, 7)

    def test_get_board(self):
        self.bo = AIController()
        self.bo.set_row(2)
        self.bo.set_column(2)
        self.bo.create_board()
        test_board = Board()
        test_board.set_row(2)
        test_board.set_column(2)
        test_board.create_board()
        self.assertEqual(str(self.bo.get_board()), str(test_board))

    def test_create_board(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        test_board = Board()
        test_board.set_row(5)
        test_board.set_column(7)
        test_board.create_board()
        self.assertEqual(self.bo.get_board().get_board(), test_board.get_board())

    def test_destroy_board(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        self.assertEqual(self.bo.destroy_board(), None)

    def test_check_odd(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        self.assertTrue(self.bo._check_odd(3, 5))

    def test_get_mirror_move(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(5)
        self.bo.create_board()
        self.assertEqual(self.bo._get_mirror_move(0, 1).get_x, 4)
        self.assertEqual(self.bo._get_mirror_move(0, 1).get_y, 3)

    def test_first_odd(self):
        self.bo = AIController()
        self.bo.set_row(3)
        self.bo.set_column(3)
        self.bo.create_board()
        self.assertEqual(self.bo._first_odd(3, 3, [Point(0, 0), Point(0, 1), Point(0, 2), Point(1, 0), Point(1, 1),
                                                   Point(1, 2), Point(2, 0), Point(2, 1), Point(2, 2)], 1, 2).get_x, 1)
        self.assertEqual(self.bo._first_odd(3, 3, [Point(0, 0), Point(0, 1), Point(0, 2), Point(1, 0), Point(1, 1),
                                                   Point(1, 2), Point(2, 0), Point(2, 1), Point(2, 2)], 1, 2).get_y, 1)

        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(5)
        self.bo.create_board()
        self.bo._first_odd(5, 5, [Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3), Point(0, 4),
                                  Point(1, 0), Point(1, 1), Point(1, 2), Point(1, 3), Point(1, 4),
                                  Point(2, 0), Point(2, 1), Point(2, 2), Point(2, 3), Point(2, 4),
                                  Point(3, 0), Point(3, 1), Point(3, 2), Point(3, 3), Point(3, 4),
                                  Point(4, 0), Point(4, 1), Point(4, 2), Point(4, 3), Point(4, 4)], 1, 1)
        self.assertEqual(self.bo._first_odd(5, 5, [Point(0, 0)], 4, 4).get_x, 0)
        self.assertEqual(self.bo._first_odd(5, 5, [Point(0, 0)], 4, 4).get_y, 0)

    def test_decide_move(self):
        self.bo = AIController()
        self.bo.set_row(2)
        self.bo.set_column(3)
        self.bo.create_board()
        self.assertEqual(self.bo._decide_move(True, 2, 3, [Point(0,1), Point(2,0), Point(2,1)]), 2)

        self.bo = AIController()
        self.bo.set_row(1)
        self.bo.set_column(1)
        self.bo.create_board()
        self.assertEqual(self.bo._decide_move(True, 1, 1, [Point(0, 0)]), 1)

    def test_best_move(self):
        self.bo = AIController()
        self.bo.set_row(2)
        self.bo.set_column(3)
        self.bo.create_board()
        self.bo.make_move_player(0, 1)
        self.assertEqual(self.bo._best_move([Point(0, 2), Point(1, 2)]).get_x, 0)
        self.assertEqual(self.bo._best_move([Point(0, 2), Point(1, 2)]).get_y, 2)

    def test_best_board_move(self):
        self.bo = AIController()
        self.bo.set_row(2)
        self.bo.set_column(3)
        self.bo.create_board()
        self.bo.make_move_player(0, 0)
        self.assertEqual(self.bo.best_board_move(Point(0, 2)), 1)

    def test_repr(self):
        self.bo = AIController()
        self.bo.set_row(5)
        self.bo.set_column(7)
        self.bo.create_board()
        self.assertNotEqual(self.bo.get_board(), None)
        self.assertEqual(self.bo.get_board(), self.bo.get_board())

    def test_game_over(self):
        self.bo = AIController()
        self.bo.set_row(3)
        self.bo.set_column(5)
        self.bo.create_board()
        self.assertTrue(self.bo.game_over())

    def test_make_move_player(self):
        self.bo = AIController()
        self.bo.set_row(2)
        self.bo.set_column(4)
        self.bo.create_board()
        self.bo.make_move_player(0, 1)

        try:
            self.bo.make_move_player(0, 3)
            self.bo.make_move_player(0, 2)
            self.bo.make_move_player(0, 0)
        except Exception:
            pass

        self.assertFalse(self.bo.game_over())

    def test_make_move_ai(self):
        self.bo = AIController()
        self.bo.set_row(3)
        self.bo.set_column(5)
        self.bo.create_board()
        self.bo.make_move_ai(True, 0, 0)

        try:
            self.bo.make_move_player(1, 3)
            self.bo.make_move_player(0, 3)
            self.bo.make_move_player(0, 2)
            self.bo.make_move_player(0, 1)
            self.bo.make_move_player(1, 2)
            self.bo.make_move_player(1, 1)
            self.bo.make_move_player(2, 3)
            self.bo.make_move_player(2, 1)
            self.bo.make_move_player(2, 2)
        except Exception:
            pass

        self.assertTrue(self.bo.game_over())
        self.bo.make_move_player(1, 0)
        self.assertTrue(self.bo.game_over())
        self.bo.make_move_ai(True, 1, 0)
        self.assertFalse(self.bo.game_over())


if __name__ == '__main__':
    unittest.main()
