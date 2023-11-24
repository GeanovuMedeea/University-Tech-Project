#include <stdio.h>
#include <math.h>
#include <stdbool.h>

//problem 4 assignment 1
//**a.** Compute the approximated value of square root of a positive real number. The precision is provided by the user.\
//**b.** Given a vector of numbers, find the longest contiguous subsequence such that the difference of any two consecutive elements is a prime number.

void menu()
{
	printf("\nPress 1 for computing the square root problem\n");
	printf("Press 2 for finding the longest contiguous subsequence such that the difference of any two consecutibe elements is a prime number problem\n");
	printf("Press 0 to exit \n");
	printf("Input choice: \n");
}

double square_root(double number, int precision)
{
	/*
	* computes the square root of the given number "number" with a given number of decimals
	(given by "parameter") after the float point
	*/
	double square = -1;
	square = sqrtl(number);
	square = round(pow(10, precision) * square) / pow(10, precision);
	return square;
}

bool is_number_prime(float number_to_check)
{
	/*
	* checks if a given number "number_to_check" is prime, returns true is it is prime and false if it is not prime
	*/
	if (number_to_check < 2)
		return false;
	if (number_to_check == 2)
		return true;
	if ((int)(number_to_check) % 2 == 0)
		return false;
	int i;
	for (i = 3; i * i <= number_to_check; i += 2)
		if ((int)(number_to_check) % i == 0)
			return false;
	return true;
}

void longest_subsequence_prime_difference_between_any_two_continuous_terms(int number_of_elements, float elements[], int* start_subsequence, int* end_subsequence)
{
	/*
	* computes the starting index and ending index of the longest contiguous subsequence whose any two
	* consecutive terms have a difference value which is prime and returns the values through the start and end parameters
	* start will have the starting index of the sequence, end will have the ending index of the sequence
	*/
	int i, first_current_sequence = 0, last_current_sequence = 0, max_first = -1, max_last = -1, current_sequence_length = 1, max_sequence_length = 1;
	for (i = 0; i < number_of_elements - 1; i++)
	{
		last_current_sequence = last_current_sequence + 1;
		if (is_number_prime(elements[i + 1] - elements[i]))
			current_sequence_length = current_sequence_length + 1;
		else
		{
			if (current_sequence_length > max_sequence_length)
			{
				max_sequence_length = current_sequence_length;
				current_sequence_length = 1;
				max_first = first_current_sequence;
				max_last = last_current_sequence - 1;
			}
			first_current_sequence = last_current_sequence;
		}

	}

	if (current_sequence_length > max_sequence_length)
	{
		max_sequence_length = current_sequence_length;
		max_first = first_current_sequence;
		max_last = last_current_sequence;
		first_current_sequence = last_current_sequence;
	}

	*start_subsequence = max_first;
	*end_subsequence = max_last;
}

int main()
{
	int choice = 1;
	while (choice != 0) {
		menu();
		scanf_s("%d", &choice);
		if (choice == 1)
		{
			int precision;
			double nr_to_compute_sqrt, square;
			printf("Please input a positive real number: \n");
			scanf_s("%lf", &nr_to_compute_sqrt);
			printf("Please input the precision: \n");
			scanf_s("%d", &precision);
			square = square_root(nr_to_compute_sqrt, precision);
			printf("The square root of %lf is: \n", nr_to_compute_sqrt);
			printf("%lf", square);
		}
		if (choice == 2)
		{
			int number_of_elements, i;
			int start_of_sequence = -1, end_of_sequence = -1;
			float elements[100];
			printf("Input the number of elements: \n");
			scanf_s("%d", &number_of_elements);
			printf("Input the elements: \n");
			for (i = 0; i < number_of_elements; i++)
			{
				scanf_s("%f", &elements[i]);
			}
			longest_subsequence_prime_difference_between_any_two_continuous_terms(number_of_elements, elements, &start_of_sequence, &end_of_sequence);
			if (start_of_sequence == -1 || end_of_sequence == -1) {
				printf("There is no subsequence satisfying the requirements");
			}
			else {
				printf("Longest subsequnce satisfying the requirements is: \n");
				for (i = start_of_sequence; i <= end_of_sequence; i++)
				{
					printf("%.2f", elements[i]);
					printf(" ");
				}

			}
		}
		if (choice == 0)
		{
			return 0;
		}
	}
}