if [ "`ps -ef | grep DAscheduler.sh|grep -v grep | wc -l`" -eq "0" ] ; then
	echo "[WARN] DAscheduler is already stop."
else
	./DAscheduler.sh STOP
fi
