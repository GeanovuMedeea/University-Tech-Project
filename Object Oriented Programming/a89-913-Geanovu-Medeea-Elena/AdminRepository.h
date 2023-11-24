#pragma once
#include <string>
#include "Domain.h"
#include "Exceptions.h"
#include <Vector>

class AdminRepository {
private:
    std::vector<Movie> Movies;
    std::string FileName = "default.txt";

public:
    //    AdminRepository();
    //    AdminRepository(std::string FileName);

    void addMovie(int id, const std::string& titleMovie, const std::string& genreMovie, int yearReleaseMovie, int numberLikesMovie, const std::string& TrailerLinkMovie);

    void removeMovie(int movieID);

    void updateMovie(int movieID, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newReleaseYearMovie, int newNumberLikesMovie, const std::string& newTrailerLinkMovie);

    bool validID(int movieID);

    Movie getMovie(int movieID);

    std::vector<Movie> getAllMovies();

    int getSize(); //yes, not necessarry, but tests

    void setFileName(std::string newFileName);

    std::string getFileName() const;

    void updateMovieLikesForReview(int id, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie);

    void writeToFile();

    void readFromFile();
};