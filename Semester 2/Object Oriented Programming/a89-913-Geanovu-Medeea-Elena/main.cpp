#include "UI.h"
#include "AdminRepository.h"
#include "UserRepository.h"
#include "AdminService.h"
#include "Tests.h"
#define _CRTDBG_MAP_ALLOC

#include <crtdbg.h>

/*See the movies in the database having a given genre (if the genre is empty, see all the movies), one by one. When the user chooses this option,
the data of the first movie (title, genre, year of release, number of likes) is displayed and the trailer is played in the browser.
If the user likes the trailer, she can choose to add the movie to her watch list.
If the trailer is not satisfactory, the user can choose not to add the movie to the watch list and to continue to the next. In this case,
the information corresponding to the next movie is shown and the user is again offered the possibility to add it to the watch list. This can continue as long as the
user wants, as when arriving to the end of the list of movies with the given genre, if the user chooses next, the application will again show the first movie.
Delete a movie from the watch list, after the user watched the movie. When deleting a movie from the watch list, the user can also rate the movie (with a like),
and in this case, the number of likes the movie has in the repository will be increased.
See the watch list.*/


int main()
{
    {
        TestAll();
        std::string answer;
        std::vector<std::string> options = { "CSV", "HTML"};
        while (true) {
            std::cout << "Storage type for data? (CSV or HTML)\n";
            std::cout << ">> ";
            getline(std::cin, answer);

            if (answer.compare("CSV") == 0) {
                UserRepository* userRepository = new CSVUserRepository("watchlist.csv");
                UserService userService = UserService(userRepository);
                UI ui(userService);
                ui.runApplication();
                delete userRepository;
                break;
            }
            else if (answer.compare("HTML") == 0) {
                UserRepository* userRepository = new HTMLUserRepository("watchlist.html");
                UserService userService = UserService(userRepository);
                UI ui(userService);
                ui.runApplication();
                delete userRepository;
                break;
            }
            else {
                std::cout << "Invalid input!";
            }
        }
    }
    _CrtDumpMemoryLeaks();
    return 0;
}