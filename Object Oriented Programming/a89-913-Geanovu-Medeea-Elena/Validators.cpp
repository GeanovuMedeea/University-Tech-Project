#include "Validators.h"

void Validators::is_number(const std::string& str) {
    for (char const& c : str) {
        if (std::isdigit(c) == 0) {
            throw InputError("Not a number!/negative number!\n");
        }
    }
    int check;
    check = stoi(str);
    if (check < 0)
        throw InputError("Negative number!");
}

void Validators::valid_year(const std::string& year) {
    for (char const& c : year) {
        if (std::isdigit(c) == 0) {
            throw InputError("Not a number!/negative number!\n");
        }
    }
    int check;
    check = stoi(year);
    if (check < 0 || check < 1895)
        throw InputError("Invalid year!");
}

void Validators::valid_likes(const std::string& likes)
{
    for (char const& c : likes) {
        if (std::isdigit(c) == 0) {
            throw InputError("Not a number!/negative number!\n");
        }
    }
    int check;
    check = stoi(likes);
    if (check < 0)
        throw InputError("Invalid number of likes!");
}

void Validators::validate_option(std::string login_ipt, std::vector<std::string> options) {
    std::vector<std::string>::iterator it = find(options.begin(), options.end(), login_ipt);
    if (it == options.end()) {
        throw InputError("Invalid input.");
    }
}