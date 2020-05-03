import LCD_1in44
import LCD_Config

from PIL import Image,ImageDraw,ImageFont,ImageColor
from PIL.FontFile import WIDTH

class DisplayHandler:
    
    def __init__(self):
        global width
        global height
        global lineHeight
        
        width = 128
        height = 128
    
        lineHeight = 12
        
        # 240x240 display with hardware SPI:
        global disp
        disp = LCD_1in44.LCD()
        Lcd_ScanDir = LCD_1in44.SCAN_DIR_DFT  #SCAN_DIR_DFT = D2U_L2R
        disp.LCD_Init(Lcd_ScanDir)
        disp.LCD_Clear()

        # Create blank image for drawing.
        # Make sure to create image with mode '1' for 1-bit color.
        global image
        image = Image.new('RGB', (width, height))

        # Get drawing object to draw on image.
        global draw
        draw = ImageDraw.Draw(image)

        # Draw a black filled box to clear the image.
        draw.rectangle((0,0,width,height), outline=0, fill=0)
        disp.LCD_ShowImage(image,0,0)

    def DispHandler_clear(self):
        #disp.LCD_Clear()
        draw.rectangle((0,0,width,height), outline=0, fill=0)

    def DispHandler_write(self, message, linePosition):
        draw.rectangle([(0,0),(10,10)], fill="RED")
        draw.text((0, linePosition * lineHeight), message, fill="BLUE")
        #disp.LCD_Clear()
        disp.LCD_ShowImage(image,0,0)
