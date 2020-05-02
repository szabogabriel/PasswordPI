# Setup RaspberryPI as USB keyboard

The setup of the key emulation mode is based on [this](https://randomnerdtutorials.com/raspberry-pi-zero-usb-keyboard-hid/) tutorial.

Add the necessary modules and drivers.

```Bash
pi@raspberrypi:~ $ echo "dtoverlay=dwc2" | sudo tee -a /boot/config.txt
pi@raspberrypi:~ $ echo "dwc2" | sudo tee -a /etc/modules
pi@raspberrypi:~ $ sudo echo "libcomposite" | sudo tee -a /etc/modules
```

Then create a script file, which is going to configure our USB device.

```Bash
pi@raspberrypi:~ $ sudo touch /usr/bin/isticktoit_usb
pi@raspberrypi:~ $ sudo chmod +x /usr/bin/isticktoit_usb
```

and register it in `/etc/rc.local` by adding the `/usr/bin/isticktoit_usb # libcomposite configuration` line to the end of the file, before the `return 0` part.

Now you can create the content of the registration script as well.

```Bash
#!/bin/bash
cd /sys/kernel/config/usb_gadget/
mkdir -p isticktoit
cd isticktoit
echo 0x1d6b > idVendor # Linux Foundation
echo 0x0104 > idProduct # Multifunction Composite Gadget
echo 0x0100 > bcdDevice # v1.0.0
echo 0x0200 > bcdUSB # USB2
mkdir -p strings/0x409
echo "fedcba9876543210" > strings/0x409/serialnumber
echo "PasswordPI" > strings/0x409/manufacturer
echo "iSticktoit.net USB Device" > strings/0x409/product
mkdir -p configs/c.1/strings/0x409
echo "Config 1: ECM network" > configs/c.1/strings/0x409/configuration
echo 250 > configs/c.1/MaxPower

# Add functions here
mkdir -p functions/hid.usb0
echo 1 > functions/hid.usb0/protocol
echo 1 > functions/hid.usb0/subclass
echo 8 > functions/hid.usb0/report_length
echo -ne \\x05\\x01\\x09\\x06\\xa1\\x01\\x05\\x07\\x19\\xe0\\x29\\xe7\\x15\\x00\\x25\\x01\\x75\\x01\\x95\\x08\\x81\\x02\\x95\\x01\\x75\\x08\\x81\\x03\\x95\\x05\\x75\\x01\\x05\\x08\\x19\\x01\\x29\\x05\\x91\\x02\\x95\\x01\\x75\\x03\\x91\\x03\\x95\\x06\\x75\\x08\\x15\\x00\\x25\\x65\\x05\\x07\\x19\\x00\\x29\\x65\\x81\\x00\\xc0 > functions/hid.usb0/report_desc
ln -s functions/hid.usb0 configs/c.1/
# End functions

ls /sys/class/udc > UDC
```

If everything was done correctly, then upon connecting the Raspberry PI to a PC, it should be recognized as a USB keyboard and we should be able to send USB HIB packets to the computer. The packet is 8 bytes long and is formatted as follows.

* byte 0 - control byte. It contains the status of the control keys as bit flags (Shift, Ctrl etc).
 - bit 0 - Left CTRL
 - bit 1 - Left SHIFT
 - bit 2 - Left ALT
 - bit 3 - Left GUI (Windows Key, Super Key)
 - bit 4 - Right CTRL
 - bit 5 - Right SHIFT
 - bit 6 - Right ALT
 - bit 7 - Right GUI (Windows Key, Super Key)
* byte 1 - reserved, must be empty (0x00)
* byte (2-7) - Pressed  key codes

Details regarding the USB HID protocol can be found [here](https://wiki.osdev.org/USB_Human_Interface_Devices).
