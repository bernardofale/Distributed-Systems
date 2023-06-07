#echo "Executing program at the RMI Registry node."
#cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
#./set_rmiregistry_alt.sh 22000

echo "Transfering data to the RMI Registry node."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'mkdir -p Public/classes/commInfra'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'rm -rf Public/classes/commInfra/*'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'mkdir -p Public/classes/clientSide/entities'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'rm -rf Public/classes/clientSide/entities/*'
sshpass -f password scp dirRMIRegistry.zip sd303@l040101-ws01.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the RMI Registry node."
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'cd test/MuseumHeist ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd303@l040101-ws01.ua.pt 'cd test/MuseumHeist/dirRMIRegistry ; cp clientSide/entities/*.class /home/sd303/Public/classes/clientSide/entities; cp interfaces/*.class /home/sd303/Public/classes/interfaces; cp commInfra/*.class /home/sd303/Public/classes/commInfra ; cp set_rmiregistry_d.sh /home/sd303'
echo "Executing program at the RMI Registry node."
sshpass -f password ssh sd303@l040101-ws01.ua.pt './set_rmiregistry_d.sh sd303 22320'