#include <string>
#include "Domain.h"
#include <vector>
#include <sstream>

Movie::Movie() {
    this->id = 0;
    this->title = "0";
    this->genre = "0";
    this->yearRelease = 0;
    this->numberLikes = 0;
    this->trailerLink = "0";
};

Movie::Movie(int id, const std::string& titleMovie, const std::string& genreMovie, int yearReleaseMovie, int numberLikesMovie, const std::string& trailerLinkMovie) {

    this->id = id;
    this->title = titleMovie;
    this->genre = genreMovie;
    this->yearRelease = yearReleaseMovie;
    this->numberLikes = numberLikesMovie;
    this->trailerLink = trailerLinkMovie;
}

//Getters
int Movie::getMovieID()
{
    return this->id;
}

std::string Movie::getMovieTitle() const
{
    return this->title;
}

std::string Movie::getMovieGenre() const
{
    return this->genre;
}

int Movie::getMovieYear()
{
    return this->yearRelease;
}

int Movie::getMovieLikes()
{
    return this->numberLikes;
}

std::string Movie::getMovieTrailerLink() const
{
    return this->trailerLink;
}

//Operator overloading
bool Movie::operator==(const Movie& movieToCompare)
{
    if (this->id == movieToCompare.id && this->title == movieToCompare.title && this->genre == movieToCompare.genre && this->yearRelease == movieToCompare.yearRelease && this->numberLikes == movieToCompare.numberLikes && this->trailerLink == movieToCompare.trailerLink)
    {
        return true;
    }
    else
    {
        return false;
    }
}

std::string Movie::to_str() {
    std::ostringstream output;

    output << "Id: " << this->id << "; ";
    output << "Title: " << this->title << "; ";
    output << "Genre: " << this->genre << "; ";
    output << "Year of release: " << this->yearRelease << "; ";
    output << "Number of likes: " << this->numberLikes << "; ";
    output << "Trailer link: " << this->trailerLink << "\n";

    return output.str();
}

std::vector<std::string> tokenize(const std::string& str, char delimiter) {
    std::vector<std::string> result;
    std::stringstream ss(str);
    std::string(token);
    while (getline(ss, token, delimiter))
        result.push_back(token);
    return result;
}

std::istream& operator>>(std::istream& is, Movie& movie) {
    std::string line;
    getline(is, line);

    std::vector<std::string> tokens = tokenize(line, ',');
    if (tokens.size() != 6)
        return is;

    movie.id = stoi(tokens[0]);
    movie.title = tokens[1];
    movie.genre = tokens[2];
    movie.yearRelease = stoi(tokens[3]);
    movie.numberLikes = stoi(tokens[4]);
    movie.trailerLink = tokens[5];

    return is;
}

std::ostream& operator<<(std::ostream& os, const Movie& movie) {
    os << movie.id << "," << movie.title << "," << movie.genre << "," << movie.yearRelease << "," << movie.numberLikes << "," << movie.trailerLink << "\n";
    return os;
}

//Setters
void Movie::setMovieID(int newID)
{
    this->id = newID;
}

void Movie::setMovieTitle(const std::string& newTitle)
{
    this->title = newTitle;
}

void Movie::setMovieGenre(const std::string& newGenre)
{
    this->genre = newGenre;
}

void Movie::setMovieYear(int newYear)
{
    this->yearRelease = newYear;
}

void Movie::setMovieLikes(int newLikes)
{
    this->numberLikes = newLikes;
}

void Movie::setMovieTrailerLink(const std::string& newTrailerLink)
{
    this->trailerLink = newTrailerLink;
}
