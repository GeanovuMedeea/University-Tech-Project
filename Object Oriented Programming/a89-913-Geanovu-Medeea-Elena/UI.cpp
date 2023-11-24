#include "UI.h"
#include <string>
#include <iostream>
#include <stdlib.h>

using namespace std;
#define MAX_READ_LEN 60

//Prints


void UI::printChooseAdministratorOrUser()
{
    std::cout << std::endl;
    std::cout << "... * .*.*... * . ***. .. . *`. *`  ... ` *.  *..*` . . ' ` *   . ." << std::endl;
    std::cout << "                 WELCOME TO THE Local Movie Database!              " << std::endl;
    std::cout << " Administrator mode(ADMINISTRATOR) | User mode(USER) | Exit (EXIT) " << std::endl;
    std::cout << ">> ";
}

void UI::printMovie(Movie movieToPrint)
{
    std::cout << std::endl;
    std::cout << "ID: " << movieToPrint.getMovieID() << std::endl;
    std::cout << "Title: " << movieToPrint.getMovieTitle() << std::endl;
    std::cout << "Genre: " << movieToPrint.getMovieGenre() << std::endl;
    std::cout << "Year: " << movieToPrint.getMovieYear() << std::endl;
    std::cout << "Likes: " << movieToPrint.getMovieLikes() << std::endl;
    std::cout << "Trailer: " << movieToPrint.getMovieTrailerLink() << std::endl;
    std::cout << std::endl;
}

//Administrator menu
void UI::printAdministratorMenu()
{
    std::cout << std::endl;
    std::cout << "<<ADMINISTRATOR MODE>>         " << std::endl;
    std::cout << std::endl;
    std::cout << "1. Add a movie" << std::endl;
    std::cout << "2. Remove a movie" << std::endl;
    std::cout << "3. Update a movie" << std::endl;
    std::cout << "4. Print all movies" << std::endl;
    std::cout << "5. Save current movie list" << std::endl;
    std::cout << "6. Read from movie list" << std::endl;
    std::cout << "0. Exit" << std::endl;
    std::cout << ">> ";
}

void UI::printAllMovies(int AdministratorOrUserServiceChoice)
{
    if (AdministratorOrUserServiceChoice == 1)
    {
        std::vector<Movie> allMovies = this->service.getAllMovies();

        for (auto&& movie : allMovies)
        {
            this->printMovie(movie);
        }
    }
    //else if (AdministratorOrUserServiceChoice == 2)
    //{
    //    std::vector<Movie> allMovies = this->userService.getAllMovies();
    //    for (auto&& movie : allMovies)
    //    {
    //        this->printMovie(movie);
    //    }
    //}
}

UI::UI()
{
    this->service = AdminService();
    this->userService = UserService();
}

UI::UI(UserService service)
{
    this->service = AdminService();
    this->userService = service;
}

//Administrator functions
void UI::administratorMode()
{
    this->printAdministratorMenu();
    std::string command;
    std::cin >> command;

    std::vector<std::string> options = { "ADD", "REMOVE", "UPDATE", "PRINT", "WRITE", "READ", "EXIT" };

    while (true) {
        try {
            this->validator.validate_option(command, options);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> command;
        }

        if (command.compare("ADD") == 0)
        {
            this->addMovie();
        }
        else if (command.compare("REMOVE") == 0)
        {
            this->removeMovie();
        }
        else if (command.compare("UPDATE") == 0)
        {
            this->updateMovie();
        }
        else if (command.compare("PRINT") == 0)
        {
            this->printAllMovies(1);
        }
        else if (command.compare("WRITE") == 0)
        {
            this->writeToMovieFileList();
        }
        else if (command.compare("READ") == 0)
        {
            this->readFromMovieFileList();
        }
        else if (command.compare("EXIT") == 0)
        {
            break;
        }
        this->printAdministratorMenu();
        std::cin >> command;
    }
}

