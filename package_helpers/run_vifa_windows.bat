Rem script de lancement de Vifa-2022 - windows

Rem si il y a un problème, faire attention aux chemins de fichiers et à avoir téléchargé toutes les dépendences

START "ivyCommunications_windows" ivyCommunications_windows\ivyCommunications.exe
jdk-17.0.1_windows\bin\java.exe -jar Vifa-SNAPSHOT.jar

taskkill /FI "WindowTitle eq ivyCommunications_windows*" /T /F
