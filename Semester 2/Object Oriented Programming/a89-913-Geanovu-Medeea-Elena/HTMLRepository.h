#pragma once
#include "UserRepository.h"

class HTMLUserRepository : public UserRepository {
public:
    HTMLUserRepository(const std::string& filename);
    ~HTMLUserRepository() override;
    std::vector<Movie> readFromFile() override;

    void writeToFile() override;

    void display() const override;
};
