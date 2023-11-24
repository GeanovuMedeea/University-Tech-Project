class Book:
    def __init__(self, id, name, author):
        """
        Creates a book with id = id, name = name, author = author
        :param id: string, unique
        :param name: string
        :param author: string
        """
        self.__id = id
        self.__name = name
        self.__author = author

    @property
    def id(self):
        return self.__id

    @property
    def name(self):
        return self.__name

    @property
    def author(self):
        return self.__author

    @id.setter
    def id(self, new_value):
        if len(new_value) < 9:
            raise ValueError("ISBN must have at least length 9")
        self.__id = new_value

    @name.setter
    def name(self, new_value):
        self.__name = new_value

    @author.setter
    def author(self, new_value):
        self.__author = new_value

    def __str__(self):
        return "ISBN: " + self.id + " Title: " + self.name + " Author: " + self.author


# if __name__ == "__main__":
#     book =Book("A-1-2-3-4", "Random Book", "Whoever")
#     flour = Book("B-2-3-4-5", "Everyday", "David Levithan")
#
#     print(book)
#     # print(Ingredient.get_name(ingr))
#
#     book.name = "Magical book"
#     book.id = "C-1-2-3-4"
#     print(book.id, book.name, book.author)
#     print(book)
#     # print(ingr.__id, ingr.__name)

