from src.services.service import Services


class UI:
    def __init__(self, service=None):
        if service is None:
            service = Services()
        self._service = service

    def ui_add_book(self):
        try:
            id = input("Input the ISBN of the book: ")
            name = input("Input the name of the book: ")
            author = input("Input the name of the author: ")
            self._service.add_book(id, name, author)
            print("Book successfully added!")
        except Exception as e:
            print(e)

    def ui_list(self):
        try:
            self._service.list_books()
        except Exception as e:
            print(e)

    def ui_filter_book(self):
        try:
            name = input("Input the word with which to filter the books whose titles start with it: ")
            self._service.filter_book(name)
            print("Books successfully filtered!")
        except Exception as e:
            print(e)

    def ui_undo(self):
        try:
            self._service.undo()
            print("Operation successfully undone!")
        except Exception as e:
            print(e)

    @staticmethod
    def _print_menu():
        print("\n1. Add a book")
        print("2. List all books")
        print("3. Filter books by title")
        print("4. Undo")
        print("0. Exit")

    def start(self):
        while True:
            self._print_menu()
            choice = input("> ")
            if choice == "1":
                self.ui_add_book()
            elif choice == "2":
                self.ui_list()
            elif choice == "3":
                self.ui_filter_book()
            elif choice == '4':
                self.ui_undo()
            elif choice == "0":
                print("Exit program")
                break
            else:
                print("Option not implemented, try again")
