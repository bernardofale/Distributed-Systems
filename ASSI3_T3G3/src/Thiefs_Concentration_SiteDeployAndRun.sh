#echo "Executing program at the Concentration Site node."
#cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirConcentrationSite
#./CS_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
#echo "Thiefs Concentration Site Server shutdown."

echo "Transfering data to the Thiefs Concentration Site node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirConcentrationSite.zip sd303@l040101-ws07.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Thiefs Concentration Site node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'cd test/MuseumHeist ; unzip -uq dirConcentrationSite.zip'
sshpass -f password scp genclass.zip sd303@l040101-ws07.ua.pt:test/MuseumHeist/dirConcentrationSite
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'cd test/MuseumHeist/dirConcentrationSite ; unzip -uq genclass.zip'
echo "Executing program at the Thiefs Concentration Site node."
sshpass -f password ssh sd303@l040101-ws07.ua.pt 'cd test/MuseumHeist/dirConcentrationSite ; ./CS_com_d.sh sd303'
echo "Thiefs Concentration Site Server shutdown."
