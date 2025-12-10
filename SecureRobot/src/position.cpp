//#include <Arduino.h>
#include <iostream>
#include "position.hpp"
#include "aktivitetsplanering.h"

Position* init_position(void) {
    Position* pos = new (std::nothrow) Position;
    if (!pos) {
        Serial.println("Error allocating memory\n");
        return nullptr;
    }
    pos->x = 15.84f;
    pos->y = 5.04f;
    pos->theta = 270.f;
    return pos;
}

void update_position(Position* pos, const float speed, const float dt) {
    float ticks_per_sec = speed * TICKS_PER_ROTATION;
    float ticks = ticks_per_sec * dt;
    float distance = ticks * DISTANCE_PER_TICK;
    float theta_rad = pos->theta * M_PI / 180.0f;
    pos->x += distance * std::cos(theta_rad);
    pos->y += distance * std::sin(theta_rad) * -1;
}

void update_heading(Position* pos, const float speed, const float dt, const float target_theta, const int direction) {
    pos->theta += speed * dt * direction;
    if (pos->theta >= 360.0f) pos->theta -= 360.0f;
    else if (pos->theta < 0.0f) pos->theta += 360.0f;
}
