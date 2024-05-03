if [ $# -lt 1 ]; then
echo "Mettez le nom de version en argument ex: './package_platforms.sh 1.1.3'"
exit 1
fi

echo "If you have not uncommented the additionnal dependencies in the pom.xml file (since you are not on windows), the package produced will not work"

if [ $2 == "mvn" ]; then
mvn package
fi

mkdir -p ./target/packages

if ! [[ -f ./target/packages/openjdk_windows.zip ]]; then
echo "Downloading Windows JVM archive..."
wget https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_windows-x64_bin.zip -O ./target/packages/openjdk_windows.zip
fi


if ! [[ -d ./target/packages/jdk-17.0.1_windows ]]; then
echo "Extracting Windows JVM archive..."
unzip ./target/packages/openjdk_windows.zip -d ./target/packages
mv ./target/packages/jdk-17.0.1 ./target/packages/jdk-17.0.1_windows
fi

echo "Copying files..."

mkdir ./target/packages/jinput-2.0.7
cp ./lib/jinput-2.0.7/library ./target/packages/jinput-2.0.7/library -r
cp ./lib/jinput-2.0.7/natives ./target/packages/jinput-2.0.7/natives -r

cp ./target/Vifa-1.0-SNAPSHOT-shaded.jar ./target/packages/Vifa-SNAPSHOT.jar
cp vifa22.conf ./target/packages/vifa22.conf

cp ./package_helpers/run_vifa_linux.sh ./target/packages/run_vifa_linux.sh
cp ./package_helpers/run_vifa_macos.sh ./target/packages/run_vifa_macos.sh
cp ./package_helpers/run_vifa_windows.bat ./target/packages/run_vifa_windows.bat

echo "Assuming that a pyinstaller version of ivyCommunications has been build for windows and is located in ./lib/ivyCommunications_windows_pyinstaller ..."
cp ./lib/ivyCommunications_windows_pyinstaller ./target/packages/ivyCommunications_windows -r

cd ./target/packages

echo "Packaging for Windows..."
zip Vifa-MAXI_windows_$1.zip Vifa-SNAPSHOT.jar run_vifa_windows.bat vifa22.conf ivyCommunications_windows jdk-17.0.1_windows jinput-2.0.7 -r -q
