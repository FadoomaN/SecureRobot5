#include <WiFi.h>


const char* ssid     = "Galaxy...y";
const char* host     = "10.197.83.40"; 
const uint16_t port  = 5000;

void setup() {
  Serial.begin(9600);
  WiFi.begin(ssid);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nAnsluten till WiFi");

  WiFiClient client;
  if (client.connect(host, port)) {
    Serial.println("Ansluten till servern");
    client.println("Hej från ESP32!");
    while (client.connected()) {
      if (client.available()) {
        String line = client.readStringUntil('\n');
        Serial.println("Meddelande från server: " + line);
      }
    }
    client.stop();
  } else {
    Serial.println("Kunde inte ansluta till servern");
  }
}

void loop() {}

