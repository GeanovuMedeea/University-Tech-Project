#include "HTMLRepository.h"
#include "Exceptions.h"

HTMLUserRepository::HTMLUserRepository(const std::string& filename) : UserRepository(filename) {
    this->FileName = filename;
}

HTMLUserRepository::~HTMLUserRepository()
{
    {}
}

void removeEmptySpaces(std::string& string) {
    while (!string.empty() && (string.back() == ' ' || string.back() == '\t' || string.back() == '\n'))
        string.pop_back();
    reverse(string.begin(), string.end());
    while (!string.empty() && (string.back() == ' ' || string.back() == '\t' || string.back() == '\n'))
        string.pop_back();
    reverse(string.begin(), string.end());
}

void extractDataFromHtml(std::string& string) {
    removeEmptySpaces(string);
    string = string.substr(0, string.size() - 5);
    reverse(string.begin(), string.end());
    string = string.substr(0, string.size() - 4);
    reverse(string.begin(), string.end());
    removeEmptySpaces(string);
}

void removeExtraToLinkData(std::string& string) {
    string = string.substr(0, string.size() - 4);
    reverse(string.begin(), string.end());
    string = string.substr(0, string.size() - 9);
    reverse(string.begin(), string.end());
    int length = (string.size() - 2) / 2;
    string = string.substr(0, string.size() - length - 2);
}

std::vector<Movie> HTMLUserRepository::readFromFile() {
    std::ifstream file(this->FileName);

    if (!file.is_open()) {
        throw FileError("Repo! File couldn't be opened!");
    }

    std::vector<Movie> newWatchList;
    std::string line;

    for (int i = 0; i <= 6; i++) {
        std::getline(file, line);
    }

    do {
        std::getline(file, line);
        removeEmptySpaces(line);
        if (line != "<tr>")
            break;

        std::string id, title, genre, year_of_release, number_of_likes, trailer;

        std::getline(file, id);
        extractDataFromHtml(id);

        std::getline(file, title);
        extractDataFromHtml(title);

        std::getline(file, genre);
        extractDataFromHtml(genre);

        std::getline(file, year_of_release);
        extractDataFromHtml(year_of_release);

        std::getline(file, number_of_likes);
        extractDataFromHtml(number_of_likes);

        std::getline(file, trailer);
        extractDataFromHtml(trailer);
        removeExtraToLinkData(trailer);

        std::getline(file, line);
        newWatchList.push_back(Movie(stoi(id), title, genre, stoi(year_of_release), stoi(number_of_likes), trailer));
    } while (true);

    file.close();
    return newWatchList;
}

void HTMLUserRepository::writeToFile() {
    std::ofstream file(this->FileName);

    if (!file.is_open()) {
        throw FileError("Repo! File couldn't be opened!");
    }

    file << "<!DOCTYPE html>\n<html>\n<head>\n<title>Watchlist</title>\n</head>\n<body>\n<table border=1>\n";
    for (auto& movie : this->Movies) {
        file << "<tr>\n" << "<td>" << movie.getMovieID() << "</td>\n" << "<td>" << movie.getMovieTitle() << "</td>\n" << "<td>" << movie.getMovieGenre() << "</td>\n"
            << "<td>" << movie.getMovieYear() << "</td>\n" << "<td>" << movie.getMovieLikes() << "</td>\n"
            << "<td><a href=\"" << movie.getMovieTrailerLink() << "\">" << movie.getMovieTrailerLink() << "</a></td>\n" << "</tr>\n";
    }
    file << "</table>\n</body>\n</html>";
    file.close();
}

void HTMLUserRepository::display() const{
    std::string pathToOpen = "start C:/Users/geano/source/repos/a7-913-Geanovu-Medeea-Elena-1/";
    pathToOpen = pathToOpen + this->FileName + "/";
    //std::string pathToOpen = "start \"\" \"" + this->FileName + "\"";
    system(pathToOpen.c_str());
}