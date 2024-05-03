#script de lancement de Vifa-2022 - mac

#si il y a un problème, faire attention aux chemins de fichiers, à avoir téléchargé toutes les dépendences, et à avoir rendu ce script exécutable si il ne l'est pas

python3 ./ivyCommunications_mac/ivyCommunications.py &
IVYPID=$! #retenir le pid du script python compilé qui tourne en arrière-plan
java -jar Vifa-SNAPSHOT.jar
kill -9 $IVYPID #arrêter le script en arrière plan
