#include <assert.h>
#include "AdminService.h"
#include "Domain.h"
#include <iostream>
#include <string>
#include <fstream>
#include "AdminRepository.h"
#include <fstream>
#include <sstream>
#include "Domain.h"
#include "Tests.h"
#include "UserRepository.h"
#include "CSVRepository.h"
#include "HTMLRepository.h"
#include "UserService.h"


void testService()
{
    AdminRepository repository;
    AdminService emptyService;
    assert(emptyService.getAllMovies().size() == 0);
    AdminService service;
    service.setFileName("Movies.txt");

    service.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    assert(service.getSize() == 1);
    assert(service.movieExists(1) == true);

    service.removeMovie(1);
    assert(service.getSize() == 0);
    service.addMovie(1, "TestTitle2", "TestGenre2", 3, 4, "TestLink2");
    assert(service.getMovie(1).getMovieGenre() == "TestGenre2");
    service.updateMovie(1, 3, "TestTitle2", "TestGenre3", 3, 4, "TestLink2");
    assert(service.getMovie(3).getMovieGenre() == "TestGenre3");
    service.updateMovieLikesForReview(3, 3, "TestTitle2", "TestGenre3", 3, 5, "TestLink2");
    assert(service.getMovie(3).getMovieLikes() == 5);

    assert(service.getAllMovies().size() == 1);

    assert(service.getSize() == 1);
    assert(service.getFileName() == "Movies.txt");

    Movie movieTest = Movie(6, "TestTitle2", "TestGenre10", 3, 5, "TestLink2");
    assert(service.addMovie(6, "TestTitle2", "TestGenre10", 3, 5, "TestLink2") == true);
    std::string genreToFilterTest = "TestGenre10";
    std::vector<Movie> filtered = service.filterByGenre(genreToFilterTest);
    assert(filtered.size() == 1);
    assert(filtered[0] == movieTest);
    std::cout << "Service tests passed!" << std::endl;
}

void testUserService()
{
    UserRepository* repository = new CSVUserRepository("watchlistTEST.csv");
    UserService service = UserService(repository);

    assert(service.addMovieUser(3, "TestTitle2", "TestGenre3", 3, 4, "TestLink2") == true);
    assert(service.removeMovieUser(3) == true);
    delete repository;

    UserRepository* repository2 = new HTMLUserRepository("watchlistTEST.html");
    UserService service2 = UserService(repository2);

    assert(service2.addMovieUser(3, "TestTitle2", "TestGenre3", 3, 4, "TestLink2") == true);
    assert(service2.removeMovieUser(3) == true);
    delete repository2;
    std::cout << "User AdminService tests passed!" << std::endl;
}

void testMovieExists()
{
    AdminRepository repository;
    AdminService service;
    service.setFileName("Movies.txt");

    service.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    assert(service.movieExists(1) == true);
    assert(service.movieExists(2) == false);

    std::cout << "MovieExists tests passed!" << std::endl;
}


void testDomain()
{
    Movie movieTest;

    assert(movieTest.getMovieID() == 0);
    assert(movieTest.getMovieTitle() == "0");
    assert(movieTest.getMovieGenre() == "0");
    assert(movieTest.getMovieYear() == 0);
    assert(movieTest.getMovieLikes() == 0);
    assert(movieTest.getMovieTrailerLink() == "0");

    Movie movie(1, "TestTitle", "TestGenre", 2, 3, "TestLink");

    assert(movie.getMovieID() == 1);
    assert(movie.getMovieTitle() == "TestTitle");
    assert(movie.getMovieGenre() == "TestGenre");
    assert(movie.getMovieYear() == 2);
    assert(movie.getMovieLikes() == 3);
    assert(movie.getMovieTrailerLink() == "TestLink");

    movie.setMovieID(5);
    assert(movie.getMovieID() == 5);

    movie.setMovieTitle("A");
    assert(movie.getMovieTitle() == "A");

    movie.setMovieGenre("B");
    assert(movie.getMovieGenre() == "B");

    movie.setMovieYear(2000);
    assert(movie.getMovieYear() == 2000);

    movie.setMovieLikes(100);
    assert(movie.getMovieLikes() == 100);

    movie.setMovieTrailerLink("TestLink2");
    assert(movie.getMovieTrailerLink() == "TestLink2");
    std::cout << "Domain tests passed!" << std::endl;
}

