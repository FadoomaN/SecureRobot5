#ifndef WIFI_SERVER_COM_H
#define WIFI_SERVER_COM_H

#include <Arduino.h>

void wifiServerSetup();
void wifiServerLoop();                  // kan kallas i loop() eller task
bool sendToServer(const String& line);  // skickar en rad (lägger till '\n')
void receiveFromServer(void* param);
void testMsgHandle(const String& msg);

#endif
