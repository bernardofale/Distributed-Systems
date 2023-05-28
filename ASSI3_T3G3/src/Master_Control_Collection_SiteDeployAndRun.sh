echo "Executing program at the Master Control Collection Site node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirCollectionSite
./CCS_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "Collection Site Server shutdown."

#echo "Transfering data to the Master Control Collection Site node."
#sshpass -f password ssh sd202@l040101-ws05.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws05.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirCollectionSite.zip sd202@l040101-ws05.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Master Control Collection Site node."
#sshpass -f password ssh sd202@l040101-ws05.ua.pt 'cd test/MuseumHeist ; unzip -uq dirCollectionSite.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws05.ua.pt:test/MuseumHeist/dirCollectionSite
#sshpass -f password ssh sd202@l040101-ws05.ua.pt 'cd test/MuseumHeist/dirCollectionSite ; unzip -uq genclass.zip'
#echo "Executing program at the Master Control Collection Site node."
#sshpass -f password ssh sd202@l040101-ws05.ua.pt 'cd test/MuseumHeist/dirCollectionSite ; ./controlSite_com_d.sh sd202'
#echo "Master Control Collection Site Server shutdown."
