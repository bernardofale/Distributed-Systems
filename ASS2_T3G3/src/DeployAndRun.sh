xterm  -T "Assault Party 0" -hold -e "./AP0DeployAndRun.sh" &
xterm  -T "Assault Party 1" -hold -e "./AP1DeployAndRun.sh" &
xterm  -T "General Repository" -hold -e "./GeneralRepoDeployAndRun.sh" &
xterm  -T "Master Thief CCS" -hold -e "./MTCCSDeployAndRun.sh" &
xterm  -T "ordinary Thief CS" -hold -e "./OTCSDeployAndRun.sh" &
xterm  -T "Museum" -hold -e "./MuseumDeployAndRun.sh" &
sleep 1
xterm  -T "MasterThief" -hold -e "./MTDeployAndRun.sh" &
xterm  -T "OrdinaryThief" -hold -e "./OTDeployAndRun.sh" &
