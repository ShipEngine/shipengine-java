List Carriers Documentation
===========================
[ShipEngine](www.shipengine.com) allows you to connect
your own carrier accounts through the ShipEngine [dashboard](https://www.shipengine.com/docs/carriers/setup/). You can list all the carrier accounts you have connected with the `list_carriers` method.

To learn more about carrier accounts please see [our docs](https://www.shipengine.com/docs/reference/list-carriers/).

Output
------
The `list_carriers` method returns a list of carrier accounts connected to a given ShipEngine account.

Example
=======
```java

```

Example Output
==============
A list of carrier accounts connected to a given ShipEngine account.
```java

```

Exceptions
==========

- This method will only throw an exception that is an instance/extension of
  ([ShipEngineError](../shipengine/errors/__init__.py)) if there is a problem if a problem occurs, such as a network
  error or an error response from the API.
