#!/bin/sh
echo "脚本启动..."
cd `dirname $0`;cd ..;
case $1 in
	start)
        	flag=`ps -ef|grep "monitor-datapicker.jar"|grep -v "grep"|wc -l`
	        if [[ $flag -gt 0 ]];then
        	    pid=`ps -ef|grep "monitor-datapicker.jar"|grep -v "grep"|awk  '{print $2}'`
            	echo -e "程序正在运行，请先停止再启动，进程号\033[32;1m $pid \033[0m"
            	exit 1
        	fi
		for i in `pwd`/lib/*.jar; do
   			CLASSPATH=$i:"$CLASSPATH";
		done
        	export CLASSPATH=.:$CLASSPATH

		echo "CLASSPATH=$CLASSPATH"

		nohup java "-Xbootclasspath/p:$CLASSPATH"  -Xms1024M -Xmx1024M -Xmn512M -XX:MaxPermSize=128m -jar bin/monitor-datapicker.jar >> console.log 2>&1 &
	        #java "-Xbootclasspath/p:$CLASSPATH"  -Xms1024M -Xmx1024M -Xmn512M -XX:MaxPermSize=128m -jar bin/monitor-datapicker.jar >> console.log 2>&1
		echo $! > server.pid
		echo "程序启动成功!"
		;;
	stop)
		kill `cat server.pid`
		rm -rf server.pid
		echo "程序停止成功!"
		;;
	*)
		echo "Usage: run.sh {start|stop}"
		;;
esac