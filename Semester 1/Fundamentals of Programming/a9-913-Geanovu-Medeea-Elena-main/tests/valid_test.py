import unittest
from Service.validators import Validate
from AI.ai import AIController


class MyTestCase(unittest.TestCase):

    def test_valid(self):
        self.bo = AIController()
        self.bo.set_row(3)
        self.bo.set_column(3)
        self.bo.create_board()
        self.bo.make_move_player(2, 2)
        test_valid = Validate()
        try:
            self.assertRaises(test_valid.valid_input(2, 2, self.bo.get_board()), str('Square already taken!'))
        except Exception as error:
            self.assertEqual(str(error), str('Square already taken!'))

        try:
            self.assertRaises(test_valid.valid_input('a', 'b', self.bo.get_board()), str('Please give integers!'))
        except Exception as error:
            self.assertEqual(str(error), str('Please give integers!'))

        try:
            self.assertRaises(test_valid.valid_input(0, -2, self.bo.get_board()), str('Please give integers!'))
        except Exception as error:
            self.assertEqual(str(error), str('Point out of border!'))

        try:
            self.assertRaises(test_valid.valid_input(-4, 1, self.bo.get_board()), str('Please give integers!'))
        except Exception as error:
            self.assertEqual(str(error), str('Point out of border!'))

        try:
            self.assertRaises(test_valid.valid_input(7, 2, self.bo.get_board()), str('Point out of border!'))
        except Exception as error:
            self.assertEqual(str(error), str('Point out of border!'))

        try:
            self.assertRaises(test_valid.valid_input(2, 7, self.bo.get_board()), str('Point out of border!'))
        except Exception as error:
            self.assertEqual(str(error), str('Point out of border!'))

        try:
            self.assertRaises(test_valid.valid_input(6, 7, self.bo.get_board()), str('Point out of border!'))
        except Exception as error:
            self.assertEqual(str(error), str('Point out of border!'))




if __name__ == '__main__':
    unittest.main()