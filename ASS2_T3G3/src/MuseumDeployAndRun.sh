echo "Transfering data to the Museum node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'mkdir -p test/MuseumHeist'
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'rm -rf test/MuseumHeist/*'
sshpass -f password scp dirMuseum.zip sd303@l040101-ws03.ua.pt:test/MuseumHeist
echo "Decompressing data sent to the Museum node."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist ; unzip -uq dirMuseum.zip'
echo "Executing program at the server Museum."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirMuseum ; java serverSide.main.ServerMuseum 22005 l040101-ws07.ua.pt 22006'
echo "Server shutdown."
sshpass -f password ssh sd303@l040101-ws03.ua.pt 'cd test/MuseumHeist/dirMuseum ; less stat'
