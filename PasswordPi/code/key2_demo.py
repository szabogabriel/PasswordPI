import LCD_1in44
import LCD_Config

import RPi.GPIO as GPIO

import time
from PIL import Image,ImageDraw,ImageFont,ImageColor

import keyUtil

KEY_UP_PIN     = 6 
KEY_DOWN_PIN   = 19
KEY_LEFT_PIN   = 5
KEY_RIGHT_PIN  = 26
KEY_PRESS_PIN  = 13
KEY1_PIN       = 21
KEY2_PIN       = 20
KEY3_PIN       = 16

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


#init GPIO
GPIO.setmode(GPIO.BCM) 
GPIO.cleanup()
GPIO.setup(KEY_UP_PIN,      GPIO.IN, pull_up_down=GPIO.PUD_UP)    # Input with pull-up
GPIO.setup(KEY_DOWN_PIN,    GPIO.IN, pull_up_down=GPIO.PUD_UP)  # Input with pull-up
GPIO.setup(KEY_LEFT_PIN,    GPIO.IN, pull_up_down=GPIO.PUD_UP)  # Input with pull-up
GPIO.setup(KEY_RIGHT_PIN,   GPIO.IN, pull_up_down=GPIO.PUD_UP) # Input with pull-up
GPIO.setup(KEY_PRESS_PIN,   GPIO.IN, pull_up_down=GPIO.PUD_UP) # Input with pull-up
GPIO.setup(KEY1_PIN,        GPIO.IN, pull_up_down=GPIO.PUD_UP)      # Input with pull-up
GPIO.setup(KEY2_PIN,        GPIO.IN, pull_up_down=GPIO.PUD_UP)      # Input with pull-up
GPIO.setup(KEY3_PIN,        GPIO.IN, pull_up_down=GPIO.PUD_UP)      # Input with pull-up

GPIO.add_event_detect(KEY_UP_PIN,    GPIO.RISING, callback=buttonKeyUpCallback)
GPIO.add_event_detect(KEY_DOWN_PIN,  GPIO.RISING, callback=buttonKeyDownCallback)
GPIO.add_event_detect(KEY_LEFT_PIN,  GPIO.RISING, callback=buttonKeyLeftCallback)
GPIO.add_event_detect(KEY_RIGHT_PIN, GPIO.RISING, callback=buttonKeyRightCallback)
GPIO.add_event_detect(KEY_PRESS_PIN, GPIO.RISING, callback=buttonKeyCenterCallback)
GPIO.add_event_detect(KEY1_PIN,      GPIO.RISING, callback=buttonKeyACallback)
GPIO.add_event_detect(KEY2_PIN,      GPIO.RISING, callback=buttonKeyBCallback)
GPIO.add_event_detect(KEY3_PIN,      GPIO.RISING, callback=buttonKeyCCallback)

# 240x240 display with hardware SPI:
disp = LCD_1in44.LCD()
Lcd_ScanDir = LCD_1in44.SCAN_DIR_DFT  #SCAN_DIR_DFT = D2U_L2R
disp.LCD_Init(Lcd_ScanDir)
disp.LCD_Clear()

# Create blank image for drawing.
# Make sure to create image with mode '1' for 1-bit color.
width = 128
height = 128
image = Image.new('RGB', (width, height))

# Get drawing object to draw on image.
draw = ImageDraw.Draw(image)

# Draw a black filled box to clear the image.
draw.rectangle((0,0,width,height), outline=0, fill=0)
disp.LCD_ShowImage(image,0,0)


while 1:
    time.sleep(1000)