void testAddMovie()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    repository.setFileName("test.txt");

    Movie movieToAdd(1, "TestTitle1", "TestGenre1", 3, 4, "link");
    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "link");

    assert(repository.getSize() == 1);
    assert(repository.getAllMovies()[0] == movieToAdd);

    repository.addMovie(2, "TestTitle3", "TestGenre2", 2, 4, "link");
    Movie movieToUpdate(2, "TestTitle3", "TestGenre2", 2, 4, "link");

    assert(repository.getSize() == 2);
    assert(repository.getAllMovies()[1] == movieToUpdate);

    std::cout << "AddMovie test passed!" << std::endl;
}

void testRemoveMovie()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    Movie movieToAdd(1, "TestTitle1", "TestGenre1", 3, 4, "link");
    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "link");
    repository.removeMovie(1);

    assert(repository.getSize() == 0);
    std::cout << "RemoveMovie test passed!" << std::endl;
}

void testUpdateMovie()
{
    AdminRepository repository;
    repository.setFileName("test.txt");

    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    repository.updateMovie(1, 1, "TestTitle2", "TestGenre2", 4, 5, "TestLink2");
    Movie movieToUpdate(1, "TestTitle2", "TestGenre2", 4, 5, "TestLink2");

    assert(repository.getSize() == 1);
    assert(repository.getAllMovies()[0] == movieToUpdate);
    std::cout << "UpdateMovie test passed!" << std::endl;
}

void testGetMovie()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    Movie movieTestEmpty = Movie(-1, "", "", -1, -1, "");
    assert(repository.getMovie(2) == movieTestEmpty);

    repository.addMovie(2, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    Movie movieToAdd(2, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");

    assert(repository.getMovie(2) == movieToAdd);
    repository.removeMovie(2);

    std::cout << "GetMovie test passed!" << std::endl;
}

void testGetAllMovies()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink");
    Movie movieToAdd(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink");
    assert(repository.getAllMovies()[0] == movieToAdd);
    repository.removeMovie(1);
    std::cout << "GetAllMovies test passed!" << std::endl;
}

void testGetSize() //NOT NECESSARY, but i need it for the tests
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    repository.addMovie(1, "TestTitle2", "TestGenre2", 3, 4, "TestLink2");

    assert(repository.getSize() == 1);
    repository.removeMovie(1);

    std::cout << "GetTitle test passed!" << std::endl;
}

void testSetFileName()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    repository.setFileName("test2.txt");

    assert(repository.getFileName() == "test2.txt");

    std::cout << "SetFile name test passed!" << std::endl;
}

void testWriteToFile()
{
    AdminRepository repository;
    repository.setFileName("test.txt");
    Movie movieToAdd(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    repository.writeToFile();
    repository.removeMovie(1);
    assert(repository.getSize() == 0);

    repository.readFromFile();
    assert(repository.getSize() == 1);
    assert(repository.getAllMovies()[0] == movieToAdd);
    repository.removeMovie(1);
    repository.writeToFile();

    std::cout << "WriteToFile test passed!" << std::endl;
}

void testValidID()
{
    AdminRepository repository;
    repository.setFileName("test.txt");

    repository.addMovie(1, "TestTitle1", "TestGenre1", 3, 4, "TestLink1");
    assert(repository.validID(1) == true);
    assert(repository.validID(2) == false);

    repository.removeMovie(1);
    assert(repository.getSize() == 0);

    std::cout << "ValidID test passed!" << std::endl;
}

void testRepository()
{
    testAddMovie();
    testMovieExists();
    testRemoveMovie();
    testUpdateMovie();
    testGetMovie();
    testGetAllMovies();
    testGetSize();
    testSetFileName();
    testWriteToFile();
    testValidID();
}

void TestAll()
{
    testDomain();
    testRepository();
    testService();
    testUserService();
}