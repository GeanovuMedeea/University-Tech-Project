from src.domain.entities import Book
import pickle
import re

#stores data in memory
class Repository:
    def __init__(self):
        self._data = {}

    def list(self): #make it to return a list
        """
        displays the books
        :return: None
        """
        for book_id in self._data:
            print(self.get(book_id))

    def add(self, book):
        """
        adds a book
        :param book: type Book
        :return: None
        """
        if type(book) != Book:
            raise TypeError("Can only add books to repo!")
        if book.id in self._data:
            raise ValueError("Book already added")
        self._data[book.id] = book # {"id": "id + name + author"}


    def rem_book(self, book_name):
        """
        Removes the book
        :param book_name: type Book
        :return: None
        """
        for id_book in self._data.copy(): #cannot delete from dictionary while iterating, so we make a copy
            x = str(self.get(id_book))
            y = re.split("[, .!?]", x)
            if str(y[3]) == str(book_name):
                del self._data[id_book]


    def get(self, book_id):
        """
        returns the book information as string
        :param book_id: string
        :return: return string or None
        """
        try:
            return self._data[book_id]
        except:
            return None


    def __len__(self):
        return len(self._data)


# fileRepository inherits from Repository
# it has all fields and methods of Repository
class fileRepository(Repository):
    def __init__(self):
        # call base class constructor
        super().__init__()
        # load input file
        x = self._load_file()
        if x!=None:
            if len(x) == 0:
                books = [Book("A-1-2-3-4", "The Hobbit", "J.R.R. Tolkien"), Book("B-2-53-25-1", "Ion", "Liviu Rebreanu"),Book("9-AV-2-5-1", "Everyday", "David Levithan"),Book("J-76-H-A-89", "Pride and Prejudice", "Jane Austen"),
                         Book("N-1-5-13-F", "Don Quixote", "Miguel de Cervantes"), Book("H-9-141-G-1", "The Little Prince", "Antoine de Saint-Exupery"), Book("A-62-G-141-A", "The Odyssey", "Homer"),
                         Book("B-7-AFA-24-A", "Life of Pi", "Yann Martel"), Book("L-F-123-52-4", "Ana Karenina", "Lev Tolstoi"), Book("88-141-123-1-1", "Frankenstein", "Mary Shelley")]
                f = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\bookfiles.txt",
                         "w")
                try:
                    for b in books:
                        self.add(b)
                        book_str = str(b.id) + ";" + b.name + ";" + b.author + "\n"
                        f.write(book_str)
                    f.close()
                except Exception as e:
                    print("An error occurred -" + str(e))
            else:
                for b in x:
                    self.add(b)

    # NOTE - this is a Template Method design pattern
    def add(self, book):
        # do everything the parent's add() does
        super().add(book)
        # save the ingredients to file
        self._save_file()

    def list(self):
        super().list()

    def rem_book(self, book_name):
        super().rem_book(book_name)
        self._save_file()

    def _load_file(self):
        """
        loads string type from file and converts it to Book
        :return:
        """
        result = []
        try:
            f = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\bookfiles.txt", "r")
            line = f.readline().strip()
            while len(line) > 0:
                line = line.split(";")
                result.append(Book(line[0], line[1], line[2]))
                line = f.readline().strip()
            f.close()
        except IOError as e:
            """
                Here we 'log' the error, and throw it to the outer layers 
            """
            print("An error occured - " + str(e))
            raise e

        return result

    def _save_file(self):
        """
        saves to text files
        :return:
        """
        try:
            f = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\bookfiles.txt", "w")
            for book_id in self._data:
                x = str(self.get(book_id))
                y = x.split()
                y = y[3:]
                book_str = book_id + ";"
                while y[0] != 'Author:' and len(y) > 0:
                    if y[1] != 'Author:':
                        book_str = book_str + str(y[0]) + " "
                    else:
                        book_str = book_str + str(y[0])
                    y.remove(y[0])

                book_str = book_str + ";"
                y.remove(y[0])
                while len(y) > 0:
                    if len(y) > 1:
                        book_str = book_str + str(y[0]) + " "
                    else:
                        book_str = book_str + str(y[0])
                    y.remove(y[0])
                book_str = book_str + "\n"
                f.write(book_str)
            f.close()
        except Exception as e:
            print("An error occurred -" + str(e))


"""
Create a binary file repo that DOES work
"""

class binFileRepository(fileRepository):
    def __init__(self):
        super().__init__()

    def add(self, book):
        # do everything the parent's add() does
        super().add(book)
        # save the ingredients to file
        self._save_file()

    def list(self):
        super().list()

    def rem_book(self, book_name):
        super().rem_book(book_name)
        self._save_file()

    def _load_file(self):
        file_name = r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\books.pickle"
        try:
            fin = open(file_name, "wb")
            self._data = pickle.load(fin)
            fin.close()
        except Exception as e:
            books = [Book("A-1-2-3-4", "The Hobbit", "J.R.R. Tolkien"), Book("B-2-53-25-1", "Ion", "Liviu Rebreanu"),
                     Book("9-AV-2-5-1", "Everyday", "David Levithan"),
                     Book("J-76-H-A-89", "Pride and Prejudice", "Jane Austen"),
                     Book("N-1-5-13-F", "Don Quixote", "Miguel de Cervantes"),
                     Book("H-9-141-G-1", "The Little Prince", "Antoine de Saint-Exupery"),
                     Book("A-62-G-141-A", "The Odyssey", "Homer"),
                     Book("B-7-AFA-24-A", "Life of Pi", "Yann Martel"),
                     Book("L-F-123-52-4", "Ana Karenina", "Lev Tolstoi"),
                     Book("88-141-123-1-1", "Frankenstein", "Mary Shelley")]
            fout = open(file_name, "wb")
            for b in books:
                self.add(b)
            pickle.dump(self._data, fout)
            fout.close()
            print("An error has occured - ", str(e))

    def _save_file(self):
        # w -write, b - binary
        fout = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a7-913-Geanovu-Medeea-Elena\src\files\books.pickle", "wb")
        pickle.dump(self._data, fout)
        fout.close()

