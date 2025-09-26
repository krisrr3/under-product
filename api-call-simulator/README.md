This call simulator will create API calls from channels. It's based on Spring boot.

It generates 200 API calls from the Products and creates entries in a JSON file of the API call traces. Under Product parses the JSON file to create a representation based on the spread of the data.

The Products in this case are EVs and more specifically EV Model 1, EV Model 2 and EV model 3.

The APIs underneath are Pricing API, Customer API, Payments API and Delivery API. 

Each API also is associated with a specific funding model, which could be 
- Equal Share
- Share by Rate
- Paid by Most Profitable