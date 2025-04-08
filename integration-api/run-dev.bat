@echo off
echo ============================
echo  Build and Run (Profile: dev)
echo ============================

mvn clean install && mvn spring-boot:run "-Dspring-boot.run.profiles=dev"

echo.
echo  Execução finalizada.
pause
