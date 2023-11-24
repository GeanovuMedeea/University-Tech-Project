import copy

from src.domain.entities import Book
from copy import deepcopy
from src.repository.repo import Repository
from src.repository.repo import fileRepository
from src.repository.repo import binFileRepository


# self._undo_service.register_operation(self._repo)


class UndoService:
    def __init__(self, book_repository):
        """
        Service class which maintains the initial set up
        :param book_repository: Repository
        """
        self._repo3 = book_repository
        self._undo_stack = []
        self._undo_pointer = 0

    def register_operation(self, operation):
        """
        Registers the operation and pushes it onto the stack
        :param operation:
        :return:
        """
        self._normalise_stack()
        op2 = copy.deepcopy(operation)
        self._undo_stack.append(op2)
        self._undo_pointer += 1

    def _normalise_stack(self):
        """
        When an operation is executed that is not undo everything beyond self._undo_pointer has to be
        eliminated from the stack. This is what the function does
        :return: None
        """
        while len(self._undo_stack) != self._undo_pointer:
            self._undo_stack.pop()

    def undo(self, repo):
        """
        Undoes the last performed operation
        :return: None
        """
        if self._undo_pointer == 0:
            raise ValueError("No operations to undo")
        self._undo_pointer -= 1
        x = copy.deepcopy(self._undo_stack[self._undo_pointer])
        repo = copy.deepcopy(x)
        return repo



class Services:
    def __init__(self, book_repository=None):
        self._repo = book_repository

        if book_repository is None:
            books = [Book("A-1-2-3-4", "The Hobbit", "J.R.R. Tolkien"), Book("B-2-53-25-1", "Ion", "Liviu Rebreanu"),
                     Book("9-AV-2-5-1", "Everyday", "David Levithan"),
                     Book("J-76-H-A-89", "Pride and Prejudice", "Jane Austen"),
                     Book("N-1-5-13-F", "Don Quixote", "Miguel de Cervantes"),
                     Book("H-9-141-G-1", "The Little Prince", "Antoine de Saint-Exupery"),
                     Book("A-62-G-141-A", "The Odyssey", "Homer"),
                     Book("B-7-AFA-24-A", "Life of Pi", "Yann Martel"),
                     Book("L-F-123-52-4", "Ana Karenina", "Lev Tolstoi"),
                     Book("88-141-123-1-1", "Frankenstein", "Mary Shelley")]

            f = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\bookfiles.txt",
                     "w")
            try:
                for b in books:
                    book_str = str(b.id) + ";" + b.name + ";" + b.author + "\n"
                    f.write(book_str)
                f.close()
            except Exception as e:
                print("An error occurred -" + str(e))

            self._repo = Repository()
            for i in range(0,10):
                self._repo.add(books[i])
        else:
            self._repo = book_repository()
        self._undo_service = UndoService(self._repo)


    def add_book(self, id, name, author):
        """
            Adds a boo by calling the book repo
            :param id: string, unique
            :param name: string
            :param author: string
            :return: None
            """
        book = Book(id, name, author)
        if self._repo.get(id):
            raise ValueError("Book with id already exists")
        self._repo.add(book)
        self._undo_service.register_operation(self._repo)

    def list_books(self):
        """
        displays the books
        :return: none
        """
        self._repo.list()

    def filter_book(self, book_name):
        """
        Returns a list of all students who match the name with the parameter name
        :param book_name: string
        :return: list
        """
        self._repo.rem_book(book_name)
        self._undo_service.register_operation(self._repo)

    def undo(self):
        """
            undo function from service
            """
        self._repo = copy.deepcopy(self._repo)
        self._repo = copy.deepcopy(self._undo_service.undo(self._repo))

