
# Travel Agency - Desktop App
Final project developed in team - 1º DAW

"Sol Experience" consists in creating packs and experiences for every user who wants to travel to Málaga.

It has its own admin menu, logging & role system and also, it creates a PDF ticket when a pack is purchased.

## Technologies
* Java & JavaFX
* Scene Builder
* CSS
* Apache NetBeans IDE

## Features
* General
	* Logging & role system.
	* It retrieves experiences from database and show them dynamically.
	* You can filter experiences by price, stars, etc.
	* Modern info panels to report errors, information, etc.
	* Creation of a PDF Ticket when a pack is purchased.
* User role
	* Create a pack
	* Modify the pack
		* Insert/read/modify/delete pack experiences.
	* Purchase the pack.
* Admin role
	* Create new experiences.
	* Modify existing experiences.
	* Delete experiences.

## Libraries
* ControlsFX - v11.1.0
* JFoenix - v9.0.10
* Commons Codec - v1.15
* ITextPDF - v5.4.0
* MySQL-Connector - v8.0.24
* JavaFX
	* SDK - v17.0.1
	* Javadoc - v17.0.1

## Java Version
(Java Plaform) JDK - v16

## Setup
### JavaFX SDK & Javadoc
* Create a new library in the project and set all .jar files from "javafx-sdk-17.0.1/lib" into "Classpath" section.
* Without leaving the library, set the directory "javafx-17.0.1-javadoc" itself into the "Javadoc" section.

### VM Options ("Run" Properties Section)
Type:
````
--module-path "C:\Users\Equipo\Desktop\java desktop app\javafx-sdk-17.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.web --add-exports javafx.base/com.sun.javafx.event=ALL-UNNAMED
````
Being "C:\Users\Equipo\Desktop\java desktop app\javafx-sdk-17.0.1\lib" the path to change.

### Database Connection Options
* Go to the "proyectoFinal.vista.login.register" package and open the "registerController.java" file.
* Change the necessary DB Options on the Initialize method.

## Resources
* All the project libraries (./src/librerias/).
* .sql file with the whole database structrure (./src/BBDD/).
* PDF Ticket files path (./src/proyectoFinal/tickets/).


## Authors

  

Linkedin: [http://linkedin.com/in/ivan-torres-garcia](http://linkedin.com/in/ivan-torres-garcia)

Linkedin: [http://linkedin.com/in/juan-carlos-soben-rodriguez](https://www.linkedin.com/in/juan-carlos-soben-rodriguez-4a7b8513b)
