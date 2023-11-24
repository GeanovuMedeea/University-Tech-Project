#include "UserRepository.h"
#include "Exceptions.h"

UserRepository::UserRepository()
{
    this->Movies = std::vector<Movie>{};
    this->FileName = "";
}

UserRepository::UserRepository(const std::string& filename) {
    this->FileName = filename;
}

std::vector<Movie> UserRepository::getAllMovies() {
    return this->Movies;
}

void UserRepository::setFileName(const std::string& filename)
{
    this->FileName = filename;
}

void UserRepository::setMovies(std::vector<Movie> newWatchList) {
    this->Movies = newWatchList;
}

void UserRepository::addMovie(int id, const std::string& titleMovie, const std::string& genreMovie, int yearReleaseMovie, int numberLikesMovie, const std::string& TrailerLinkMovie) {
    this->Movies = readFromFile();

    Movie movieToAdd = Movie(id, titleMovie, genreMovie, yearReleaseMovie, numberLikesMovie, TrailerLinkMovie);

    std::vector<Movie>::iterator movieFound = std::find(this->Movies.begin(), this->Movies.end(), movieToAdd);
    if (movieFound != this->Movies.end()) {
        throw OperationError("Repo! Movie already exists in the database.");
    }

    this->Movies.push_back(Movie(id, titleMovie, genreMovie, yearReleaseMovie, numberLikesMovie, TrailerLinkMovie));

    writeToFile();
}

void UserRepository::removeMovie(int movieID) {
    this->Movies = readFromFile();

    std::vector<Movie>::iterator foundMovieToDelete = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToCheck) -> bool { return movieToCheck.getMovieID() == movieID; });
    if (foundMovieToDelete == this->Movies.end()) {
        throw OperationError("Repo! Movie is not in your watchlist.");
    }
    this->Movies.erase(foundMovieToDelete);

    writeToFile();
}

//void UserRepository::updateMovie(Movie movie, Movie new_movie) {
//    this->watchlist = read_from_file();
//
//    std::vector<Movie>::iterator it = find(this->watchlist.begin(), this->watchlist.end(), movie);
//    if (it != this->Movies.end()) {
//        *it = new_movie;
//    }
//
//    writeToFile();
//}

std::vector<Movie> UserRepository::readFromFile() {
    std::ifstream file(this->FileName);
    if (!file.is_open()) {
        throw FileError("Repo! File could not be open.");
    }
    std::vector<Movie> newWatchList;

    Movie movie;
    while (file >> movie) {
        newWatchList.push_back(movie);
    }

    file.close();
    return newWatchList;
}

void UserRepository::writeToFile() {
    std::ofstream file(this->FileName);
    if (!file.is_open())
        throw FileError("Repo! File could not be open.");
    for (auto& movie : this->Movies)
    {
        file << movie;
    }
    file.close();
}