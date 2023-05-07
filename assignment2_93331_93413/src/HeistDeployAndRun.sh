echo "Transfering data to the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp ASS2_T3G3.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq ASS2_T3G3.zip'
echo "Executing program at the MasterThief node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/ASS2_T3G3 ; java clientSide.main.ClientMasterThief l040101-ws07.ua.pt 22001 l040101-ws02.ua.pt 22002'
