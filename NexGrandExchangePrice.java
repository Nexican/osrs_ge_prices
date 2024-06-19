import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NexGrandExchangePrice {

    private static final String OSRS_URL = "https://secure.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=";
    private static final int BILLION = 1_000_000_000, MILLION = 1_000_000, THOUSAND = 1_000;
    private static HashMap<Integer, Integer> cache = new HashMap<>();

    public static int price(final int id) {
        return cache.computeIfAbsent(id, key -> {
            int price = getPrice(key);
            System.out.println("[NexGrandExchangePrice] Added " + key + " to local cache with value: " + price);
            return price;
        });
    }

    public static void resetCache() {
        cache = new HashMap<>();
    }

    private static int getPrice(final int id) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(OSRS_URL + id).openStream()))) {
            String raw = reader.readLine();
            String priceString = extractPrice(raw);
            return parsePrice(priceString);
        } catch (Exception e) {
            System.out.println("[NexGrandExchangePrice] ## ! ## Error fetching price for id: " + id);
        }
        return 1;
    }

    private static String extractPrice(String input) throws Exception {
        Pattern pattern = Pattern.compile("\"price\":\\s*\"?([\\d,.]+[kmb]?)\"?");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).replace(",", "");
        } else {
            System.out.println("[NexGrandExchangePrice] ## ! ## Price not found in the response");
            return null;
        }
    }

    private static int parsePrice(String input) {
        if (input.endsWith("b") || input.endsWith("m") || input.endsWith("k")) {
            double multiplier = input.endsWith("b") ? BILLION : (input.endsWith("m") ? MILLION : THOUSAND);
            return (int) (Double.parseDouble(input.substring(0, input.length() - 1)) * multiplier);
        } else {
            return Integer.parseInt(input);
        }
    }
}
