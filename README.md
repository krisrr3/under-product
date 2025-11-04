# Under Product

Under Product is a capability that solves the transparency issue that exists between Product and APIs, and makes it clear to Product teams why amd how they are funding APIs, stacks and tools that the business depends on. Even though this will require an upfront conversation during design time to business leaders, the effort will payoff at each financial cycle.

The outcome is for API longevity to be improved.

# Domain

Using DDD to spell out the Ubiquitous Language,

> A Channel is used to distribute a Product to Customers. The channel relies on APIs for various functionalities to be processed for the Customer. Some of the APIs also rely on other APIs to enable certain functionalities to be fulfilled.

> At design time, the Pricing Strategy of an API will be chosen, and it will have the rules to calculate how the Total Cost of Ownership.

> The first type of Pricing Strategy is called Most Profitable Pays (MPP), which essentially means the Product making the most profit pays for the API.

> The 2nd type of Pricing Strategy is called Equal Share(ES), which means that it is shared by the number of Products needing it equally.

> The 3rd type of Pricing Strategy is called Pay by Rate (PbR), which means that the cost of the API is shared across Products based on the number of calls made to an API by particular Products.

# How it works

## API Headers for traceability

From an Architecture standpoint, communicating that all Applications and underlying layers will use headers to capture the product and the previous layer that called an API, will support the building of a near real-time model that would drive the analytics and reporting that prevent organisational friction during budgeting hour.

This can be rolled out through a script to GitHub repos where applications and APIs are hosted.

```json
{
    "OriginatingProduct": "Employee Retention",
    "Channel": "Web",
    "CallingLayer": "Salary API",
    "CalledLayer": "Payments API"
}
```

## Data Sources

Under-Product reads the API header logs and uses the Api Metadata, which could be stored in an API Catalogue or an architecture repository, to contruct a summary of 
- API usage by Product
- API cost allocation per API


# Build and deploy

The following commands can be used to build and deploy

        cd ./under-product-engine
        mvn package
        eb deploy



