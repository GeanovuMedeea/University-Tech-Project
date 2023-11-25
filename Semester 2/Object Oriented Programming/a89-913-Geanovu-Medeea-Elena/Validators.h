#pragma once

#include "Exceptions.h"
#include <vector>
#include <algorithm>
#include <string>

class Validators {
public:
    void is_number(const std::string& str);

    void valid_year(const std::string& year);

    void valid_likes(const std::string& likes);

    void validate_option(std::string login_ipt, std::vector<std::string> options);
};