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

bool is_number_prime(float n)
{
	/*
	* checks if a given number "n" is prime, returns true is it is prime and false if it is not prime
	*/
	if (n < 2)
		return false;
	if (n == 2)
		return true;
	if ((int)(n) % 2 == 0)
		return false;
	int i;
	for (i = 3; i * i <= n; i += 2)
		if ((int)(n) % i == 0)
			return false;
	return true;
}

void longest_subsequence_prime_difference_between_any_two_continuous_terms(int elem_nr, float v[], int*  start, int* end)
{
	/*
	* computes the starting index and ending index of the longest contiguous subsequence whose any two
	* consecutive terms have a difference value which is prime and returns the values through the start and end parameters
	*/
	int i, first = 0, last = 0, max_first = -1, max_last = -1, p = 1, pmax = 1;
	for (i = 0; i < elem_nr - 1; i++)
	{
		last = last + 1;
		if (is_number_prime(v[i + 1] - v[i]))
			p = p + 1;
		else
		{
			if (p > pmax)
			{
				pmax = p;
				p = 1;
				max_first = first;
				max_last = last - 1;
			}
			first = last;
		}

	}

	if (p > pmax)
	{
		pmax = p;
		max_first = first;
		max_last = last;
		first = last;
	}

	*start = max_first;
	*end = max_last;
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
			double an, square;
			printf("Please input a positive real number: \n");
			scanf_s("%lf", &an);
			printf("Please input the precision: \n");
			scanf_s("%d", &precision);
			square = square_root(an, precision);
			printf("The square root of %lf is: \n", an);
			printf("%lf", square);
		}
		if (choice == 2)
		{
			int elem_nr, i;
			int start = -1, end = -1;
			float v[100];
			printf("Input the number of elements: \n");
			scanf_s("%d", &elem_nr);
			printf("Input the elements: \n");
			for (i = 0; i < elem_nr; i++)
			{
				scanf_s("%f", &v[i]);
			}
			longest_subsequence_prime_difference_between_any_two_continuous_terms(elem_nr, v, &start, &end);
			if (start == -1 || end == -1) {
				printf("There is no subsequence satisfying the requirements");
			}
			else {
				printf("Longest subsequnce satisfying the requirements is: \n");
				for (i = start; i <= end; i++)
				{
					printf("%.2f", v[i]);
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