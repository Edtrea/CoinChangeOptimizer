// ICT 167 Assignment 1
// Lim Wen Chao
// 14 Oct 2021
// Client.java
// The main part of the program. Interfaces with user.

import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    
    /** 
     * @param args
     */
    public static void main(String[] args) {
        // Initiate required variables
        Scanner input = new Scanner(System.in);
        String country = "", name = "", tempInput, resultString;
        boolean flag = true;
        int money = 0, resultChangesIndex = -1;
        ArrayList<Change> changes = new ArrayList<Change>();

        // Print out student Name, ID and tutor name
        studentInfo();
        System.out.println();

        //Ask user to use pre-entered test data or not
        System.out.println("Use pre-entered test data?(Y/N)");
        tempInput = input.nextLine();
        //If user inputs Y then use test data else ask user for manual inputs
        if(tempInput.equalsIgnoreCase("Y"))
        {
            testData(changes);
            tempInput = "";
        }
        else
        {
            // Display list of Country and request user to select a country to determine
            // coin denominations
            System.out.println("List of countries");
            // Loops through Enum for all Countries
            displayCountry();
            System.out.println("Select and enter the country code from the list above.");
            country = input.nextLine();
            //Checks if country code is valid
            while (true) {
                if (checkValidCountry(country) == false) {
                    displayCountry();
                    System.out.println("Please select and enter a valid country code from the list.");
                    country = input.nextLine();
                } else {
                    System.out.println("Country selected: " + CoinDenoms.valueOf(country.toUpperCase()).getCountryName());
                    break;
                }
            }

            // Loop for ask user for person name & coin value amount
            while (flag) {
                System.out.println("Please enter the name of the person:");
                name = input.nextLine();
                //Check name is not blank or empty
                while (true)
                {
                    if (name.isEmpty() == false && name.isBlank() == false)
                    {
                        break;
                    }
                    else
                    {
                        System.out.println("Person name cannot be blank");
                        System.out.println("Please enter the name of the person:");
                        name = input.nextLine();
                    }
                }
                System.out.println("Please enter a positive coin value(in cents) for the person (multiple of 5):");
                tempInput = input.nextLine();
                // Loops to check if coin amount value is valid
                while (true) {
                    if (checkIsValidCoinValue(tempInput) == false) {
                        System.out.println("Coin value must be positive and in multiples of 5");
                        tempInput = input.nextLine();
                    } else {
                        money = Integer.parseInt(tempInput);
                        tempInput = "";
                        break;
                    }
                }

                if (searchPersonName(changes, name) >= 0 && changes.size() > 0) //If Person exist, add money to existing
                {
                    changes.get(searchPersonName(changes, name)).setCoinAmt(changes.get(searchPersonName(changes, name)).getCoinAmt() + money);
                    
                }
                else //if person do not exist yet create new
                {
                    changes.add(new Change(name, money, country.toUpperCase()));
                }

                System.out.println("Do you have more person to enter(Y/N):");
                tempInput = input.nextLine();
                //Check if input is Y/N. If Y repeat outer while loops Else break
                while(true)
                {
                    if (tempInput.equalsIgnoreCase("Y"))
                    {
                        tempInput = "";
                        break;
                    }
                    else if (tempInput.equalsIgnoreCase("N"))
                    {
                        flag = false;
                        break;
                    }
                    else 
                    {
                        System.out.println("Please indicate if you have more person to enter(Y/N):");
                        tempInput = input.nextLine();
                    }
                }
            }
        }

        flag = true;

        //Loops to display and run function
        while(flag)
        {
            tempInput = "";
            System.out.println("1. Enter a name and display change to be given for each denomination");
            System.out.println("2. Find the name with the smallest amount and display change to be given for each denomination");
            System.out.println("3. Find the name with the largest amount and display change to be given for each denomination");
            System.out.println("4. Calculate and display the total number of coins for each denomination, and the sum of these totals");
            System.out.println("5. Exit");

            System.out.println("Please enter a number from 1-5 to select a function");
            tempInput = input.nextLine();

            //Run different code depending on user inputs
            switch(tempInput)
            {
                case "1":
                    System.out.println("Enter a name to search");
                    String nameSearch = input.nextLine();

                    //Get Change Object's index in changes arraylist with same name
                    resultChangesIndex = searchPersonName(changes, nameSearch);
                    //Check if index is valid
                    if(checkValidIndex(resultChangesIndex) == false)
                    {
                        System.out.println("Person not found.");
                        break;
                    }
                    //Get string to display to user
                    resultString = buildStringChangeResult(changes.get(resultChangesIndex));
                    System.out.println(resultString);
                    tempInput = "";
                    break;
                case "2":
                    //Search for Change object's index in changes arraylist with least coin amount
                    resultChangesIndex = searchLeastCoin(changes);
                    //Get string to display to user
                    resultString = buildStringChangeResult(changes.get(resultChangesIndex));
                    System.out.println(resultString);
                    tempInput = "";
                    break;
                case "3":
                    //Search for Change object's index in changes arraylist with most coin amount
                    resultChangesIndex = searchMostCoin(changes);
                    //Get string to display to user
                    resultString = buildStringChangeResult(changes.get(resultChangesIndex));
                    System.out.println(resultString);
                    tempInput = "";
                    break;
                case "4":
                    //Get array of coin numbers for each denominations for all Change objects
                    int[] resultCoinNum = coinNumPerDenom(changes);
                    System.out.println("Change:");
                    //Loops through array to display the coin numbers for each denominations for all Change objects
                    for (int i = 0; i < changes.get(0).getCoinDenom().length; i++)
                    {
                        System.out.println(changes.get(0).getCoinDenom()[i] + " cents: " + resultCoinNum[i]);
                    }

                    int totalCoins = 0;
                    //Loops through array of coin number for each denominations for all Change objects and add them
                    for (int i = 0; i < resultCoinNum.length; i++)
                    {
                        totalCoins += resultCoinNum[i];
                    }
                    System.out.println("\nTotal coins: " + totalCoins);
                    tempInput = "";
                    break;
                case "5":
                    flag = false;
                    input.close();
                    break;
                default:
                    System.out.println("Please enter a number from 1-5 to select a function");
                    break;
            }

        }

    }

    //Display student Information
    //Precondition: None
    //Postcondition: Hardcoded string is printed out in terminal
    private static void studentInfo()
    {
        System.out.println("Name: Lim Wen Chao");
        System.out.println("Student Id: CT0360379/34368872");
        System.out.println("Tutor Name: Chong Siew Cheong");
    }

    // Loops through CoinDenoms Enum and display the list
    //Precondition: CoinDenoms Enum class has enum hardcoded for
    //Postcondition: Prints out all country and countryCode in terminal
    private static void displayCountry() {
        //Loops through enum and print out country name and country code
        for (CoinDenoms countryEnum : CoinDenoms.values()) {
            System.out.println(countryEnum.getCountryName() + ": " + countryEnum.getCountrycode());
        }
    }

    /** 
     * @param input
     * @return boolean
     */
    // Loops through CoinDenoms Enum and check if user input countryCode are found
    //Precondition: Any user input are accepted
    //Postcondition: returns true/false based on whether the user input is a country code found in CoinDenoms enum
    private static boolean checkValidCountry(String input) {
        //Loops through CoinDenoms enum and compare to input.
        for (CoinDenoms countryEnum : CoinDenoms.values()) {
            //Check if input has the same country code as the country in enum
            if (countryEnum.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
    }

    /** 
     * @param input
     * @return boolean
     */
    //Checks if user input for Coin Value is not null or blank and divisible by 5 and is Integer
    //Precondition: Any user input are accepted
    //Postcondition: returns true/false based on whether user input is an integer that is divisible by 5
    private static boolean checkIsValidCoinValue(String input) {
        //Check if input is blank or empty
        if (input.isBlank() == true || input.isEmpty() == true) {
            return false;
        }

        //Try to cast input to integer
        try {
            int value = Integer.parseInt(input);
            if ((value % 5) == 0 && value >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /** 
     * @param changes
     * @param personName
     * @return int
     */
    //Loops through Arraylist of Changes and search for the object with same person name. Returns the index value
    //Precondition: A arraylist of Change and String are provided
    //Postcondition: returns the index of Change instance stored in changes after searching for the Change instance with same personName. 
    //if no person Name are found after searching changes then -1 are returned
    private static int searchPersonName(ArrayList<Change> changes, String personName)
    {
        //Loops through all Change objects to find the object with the same person name as user input
        for (int i = 0; i < changes.size(); i++)
        {
            //Check if object person name is same as user input
            if(changes.get(i).getPersonName().equals(personName))
            {
                return i;
            }
        }
        return -1;
    }

    /** 
     * @param index
     * @return boolean
     */
    //If index given is less than 0 means Search Value given not found in array of Changes
    //Precondition: An integer representing index of an array/arraylist is provided
    //Postcondition: returns true if more than or equls 0 else returns false
    private static boolean checkValidIndex(int index)
    {
        if (index >= 0)
        {
            return true;
        }
        return false;
    }

    /** 
     * @param change
     * @return String
     */
    //Given a Change object will display the person Name, money and changes for each coin denomination
    //Precondition: A Change instance is provided
    //Postcondition: Returns a string with information on the Change instance's personName, coinAmt & coin number per denomination
    private static String buildStringChangeResult(Change change)
    {
        StringBuilder resultString = new StringBuilder();
        //Calculates the Coin change for person
        change.calCoinDenom();

        //Build string to display person name and coin amount
        resultString.append("Customer:");
        resultString.append("\n");
        resultString.append(change.getPersonName() + " " + change.getCoinAmt() + " cents\n");
        
        //Build string to display change per denomination for person
        resultString.append("Change:");
        resultString.append("\n");
        //Loop through Coin Change result array
        for (int i = 0; i < change.getCoinChange().size(); i++)
        {
            // Only display result if Coin numer more than 0
            if(change.getCoinChange().get(i) > 0)
            {
                resultString.append(change.getCoinDenom()[i] + " cents: " + change.getCoinChange().get(i));
                resultString.append("\n");
            }
        }

        return resultString.toString();
    }

    /** 
     * @param changes
     * @return int
     */
    //Loop through list of Changes and find the person with least coin value and return the index
    //Precondition: Given arraylist of Change instances
    //Postcondition: returns the index of the Change in arraylist that has the least coinAmt
    private static int searchLeastCoin(ArrayList<Change> changes)
    {
        int least = Integer.MAX_VALUE;
        int index = -1;

        //Loops through all Change object to get coin value amount and compare all to get the smallest value
        for (int i = 0; i < changes.size(); i++)
        {
            if (changes.get(i).getCoinAmt() < least)
            {
                least = changes.get(i).getCoinAmt();
                index = i;
            }
        }
        return index;
    }

    /** 
     * @param changes
     * @return int
     */
    //Loop through list of changes and fine the person with most coin value and return the index 
    //Precondition: Given arraylist of Change instances
    //Postcondition: returns the index of the Change in arraylist that has the most coinAmt
    private static int searchMostCoin(ArrayList<Change> changes)
    {
        int most = Integer.MIN_VALUE;
        int index = -1;

        //Loops through all Change object to get coin value amount. Then compare all value to get the largest value
        for (int i = 0; i < changes.size(); i++)
        {
            if (changes.get(i).getCoinAmt() > most)
            {
                most = changes.get(i).getCoinAmt();
                index = i;
            }
        }
        return index;
    }

    /** 
     * @param changes
     * @return int[]
     */
    //Loops through list of all Change instances, calculate the coin change in each denomination and returns an array with the numbers
    //Precondition: Given arraylist of Change instances
    //Postcondition: returns an array of integer tha represents the total number of coins per coin denomination for all Change instances in arraylist
    public static int[] coinNumPerDenom(ArrayList<Change> changes)
    {
        //Create new int array to store number of coins per denomination
        int[] totalCoinDenom = new int[changes.get(0).getCoinDenom().length];
        
        //Loops through all Change object to get the coin change and adding the number to totalCoinDenom
        for (int i = 0; i < changes.size(); i++)
        {
            changes.get(i).calCoinDenom();
            for(int x = 0; x < changes.get(i).getCoinChange().size(); x++)
            {
                totalCoinDenom[x] += changes.get(i).getCoinChange().get(x);
            }
        }
        return totalCoinDenom;
    }

    /** 
     * @param changes
     */
    //Adds hardcoded test data for Change instances into arraylist changes
    //Precondition: Hardcoded arrays of test names and coin amount must be present. Country is hardcoded to be Australia.
    //Postcondition: Create Change instances based on hardcoded array of test data and add them to arraylist changes
    public static void testData(ArrayList<Change> changes)
    {
        String testCountry = "AU";
        String[] testNames = {"Test1", "Test2", "Test3", "Test4", "Test5", "Test6", "Test7", "Test8", "Test9", "Test10"};
        int [] testMoney = {385, 145, 25, 200, 150, 75, 35, 250, 195, 105};

        //Loops through all test data and create new Change object then adding to changes arraylist
        for (int i = 0; i < testNames.length; i++)
        {
            changes.add(new Change(testNames[i], testMoney[i], testCountry));
        }
    }

}