void UI::addMovie()
{
    //int id, numberLikesMovie, yearReleaseMovie;
    char tempId[MAX_READ_LEN], tempNumberLikesMovie[MAX_READ_LEN], tempYearReleaseMovie[MAX_READ_LEN], genreMovie[MAX_READ_LEN], titleMovie[MAX_READ_LEN], TrailerLinkMovie[MAX_READ_LEN];

    std::cout << "Enter the ID of the movie: ";
    std::cin >> tempId;

    while (true) {
        try {
            this->validator.is_number(tempId);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> tempId;
            continue;
        }
        break;
    }
    int id = stoi(tempId);

    //while (id <= 0)
    //{
    //    std::cout << "The ID must be a positive integer!" << std::endl;
    //    std::cout << "Enter the ID of the movie: ";
    //    std::cin >> id;
    //}

    std::cout << "Enter the Title of the movie: ";
    std::cin >> titleMovie;

    std::cout << "Enter the Genre of the movie: ";
    std::cin >> genreMovie;

    std::cout << "Enter the Release Year of the movie: ";
    std::cin >> tempYearReleaseMovie;

    int yearReleaseMovie;

    while (true) {
        try {
            this->validator.valid_year(tempYearReleaseMovie);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> tempYearReleaseMovie;
            continue;
        }
        yearReleaseMovie = stoi(tempYearReleaseMovie);
        break;
    }

    //while (yearReleaseMovie < 1895)
    //{
    //    std::cout << "The Release Year must be greater or equal with 1895!" << std::endl;
    //    std::cout << "Enter the Release Year of the movie: ";
    //    std::cin >> yearReleaseMovie;
    //}

    std::cout << "Enter the amount of Likes for the movie: ";
    std::cin >> tempNumberLikesMovie;

    int numberLikesMovie = stoi(tempNumberLikesMovie);
    while (true) {
        try {
            this->validator.valid_likes(tempNumberLikesMovie);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> tempNumberLikesMovie;
            continue;
        }
        numberLikesMovie = stoi(tempNumberLikesMovie);
        break;
    }

    std::cout << "Enter the Trailer link of the movie: ";
    std::cin >> TrailerLinkMovie;

    try {
        this->service.addMovie(id, titleMovie, genreMovie, yearReleaseMovie, numberLikesMovie, TrailerLinkMovie);
        std::cout << "Movie added successfully.\n";
    }
    catch (OperationError& ioe) {
        std::cout << ioe.what() << "\n";
    }
}

void UI::removeMovie()
{
    char tempId[MAX_READ_LEN];
    std::cout << "Enter the ID of the movie you want to remove: ";
    std::cin >> tempId;

    while (true) {
        try {
            this->validator.is_number(tempId);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> tempId;
            continue;
        }
        break;
    }
    int id = stoi(tempId);


    try {
        this->service.removeMovie(id);
        std::cout << "Movie removed successfully.\n";
    }
    catch (OperationError& ioe) {
        std::cout << ioe.what() << "\n";
    }
}

