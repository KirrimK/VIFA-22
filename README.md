# VIFA_redux

An educational application for visualizing aerodynamic forces on an aircraft.

(This program is based on an old school project, available in its original state [here](https://gitlab.com/KirrimK/Vifa-2022))

## How to build

Dependencies:
- python >= 3.8 (with pyinstaller, numpy, matplotlib, ivy-python installed via pip)
- OpenJDK ~17.0.1
- Maven >= 3.8.1

First, install the ivy-java library jar in the local maven repository with the following command
(from the project root):

```mvn install:install-file -Dfile=./lib/ivy-java-1.2.18.jar -DgroupId=fr.dgac.ivy -DartifactId=ivy-java -Dversion=1.2.18 -Dpackaging=jar``` (linux)

```mvn install:install-file -Dfile=.\lib\ivy-java-1.2.18.jar -DgroupId=fr.dgac.ivy -DartifactId=ivy-java -Dversion=1.2.18 -Dpackaging=jar``` (windows)

Then run ```mvn javafx:run``` to build and run the app. The required dependencies
(except ivy, to be installed as described above) will be automatically downloaded.

Note that you won't see an airplane appear because the ivyCommunications program hasn't been launched.

You can run that program using ```python ./lib/ivyCommunications/ivyCommunications.py``` in another terminal.

You can also package it in executable form by running ``pyinstaller ivyCommunications.py``,
after changing directory to the lib/ivyCommunications folder.
The result is in the dist folder, which will be created later.
You can then redistribute the generated folder without needing to install python on the destination machine.

To package the GUI on the same platform as your machine, run ``mvn package``.
The result is a shaded .jar in the “target” folder.

The .jar can then be redistributed on any machine with OpenJDK ~17.0.1 installed
(on the same platform on which you built the project),
or you can include a copy of the JDK with it.

## Authors :

GUI:
- Rémy B.
- Hugo C.
- Victor de C.
- Charles D.

Data backend:
- Thierry DRUOT @ENAC
- Pascal DAUPTAIN @ENAC
