echo "Compiling source code."
javac --release 8 -cp ".:./genclass.jar" */*.java */*/*.java


echo "Distributing intermediate code to the different execution environments."

echo "Register Remote Objects"
rm -rf dirRegistry/serverSide dirRegistry/interfaces
mkdir -p dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces
cp serverSide/main/ServerRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces

echo "General Repository of Information"
rm -rf dirGeneralRepos/serverSide dirGeneralRepos/clientSide dirGeneralRepos/interfaces dirGeneralRepos/commInfra
mkdir -p dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities dirGeneralRepos/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerGeneralRepo.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRepo.class dirGeneralRepos/serverSide/objects
cp interfaces/Register.class interfaces/GeneralReposInterface.class dirGeneralRepos/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirGeneralRepos/clientSide/entities
cp commInfra/*.class dirGeneralRepos/commInfra

echo "Concentration Site"
rm -rf dirConcentrationSite/serverSide dirConcentrationSite/clientSide dirConcentrationSite/interfaces dirConcentrationSite/commInfra
mkdir -p dirConcentrationSite/serverSide dirConcentrationSite/serverSide/main dirConcentrationSite/serverSide/objects dirConcentrationSite/interfaces dirConcentrationSite/clientSide dirConcentrationSite/clientSide/entities dirConcentrationSite/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerOrdinaryThievesCS.class dirConcentrationSite/serverSide/main
cp serverSide/objects/OrdinaryThievesCS.class dirConcentrationSite/serverSide/objects
cp interfaces/*.class dirConcentrationSite/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirConcentrationSite/clientSide/entities
cp commInfra/*.class dirConcentrationSite/commInfra

echo "Assault Party 0"
rm -rf dirAssaultParty0/serverSide dirAssaultParty0/clientSide dirAssaultParty0/interfaces dirAssaultParty0/commInfra
mkdir -p dirAssaultParty0/serverSide dirAssaultParty0/serverSide/main dirAssaultParty0/serverSide/objects dirAssaultParty0/interfaces dirAssaultParty0/clientSide dirAssaultParty0/clientSide/entities dirAssaultParty0/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerAssaultParty.class dirAssaultParty0/serverSide/main
cp serverSide/objects/AssaultParty.class dirAssaultParty0/serverSide/objects
cp interfaces/*.class dirAssaultParty0/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirAssaultParty0/clientSide/entities
cp commInfra/*.class dirAssaultParty0/commInfra

echo "Assault Party 1"
rm -rf dirAssaultParty1/serverSide dirAssaultParty1/clientSide dirAssaultParty1/interfaces dirAssaultParty1/commInfra
mkdir -p dirAssaultParty1/serverSide dirAssaultParty1/serverSide/main dirAssaultParty1/serverSide/objects dirAssaultParty1/interfaces dirAssaultParty1/clientSide dirAssaultParty1/clientSide/entities dirAssaultParty1/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerAssaultParty.class dirAssaultParty1/serverSide/main
cp serverSide/objects/AssaultParty.class dirAssaultParty1/serverSide/objects
cp interfaces/*.class dirAssaultParty1/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirAssaultParty1/clientSide/entities
cp commInfra/*.class dirAssaultParty1/commInfra

echo "Master Control and Collection Site"
rm -rf dirCollectionSite/serverSide dirCollectionSite/clientSide dirCollectionSite/interfaces dirCollectionSite/commInfra
mkdir -p dirCollectionSite/serverSide dirCollectionSite/serverSide/main dirCollectionSite/serverSide/objects dirCollectionSite/interfaces dirCollectionSite/clientSide dirCollectionSite/clientSide/entities dirCollectionSite/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerMasterThiefCCS.class dirCollectionSite/serverSide/main
cp serverSide/objects/MasterThiefCCS.class dirCollectionSite/serverSide/objects
cp interfaces/*.class dirCollectionSite/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirCollectionSite/clientSide/entities
cp commInfra/*.class dirCollectionSite/commInfra

echo "Museum"
rm -rf dirMuseum/serverSide dirMuseum/clientSide dirMuseum/interfaces dirMuseum/commInfra
mkdir -p dirMuseum/serverSide dirMuseum/serverSide/main dirMuseum/serverSide/objects dirMuseum/interfaces dirMuseum/clientSide dirMuseum/clientSide/entities dirMuseum/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerMuseum.class dirMuseum/serverSide/main
cp serverSide/objects/Museum.class dirMuseum/serverSide/objects
cp interfaces/*.class dirMuseum/interfaces
cp clientSide/entities/*.class dirMuseum/clientSide/entities
cp commInfra/*.class dirMuseum/commInfra

echo "Master Thief"
rm -rf dirMasterThief/serverSide dirMasterThief/clientSide dirMasterThief/interfaces dirMasterThief/commInfra
mkdir -p dirMasterThief/serverSide dirMasterThief/serverSide/main dirMasterThief/clientSide dirMasterThief/clientSide/main dirMasterThief/clientSide/entities dirMasterThief/interfaces dirMasterThief/commInfra
cp serverSide/main/Simul_Par.class dirMasterThief/serverSide/main
cp clientSide/main/ClientMasterThief.class dirMasterThief/clientSide/main
cp clientSide/entities/*.class dirMasterThief/clientSide/entities
cp interfaces/*.class dirMasterThief/interfaces
cp commInfra/*.class dirMasterThief/commInfra

echo "Ordinary Thieves"
rm -rf dirOrdinaryThieves/serverSide dirOrdinaryThieves/clientSide dirOrdinaryThieves/interfaces dirOrdinaryThieves/commInfra
mkdir -p dirOrdinaryThieves/serverSide dirOrdinaryThieves/serverSide/main dirOrdinaryThieves/clientSide dirOrdinaryThieves/clientSide/main dirOrdinaryThieves/clientSide/entities dirOrdinaryThieves/interfaces dirOrdinaryThieves/commInfra
cp serverSide/main/Simul_Par.class dirOrdinaryThieves/serverSide/main
cp clientSide/main/ClientOrdinaryThief.class dirOrdinaryThieves/clientSide/main
cp clientSide/entities/*.class dirOrdinaryThieves/clientSide/entities
cp interfaces/*.class dirOrdinaryThieves/interfaces
cp commInfra/*.class dirOrdinaryThieves/commInfra

echo "Compressing execution environments."

echo "Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry

echo "General Repository of Information"
rm -f  dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos

echo "Thiefs Concentration Site"
rm -f  dirConcentrationSite.zip
zip -rq dirConcentrationSite.zip dirConcentrationSite

echo "Assault Party 0"
rm -f  dirAssaultParty0.zip
zip -rq dirAssaultParty0.zip dirAssaultParty0

echo "Assault Party 1"
rm -f  dirAssaultParty1.zip
zip -rq dirAssaultParty1.zip dirAssaultParty1

echo "Museum"
rm -f  dirMuseum.zip
zip -rq dirMuseum.zip dirMuseum

echo "Master Control Collection Site"
rm -f  dirCollectionSite.zip
zip -rq dirCollectionSite.zip dirCollectionSite

echo "Master"
rm -f  dirMasterThief.zip
zip -rq dirMasterThief.zip dirMasterThief

echo "Ordinaries"
rm -f  dirOrdinaryThieves.zip
zip -rq dirOrdinaryThieves.zip dirOrdinaryThieves

echo "Genclass"
rm -f  genclass.zip
zip -rq genclass.zip genclass.jar

echo "Deploying and decompressing execution environments."

mkdir -p /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
rm -rf /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/*

cp dirRegistry.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirGeneralRepos.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirConcentrationSite.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirAssaultParty0.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirAssaultParty1.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirMuseum.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirCollectionSite.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirMasterThief.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp dirOrdinaryThieves.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
cp genclass.zip /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim

cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
unzip -q dirRegistry.zip
cp ../dirRMIRegistry/set_rmiregistry_alt.sh .
unzip -q dirGeneralRepos.zip
unzip -q dirConcentrationSite.zip
unzip -q dirAssaultParty0.zip
unzip -q dirAssaultParty1.zip
unzip -q dirMuseum.zip
unzip -q dirCollectionSite.zip
unzip -q dirMasterThief.zip
unzip -q dirOrdinaryThieves.zip
unzip -q genclass.zip