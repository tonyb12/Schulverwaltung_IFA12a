# Schulverwaltung_IFA12a Entwickler Einrichtung

1. Docker Desktop installieren.
2. docker-compose up ausführen
    - Sollte es zu Problemen hierbau kommen, dann einfach die Ports unter dem Reiter db in der docker-compose.yml abändern. Nur die Zahl vor dem Doppelpunkt
    - Wenn die Ports abgeändert sind, dann auch in der Exposed.kt änderungen an der Url durchführen
3. SQL-Dump einspielen über PhPMyAdmin.
    - Anmeldedaten für den User sind root für UserName und Password
4. Wenn alle Container erfolgreich gestartet sind, dann in die Applikation.kt wechseln und dort die aktuelle Datei ausführen.

Anmeldedaten für die Website: 
Secretary: 
   UserName: Secretary
   Password: Secretary

Students:
   UserName: NicHam20040522
   Password: 20040522

   UserName: ToaBui19981212
   Password: 19981212
   
   UserName: ChrZah19940930
   Password: 19940930
   
   UserName: FabSch20000616
   Password: 20000616