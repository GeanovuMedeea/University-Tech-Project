import unittest
from Board.board import Board
from Point.point import Point


class MyTestCase(unittest.TestCase):
    def test_board_repr(self):
        board = '[[0, 0, 0, 0, 0]]'
        self.bo = Board(1, 5)
        self.bo.create_board()
        self.assertNotEqual(self.bo.get_board(), None)
        self.assertEqual(board, str(self.bo.get_board()))

    def test_board_destroy(self):
        board = '[[0, 0, 0, 0, 0]]'
        self.bo = Board(1, 5)
        self.bo.create_board()
        self.assertNotEqual(self.bo.get_board(), None)
        self.assertEqual(board, str(self.bo.get_board()))

        self.bo.destroy_board()
        self.assertEqual(self.bo.destroy_board(), None)

    def test_set_column(self):
        self.bo = Board(1, 6)
        self.bo.create_board()
        self.bo.set_column(5)
        self.assertEqual(self.bo.get_column, 5)

    def test_set_row(self):
        self.bo = Board(2, 4)
        self.bo.create_board()
        self.bo.set_row(1)
        self.assertEqual(self.bo.get_row, 1)

    def test_get_column(self):
        self.bo = Board(1, 6)
        self.bo.create_board()
        self.assertEqual(self.bo.get_column, 6)

    def test_get_row(self):
        self.bo = Board(1, 6)
        self.bo.create_board()
        self.assertEqual(self.bo.get_row, 1)

    def test_get_board(self):
        self.bo = Board(1, 6)
        self.bo.create_board()
        self.assertEqual(self.bo.get_board(), [[0, 0, 0, 0, 0, 0]])

    def test_len(self):
        self.bo = Board(1, 6)
        self.bo.create_board()
        self.assertEqual(len(self.bo), 1)


if __name__ == '__main__':
    unittest.main()
