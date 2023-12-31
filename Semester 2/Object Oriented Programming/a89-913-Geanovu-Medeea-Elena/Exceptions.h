#pragma once
#include <exception>

class OperationError : public std::exception {
private:
    const char* message;
public:
    explicit OperationError(const char* message);
    [[nodiscard]] const char* what() const noexcept override;
};

class InputError : public std::exception {
private:
    const char* message;
public:
    explicit InputError(const char* message);
    [[nodiscard]] const char* what() const noexcept override;
};

class FileError : public std::exception {
private:
    const char* message;
public:
    explicit FileError(const char* message);
    [[nodiscard]] const char* what() const noexcept override;
};