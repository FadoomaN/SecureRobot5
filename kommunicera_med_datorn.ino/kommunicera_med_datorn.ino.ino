#include <WiFi.h>


const char* ssid     = "Galaxy...y";
const char* host     = "10.188.219.40"; 
const uint16_t port  = 5000;

int node_1[] = {183, 890};
int node_2[] = {475, 890};
int node_3[] = {183, 1444};
int node_4[] = {475, 1444};


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
    sleep(10);
    client.println("C:183,890");
    while (client.connected()) {
     
    }
    Serial.println("socket stängd");
    client.stop();
  } else {
    Serial.println("Kunde inte ansluta till servern");
  }
}


void loop(){}
