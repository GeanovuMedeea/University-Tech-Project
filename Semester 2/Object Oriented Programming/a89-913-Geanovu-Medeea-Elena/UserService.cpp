#include "UserService.h"


UserService::UserService()
{
    this->userRepository = nullptr;
}

UserService::UserService(UserRepository* repository)
{
    this->userRepository = repository;
}

UserService::~UserService()
{
    this->userRepository = nullptr;
    delete userRepository;
}

bool UserService::addMovieUser(int movieID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink)
{
    this->userRepository->addMovie(movieID, movieTitle, movieGenre, movieYear, movieLikes, movieTrailerLink);
    return true;
}

bool UserService::removeMovieUser(int movieID)
{
    this->userRepository->removeMovie(movieID);
    return true;
}

void UserService::display()
{
    this->userRepository->display();
}

std::vector<Movie> UserService::getAllMoviesUser()
{
    return this->userRepository->getAllMovies();
}

void UserService::setFileName(std::string newFileName)
{
    this->userRepository->setFileName(newFileName);
}