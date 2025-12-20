#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Dec 20 15:49:16 2025

@author: jacob
"""
#biblotek för visualisering
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle
from PIL import Image
import time
import random
import numpy as np

#biblotek för kommunikation
import socket
import sys




# Gör matplotlib icke-blockerande
plt.ion()

# Läs in bilden
img = Image.open("louvren.png")

# Skapa figur och axlar
fig, ax = plt.subplots()
ax.imshow(img)
ax.axis("off")
plt.pause(0.1)

def visual_robot(coordinate_x, coordiante_y):
    rectangle = Rectangle((coordinate_x, coordiante_y), 10, 5, linewidth=3, edgecolor='red', facecolor='none', angle=0)
    return rectangle


# blue dot = regular nevigation node
dotA1 = ax.scatter([183], [890], color='blue', s=10)
dotA2 = ax.scatter([475], [890], color='blue', s=10)
dotA3 = ax.scatter([183], [1444], color='blue', s=10)
dotA4 = ax.scatter([475], [1444], color='blue', s=10)
plt.pause(0.1)



robot = visual_robot(183, 890)
ax.add_patch(robot)





#man måste vara ansluten till mobilens hotspot.
print("hotspot ip:", socket.gethostbyname(socket.gethostname()))


#datorn sätts till att lyssna på inkommande anslutningar till port 5000.
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server_socket.bind(('0.0.0.0', 5000))
server_socket.listen(1)
print("Servern lyssnar på port 5000...")


#socket skapas och ett medelande från en esp tas emot.
client_socket, addr = server_socket.accept()
print(f"ESP32 anslöt: {addr}")
data = client_socket.recv(1024)
print(f"Mottaget från ESP32: {data.decode()}")

#medelande till esp skickas där anslutningen bekräftas.
client_socket.send("Socket skapad till datorn".encode())
data = client_socket.recv(1024)
print(f"Mottaget från ESP32: {data.decode()}")




#loop dä data läses in och behandlas. Loopen avslutas om man trycker ctrl c över terminalen. 
while True:
    try:
        data = client_socket.recv(1024)
        print(f"Mottaget från ESP32: {data.decode()}")
        instruction = data.decode().split(":")
        
        
        
        print(instruction[0], "    ", instruction[1])
    except:
        client_socket.close()
        print("socket avslutad")
        sys.exit()
    
    

