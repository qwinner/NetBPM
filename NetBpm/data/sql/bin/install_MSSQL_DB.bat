@echo off
set sqluser=nbpm
set sqlpassword=nbpm

REM isql docu -> http://msdn.microsoft.com/library/default.asp?url=/library/en-us/coprompt/cp_isql_8r39.asp

isql -U%sqluser% -P%sqlpassword% -m1 -n -h-1 -i ..\CreateSqlServer.sql >> DbStatus.log
IF NOT %ERRORLEVEL%==0 goto failure 
echo ... done
echo.

isql -U%sqluser% -P%sqlpassword% -m1 -n -h-1 -i ..\Organisation.sql >> DbStatus.log
IF NOT %ERRORLEVEL%==0 goto failure 
echo ... done
echo.

goto exitblock

:failure
echo ERR: Database configuration script FAILED! >> DbStatus.log
echo.
echo Database creation FAILED!
goto exitblock

:exitblock
echo.
echo End of Database Configuration Script
echo AUDIT: End of Database configuration script! >> DbStatus.log
