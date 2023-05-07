echo "Transfering data to the OrdinaryThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirOrdinaryThief sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the customers node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirOrdinaryThief.zip'
echo "Executing program at the customers node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirOrdinaryThief ; java clientSide.main.ClientOrdinaryThief l040101-ws07.ua.pt 22001 l040101-ws02.ua.pt 22002 stat 3'
