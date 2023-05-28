echo "Executing program at the General Repository node."
cd /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim/dirGeneralRepos
./repos_com_alt.sh /Users/bernardofale/Documents/Distributed-Systems/ASSI3_T3G3/src/sim
echo "General Repository Server shutdown."

#echo "Transfering data to the General Repository node."
#sshpass -f password ssh sd202@l040101-ws10.ua.pt 'mkdir -p test/MuseumHeist'
#sshpass -f password ssh sd202@l040101-ws10.ua.pt 'rm -rf test/MuseumHeist/*'
#sshpass -f password scp dirGeneralRepos.zip sd202@l040101-ws10.ua.pt:test/MuseumHeist
#echo "Decompressing data sent to the General Repository node."
#sshpass -f password ssh sd202@l040101-ws10.ua.pt 'cd test/MuseumHeist ; unzip -uq dirGeneralRepos.zip'
#sshpass -f password scp genclass.zip sd202@l040101-ws10.ua.pt:test/MuseumHeist/dirGeneralRepos
#sshpass -f password ssh sd202@l040101-ws10.ua.pt 'cd test/MuseumHeist/dirGeneralRepos ; unzip -uq genclass.zip'
#echo "Executing program at the General Repository node."
#sshpass -f password ssh sd202@l040101-ws10.ua.pt 'cd test/MuseumHeist/dirGeneralRepos ; ./generalRepos_com_d.sh sd202'
#sshpass -f password scp sd202@l040101-ws10.ua.pt:test/MuseumHeist/dirGeneralRepos/log.txt .
#echo "General Repository Server shutdown."
