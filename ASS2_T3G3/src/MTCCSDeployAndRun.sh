echo "Transfering data to the Master Thief CCS node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirMTCCS.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Master Thief CCS node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMTCCS.zip'
echo "Executing program at the server Master Thief CCS."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirMTCCS ; java serverSide.main.ServerMasterThiefCCS 22003 l040101-ws07.ua.pt 22006'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirMTCCS ; less stat'
