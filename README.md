This is a simple Android Client for consuming Spring Data REST services.
The services offer CRUD functionality for single entity (Users)
When implementing this client I had two options:
1. Use Spring Android which offers automation of several tasks (response parsing, mapping, etc.)
2. Use a standard HTTP REST mechanism, such as HttpURLConnection services invocation and then using JSON libraries to parse the response.

Went for #2 eventually for more control.
Please note that the response is returned in HATEOAS/HAL format, so a little cleaning of the response was required before deserializing it into JSON
