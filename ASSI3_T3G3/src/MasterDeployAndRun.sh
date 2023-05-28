echo "Executing program at the Master node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirMasterThief
./mt_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "Master Thief Client shutdown."

#echo "Transfering data to the Master node."
#sshpass -f password ssh sd202@l040101-ws08.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws08.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirMasterThief.zip sd202@l040101-ws08.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Master node."
#sshpass -f password ssh sd202@l040101-ws08.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMasterThief.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws08.ua.pt:test/MuseumHeist/dirMasterThief
#sshpass -f password ssh sd202@l040101-ws08.ua.pt 'cd test/MuseumHeist/dirMasterThief ; unzip -uq genclass.zip'
#echo "Executing program at the Master node."
#sshpass -f password ssh sd202@l040101-ws08.ua.pt 'cd test/MuseumHeist/dirMasterThief ; ./master_com_d.sh'
#echo "Master client shutdown."