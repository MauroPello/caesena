Per poter eseguire l'applicativo è necessario avere sulla propria macchina una versione di JDK pari o superiore alla 17.
Per quanto riguarda il database è invece necessario avere il DBMS MySql.

Di default l'applicazione si connette ad un database di nome "caesena" locato sulla propria macchina e accedibile tramite la porta 3306.
L'username e la password di default sono entrambe "caesena".

Per poter modificare questa configurazione è sufficiente cambiare le seguenti linee:
    properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/caesena");
    properties.setProperty("hibernate.connection.username", "caesena");
    properties.setProperty("hibernate.connection.password", "caesena");
Queste si trovano nel metodo costruttore (linee 75-77) nel file ControllerImpl.java dentro la cartella src/main/java/it/unibo/caesena/controller.

Nel caso si stia usando una macchina con sistema operativo Windows si esegua il seguente comando nel prompt per eseguire l'applicativo:
    gradlew.bat run
Nel caso si stia usando una macchina UNIX si esegua invece:
    ./gradlew run
