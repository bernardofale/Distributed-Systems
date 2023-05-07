echo "Transfering data to the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirMasterThief.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMasterThief.zip'
echo "Executing program at the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirMasterThief ; java clientSide.main.ClientMasterThief l040101-ws07.ua.pt 22001 l040101-ws02.ua.pt 22002'
