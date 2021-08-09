[![ShipEngine](https://shipengine.github.io/img/shipengine-logo-wide.png)](https://www.shipengine.com/)

ShipEngine Java SDK
=====================================================
### The official ShipEngine Java SDK

Quick Start
--------------------------
TODO

Methods
-------------------------------
* [`createLabelFromRate`](./docs/create-label-from-rate.md) - When retrieving rates for shipments using the `getRatesWithShipmentDetails` method, the returned information contains a `rateId` property that can be used to purchase a label without having to refill in the shipment information repeatedly.
* [`createLabelFromShipmentDetails`](./docs/create-label-from-shipment-details.md) - Purchase and print a label for shipment.
* [`getRatesWithShipmentDetails`](./docs/get-rates-with-shipment-details.md) - Given some shipment details and rate options, this method returns a list of rate quotes.
* [`listCarriers`](./docs/list-carriers.md) - Returns a list of carrier accounts that have been connected through
the [ShipEngine dashboard](https://www.shipengine.com/docs/carriers/setup/).
* [`trackUsingLabelId`](./docs/track-using-label-id.md) - Track a package by its associated label ID.
* [`trackUsingCarrierCodeAndTrackingNumber`](./docs/track-using-carrier-code-and-tracking-number.md) - Track a package for a given carrier and tracking number.
* [`validateAddresses`](./docs/validate-addresses.md) - Indicates whether the provided addresses are valid. If the addresses are valid, the method returns a normalized version based on the standards of the country in which the address resides. If an address cannot be normalized, an error is returned.
* [`voidLabelWithLabelId`](./docs/void-label-with-label-id.md) - Void a label with its Label ID.

Contributing
--------------------------
Contributions, enhancements, and bug-fixes are welcome!  [Open an issue](https://github.com/ShipEngine/shipengine-java/issues) on GitHub and [submit a pull request](https://github.com/ShipEngine/shipengine-java/pulls).

#### Building
To build the project locally on your computer:

1. __Clone this repo__<br>
`git clone https://github.com/ShipEngine/shipengine-java.git`

2. __Install dependencies__<br>
`mvn install`

3. __Build the code__<br>
`mvn package`

4. __Lint the code__<br>
`mvn checkstyle:checkstyle`

5. __Run the tests__<br>
`mvn test`

VS Code Setup
-------------------------
Install the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) to help write, test and debug the application, and install [Checkstyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle) to enable code formatting.

Committing
-------------------------
This project adheres to the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) specification.
