// ICT 167 Assignment 1
// Lim Wen Chao
// 14 Oct 2021
// CoinDenoms.java
// Stores hardcoded list of country coin denominations. More country could be added without changing Client or Change

public enum CoinDenoms {
    AU("Australia", "AU", new int[] {200,100,50,20,10,5}),
    SG("Singapore", "SG", new int[] {100,50,20,10,5});

    private String countryName, countryCode;
    private int[] coinCountry;

    //Constructor
    private CoinDenoms(String countryName, String countryCode, int[] coinCountry)
    {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.coinCountry = coinCountry;
    }

    //Getters
    public String getCountryName()
    {
        return countryName;
    }
    public String getCountrycode()
    {
        return countryCode;
    }
    public int[] getCoinCountry()
    {
        return coinCountry;
    }
}
