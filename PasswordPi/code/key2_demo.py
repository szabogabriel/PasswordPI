import time

import keyboard
import display
import keyUtil

def buttonKeyUpCallback(channel):
    print("Key Up pressed.")

def buttonKeyDownCallback(channel):
    print("Key Down pressed.")

def buttonKeyLeftCallback(channel):
    print("Key Left pressed.")

def buttonKeyRightCallback(channel):
    print("Key Right pressed.")

def buttonKeyCenterCallback(channel):
    print("Key Center pressed.")

def buttonKeyACallback(channel):
    print("Key A pressed.")
    keyUtil.write("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890")

def buttonKeyBCallback(channel):
    print("Key B pressed.")
    keyUtil.write(',./<>?;\:"|[]{}=+-_)(*&^%$#@!`~')

def buttonKeyCCallback(channel):
    print("Key C pressed.")
    keyUtil.write("'")

keyHandler = keyboard.KeyboardHandler()
dispHandler = display.DisplayHandler()

keyHandler.addButtonKeyACallback(buttonKeyACallback)
keyHandler.addButtonKeyBCallback(buttonKeyBCallback)
keyHandler.addButtonKeyCCallback(buttonKeyCCallback)


while 1:
    time.sleep(1000)
