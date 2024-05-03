if [ $# -lt 1 ]; then
echo "Mettez le nom de version en argument ex: './package_platforms.sh 1.1.3'"
exit 1
fi

echo "If you have not uncommented the additionnal dependencies in the pom.xml file and are not on linux, the package produced will not work"

if [ $2 == "mvn" ]; then
mvn package
fi

mkdir ./target/packages

if ! [[ -f ./target/packages/openjdk_linux.tar.gz ]]; then
echo "Downloading Linux JVM archive..."
wget https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_linux-x64_bin.tar.gz -O ./target/packages/openjdk_linux.tar.gz
fi


if ! [[ -d ./target/packages/jdk-17_linux ]]; then
echo "Extracting Linux JVM archive..."
tar xzf ./target/packages/openjdk_linux.tar.gz -C ./target/packages
mv ./target/packages/jdk-17.0.1 ./target/packages/jdk-17_linux
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

echo "Building pyinstaller version of ivyCommunications for linux..."

cd ./lib/ivyCommunications

pyinstaller ivyCommunications.py

cp dist/ivyCommunications ../ivyCommunications_linux_pyinstaller -r

rm -r dist
rm -r build
rm ivyCommunications.spec

cd ../..

cp ./lib/ivyCommunications_linux_pyinstaller ./target/packages/ivyCommunications_linux -r

cd ./target/packages

echo "Packaging for Linux..."

zip Vifa-MAXI_linux_$1.zip Vifa-SNAPSHOT.jar run_vifa_linux.sh vifa22.conf ivyCommunications_linux jdk-17_linux jinput-2.0.7 -r -q