void UI::updateMovie()
{
    char tempId[MAX_READ_LEN];
    std::cout << "Enter the ID of the movie: ";
    std::cin >> tempId;

    while (true) {
        try {
            this->validator.is_number(tempId);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> tempId;
            continue;
        }
        break;
    }
    int id = stoi(tempId);

    int newID = service.getMovie(id).getMovieID(), yearReleaseMovie = service.getMovie(id).getMovieYear(), numberLikesMovie = service.getMovie(id).getMovieLikes();
    std::string titleMovie = service.getMovie(id).getMovieTitle(), genreMovie = service.getMovie(id).getMovieGenre(), TrailerLinkMovie = service.getMovie(id).getMovieTrailerLink();

    std::cout << "What do you want to update? ID, Title, Genre, Year, Likes, Trailer, Exit ?\n>>" << std::endl;
    std::string commandUpdate;
    std::cin >> commandUpdate;
    std::vector<std::string> optionsUpdate = { "ID", "TITLE", "GENRE", "YEAR", "LIKES", "TRAILER", "EXIT" };

    while (true)
    {
        try {
            this->validator.validate_option(commandUpdate, optionsUpdate);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> commandUpdate;
        }
        if (commandUpdate.compare("ID") == 0)
        {
            std::cout << "Enter the new ID of the movie: ";
            char tempNewId[MAX_READ_LEN];
            std::cin >> tempNewId;

            while (true) {
                try {
                    this->validator.is_number(tempNewId);
                }
                catch (InputError& iie) {
                    std::cout << iie.what() << "\n";
                    std::cout << ">> ";
                    std::cin >> tempNewId;
                    continue;
                }
                break;
            }
            newID = stoi(tempNewId);

            if (this->service.movieExists(newID) == true)
            {
                std::cout << "There already is a movie with that ID!" << std::endl;
            }
            break;
        }
        else if (commandUpdate.compare("TITLE") == 0)
        {
            std::cout << "Enter the Title of the movie: ";
            std::cin >> titleMovie;
            break;
        }
        else if (commandUpdate.compare("GENRE") == 0)
        {
            std::cout << "Enter the Genre of the movie: ";
            std::cin >> genreMovie;
            break;
        }
        else if (commandUpdate.compare("YEAR") == 0)
        {
            char tempYearReleaseMovie[MAX_READ_LEN];
            std::cout << "Enter the Release Year of the movie: ";
            std::cin >> tempYearReleaseMovie;

            while (true) {
                try {
                    this->validator.valid_year(tempYearReleaseMovie);
                }
                catch (InputError& iie) {
                    std::cout << iie.what() << "\n";
                    std::cout << ">> ";
                    std::cin >> tempYearReleaseMovie;
                    continue;
                }
                yearReleaseMovie = stoi(tempYearReleaseMovie);
                break;
            }
            break;
        }
        else if (commandUpdate.compare("LIKES") == 0)
        {
            char tempNumberLikesMovie[MAX_READ_LEN];
            std::cout << "Enter the number of Likes for the movie: ";
            std::cin >> tempNumberLikesMovie;


            while (true) {
                try {
                    this->validator.valid_likes(tempNumberLikesMovie);
                }
                catch (InputError& iie) {
                    std::cout << iie.what() << "\n";
                    std::cout << ">> ";
                    std::cin >> tempNumberLikesMovie;
                    continue;
                }
                numberLikesMovie = stoi(tempNumberLikesMovie);
                break;
            }
            break;

        }
        else if (commandUpdate.compare("TRAILER") == 0)
        {
            std::cout << "Enter the trailer link of the movie: ";
            std::cin >> TrailerLinkMovie;
            break;
        }
        else if (commandUpdate.compare("EXIT") == 0)
            break;
    }

    try {
        this->service.updateMovie(id, newID, titleMovie, genreMovie, yearReleaseMovie, numberLikesMovie, TrailerLinkMovie);
        std::cout << "Movie updated successfully.\n";
    }
    catch (OperationError& ioe) {
        std::cout << ioe.what() << "\n";
    }


}

void UI::writeToMovieFileList()
{
    try {
        this->service.writeToFileCurrentMovieList();
        std::cout << "Movie list written!\n";
    }
    catch (OperationError& ioe) {
        std::cout << ioe.what() << "\n";
    }
}

void UI::readFromMovieFileList()
{
    try {
        this->service.readFromFileMovieFileList();
        std::cout << "New movie list laoded!\n";
    }
    catch (OperationError& ioe) {
        std::cout << ioe.what() << "\n";
    }
}

//User menu
void UI::printUserMenu() {
    std::cout << std::endl;
    std::cout << "<<USER MODE>>" << std::endl;
    std::cout << std::endl;
    std::cout << "1. Browse through all movies" << std::endl;
    std::cout << "2. Display all movies in the watch list" << std::endl;
    std::cout << "0. Exit" << std::endl;
    std::cout << ">> ";
}

void UI::printUserBrowsingMenu() {
    std::cout << std::endl;
    std::cout << "Enter NEXT to go the previous movie.\n";
    std::cout << "Enter PREVIOUS to go to the next movie.\n";
    std::cout << "Enter ADD to add the movie to the watch list.\n";
    std::cout << "Enter REVIEW to review a movie and remove from watchlist.\n";
    std::cout << "Enter PRINT to see all the movies in the watchlist.\n";
    std::cout << "Enter EXIT to exit the cycle.\n" << std::endl;
    std::cout << ">> ";
}

