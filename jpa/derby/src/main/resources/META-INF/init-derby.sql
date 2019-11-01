CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication', 'true')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.authentication.provider', 'BUILTIN')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.John', 'Doe')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.Jane', 'Doe')
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.defaultConnectionMode', 'readOnlyAccess') /* fullAccess, readOnlyAccess, noAccess */
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.fullAccessUsers', 'John')

INSERT INTO employee (FIRST_NAME, LAST_NAME, BIRTHDAY) VALUES ('John', 'Doe', '1975-04-30')
INSERT INTO employee (FIRST_NAME, LAST_NAME, BIRTHDAY) VALUES ('Jane', 'Doe', '1976-10-22')