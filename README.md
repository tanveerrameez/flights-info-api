**Explanation of implementation**


BusyFlightDetailsController is a rest controller that defines the REST api for the Busy Flights search.

BusyFlightDetailsServiceImpl is a service implementation that aggregates the search result from various suppliers.

A Supplier class encapsulates the information about a supplier. The supplier instances are created as Spring beans and encapsulates the converter implementation which converts from supplier specific request/response to Busy flights request/response . SupplierLoaderServiceImpl provides BusyFlightDetailsServiceImpl the list of suppliers.

A GetFlightsFromSupplierTask is a Callable implementation that obtains the flight information for a supplier using RestTemplate and converts the respective response to BusyFlightsResponse. The BusyFlightDetailsServiceImpl manages the invocation of the GetFlightsFromSupplierTask for each supplier using ExecutorService for parallel processing and aggregating the result.

AppConfig class contains the Spring beans

Validation of data is done using Java Validation API. Various Utility classes were written to provide common functions.

I have used Lombok, jackson, mockito libraries. 

Limitations:
1. Tests are limited and mainly confined to BusyFlightDetailsServiceTest and BusyFlightDetailsController and a few other classes. 
2. The test cases are limited and checking only certain happy and edge conditons.
3. Validation is not extensive.


Assumptions: 
1. Crazy Air and ToughJet Api are provided locally with api returning dummy data. the data does not take into account the input BusyFlightRequest data. In production, this would be external apis that return data based on the input BusyFlightRequest data.



--------------------------------------------------------------



**Travix - Problem to be solved**

**Background:**

BusyFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.


**What is required:**

Use this GitHub repository as a base to implement the Busy Flights service that should produce an aggregated result from both CrazyAir and ToughJet.
The result should be a JSON response which contains a list of flights ordered by fare which has the following attributes:

**Busy Flights API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| numberOfPassengers | Maximum 4 passengers |

**Response**

| Name | Description |
| ------ | ------ |
| airline | Name of Airline |
| supplier | Eg: CrazyAir or ToughJet |
| fare | Total price rounded to 2 decimals |
| departureAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| destinationAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_DATE_TIME format |
| arrivalDate | ISO_DATE_TIME format |

The service should connect to the both the suppliers using HTTP.

**CrazyAir API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| passengerCount | Number of passengers |

**Response**


| Name | Description |
| ------ | ------ |
| airline | Name of the airline |
| price | Total price |
| cabinclass | E for Economy and B for Business |
| departureAirportCode | Eg: LHR |
| destinationAirportCode | Eg: LHR |
| departureDate | ISO_LOCAL_DATE_TIME format |
| arrivalDate | ISO_LOCAL_DATE_TIME format |

**ToughJet API**

**Request**

| Name | Description |
| ------ | ------ |
| from | 3 letter IATA code(eg. LHR, AMS) |
| to | 3 letter IATA code(eg. LHR, AMS) |
| outboundDate |ISO_LOCAL_DATE format |
| inboundDate | ISO_LOCAL_DATE format |
| numberOfAdults | Number of passengers |

**Response**

| Name | Description |
| ------ | ------ |
| carrier | Name of the Airline |
| basePrice | Price without tax(doesn't include discount) |
| tax | Tax which needs to be charged along with the price |
| discount | Discount which needs to be applied on the price(in percentage) |
| departureAirportName | 3 letter IATA code(eg. LHR, AMS) |
| arrivalAirportName | 3 letter IATA code(eg. LHR, AMS) |
| outboundDateTime | ISO_INSTANT format |
| inboundDateTime | ISO_INSTANT format |

**What you need to provide:**

- A solution that meets the above requirements.
- The implementation should be made as close to 'production ready' as possible within the time constraints.

It is fine to change any of the supplied application code, if you choose to do so please add comments to indicate what has changed and why.

**Note**

Please clone this project then create your own repository from it. Do not fork/branch this project when creating your solution as it will be visible to other applicants.
