if [ "`ps -ef | grep DAscheduler.sh|grep -v grep | wc -l`" -eq "0" ] ; then
	nohup ./DAscheduler.sh START > /dev/null &
	#./DAscheduler.sh START
else
	echo "[WARN] DAscheduler is already run."
fi
