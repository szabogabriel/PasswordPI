import RPi.GPIO as GPIO

class KeyboardHandler:

    def __init__(self):
        global KEY_UP_PIN 
        global KEY_DOWN_PIN
        global KEY_LEFT_PIN
        global KEY_RIGHT_PIN
        global KEY_PRESS_PIN
        global KEY1_PIN
        global KEY2_PIN
        global KEY3_PIN
        
        KEY_UP_PIN     = 6 
        KEY_DOWN_PIN   = 19
        KEY_LEFT_PIN   = 5
        KEY_RIGHT_PIN  = 26
        KEY_PRESS_PIN  = 13
        KEY1_PIN       = 21
        KEY2_PIN       = 20
        KEY3_PIN       = 16
        
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

    def addButtonKeyUpCallback(self, callback):        
        GPIO.add_event_detect(KEY_UP_PIN,    GPIO.RISING, callback=callback)
    
    def addButtonKeyDownCallback(self, callback):
        GPIO.add_event_detect(KEY_DOWN_PIN,  GPIO.RISING, callback=callback)
    
    def addButtonKeyLeftCallback(self, callback):
        GPIO.add_event_detect(KEY_LEFT_PIN,  GPIO.RISING, callback=buttonKeyLeftCallback)
    
    def addButtonkeyRightCallback(self, callback):
        GPIO.add_event_detect(KEY_RIGHT_PIN, GPIO.RISING, callback=buttonKeyRightCallback)
    
    def addButtonKeyCenterCallback(self, callback):
        GPIO.add_event_detect(KEY_PRESS_PIN, GPIO.RISING, callback=callback)
    
    def addButtonKeyACallback(self, callback):
        GPIO.add_event_detect(KEY1_PIN,      GPIO.RISING, callback=callback)
    
    def addButtonKeyBCallback(self, callback):
        GPIO.add_event_detect(KEY2_PIN,      GPIO.RISING, callback=callback)
    
    def addButtonKeyCCallback(self, callback):
        GPIO.add_event_detect(KEY3_PIN,      GPIO.RISING, callback=callback)
    
