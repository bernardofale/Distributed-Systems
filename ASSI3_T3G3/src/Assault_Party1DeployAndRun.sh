echo "Executing program at the Assault Party 1 node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirAssaultParty1
./ap1_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "Assault Party 1 Server shutdown."

#echo "Transfering data to the Assault Party 1 node."
#sshpass -f password ssh sd202@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirAssaultParty1.zip sd202@l040101-ws03.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Assault Party 1 node."
#sshpass -f password ssh sd202@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirAssaultParty1.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws03.ua.pt:test/MuseumHeist/dirAssaultParty1
#sshpass -f password ssh sd202@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirAssaultParty1 ; unzip -uq genclass.zip'
#echo "Executing program at the Assault Party 1 node."
#sshpass -f password ssh sd202@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirAssaultParty1 ; ./AssaultParty1_com_d.sh sd202'
#echo "Assault Party 1 Server shutdown."