void UI::cycleThroughMovies()
{
    string genreToFilterBy;
    std::cout << "Enter the genre of movies you want to see.";
    std::cout << " Press ENTER to see all movies. " << std::endl;
    std::getline(std::cin, genreToFilterBy);
    std::getline(std::cin, genreToFilterBy);

    int currentMovieIndex = 0;
    string command;
    this->printUserBrowsingMenu();

    std::vector<Movie> Movies;

    if (genreToFilterBy != "") {
        Movies = this->service.filterByGenre(genreToFilterBy);
    }
    else {
        Movies = this->service.getAllMovies();
    }

    if (Movies.size() == 0) {
        std::cout << "There are no movies with that genre!" << std::endl;
        return;
    }

    this->printMovie(Movies[0]);
    string completeUrl;
    completeUrl = "start " + Movies[0].getMovieTrailerLink();
    std::system(completeUrl.c_str());
    completeUrl.clear();

    std::cout << ">> ";
    std::cin >> command;

    std::vector<std::string> options = { "PREVIOUS", "NEXT", "ADD", "REVIEW", "PRINT", "EXIT" };

    while (true) {
        try {
            this->validator.validate_option(command, options);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> command;
        }
        if (command == "PREVIOUS")
        {
            if (currentMovieIndex == 0)
            {
                currentMovieIndex = Movies.size() - 1;
                this->printMovie(Movies[currentMovieIndex]);
                string completeUrl;
                completeUrl = "start " + Movies[currentMovieIndex].getMovieTrailerLink();
                std::system(completeUrl.c_str());
                completeUrl.clear();
            }
            else {
                currentMovieIndex--;
                this->printMovie(Movies[currentMovieIndex]);
                string completeUrl;
                completeUrl = "start " + Movies[currentMovieIndex].getMovieTrailerLink();
                std::system(completeUrl.c_str());
                completeUrl.clear();
            }
        }
        else if (command == "NEXT")
        {
            if (currentMovieIndex == Movies.size() - 1) {
                currentMovieIndex = 0;
                this->printMovie(Movies[currentMovieIndex]);
                string completeUrl;
                completeUrl = "start " + Movies[currentMovieIndex].getMovieTrailerLink();
                std::system(completeUrl.c_str());
                completeUrl.clear();
            }
            else
            {
                currentMovieIndex++;
                this->printMovie(Movies[currentMovieIndex]);
                string completeUrl;
                completeUrl = "start " + Movies[currentMovieIndex].getMovieTrailerLink();
                std::system(completeUrl.c_str());
                completeUrl.clear();
            }
        }
        else if (command == "ADD")
        {
            try {
                this->addToWatchList(Movies[currentMovieIndex]);
            }
            catch (InputError& iie) {
                std::cout << iie.what() << "\n";
            }
            catch (OperationError& iie) {
                std::cout << iie.what() << "\n";
            }
            std::cout << "Movie added to watch list!" << std::endl;
        }
        else if (command == "REVIEW")
        {
            int movieToReviewIndex;
            std::cout << "Movie ID: ";
            std::cin >> movieToReviewIndex;
            for (auto&& movie : Movies)
            {
                if (movie.getMovieID() == movieToReviewIndex)
                {
                    try {
                        this->reviewMovie(movie);
                    }
                    catch (InputError& iie) {
                        std::cout << iie.what() << "\n";
                    }
                    //Movies = this->service.filterByGenre(genreToFilterBy);
                    break;
                }
            }
        }
        else if (command == "PRINT")
        {
            this->printWatchList();
        }
        else if (command == "EXIT")
        {
            Movies.clear();
            break;
        }
        this->printUserBrowsingMenu();
        std::cin >> command;
    }
}

