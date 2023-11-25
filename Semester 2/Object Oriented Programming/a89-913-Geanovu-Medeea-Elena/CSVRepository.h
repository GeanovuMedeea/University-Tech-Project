#pragma once
#include "UserRepository.h"

class CSVUserRepository : public UserRepository {
public:
    CSVUserRepository(const std::string& filename);
    ~CSVUserRepository() override;
    void writeToFile() override;
    void display() const override;
};