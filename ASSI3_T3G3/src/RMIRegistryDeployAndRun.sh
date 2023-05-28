echo "Executing program at the RMI Registry node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
./set_rmiregistry_alt.sh 22000

#echo "Transfering data to the RMI Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'mkdir -p Public/classes/interfaces'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'rm -rf Public/classes/interfaces/*'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'mkdir -p Public/classes/commInfra'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'rm -rf Public/classes/commInfra/*'
#sshpass -f password scp dirRMIRegistry.zip sd202@l040101-ws01.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the RMI Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'cd test/MuseumHeist ; unzip -uq dirRMIRegistry.zip'
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'cd test/MuseumHeist/dirRMIRegistry ; cp interfaces/*.class /home/sd202/Public/classes/interfaces; cp commInfra/*.class /home/sd202/Public/classes/commInfra ; cp set_rmiregistry_d.sh /home/sd202'
#echo "Executing program at the RMI Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt './set_rmiregistry_d.sh sd202 22211'