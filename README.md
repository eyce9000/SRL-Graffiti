[adt-instructions]:https://developers.google.com/eclipse/docs/getting_started
[gae-instructions]:http://developer.android.com/sdk/installing/installing-adt.html

Graffiti Server a is backend service for the Graffiti TAMU CHI 2012 Fall project.

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


Common Setup Issues
-------
_____________
What do I need to make the project work?
------------
1. Install Eclipse
2. Install the Google App Engine plugin for Eclipse ([instructions][adt-instructions]) 
3. Install the Android Development Toolkit (ADT) for eclipse ([instructions][gae-instructions])
4. Clone this project into your Eclipse workspace directory.
5. Import the three Eclipse projects project into your Eclipse workspace.


<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/import1.png" />

<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/import2.png" />

<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/import3.png" />

________________
How do I start the development server?
-------------
1. Find the Graffiti Server project in your Eclipse workspace.
2. Right click on the project.
3. Go to Run As... -> Web Application

<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/launch-server.png" />

__________________
Eclipse says I am missing libraries for Graffiti Server! How do I fix it?
-----------------
1. Right click on the project and go to Properties.
2. Go to the Google project property menu.
3. Change the App Engine SDK from "Use Default SDK..." to "Use Specific SDK" and pick the latest SDK.
<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/fix-references-a.png" />
<img src="https://raw.github.com/eyce9000/SRL-Graffiti/master/instructions/images/fix-references-b.png" />
4. Click OK. This forces the Google plugin to replace the libraries, getting rid of the errors. If you want you can set the App Engine SDK back to "Used Default SDK" but it is not necessary.
