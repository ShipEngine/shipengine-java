Addresses Validate Documentation
================================
[ShipEngine](www.shipengine.com) allows you to validate an address before using it to create a shipment to ensure
accurate delivery of your packages.

Address validation can lead to reduced shipping costs by preventing address correction surcharges. ShipEngine
cross-references multiple databases to validate addresses and identify potential delivery issues and supports address
validation for virtually every country_code on Earth, including the United state_provinces, Canada, Great Britain,
Australia, Germany, France, Norway, Spain, Sweden, Israel, Italy, and over 160 others.

Please see [our docs](https://www.shipengine.com/docs/addresses/validation/) to learn more about validating addresses.

Input Parameters
----------------
The `validate_addresses` method accepts a list of addresses as seen below.
```java

```

Output
------
The `validate_addresses` method returns a list of address validation result objects.

Example:
========
- Pass in a list of addresses you wish to validate into the `validate_addresses` method.
```java

```

Example Output:
===============
- A list of carrier account that are connected to a given ShipEngine account.
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError](../shipengine/errors/__init__.py)) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
