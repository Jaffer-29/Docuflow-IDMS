@echo off
REM ===================================================================
REM  Builds a Windows INSTALLER (DocuFlow-1.0.exe) with the logo.
REM  After installing, DocuFlow appears in the Start Menu / apps list
REM  and on the Desktop, just like any other installed program.
REM
REM  Run this ON WINDOWS with:
REM    - JDK 21+  (jpackage on PATH)
REM    - WiX Toolset v3.x  (https://wixtoolset.org/  -> "v3" release)
REM      Install quickly with:  choco install wixtoolset
REM
REM  Output: target\dist\DocuFlow-1.0.exe   (double-click to install)
REM
REM  Prefer a portable no-install version instead? Change
REM  --type exe  to  --type app-image  and remove the --win-* lines;
REM  that produces target\dist\DocuFlow\DocuFlow.exe (no WiX needed).
REM ===================================================================
setlocal
cd /d "%~dp0.."

echo [1/2] Building application with Maven...
call mvnw.cmd -q clean package -DskipTests
if errorlevel 1 (
    echo Maven build failed.
    exit /b 1
)

echo [2/2] Creating Windows installer with jpackage...
jpackage ^
  --type exe ^
  --name DocuFlow ^
  --input target\app ^
  --main-jar idms-1.0-SNAPSHOT.jar ^
  --main-class ds.comsats.idms.idms.Launcher ^
  --icon src\main\resources\Logo.ico ^
  --app-version 1.0 ^
  --vendor "COMSATS IDMS" ^
  --description "Intelligent Document Management System" ^
  --win-shortcut ^
  --win-menu ^
  --win-menu-group "DocuFlow" ^
  --win-dir-chooser ^
  --dest target\dist
if errorlevel 1 (
    echo jpackage failed.  ^(Is WiX Toolset v3 installed and on PATH?^)
    exit /b 1
)

echo.
echo Done. Installer created in:  target\dist\
endlocal
