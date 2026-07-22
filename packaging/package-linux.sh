#!/usr/bin/env bash
# ===================================================================
#  Builds a Linux INSTALLER (.deb) for DocuFlow with the logo.
#  After installing, DocuFlow appears in your applications menu
#  (the app grid) with the logo, just like any other installed app.
#
#  Run on Linux with:
#    - JDK 21+  (jpackage on PATH)
#    - dpkg + fakeroot  (already present on Ubuntu/Debian)
#
#  Output: target/dist/docuflow_1.0_amd64.deb
#  Install:   sudo apt install ./target/dist/docuflow_1.0_amd64.deb
#  Uninstall: sudo apt remove docuflow
#
#  Prefer a portable no-install version instead? Change
#  --type deb  to  --type app-image  and remove the --linux-* lines;
#  that produces target/dist/DocuFlow/bin/DocuFlow (nothing installed).
# ===================================================================
set -euo pipefail
cd "$(dirname "$0")/.."

echo "[1/2] Building application with Maven..."
./mvnw -q clean package -DskipTests

echo "[2/2] Creating Linux installer (.deb) with jpackage..."
jpackage \
  --type deb \
  --name DocuFlow \
  --input target/app \
  --main-jar idms-1.0-SNAPSHOT.jar \
  --main-class ds.comsats.idms.idms.Launcher \
  --icon src/main/resources/Logo.png \
  --app-version 1.0 \
  --vendor "COMSATS IDMS" \
  --description "Intelligent Document Management System" \
  --linux-shortcut \
  --linux-menu-group "Office" \
  --linux-app-category "Office" \
  --linux-package-name docuflow \
  --dest target/dist

echo
echo "Done. Installer in target/dist/"
echo "Install with:  sudo apt install ./target/dist/docuflow_1.0_amd64.deb"
