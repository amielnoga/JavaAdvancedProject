# Adopet Web Application
This is a final project for the Open University CS course **20503 - Advanced Programming in Java** during **semester 2025A**.

## Contributors
- Eitan Kot
- Nogah Amiel
- Yuval Ofir

##  Installation & Setup

### Prerequisites
- JDK
- Maven
- SQLite

### Steps to Run Locally
#### Configure System Environment Variables ####
  Navigate to System --> Enviorment Variables:  
  Add 'JAVA_HOME' var and set the value to your JDK installion path, e.g "C:\Program Files\Java\jdk1.8.0_202"  
  Add 'Path' var and and set the value to your Maven bin folder path, e.g "C:\Program Files\Apache\Maven\apache-maven-3.9.9\bin"
#### Clone the Repository ####
   ```sh
   git clone https://github.com/amielnoga/JavaAdvencedProject.git
   cd JavaAdvencedProject
 ```
#### Build the Project ####
```sh
mvn clean install
```
#### Link the DB ####
Run ServerOperations.main() on src/main/java/BackEnd/ServerOperations.java 

#### Run the Application ####
  Click on Run 'AdopetApplication'
#### Access the Web Application ####
  On the browser navigate to http://localhost:8080/home.xhtml
  
  Recommended browser: Google Chrome (the system has been tested on Chrome and this is the browser we officially
  support).
