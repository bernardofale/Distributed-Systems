echo "Executing program at the Ordinaries node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirOrdinaryThieves
./ot_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "Ordinaries Client shutdown."

#echo "Transfering data to the Ordinaries node."
#sshpass -f password ssh sd202@l040101-ws09.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws09.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirOrdinaryThieves.zip sd202@l040101-ws09.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Ordinary node."
#sshpass -f password ssh sd202@l040101-ws09.ua.pt 'cd test/MuseumHeist ; unzip -uq dirOrdinaryThieves.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws09.ua.pt:test/MuseumHeist/dirOrdinaryThieves
#sshpass -f password ssh sd202@l040101-ws09.ua.pt 'cd test/MuseumHeist/dirOrdinaryThieves ; unzip -uq genclass.zip'
#echo "Executing program at the Ordinary node."
#sshpass -f password ssh sd202@l040101-ws09.ua.pt 'cd test/MuseumHeist/dirOrdinaryThieves ; ./ordinaries_com_d.sh'
#echo "Ordinaries client shutdown."