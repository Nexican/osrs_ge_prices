# OSRS GE PRICES
Gets prices from the osrs ge website <br>
Has a local caching system

## Usage
```java
NexGrandExchangePrice.price(ITEM_ID);
```

## Example
Code:
```java
public static void main(String[] args) {
  int air_rune_price = NexGrandExchangePrice.price(556);
  System.out.println("Air rune price: " + air_rune_price);
  air_rune_price = NexGrandExchangePrice.price(556);
  System.out.println("Air rune cache price: " + air_rune_price);
}
```
Output:
>[NexGrandExchangePrice] Added 556 to local cache with price: 4 <br>
Air rune price: 4 <br>
[NexGrandExchangePrice] Found 556 in local cache with price: 4 <br>
Air rune cache price: 4 <br>
