#!/bin/bash

readonly remoteIp=192.168.0.73
readonly remoteUname=pi
readonly targetDir=/home/pi/PasswordPi/lib

ssh ${remoteUname}@${remoteIp} sudo chown -R ${remoteUname}:${remoteUname} ${targetDir}

rsync -avh -e ssh release/pi.password-0.1.0_SNAPSHOT.jar ${remoteUname}@${remoteIp}:${targetDir}
