echo "Transfering data to the general repository node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirGeneralRepo.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the general repository node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirGeneralRepo.zip'
echo "Executing program at the server general repository."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirGeneralRepo ; java serverSide.main.ServerGeneralRepo 22002'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirGeneralRepo ; less stat'
