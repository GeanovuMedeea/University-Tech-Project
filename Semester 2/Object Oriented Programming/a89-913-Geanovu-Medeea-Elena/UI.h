#pragma once 
#include <string>
#include <iostream>
#include "AdminService.h"
#include "UserService.h"
#include "Validators.h"
#include "exceptions.h"
#include "CSVRepository.h"
#include "HTMLRepository.h"


class UI
{
private:
    UserService userService;
    AdminService service;
    Validators validator;

public:

    //   UI(Service service, AdminService userService);
    UI();
    UI(UserService service);
    void administratorMode();
    void printAdministratorMenu();
    void printAllMovies(int AdministratorOrUserServiceChoice);
    void printChooseAdministratorOrUser();
    void printMovie(Movie movieToPrint);

    void runApplication();

    void addMovie();
    void removeMovie();
    void updateMovie();
    void writeToMovieFileList();
    void readFromMovieFileList();

    void userMode();
    void printUserMenu();
    void printUserBrowsingMenu();

    void cycleThroughMovies();
    void addToWatchList(Movie movieToAdd);
    void reviewMovie(Movie movieToReview);
    void printWatchList();
};