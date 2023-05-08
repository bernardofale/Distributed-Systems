echo "Transfering data to the Ordinary Thieves CS node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirOTCS.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Ordinary Thieves CS node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirOTCS.zip'
echo "Executing program at the server Ordinary Thieves CS."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirOTCS ; java serverSide.main.ServerOrdinaryThievesCS 22004 l040101-ws07.ua.pt 22006'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirOTCS ; less stat'
