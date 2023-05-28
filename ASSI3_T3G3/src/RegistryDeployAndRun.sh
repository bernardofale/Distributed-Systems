echo "Executing program at the Registry node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirRegistry
./registry_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim

#echo "Transfering data to the Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password scp dirRegistry.zip sd202@l040101-ws01.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'cd test/MuseumHeist ; unzip -uq dirRegistry.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws01.ua.pt:test/MuseumHeist/dirRegistry
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'cd test/MuseumHeist/dirRegistry ; unzip -uq genclass.zip'
#echo "Executing program at the Registry node."
#sshpass -f password ssh sd202@l040101-ws01.ua.pt 'cd test/MuseumHeist/dirRegistry ; ./registry_com_d.sh sd202'