void UI::addToWatchList(Movie movieToAdd)
{
    try {
        this->userService.addMovieUser(movieToAdd.getMovieID(), movieToAdd.getMovieTitle(), movieToAdd.getMovieGenre(), movieToAdd.getMovieYear(), movieToAdd.getMovieLikes(), movieToAdd.getMovieTrailerLink());
    }
    catch (OperationError& iie) {
        std::cout << iie.what();
    }
}

void UI::reviewMovie(Movie movieToReview)
{
    string likeChoice;
    std::cout << "Do you like the movie? (Yes/No) \n >> ";
    cin >> likeChoice;
    std::vector<std::string> options = { "Yes", "No"};

    while (true)
    {
        try {
            this->validator.validate_option(likeChoice, options);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> likeChoice;
            continue;
        }
        break;
    }
    if (likeChoice == "Yes")
    {

        this->service.updateMovieLikesForReview(movieToReview.getMovieID(), movieToReview.getMovieID(), movieToReview.getMovieTitle(), movieToReview.getMovieGenre(), movieToReview.getMovieYear(), movieToReview.getMovieLikes() + 1, movieToReview.getMovieTrailerLink());
        try {
            this->userService.removeMovieUser(movieToReview.getMovieID());
        }
        catch (OperationError & iie){
            std::cout << iie.what() << "\n";
        }
        std::cout << "Movie liked! \n";
    }
    else
    {
        try {
            this->userService.removeMovieUser(movieToReview.getMovieID());
        }
        catch (OperationError& iie) {
            std::cout << iie.what() << "\n";
        }
        std::cout << "Movie reviewed! \n";
    }
}

void UI::printWatchList()
{
    this->userService.display();
    //int totalCost = 0;
    //std::vector<Movie> watchList = this->userService.getAllMovies();
    //for (int i = 0; i < watchList.size(); i++) {
    //    this->printMovie(watchList[i]);
    //}
}

//User Mode Functions

void UI::userMode()
{
    this->printUserMenu();

    std::string command;
    std::cin >> command;
    std::vector<std::string> options = { "BROWSE", "PRINT", "EXIT" };

    while (true) {
        while (true)
        {
            try {
                this->validator.validate_option(command, options);
            }
            catch (InputError& iie) {
                std::cout << iie.what() << "\n";
                std::cout << ">> ";
                std::cin >> command;
                continue;
            }
            break;
        }
        if (command.compare("BROWSE") == 0)
        {
            this->cycleThroughMovies();
        }
        else if (command.compare("PRINT") == 0)
        {
            this->printWatchList();
        }
        else if (command.compare("EXIT") == 0)
        {
            std::cout << "Exit user mode" << std::endl;
            break;
        }
        this->printUserMenu();
        std::cin >> command;
    }
}

void UI::runApplication()
{
    this->printChooseAdministratorOrUser();
    std::string command;
    std::cin >> command;
    std::vector<std::string> options = { "ADMINISTRATOR", "USER", "EXIT" };

    while (true) {
        try {
            this->validator.validate_option(command, options);
        }
        catch (InputError& iie) {
            std::cout << iie.what() << "\n";
            std::cout << ">> ";
            std::cin >> command;
        }
        if (command.compare("ADMINISTRATOR") == 0)
        {
            this->administratorMode();
        }
        else if (command.compare("USER") == 0)
        {
            //this->userService.setFileName("watchlist.txt");
            //readFromMovieFileList();
            this->userMode();
        }
        else if (command.compare("EXIT") == 0)
        {
            std::cout << "Exit program" << std::endl;
            break;
        }
        this->printChooseAdministratorOrUser();
        std::cin >> command;
    }
    /* while (command.compare("EXIT") != 0)
     {
         if (command.compare("ADMINISTRATOR") == 0)
         {
             this->administratorMode();
         }
         else if (command.compare("USER") == 0)
         {
             this->userService.setFileName("watchlist.txt");
             readFromMovieFileList();
             this->userMode();
         }
         else if (command.compare("EXIT") == 0)
         {
             std::cout << "Exit program" << std::endl;
             return;
         }
         else
         {
             std::cout << "Invalid command!" << std::endl;
         }
         this->printChooseAdministratorOrUser();
         std::cin >> command;
     }*/
}
