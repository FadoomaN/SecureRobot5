#include "WifiServerCom.h"
#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiUDP.h>

// TODO: byt till ditt riktiga nätverk + server-IP
static const char* WIFI_SSID      = "FadiHS";
static const char* WIFI_PASSWORD  = "fadi2003";
static const char* SERVER_IP      = "192.168.98.153";
static const uint16_t SERVER_PORT = 5000;

static WiFiClient serverClient;
static unsigned long lastConnectAttempt = 0;
static const unsigned long RECONNECT_INTERVAL_MS = 5000;

static WiFiServer rxServer(SERVER_PORT);
WiFiUDP udp;
static bool rxServerStarted = false;

static void ensureWifiConnected() {
    if (WiFi.status() == WL_CONNECTED) return;

    WiFi.mode(WIFI_STA);
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

    Serial.print("[WiFi] Connecting to '");
    Serial.print(WIFI_SSID);
    Serial.println("'...");
    
    int attempts = 0;
    int maxAttempts = 40;  // 20 seconds (500ms * 40)
    while (WiFi.status() != WL_CONNECTED && attempts < maxAttempts) {
        delay(500);
        Serial.print(".");
        attempts++;
    }
    Serial.println();
    
    if (WiFi.status() == WL_CONNECTED) {
        Serial.print("[WiFi] *** CONNECTED *** IP: ");
        Serial.println(WiFi.localIP());
    } else {
        Serial.print("[WiFi] *** CONNECTION FAILED after ");
        Serial.print(attempts * 500);
        Serial.println("ms ***");
    }
}


static void ensureServerConnected() {
    if (serverClient.connected()) return;

    unsigned long now = millis();
    if (now - lastConnectAttempt < RECONNECT_INTERVAL_MS) return;
    lastConnectAttempt = now;

    Serial.print("Connecting to server ");
    Serial.print(SERVER_IP);
    Serial.print(":");
    Serial.println(SERVER_PORT);

    if (!serverClient.connect(SERVER_IP, SERVER_PORT)) {
        Serial.println("Server connect failed");
        return;
    }
    Serial.println("Connected to server");
}


void wifiServerSetup() {
    // Don't try to connect in setup - it will block
    // Let the tasks handle WiFi connection and server startup
    Serial.println("[Setup] WiFi server setup complete - tasks will handle connection");
}


void wifiServerLoop() {
    // Hålla kopplingen vid liv
    if (WiFi.status() != WL_CONNECTED) {
        ensureWifiConnected();
    }
    if (!serverClient.connected()) {
        ensureServerConnected();
    }
}


bool sendToServer(const String& line) {
    if (WiFi.status() != WL_CONNECTED) {
        Serial.println("sendToServer: no WiFi");
        return false;
    }
    if (!serverClient.connected()) {
        ensureServerConnected();
        if (!serverClient.connected()) {
            Serial.println("sendToServer: server not connected");
            return false;
        }
    }

    String withNewline = line;
    if (!withNewline.endsWith("\n")) withNewline += "\n";

    size_t written = serverClient.print(withNewline);
    return written == withNewline.length();
}


void receiveFromServer(void* param) {
  (void)param;

  // Vänta på WiFi
  while (WiFi.status() != WL_CONNECTED) {
    vTaskDelay(pdMS_TO_TICKS(200));
  }

  udp.begin(6000);
  Serial.printf("[UDP] Listening on %u (IP %s)\n",
                6000, WiFi.localIP().toString().c_str());

  char buf[512];

  for (;;) {
    int packetSize = udp.parsePacket();
    if (packetSize > 0) {
      int len = udp.read(buf, sizeof(buf) - 1);
      if (len > 0) {
        buf[len] = '\0';

        String msg = String(buf);

        Serial.print("[UDP] Received String: ");
        Serial.println(msg);

        //Hantera medelande

      }
    }
    vTaskDelay(pdMS_TO_TICKS(5));
  }
}


void testMsgHandle(const String& msg) {
    Serial.println("Handling message: " + msg);

    
}