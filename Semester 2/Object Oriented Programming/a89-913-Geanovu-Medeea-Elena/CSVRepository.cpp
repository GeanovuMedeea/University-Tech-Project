#include "CSVRepository.h"
#include "Exceptions.h"

CSVUserRepository::CSVUserRepository(const std::string& filename) : UserRepository(filename) {
    this->FileName = filename;
}

CSVUserRepository::~CSVUserRepository()
{

}

void CSVUserRepository::writeToFile() {
    std::ofstream file(this->FileName);

    if (!file.is_open()) {
        throw FileError("Repo! File could not be opened!");
    }

    for (auto& movie : this->Movies) {
        file << movie;
    }

    file.close();
}

void CSVUserRepository::display() const{
    std::string pathToOpen = "notepad \"" + this->FileName + "\"";
    system(pathToOpen.c_str());
}