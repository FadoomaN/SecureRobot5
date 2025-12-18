#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Wed Dec 17 15:09:31 2025

@author: jacob
"""

import socket

print("ip:", socket.gethostbyname(socket.gethostname()))

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_socket.bind(('0.0.0.0', 5000))  # Lyssnar på alla IP
server_socket.listen(1)
print("Servern lyssnar på port 5000...")

client_socket, addr = server_socket.accept()
print(f"ESP32 anslöt: {addr}")
data = client_socket.recv(1024)
print(f"Mottaget från ESP32: {data.decode()}")
client_socket.send("Hej ESP32!".encode())
client_socket.close()
    

