# Key Features

## Link Apis to Products

Ability to link APIs to their Products in a tabular and visual graph format.

## API Headers repository
The approach to store the API Headers data is preferred to be a NoSQL database which can scale easily. 

Desicion to be documented.

## Roll out API Header changes

Once the headers are finalised, rollout changes to APIs in their various forms to now add the API headers for every request.

There are multiple use cases that fit the different situations for enterprises and they are described below.

### Automated 

#### Using GitHub App

Use the [Under-Product GitHub App](https://docs.github.com/en/apps/creating-github-apps/about-creating-github-apps/about-creating-github-apps?versionId=free-pro-team%40latest&productId=copilot&restPage=tutorials%2Ccopilot-chat-cookbook%2Cdocument-code) for details on how to automatically update API headers.

#### Legacy APIs

Use the python script that can update APIs using SOAP to add the new headers.

### Manual
For certain situations, these headers would need conversations with external parties such as external suppliers and SaaS before they can be applied.

Here are the specs that are required:

#### Required API Headers

Below are the required headers for API requests:

| Header Name         | Description                        | Example Value           |
|---------------------|------------------------------------|------------------------|
| `originatingProduct`      | Product linked to the API Call. | `EV1` |
| `calledFrom`     | APP or API where the current API is being called from.    | `"calledFrom": { "id": "orders-api", "name": "Orders API", "type": "API" }` |
| `calledLayer`      | API that is being called.         | `payments-api`     |


## More feature to be added



