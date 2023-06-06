echo "Compiling source code."
javac --release 8 -cp ".:./genclass.jar" */*.java */*/*.java


echo "Distributing intermediate code to the different execution environments."

echo "RMI registry"
rm -rf dirRMIRegistry/interfaces dirRMIRegistry/commInfra
mkdir -p dirRMIRegistry/interfaces dirRMIRegistry/commInfra
cp interfaces/*.class dirRMIRegistry/interfaces
cp commInfra/*.class dirRMIRegistry/commInfra

echo "Register Remote Objects"
rm -rf dirRegistry/serverSide dirRegistry/interfaces
mkdir -p dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces
cp serverSide/main/ServerHeistProjRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces

echo "General Repository of Information"
rm -rf dirGeneralRepos/serverSide dirGeneralRepos/clientSide dirGeneralRepos/interfaces
mkdir -p dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities
cp serverSide/main/Simul_Par.class serverSide/main/ServerHeistProjGeneralRepos.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRepos.class dirGeneralRepos/serverSide/objects
cp interfaces/Register.class interfaces/GeneralReposInterface.class dirGeneralRepos/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirGeneralRepos/clientSide/entities

echo "Thiefs Concentration Site"
rm -rf dirConcentrationSite/serverSide dirConcentrationSite/clientSide dirConcentrationSite/interfaces dirConcentrationSite/commInfra
mkdir -p dirConcentrationSite/serverSide dirConcentrationSite/serverSide/main dirConcentrationSite/serverSide/objects dirConcentrationSite/interfaces dirConcentrationSite/clientSide dirConcentrationSite/clientSide/entities dirConcentrationSite/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerHeistProjThiefs_Concentration_Site.class dirConcentrationSite/serverSide/main
cp serverSide/objects/Thiefs_Concentration_Site.class dirConcentrationSite/serverSide/objects
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

echo "Master Control Collection Site"
rm -rf dirCollectionSite/serverSide dirCollectionSite/clientSide dirCollectionSite/interfaces dirCollectionSite/commInfra
mkdir -p dirCollectionSite/serverSide dirCollectionSite/serverSide/main dirCollectionSite/serverSide/objects dirCollectionSite/interfaces dirCollectionSite/clientSide dirCollectionSite/clientSide/entities dirCollectionSite/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerHeistProjMaster_Control_Collection_Site.class dirCollectionSite/serverSide/main
cp serverSide/objects/Master_Control_Collection_Site.class dirCollectionSite/serverSide/objects
cp interfaces/*.class dirCollectionSite/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirCollectionSite/clientSide/entities
cp commInfra/*.class dirCollectionSite/commInfra

echo "Museum"
rm -rf dirMuseum/serverSide dirMuseum/clientSide dirMuseum/interfaces dirMuseum/commInfra
mkdir -p dirMuseum/serverSide dirMuseum/serverSide/main dirMuseum/serverSide/objects dirMuseum/interfaces dirMuseum/clientSide dirMuseum/clientSide/entities dirMuseum/commInfra
cp serverSide/main/Simul_Par.class serverSide/main/ServerHeistProjMuseum.class dirMuseum/serverSide/main
cp serverSide/objects/Museum.class dirMuseum/serverSide/objects
cp interfaces/*.class dirMuseum/interfaces
cp clientSide/entities/MasterThiefStates.class clientSide/entities/OrdinaryThievesStates.class dirMuseum/clientSide/entities
cp commInfra/*.class dirMuseum/commInfra

echo "Master"
rm -rf dirMasterThief/serverSide dirMasterThief/clientSide dirMasterThief/interfaces
mkdir -p dirMasterThief/serverSide dirMasterThief/serverSide/main dirMasterThief/clientSide dirMasterThief/clientSide/main dirMasterThief/clientSide/entities dirMasterThief/interfaces
cp serverSide/main/Simul_Par.class dirMasterThief/serverSide/main
cp clientSide/main/ClientHeistMuseumMaster.class dirMasterThief/clientSide/main
cp clientSide/entities/Master.class clientSide/entities/MasterThiefStates.class dirMasterThief/clientSide/entities
cp interfaces/Master_Control_Collection_SiteInterface.class interfaces/Thiefs_Concentration_SiteInterface.class interfaces/GeneralReposInterface.class interfaces/ReturnBoolean.class interfaces/ReturnInt.class interfaces/ReturnIntArray.class dirMasterThief/interfaces

echo "Ordinaries"
rm -rf dirOrdinaryThieves/serverSide dirOrdinaryThieves/clientSide dirOrdinaryThieves/interfaces dirOrdinaryThieves/commInfra
mkdir -p dirOrdinaryThieves/serverSide dirOrdinaryThieves/serverSide/main dirOrdinaryThieves/clientSide dirOrdinaryThieves/clientSide/main dirOrdinaryThieves/clientSide/entities dirOrdinaryThieves/interfaces dirOrdinaryThieves/commInfra
cp serverSide/main/Simul_Par.class dirOrdinaryThieves/serverSide/main
cp clientSide/main/ClientHeistMuseumOrdinary.class dirOrdinaryThieves/clientSide/main
cp clientSide/entities/Ordinary.class clientSide/entities/OrdinaryThievesStates.class dirOrdinaryThieves/clientSide/entities
cp interfaces/Master_Control_Collection_SiteInterface.class interfaces/Thiefs_Concentration_SiteInterface.class interfaces/MuseumInterface.class interfaces/AssaultPartyInterface.class interfaces/GeneralReposInterface.class interfaces/ReturnBoolean.class interfaces/ReturnInt.class interfaces/ReturnIntArray.class dirOrdinaryThieves/interfaces
cp commInfra/MemException.class dirOrdinaryThieves/commInfra

echo "Compressing execution environments."

echo "RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry

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