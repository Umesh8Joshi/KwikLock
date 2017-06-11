#include<Servo.h>
char data = 0;
Servo myServo;
void setup() {
  Serial.begin(9600);
  myServo.attach(9);
}
void loop() {
  if(Serial.available() > 0)
  {
    data = Serial.read();
    Serial.print(data);
    Serial.print("\n");
    if(data == '1')
    {
      myServo.write(180);
    }
    else if(data ==  '0')
    {
      myServo.write(90);
    }
  }
}
