import LCD_1in44
import LCD_Config
import socket
from flask import Flask

from PIL import Image,ImageDraw,ImageFont,ImageColor

app = Flask(__name__)
myIp=""
counter=0

def initLcdDisplay():
    global LCD
    LCD = LCD_1in44.LCD()
    global image
    image = Image.new("RGB", (LCD.width, LCD.height), "WHITE")
    global draw
    draw = ImageDraw.Draw(image)
    Lcd_ScanDir = LCD_1in44.SCAN_DIR_DFT
    LCD.LCD_Init(Lcd_ScanDir)
    LCD.LCD_Clear()

@app.route("/")
def index():
    global counter
    counter += 1
    updateScreen()
    return 'Hello, world!'

def getIP():
    if myIp == "":
        s=socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(("8.8.8.8", 80))
        ret=s.getsockname()[0]
        s.close()
    else:
        ret = myIp
    return ret

#try:
def updateScreen():
    global counter
    print ("**********Init LCD**********")

    print ("***draw text")
    draw.rectangle([(0,0),(LCD.width,LCD.height)],fill="WHITE")
    draw.text((0, 0), 'IP:', fill = "BLUE")
    draw.text((30, 0), getIP(), fill = "BLUE")
    draw.text((0, 12), "Number of call: " + str(counter), fill="BLUE")

    LCD.LCD_Clear()
    LCD.LCD_ShowImage(image,0,0)
	
def main():
    initLcdDisplay()
    updateScreen()
    app.run(debug=True, host=getIP())

if __name__ == '__main__':
    main()

