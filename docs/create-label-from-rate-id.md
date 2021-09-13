Create Label From Rate ID Documentation
=======================================
When retrieving rates for shipments using the `get_rates_from_shipment` method, the returned information contains a `rate_id` property that can be used to purchase a label without having to refill in the shipment information repeatedly.

Please see [our docs](https://www.shipengine.com/docs/labels/create-from-rate/) to learn more about creating shipping labels from rates.

Input Parameters
----------------
The `create_label_from_rate_id` method accepts a valid `rate_id` and a dictionary of label params that
will dictate the label display and level of verification.

```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
  public static void main() {
    ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");
    
    String labelId = "se-1234";
    
    Map<String, Object> labelParams = new HashMap<>() {{
      put("label_layout", "4x6");
      put("label_format", "pdf");
      put("label_download_type", "url");
    }};
    
    Map<String, String> result = shipengine.createLabelFromRateId(labelId, labelParams);
    System.out.println("result = " + result);
  }
} 
```

Output
------
The `create_label_from_rate_id` method returns a shipping label that corresponds to the
shipping and rate details encapsulated in the `rate_id` passed in.

Example:
========

```java

```

Example Output:
===============
- Successful `create_label_from_rate_id()` result.
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError](../shipengine/errors/__init__.py)) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
