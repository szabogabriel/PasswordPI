#!/bin/bash

readonly remoteIp=192.168.0.73
readonly remoteUname=pi
readonly targetDir=/home/pi/PasswordPi/code

scp -r src/* ${remoteUname}@${remoteIp}/${targetDir}
