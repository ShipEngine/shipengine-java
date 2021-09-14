Void Label With Label Id
================================
[ShipEngine](www.shipengine.com) allows you to attempt to void a previously purchased label. Please see
[our docs](https://www.shipengine.com/docs/labels/voiding/) to learn more about voiding a label.

Input Parameters
----------------

The `voidLabelWithLabelId` method accepts a string that contains the `labelId` that is being voided.

Output
------
The `voidLabelWithLabelId` method returns an object that indicates the status of the void label request.

Example
-------
```java
import com.shipengine.ShipEngine;

public class ShipEngineDemo {
    public static void main() {
        ShipEngine shipengine = new ShipEngine("<YOUR_API_KEY_HERE>");
        Map<String, String> result = shipengine.voidLabelWithLabelId("se-1234");
        System.out.println("result = " + result);
    }
} 
```

Example Output
--------------

### Successful Address Validation
```java
{approved=true, message=This label has been voided.}
```

### *JSON Output for reference:*

```json5
{
  "approved": true,
  "message": "This label has been voided."
}
```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.