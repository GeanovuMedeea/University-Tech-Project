import unittest
from Board.board import Board
from Point.point import Point
from Service.service_board import Service


class MyTestCase(unittest.TestCase):

    def test_get_row(self):
        self.service = Service()
        self.service.set_row(7)
        self.service.set_column(8)
        self.assertEqual(self.service.get_row, 7)

    def test_get_column(self):
        self.service = Service()
        self.service.set_row(7)
        self.service.set_column(8)
        self.assertEqual(self.service.get_column, 8)

    def test_set_row(self):
        self.service = Service()
        self.service.set_row(7)
        self.service.set_column(8)
        self.service.set_row(2)
        self.assertEqual(self.service.get_row, 2)

    def test_set_column(self):
        self.service = Service()
        self.service.set_row(7)
        self.service.set_column(8)
        self.service.set_column(3)
        self.assertEqual(self.service.get_column, 3)

    def test_create_board(self):
        self.service = Service()
        self.service.set_row(5)
        self.service.set_column(7)
        self.service.create_board()
        test_board = Board()
        test_board.set_row(5)
        test_board.set_column(7)
        test_board.create_board()
        self.assertEqual(self.service.get_board().get_board(), test_board.get_board())

    def test_destroy_board(self):
        self.service = Service()
        self.service.set_row(5)
        self.service.set_column(7)
        self.service.create_board()
        self.assertEqual(self.service.destroy_board(), None)

    def test_available_move(self):
        self.service = Service()
        self.service.set_row(7)
        self.service.set_column(8)
        self.service.create_board()
        moves = self.service.available_move()
        self.assertEqual(len(moves), 56)

        self.service.set_column(7)
        self.service.create_board()
        moves = self.service.available_move()
        self.assertEqual(len(moves), 49)

        self.service.set_column(5)
        self.service.create_board()
        moves = self.service.available_move()
        self.assertEqual(len(moves), 35)

    def test_board_move(self):
        self.service = Service()
        self.service.set_row(3)
        self.service.set_column(3)
        self.service.create_board()
        self.service.board_move(Point(2, 2))
        moves = self.service.available_move()
        self.assertEqual(len(moves)-1, 5)

        self.service.board_move(Point(0, 0))
        moves = self.service.available_move()
        self.assertEqual(len(moves)-2, 2)

        self.service.board_move(Point(2, 0))
        moves = self.service.available_move()
        self.assertEqual(len(moves)-3, 1)

        self.service.board_move(Point(0, 2))
        moves = self.service.available_move()
        self.assertEqual(len(moves)-4, 0)


if __name__ == '__main__':
    unittest.main()
