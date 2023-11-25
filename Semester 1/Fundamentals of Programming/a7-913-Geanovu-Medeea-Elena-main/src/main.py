'''

Manage a list of books. Each book has an isbn (string, unique), an author and a title (strings). Provide the following features:

Add a book. Book data is read from the console.
Display the list of books.
Filter the list so that book titles starting with a given word are deleted from the list.
Undo the last operation that modified program data. This step can be repeated.
The user can undo only those operations made during the current run of the program.

'''

from src.ui.settings import Settings

Settings().get_ui().start()

