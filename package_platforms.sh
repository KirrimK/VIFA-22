if [ $# -lt 1 ]; then
echo "Mettez le nom de version en argument ex: './package_platforms.sh 1.1.3'"
exit 1
fi

echo "If you have not uncommented the additionnal dependencies in the pom.xml file, the packages produced for other platforms than this machine will not work"

mvn package

bash ./package_linux.sh $1 $2
bash ./package_mac.sh $1 $2
bash ./package_windows.sh $1 $2

echo "Done"
