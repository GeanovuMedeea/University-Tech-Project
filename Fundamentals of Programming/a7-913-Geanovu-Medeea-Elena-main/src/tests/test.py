import unittest
from src.domain.entities import Book
from src.services.service import Services
from src.repository.repo import fileRepository
from src.repository.repo import Repository

class TestBasic(unittest.TestCase):
    def test_add_memory(self):
        """
        Test that it can sum a list of integers
        """
        repo = Repository()
        repo.add(Book("A-1-2-3-4", "Wheel of Time", "Robert Jordan"))
        self.assertEqual(str(repo.get("A-1-2-3-4")),"ISBN: A-1-2-3-4 Title: Wheel of Time Author: Robert Jordan")

    def test_add_files(self):
        repo = fileRepository()
        repo.add(Book("A-1-2-3-4", "Wheel of Time", "Robert Jordan"))
        self.assertEqual(str(repo.get("A-1-2-3-4")), "ISBN: A-1-2-3-4 Title: Wheel of Time Author: Robert Jordan")

    def test_add_service(self):
        repo = Repository()
        repo.add(Book("A-1-2-3-4", "Wheel of Time", "Robert Jordan"))
        book = Book("A-1-2-3-4", "Wheel of Time", "Robert Jordan")
        if repo.get(book.id) == None:
            raise ValueError("Book with id already exists")

    def test_bad_type(self):
        """
        test that it won't add objects that are not type Book
        :return:
        """
        repo = Repository()
        Book = 12345
        with self.assertRaises(TypeError):
             repo.add(Book)

if __name__ == '__main__':
    unittest.main()
