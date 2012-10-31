Graffiti Server
--------

Graffiti Servier is backend service for the Graffiti TAMU CHI 2012 Fall project.

There are 3 distinct projects contained here:
1. Graffiti Client:
--------------
This project provides java client code for accessing the json API of the Graffiti Server. 
This includes support for logging in, uploading images, uploading positioned sketches and retrieving positioned sketches and images.

2. Graffiti Android Client:
--------------
The Graffiti Android Client provides code wrappers for the Graffiti Client to make it more Android friendly, focusing on making all network calls non-blocking, asynchronous calls.

3. Graffiti Server:
--------------
The Graffiti Server is a Google App Engine application that provides a JSON RPC API for uploading images and sketches with geospatial information, and retrieving the images and sketches by location and user.