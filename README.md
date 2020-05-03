# Password PI

This project is a hardware password manager. It acts as a keyboard emulator for the host machine and sends the password as keyboard strokes.

## Necessary hardware

 - Raspberry PI Zero W: https://www.raspberrypi.org/products/raspberry-pi-zero-w/
 - Waveshare display with keyboards: https://www.waveshare.com/product/raspberry-pi/displays/lcd-oled/1.44inch-lcd-hat.htm

## Necessary software
  - Raspbian OS (Ubuntu-based)
  - Waveshare libraries
  - Python 3 defaultly installed with Raspbian OS
 
## List of functions to be implemented
  - Emulation of a USB keyboard
     - Password PI acts as a USB keyboard
     - Works on any device supporting USB keyboards (Linux, Windows and Apple PCs, even smartphones and tablets)
     - Since USB-HID protocol only sends state of the keyboard LEDs, there is no trivial way for an infected system to "steal" the passwords from the Password PI vault.
     - Since Password PI is according to the USB-HID protocol sends only list of pressed and released keys, but not its ASCII value, (e.g. the character `a` is `0x0000040000000000` and not its ASCII value `0x61`, and in case of `A` it is `0x0200040000000000` (bitflag for shift + character `a`)), it is necessary to implement language layouts (En, De, Sk, ...).
     - It is used to send passwords as if it was typed on a keyboard from the user.
 
  - Vault of encrypted passwords
     - It is possible to use any encryption algorithm supported available as Python libraries (or self implement one).
     - The passwords are stored locally on the Password PI. It is currently not decided, whether it will be stored as a single file (like in KeePass) or in a separate file for every password. A standalone SQLite database could be used as well.
     - Available (saved) passwords are displayed directly on the Password PI's display, where it is possible to select the one to be sent to the host machine.
 
  - Securing the vault via a central master password
     - The master password must be used to decode all the passwords stored in the given vault.
     - It must be entered via the keys directly available on the Password PI (Joystick and the 3 buttons)
     - It could be a pattern (just like on smartphones), or a combination of buttons. No concrete decision have been met yet.
 
  - Simplified management via the Password PI interface
     - Done directly on the Password PI device, without the usage of the main (host) device.
     - Turning the WiFi on and off and its configuration. Also providing information (domain name, IP address etc.).
     - Action to 'send' the password (emulation of entering the password on the keyboard and sending it to the host system).
     - Allowing and denying access via the web interface.
 
  - Complex management vie the web interface
     - It is not yet decided, whether Password PI should act as an access point, or rather connect to an existing network.
     - The web interface should provide support when creating profiles and generating passwords. It still happens on the Password PI device, not via any online service. The reason for this interface is to ease up entering information.
     - If support implemented, it should enable import of existing password vaults.
     - Should the Password PI connect to an existing network with access to the internet, it could be used to update the local vault centrally (e.g. project-related profile management).
 
## Additional, non essencial functions (nice to have's)
  - Smart Card as a password storage (so only the functionalty is implemented on the Password PI, everything is stored on the Smart Card)
  - 2FA support. Entering QR code via the web interface hence using Password PI as a second factor for generating time-based pins.
  - local agent installed on the host system. It could communicate with Password PI via the REST API. This could be useful when switching between keyboard layouts, thus notifying the Password PI.
