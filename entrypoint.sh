#!/bin/bash
# entrypoint.sh
export PASV_ADDRESS=$(curl http://whatismyip.akamai.com/)
exec /usr/sbin/run-vsftpd.sh
