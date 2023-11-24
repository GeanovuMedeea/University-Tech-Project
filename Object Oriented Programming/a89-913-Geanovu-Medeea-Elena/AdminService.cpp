#include "AdminService.h"
#include <exception>
#include <algorithm>
using namespace std;


//Service::Service()
//{
//    this->repository = AdminRepository();
//}
//
//Service::Service(Repository repository)
//{
//    this->repository = repository;
//}


AdminService::AdminService() {
    this->repository = AdminRepository();
};

AdminService::~AdminService()
{
    {}
}

bool AdminService::addMovie(int movieID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink)
{
    this->repository.addMovie(movieID, movieTitle, movieGenre, movieYear, movieLikes, movieTrailerLink);
    return true;

}

bool AdminService::removeMovie(int movieID)
{
    this->repository.removeMovie(movieID);
    return true;
}

bool AdminService::updateMovie(int movieID, int movieNewID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink)
{
    this->repository.updateMovie(movieID, movieNewID, movieTitle, movieGenre, movieYear, movieLikes, movieTrailerLink);
    return true;

}

bool AdminService::movieExists(int movieID)
{
    if (this->repository.validID(movieID))
        return true;
    return false;
    //for (auto&& movie : this->repository.getAllMovies())
    //{
    //    if (movie.getMovieID() == movieID)
    //    {
    //        return true;
    //    }
    //}
    //return false;
}

Movie AdminService::getMovie(int movieID)
{
    return this->repository.getMovie(movieID);

}

std::vector<Movie> AdminService::getAllMovies()
{
    return this->repository.getAllMovies();
}

int AdminService::getSize()
{
    return this->repository.getSize();
}

void AdminService::setFileName(std::string newFileName)
{
    this->repository.setFileName(newFileName);
}

std::string AdminService::getFileName() const
{
    return this->repository.getFileName();
}

std::vector<Movie> AdminService::filterByGenre(std::string genreToFilterBy)
{
    std::vector<Movie> allMovies = this->repository.getAllMovies();
    std::vector<Movie> filteredMovies (allMovies.size());

    std::vector<Movie>::iterator moviePassedFilter = std::copy_if(allMovies.begin(), allMovies.end(), filteredMovies.begin(), [&](Movie& movieToCheckForFilter) -> bool { return movieToCheckForFilter.getMovieGenre() == genreToFilterBy; });
    int numberOfMoviesWithNonEmptyData = std::count_if(filteredMovies.begin(), filteredMovies.end(), [&](Movie& movieCheckIfHasData) -> bool { return movieCheckIfHasData.getMovieGenre() == genreToFilterBy; });
    filteredMovies.resize(numberOfMoviesWithNonEmptyData);
    return filteredMovies;
}

void AdminService::writeToFileCurrentMovieList() {
    this->repository.writeToFile();
}

void AdminService::readFromFileMovieFileList() {
    this->repository.readFromFile();
}

void AdminService::updateMovieLikesForReview(int id, int newID, const std::string& newTitleMovie, const std::string& newGenreMovie, int newYearMovie, int newLikesMovie, const std::string& newTrailerLinkMovie) {
    this->repository.updateMovieLikesForReview(id, newID, newTitleMovie, newGenreMovie, newYearMovie, newLikesMovie, newTrailerLinkMovie);
}