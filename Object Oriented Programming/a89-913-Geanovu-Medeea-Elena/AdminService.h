#pragma once
#include <string>
#include "AdminRepository.h"
#include "UserRepository.h"
#include <vector>

class AdminService
{
private:

    AdminRepository repository;

public:

    //    AdminService();
     //   AdminService(Repository repository);
    AdminService();
    ~AdminService();

    bool addMovie(int movieID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink);
    bool removeMovie(int movieID);
    bool updateMovie(int movieID, int movieNewID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink);
    bool movieExists(int movieID);

    Movie getMovie(int movieID);
    std::vector<Movie> getAllMovies();
    int getSize();

    void setFileName(std::string newFileName);
    std::string getFileName() const;

    std::vector<Movie> filterByGenre(std::string genreToFilterBy);

    void updateMovieLikesForReview(int id, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie);

    void writeToFileCurrentMovieList();
    void readFromFileMovieFileList();
};