#echo "Executing program at the Assault Party 0 node."
#cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirAssaultParty0
#./ap0_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
#echo "Assault Party 0 Server shutdown."

echo "Transfering data to the Assault Party 0 node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirAssaultParty0.zip sd303@l040101-ws02.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Assault Party 0 node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'cd test/MuseumHeist ; unzip -uq dirAssaultParty0.zip'
sshpass -f password scp genclass.zip sd303@l040101-ws02.ua.pt:test/MuseumHeist/dirAssaultParty0
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'cd test/MuseumHeist/dirAssaultParty0 ; unzip -uq genclass.zip'
echo "Executing program at the Assault Party 0 node."
sshpass -f password ssh sd303@l040101-ws02.ua.pt 'cd test/MuseumHeist/dirAssaultParty0 ; ./ap0_com_d.sh sd303'
echo "Assault Party 0 Server shutdown."

