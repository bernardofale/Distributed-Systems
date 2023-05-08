echo "Transfering data to the Assault Party 0 node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirAP0.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Assault Party 0 node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirAP0.zip'
echo "Executing program at the server Assault Party 0."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirAP0 ; java serverSide.main.ServerAssaultParty0 22001 l040101-ws07.ua.pt 22006'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirAP0 ; less stat'
