#pragma once
#include <string>

class Movie {
private:
    int id;
    std::string title;
    std::string genre;
    int yearRelease;
    int numberLikes;
    std::string trailerLink;

public:
    Movie();
    Movie(int id, const std::string& title, const std::string& genre, int yearRelease, int numberLikes, const std::string& trailerLink);

    int getMovieID();
    std::string getMovieTitle() const;
    std::string getMovieGenre() const;

    int getMovieYear();
    int getMovieLikes();
    std::string getMovieTrailerLink() const;

    bool operator==(const Movie& movieToCompare);
    //Movie& operator=(const Movie& movie);

    /*
     * Custom operators that override the functionality of the stream operators using <iostream>. Facilitates the
     * read-a-movie and write-a-movie operations.
     */
    friend std::istream& operator>>(std::istream& is, Movie& movie);

    friend std::ostream& operator<<(std::ostream& os, const Movie& movie);

    std::string to_str();


    void setMovieID(int newID);
    void setMovieTitle(const std::string& newTitle);
    void setMovieGenre(const std::string& newGenre);
    void setMovieYear(int newYear);
    void setMovieLikes(int newLikes);
    void setMovieTrailerLink(const std::string& newTrailerLink);
};
