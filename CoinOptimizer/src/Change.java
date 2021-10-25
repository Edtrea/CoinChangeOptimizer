// ICT 167 Assignment 1
// Lim Wen Chao
// 14 Oct 2021
// Change.java
// The Object Change. Stores Person name, coin amount, Coin denomination and coin change per denomination

import java.util.ArrayList;

public class Change {
    private String personName;
    private int coinAmt;
    private ArrayList<Integer> coinChange = new ArrayList<Integer>();
    private int[] coinDenom;

    //Constructors
    public Change()
    {
        personName = "No_Name_Input";
        coinAmt = 0;
        setCoinDenom("AU"); //Default will be AU
    }
    //Precondition: Requires a String for name and country and integer for value
    //Postcondition: New Change object is created and instance variable are set based on provided values 
    public Change(String name, int value, String country)
    {
        personName = name;
        coinAmt = value;
        setCoinDenom(country);
    }

    //Getters
    /** 
     * @return String
     */
    public String getPersonName()
    {
        return personName;
    }
    /** 
     * @return int
     */
    public int getCoinAmt()
    {
        return coinAmt;
    }
    /** 
     * @return int[]
     */
    public int[] getCoinDenom()
    {
        return coinDenom;
    }
    /** 
     * @return ArrayList<Integer>
     */
    public ArrayList<Integer> getCoinChange()
    {
        return coinChange;
    }

    //Setter
    /** 
     * @param input
     */
    public void setPersonName(String input)
    {
        personName = input;
    }
    /** 
     * @param input
     */
    public void setCoinAmt(int input)
    {
        coinAmt = input;
    }
    /** 
     * @param country
     */
    //Get coin Denomination by country
    //Precondition: Country is found in Enum
    //Postcondition: Sets coinDenom as the same array value found in CoinDenoms for that country
    private void setCoinDenom(String country)
    {
        coinDenom = CoinDenoms.valueOf(country).getCoinCountry();
    }

    //Run to calculates the number of coin changes needed for each denominations
    //Precondition: No precondition
    //Postcondition: Calculates and stores the number of coins for each coin denominations required for instance's coin amount in coinChange array
    public void calCoinDenom()
    {
        int moneyLeft = coinAmt, numOfCoin = 0;
        coinChange.clear();
        for (int i = 0; i < coinDenom.length; i++)
        {
            if (moneyLeft >= coinDenom[i])
            {
                numOfCoin = moneyLeft/coinDenom[i];
                moneyLeft -= numOfCoin*coinDenom[i];
                coinChange.add(numOfCoin);
                numOfCoin = 0;
            }
            else
            {
                coinChange.add(0);
            }
        }
    }
}
