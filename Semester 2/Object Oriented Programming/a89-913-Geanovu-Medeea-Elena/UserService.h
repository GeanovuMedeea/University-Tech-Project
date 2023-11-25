#pragma once
#pragma once
#include <string>
#include "UserRepository.h"
#include <vector>

class UserService
{
private:
    UserRepository* userRepository;

public:

    //    AdminService();
     //   AdminService(Repository repository);
    UserService();
    UserService(UserRepository* repository);
    ~UserService();

    bool addMovieUser(int movieID, std::string movieTitle, std::string movieGenre, int movieYear, int movieLikes, std::string movieTrailerLink);
    bool removeMovieUser(int movieID);
    std::vector<Movie> getAllMoviesUser();

    void setFileName(std::string newFileName);

    void display();
};