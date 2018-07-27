#include <Arduino.h>

const int PIN_READER = 2;
const int PIN_BUTTON = 3;
const int PIN_LED = 5;

int downTime = -1;

bool isPush() { return digitalRead(PIN_BUTTON) == false; }

void setup() {
  pinMode(PIN_BUTTON, INPUT_PULLUP);
  pinMode(PIN_LED, OUTPUT);
  Serial.begin(28800);
}

void loop() {
  if (isPush()) {
    int sendValue = analogRead(PIN_READER);
    String hoge = String(sendValue) + "\n";

    Serial.println(hoge);
    digitalWrite(PIN_LED, HIGH);
  } else {
    digitalWrite(PIN_LED, LOW);
  }
}
