if [ $# -lt 1 ]; then
echo "Mettez le nom de version en argument ex: './package_platforms.sh 1.1.3'"
exit 1
fi

echo "If you have not uncommented the additionnal dependencies in the pom.xml file and are not on mac, the package produced will not work"

mvn package
mkdir ./target/packages

echo "Copying files..."

mkdir ./target/packages/jinput-2.0.7
cp ./lib/jinput-2.0.7/library ./target/packages/jinput-2.0.7/library -r
cp ./lib/jinput-2.0.7/natives ./target/packages/jinput-2.0.7/natives -r

cp ./target/Vifa-1.0-SNAPSHOT-shaded.jar ./target/packages/Vifa-SNAPSHOT.jar
cp vifa22.conf ./target/packages/vifa22.conf

cp ./package_helpers/run_vifa_linux.sh ./target/packages/run_vifa_linux.sh
cp ./package_helpers/run_vifa_macos.sh ./target/packages/run_vifa_macos.sh
cp ./package_helpers/run_vifa_windows.bat ./target/packages/run_vifa_windows.bat

cp ./package_helpers/how_to_run_macos.txt ./target/packages/how_to_run_macos.txt

cp ./lib/ivyCommunications ./target/packages/ivyCommunications_mac -r

cd ./target/packages

echo "Packaging for MacOS..."

zip Vifa-Mini_macos_$1.zip Vifa-SNAPSHOT.jar run_vifa_macos.sh vifa22.conf ivyCommunications_mac how_to_run_macos.txt jinput-2.0.7 -r -q
