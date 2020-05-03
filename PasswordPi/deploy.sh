#!/bin/bash

readonly remoteIp=192.168.0.73
readonly remoteUname=pi
readonly targetDir=/home/pi/PasswordPi/code/boot

ssh ${remoteUname}@${remoteIp} sudo chown -R ${remoteUname}:${remoteUname} ${targetDir}

scp -r code/* ${remoteUname}@${remoteIp}:${targetDir}