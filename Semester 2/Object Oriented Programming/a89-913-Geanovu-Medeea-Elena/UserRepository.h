#pragma once
#include "Domain.h"
#include <algorithm>
#include <fstream>
#include <vector>

class UserRepository {
protected:
    std::vector<Movie> Movies;
    std::string FileName;

public:
    UserRepository();
    UserRepository(const std::string& filename);
    virtual ~UserRepository() {};

    void setFileName(const std::string& filename);

    void setMovies(std::vector<Movie> newWatchList);

    void addMovie(int id, const std::string& titleMovie, const std::string& genreMovie, int yearReleaseMovie, int numberLikesMovie, const std::string& TrailerLinkMovie);

    void removeMovie(int movieID);

 //   bool validID(int movieID);

 //   Movie getMovie(int movieID);

    std::vector<Movie> getAllMovies();

//    int getSize(); //yes, not necessarry, but tests

 //   void setFileName(std::string newFileName);

 //   std::string getFileName() const;

   // void updateMovieLikesForReview(int id, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie);

    virtual std::vector<Movie> readFromFile();

    virtual void writeToFile();
    virtual void display() const = 0;
};