Validate Addresses
==================
[ShipEngine](www.shipengine.com) allows you to validate an array of addresses before using it to create a shipment to
ensure accurate delivery of your packages. Please see [our docs](https://www.shipengine.com/docs/addresses/validation/)
to learn more about validating addresses.

Input Parameters
----------------

The `validateAddresses` method accepts an array of addresses.

Output
------
The `validateAddresses` method returns an array of address validation result objects.

Example
-------

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
    public static void main() {
        ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");

        Map<String, String> stubAddress = Map.of(
                "name", "ShipEngine",
                "company", "Auctane",
                "phone", "1-123-456-7891",
                "address_line1", "3800 N Lamar Blvd",
                "address_line2", "ste 220",
                "city_locality", "Austin",
                "state_province", "TX",
                "postal_code", "78756",
                "country_code", "US",
                "address_residential_indicator", "unknown"
        );
        
        List<Map<String, String>> unvalidatedAddress = List.of(stubAddress);

        List<Map<String, String>> result = shipengine.validateAddresses(unvalidatedAddress);
        System.out.println("result = " + result);
    }
} 
```

Example Output
--------------

### Successful Address Validation

```java
[{matched_address={name=SHIPENGINE,phone=1-123-456-7891,address_line1=3800N LAMAR BLVD STE 220,address_line2=,city_locality=AUSTIN,state_province=TX,postal_code=78756-0003,country_code=US,address_residential_indicator=no},original_address={name=ShipEngine,phone=1-123-456-7891,address_line1=3800N Lamar Blvd,address_line2=ste 220,city_locality=Austin,state_province=TX,postal_code=78756,country_code=US,address_residential_indicator=unknown},messages=[],status=verified}]
```

### *JSON Output for reference:*

```json5
[
  {
    "status": "verified",
    "original_address": {
      "name": "ShipEngine",
      "phone": "1-123-456-7891",
      "company_name": null,
      "address_line1": "3800 N Lamar Blvd",
      "address_line2": "ste 220",
      "address_line3": null,
      "city_locality": "Austin",
      "state_province": "TX",
      "postal_code": "78756",
      "country_code": "US",
      "address_residential_indicator": "unknown"
    },
    "matched_address": {
      "name": "SHIPENGINE",
      "phone": "1-123-456-7891",
      "company_name": null,
      "address_line1": "3800 N LAMAR BLVD STE 220",
      "address_line2": "",
      "address_line3": null,
      "city_locality": "AUSTIN",
      "state_province": "TX",
      "postal_code": "78756-0003",
      "country_code": "US",
      "address_residential_indicator": "no"
    },
    "messages": []
  }
]
```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.
