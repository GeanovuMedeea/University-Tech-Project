// 12. 2520 is the smallest number that can be divided by each of the
// numbers from 1 to 10 without any remainder. What is the smallest positive number that
// is divisible with no reminder by all the numbers from 1 to 20?

using System.Diagnostics;
using System.Linq.Expressions;

namespace Lab1
{
    internal class Program
    {
        public static bool checkPrime(int numberToCheck)
        {
            if (numberToCheck < 1) return false;
            if (numberToCheck == 2) return true;
            if (numberToCheck % 2 == 0) return false;
            for (int elementToCheckPrime = 3; elementToCheckPrime * elementToCheckPrime <= numberToCheck; elementToCheckPrime += 2)
                if (numberToCheck % elementToCheckPrime == 0) return false;
            return true;

        }

        public static int leastCommonMultiple(int firstNumber, int secondNumber)
        {

            if (checkPrime(secondNumber))
            {
                if (firstNumber % secondNumber == 0) return firstNumber;
                else
                    return firstNumber * secondNumber;
            }
            for (int numberToMultiplyToFirstUntilFirstBecomesDivisibleBySecond = 1; numberToMultiplyToFirstUntilFirstBecomesDivisibleBySecond <= secondNumber; numberToMultiplyToFirstUntilFirstBecomesDivisibleBySecond++)
            {
                int resultingMultiple = firstNumber * numberToMultiplyToFirstUntilFirstBecomesDivisibleBySecond;
                if (resultingMultiple % secondNumber == 0)
                {
                    return resultingMultiple;
                }
            }
            return firstNumber * secondNumber;
        }

        static void Main(string[] args)
        {
            //Console.WriteLine("Hello, World!");

            int smallestCommonNominator = 20;
            int numberToCheckBeforeMultiplication = 19;
            while(numberToCheckBeforeMultiplication > 2)
            {
                smallestCommonNominator = leastCommonMultiple(smallestCommonNominator, numberToCheckBeforeMultiplication);
                numberToCheckBeforeMultiplication--;
            }
            Console.WriteLine("The least common multiple of all numbers from 1 to 20 is: %");
            Console.WriteLine(smallestCommonNominator);
        }
    }
}
