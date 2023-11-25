BEGIN { sum = 0 }
length($3) > 3 {sum += 1}
END { print sum }
