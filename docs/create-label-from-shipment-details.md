Create Label From Shipment Details Documentation
================================================
When retrieving rates for shipments using the `get_rates_from_shipment` method, the returned information contains a `rate_id` property that can be used to purchase a label without having to refill in the shipment information repeatedly.

Please see [our docs](https://www.shipengine.com/docs/labels/create-from-rate/) to learn more about creating shipping labels from rates.

Input Parameters
----------------
The `create_label_from_rate` method accepts a valid `rate_id` and a dictionary of label params that
will dictate the label display and level of verification.
```java

```

Output
------
The `create_label_from_shipment` method returns a shipping label that corresponds to
the shipping details passed in.

Example:
========
```java

```

Example Output:
===============
- Successful `create_label_from_shipment()` result that contains label data that corresponds to the shipment details provided.
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError](../shipengine/errors/__init__.py)) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
