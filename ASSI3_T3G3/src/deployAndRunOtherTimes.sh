xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
sleep 10
xterm  -T "Thiefs Concentration Site" -hold -e "./Thiefs_Concentration_SiteDeployAndRun.sh" &
xterm  -T "Assault Party 0" -hold -e "./AssaultParty0DeployAndRun.sh" &
xterm  -T "Assault Party 1" -hold -e "./AssaultParty1DeployAndRun.sh" &
xterm  -T "Museum" -hold -e "./MuseumDeployAndRun.sh" &
xterm  -T "Master Control Collection Site" -hold -e "./Master_Control_Collection_SiteDeployAndRun.sh" &
sleep 10
xterm  -T "Master" -hold -e "./MasterDeployAndRun.sh" &
xterm  -T "Ordinaries" -hold -e "./OrdinariesDeployAndRun.sh" &

# Registry                          - sd202@l040101-ws01.ua.pt
# General Repository                - sd202@l040101-ws10.ua.pt 
# Thiefs Concentration Site         - sd202@l040101-ws07.ua.pt
# Assault Party 0                   - sd202@l040101-ws02.ua.pt
# Assault Party 1                   - sd202@l040101-ws03.ua.pt
# Museum                            - sd202@l040101-ws06.ua.pt
# Master Control Collection Site    - sd202@l040101-ws05.ua.pt
# Master                            - sd202@l040101-ws08.ua.pt
# Ordinaries                        - sd202@l040101-ws09.ua.pt