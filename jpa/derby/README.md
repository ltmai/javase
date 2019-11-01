## Using JPA in Java SE application

This example shows how JPA may be used in Java SE applications.
- Entity class Employee
- persistence.xml

## Apache Derby security settings

With the following security settings, users must be authenticated to access database. The users are
defined and authorization are specified. In this case user John has full access but Jane has only
read-ony access.

```sql
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'true')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.authentication.provider', 'BUILTIN')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.John', 'Doe')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.Jane', 'Doe')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode', 'readOnlyAccess') /* fullAccess, readOnlyAccess, noAccess */
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'John')
```

This can be verified via Apache Derby ij:

```batch
┌ C:\bin\java_tools\db-derby-10.14.2.0-bin\bin
└> ij
IJ-Version 10.14
ij> connect 'jdbc:derby:\dev\code\java\jpa\derby\JavaDbEmbeddedTest';
ERROR 08004: Verbindung konnte nicht authentifiziert werden. Grund: Ungültige Authentifizierung..

ij> connect 'jdbc:derby:\dev\code\java\jpa\derby\JavaDbEmbeddedTest' user 'John' password 'Doe';
ij> select * from employee;
ID                  |BIRTHDAY  |FIRST_NAME                      |LAST_NAME
-------------------------------------------------------------------------------------------------
1                   |1975-04-30|John                            |Doe
2                   |1976-10-22|Jane                            |Doe
3                   |1989-10-03|Mickey                          |Mouse

3 Zeilen ausgewählt
ij> update employee set last_name='Doe' where id=3;
Eine Zeile eingefügt/aktualisiert/gelöscht

ij> connect 'jdbc:derby:\dev\code\java\jpa\derby\JavaDbEmbeddedTest' user 'Jane' password 'Doe';
ij(CONNECTION1)> select * from employee;
ERROR 42Y07: Schema 'JANE' ist nicht vorhanden.
ij(CONNECTION1)> select * from john.employee;
ID                  |BIRTHDAY  |FIRST_NAME                      |LAST_NAME
-------------------------------------------------------------------------------------------------
1                   |1975-04-30|John                            |Doe
2                   |1976-10-22|Jane                            |Doe
3                   |1989-10-03|Mickey                          |Doe

3 Zeilen ausgewählt
ij(CONNECTION1)> update john.employee set last_name='Doe' where id=3;
ERROR 25502: Für eine Verbindung, einen Benutzer oder eine Datenbank mit Lesezugriff sind Änderungen von SQL-Daten nicht zulässig.
ij(CONNECTION1)>
```