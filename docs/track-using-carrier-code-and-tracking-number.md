Track Using Carrier Code and Tracking Number
============================================
[ShipEngine](www.shipengine.com) allows you to track a package for a given carrier and tracking number. Please 
see [our docs](https://www.shipengine.com/docs/tracking/) to learn more about tracking shipments.

Input Parameters
----------------

The `trackUsingCarrierCodeAndTrackingNumber` method requires the carrier code and tracking number of the shipment 
being tracked.

Output
------
The `trackUsingCarrierCodeAndTrackingNumber` method returns tracking information associated with the shipment for the 
carrier code and tracking number.


Example
=======
```java

```

Example Output
--------------

### Tracking Result
```java

```

### *JSON Output for reference:*

```json5

```

Exceptions
----------

- This method will only throw an exception that is an instance/extension of **ShipEngineException** if there is a
  problem if a problem occurs, such as a network error or an error response from the API.