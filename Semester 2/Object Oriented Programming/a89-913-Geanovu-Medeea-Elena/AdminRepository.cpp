#include "AdminRepository.h"
#include <fstream>
#include <iostream>
#include <sstream>
#include <algorithm>

//AdminRepository::AdminRepository()
//{
//    this->FileName = "default.txt";
//}
//
//AdminRepository::AdminRepository(std::string FileName)
//{
//    this->FileName = FileName;
//    this->readFromFile();
//}

void AdminRepository::addMovie(int id, const std::string& titleMovie, const std::string& genreMovie, int yearReleaseMovie, int numberLikesMovie, const std::string& TrailerLinkMovie)
{
    Movie movieToAdd = Movie(id, titleMovie, genreMovie, yearReleaseMovie, numberLikesMovie, TrailerLinkMovie);

    std::vector<Movie>::iterator movieFound = std::find(this->Movies.begin(), this->Movies.end(), movieToAdd);
    if (movieFound != this->Movies.end()) {
        throw OperationError("Repo! Movie already exists in the database.");
    }
    this->Movies.push_back(movieToAdd);
}


void AdminRepository::removeMovie(int movieID)
{
    std::vector<Movie>::iterator foundMovieToDelete = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToCheck) -> bool { return movieToCheck.getMovieID() == movieID; });
    if (foundMovieToDelete == this->Movies.end()) {
        throw OperationError("Repo! Movie is not in your watchlist.");
    }
    this->Movies.erase(foundMovieToDelete);

}

void AdminRepository::updateMovie(int movieID, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie)
{
    std::vector<Movie>::iterator foundMovieToUpdate = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToUpdate) -> bool { return movieToUpdate.getMovieID() == movieID; });

    if (foundMovieToUpdate == this->Movies.end()) {
        throw OperationError("Repo! Movie is not in the database.");
    }

    foundMovieToUpdate->setMovieGenre(newGenreMovie);
    foundMovieToUpdate->setMovieID(newID);
    foundMovieToUpdate->setMovieLikes(newLikesMovie);
    foundMovieToUpdate->setMovieTitle(newTitleMovie);
    foundMovieToUpdate->setMovieTrailerLink(newTrailerLinkMovie);
    foundMovieToUpdate->setMovieYear(newYearMovie);

 //   this->removeMovie(movieID);
   // this->addMovie(newID, newTitleMovie, newGenreMovie, newYearMovie, newLikesMovie, newTrailerLinkMovie);
}

Movie AdminRepository::getMovie(int movieID)
{
    std::vector<Movie>::iterator foundMovie = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToFind) -> bool { return movieToFind.getMovieID() == movieID; });
    if (foundMovie <this->Movies.end())
        return *foundMovie;
    return Movie(-1, "", "", -1, -1, "");
}

std::vector<Movie> AdminRepository::getAllMovies()
{
    return this->Movies;
}

int AdminRepository::getSize() //NOT NECESSARY (but again, need for my tests)
{
    return this->Movies.size();
}

void AdminRepository::setFileName(std::string newFileName)
{
    this->FileName = newFileName;
}

std::string AdminRepository::getFileName() const
{
    return this->FileName;
}

bool AdminRepository::validID(int movieID)
{
    std::vector<Movie>::iterator foundValidMovie = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToCheckIfItExists) -> bool { return movieToCheckIfItExists.getMovieID() == movieID; });
    if (foundValidMovie <this->Movies.end())
        return true;
    return false;
}

void AdminRepository::updateMovieLikesForReview(int id, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie)
{
    std::vector<Movie>::iterator foundMovieToUpdateLikes = std::find_if(this->Movies.begin(), this->Movies.end(), [&](Movie& movieToUpdateLikes) -> bool { return movieToUpdateLikes.getMovieID() == id; });
    foundMovieToUpdateLikes->setMovieGenre(newGenreMovie);
    foundMovieToUpdateLikes->setMovieID(newID);
    foundMovieToUpdateLikes->setMovieLikes(newLikesMovie);
    foundMovieToUpdateLikes->setMovieTitle(newTitleMovie);
    foundMovieToUpdateLikes->setMovieTrailerLink(newTrailerLinkMovie);
    foundMovieToUpdateLikes->setMovieYear(newYearMovie);
  //  this->removeMovie(id);
    //this->addMovie(newID, newTitleMovie, newGenreMovie, newYearMovie, newLikesMovie, newTrailerLinkMovie);
}

//void AdminRepository::writeToFile()
//{
//    std::ofstream file(this->FileName);
//    file << "";
//
//    if (!file.is_open())
//        throw FileError("The file could not be open.");
//
//    if (file.is_open())
//    {
//        for (auto&& movie : this->Movies)
//        {
//            file << movie.getMovieID() << ",";
//            file << movie.getMovieTitle() << ",";
//            file << movie.getMovieGenre() << ",";
//            file << movie.getMovieYear() << ",";
//            file << movie.getMovieLikes() << ",";
//            file << movie.getMovieTrailerLink() << ",";
//            file << std::endl;
//        }
//    }
//    file.close();
//}
//
//void AdminRepository::readFromFile()
//{
//    std::ifstream file(this->FileName);
//
//    if (!file.is_open()) {
//        throw FileError("The file could not be open.");
//    }
//
//    if (file.is_open())
//    {
//        this->Movies.clear();
//        int id, yearRelease, numberLikes;
//        std::string title, genre, trailerLink;
//        std::string tempid, tempyear, templikes;
//        std::string line;
//
//        while (getline(file, line))
//        {
//            std::stringstream ss(line);
//            getline(ss, tempid, ',');
//            getline(ss, title, ',');
//            getline(ss, genre, ',');
//            getline(ss, tempyear, ',');
//            getline(ss, templikes, ',');
//            getline(ss, trailerLink, ',');
//            id = stoi(tempid);
//            yearRelease = stoi(tempyear);
//            numberLikes = stoi(templikes);
//            Movie movieToAdd(id, title, genre, yearRelease, numberLikes, trailerLink);
//            this->Movies.push_back(movieToAdd);
//        }
//    }
//    file.close();
//}


void AdminRepository::readFromFile() {
    std::ifstream file(this->FileName);
    if (!file.is_open()) {
        throw FileError("Repo! The file could not be open.");
    }
    Movie movie;
    while (file >> movie) {
        this->Movies.push_back(movie);
    }
    file.close();
}

void AdminRepository::writeToFile() {
    std::ofstream file(this->FileName);
    if (!file.is_open())
        throw FileError("Repo! File could not be open.");
    for (auto& movie:this->Movies)
    {
        file << movie;
    }
    file.close();
}