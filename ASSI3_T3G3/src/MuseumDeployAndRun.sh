echo "Executing program at the Museum node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirMuseum
./museum_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "Museum Server shutdown."

#echo "Transfering data to the Museum node."
#sshpass -f password ssh sd202@l040101-ws06.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws06.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirMuseum.zip sd202@l040101-ws06.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the Museum node."
#sshpass -f password ssh sd202@l040101-ws06.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMuseum.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws06.ua.pt:test/MuseumHeist/dirMuseum
#sshpass -f password ssh sd202@l040101-ws06.ua.pt 'cd test/MuseumHeist/dirMuseum ; unzip -uq genclass.zip'
#echo "Executing program at the Museum node."
#sshpass -f password ssh sd202@l040101-ws06.ua.pt 'cd test/MuseumHeist/dirMuseum ; ./museum_com_d.sh sd202'
#echo "Museum Server shutdown."
