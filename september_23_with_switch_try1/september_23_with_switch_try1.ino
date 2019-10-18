#include <HygrometerSensor.h>
#include <SoftwareSerial.h>

HygrometerSensor analog_rain_drop(HygrometerSensor::ANALOG, A2);
HygrometerSensor analog_rain_drop1(HygrometerSensor::ANALOG, A3);
HygrometerSensor analog_rain_drop2(HygrometerSensor::ANALOG, A4);
SoftwareSerial SIM900(9, 10); 

  int RELAY1 = A1;
  int sensorA2_percentage = 0;
  int sensorA3_percentage = 0;
  int sensorA4_percentage = 0;
  int recSMS;
  int trigger_mode = 2;
  int checkhum;
  int sentSMS;
  String message = "";
  String message_status = "";
void setup()
{

  Serial.begin(19200);
   SIM900.begin(19200);
  pinMode(RELAY1, OUTPUT);
  digitalWrite(RELAY1, HIGH);
  SIM900.println("AT+CMGF=1");
  SIM900.println("AT+CNMI=2,2,0,0");


  if (!analog_rain_drop.setAnalogParameters(ANALOG_HUMIDITY_MIN, ANALOG_HUMIDITY_MAX, 800)) {
    Serial.print("Error while setting Analog parameters\n");
  }
 if (!analog_rain_drop1.setAnalogParameters(ANALOG_HUMIDITY_MIN, ANALOG_HUMIDITY_MAX, 800)) {
    Serial.print("Error while setting Analog parameters\n");
  }
   if (!analog_rain_drop2.setAnalogParameters(ANALOG_HUMIDITY_MIN, ANALOG_HUMIDITY_MAX, 800)) {
    Serial.print("Error while setting Analog parameters\n");
  }
  // Get analog parameters (optional call)


  int min, max, is_raining;
  analog_rain_drop.getAnalogParameters(min, max, is_raining);
  
  Serial.print("Minimum analog value: ");
  Serial.print(min, DEC);
  Serial.print("\nMaximum analog value: ");
  Serial.print(max, DEC);
  Serial.print("\nValue used as switch from 'dry' to 'is raining': ");
  Serial.print(is_raining, DEC);
  Serial.print("\n");

  int min1, max1, is_raining1;
  analog_rain_drop1.getAnalogParameters(min1, max1, is_raining1);
  Serial.print("Minimum analog value: ");
  Serial.print(min1, DEC);
  Serial.print("\nMaximum analog value: ");
  Serial.print(max1, DEC);
  Serial.print("\nValue used as switch from 'dry' to 'is raining': ");
  Serial.print(is_raining1, DEC);
  Serial.print("\n");

  
    int min2, max2, is_raining2;
  analog_rain_drop2.getAnalogParameters(min2, max2, is_raining2);
  Serial.print("Minimum analog value: ");
  Serial.print(min2, DEC);
  Serial.print("\nMaximum analog value: ");
  Serial.print(max2, DEC);
  Serial.print("\nValue used as switch from 'dry' to 'is raining': ");
  Serial.print(is_raining2, DEC);
  Serial.print("\n");
  
    
    delay(10000);

    Serial.print("Device Ready!");
}

void loop()
{

   recieveSMS();
    ///checking
          Serial.print("\nsensor 1 value: ");
          Serial.print(analog_rain_drop.readHumidityValue());
          Serial.print(" which means ");
          Serial.print(analog_rain_drop.readPercentageHumidity());
          Serial.print("% (");
          
            if (analog_rain_drop.isHumid()) 
            {
                sensorA2_percentage = analog_rain_drop.readPercentageHumidity();
               // Serial.print("Wet");
            } 
            else
            {           
                sensorA2_percentage = analog_rain_drop.readPercentageHumidity();
                //Serial.print("Dry");
            }
            Serial.print(")\n");
            delay(1000);
            
            Serial.print("Sensor 2 value: ");
            Serial.print(analog_rain_drop1.readHumidityValue());
            Serial.print(" which means ");
            Serial.print(analog_rain_drop1.readPercentageHumidity());
              
            Serial.print("% (");
            if (analog_rain_drop1.isHumid())
            {           
                sensorA3_percentage = analog_rain_drop1.readPercentageHumidity();
                //Serial.print("wet");
             } else 
             {
                sensorA3_percentage = analog_rain_drop1.readPercentageHumidity();
                //Serial.print("dry");
       
              }
              Serial.print(")\n");
              delay(1000);
            
              Serial.print("Sensor 3 value: ");
              Serial.print(analog_rain_drop2.readHumidityValue());
              Serial.print(" which means ");
              Serial.print(analog_rain_drop2.readPercentageHumidity());
              
              Serial.print("% (");
              if (analog_rain_drop2.isHumid()) 
              {
                sensorA4_percentage = analog_rain_drop2.readPercentageHumidity();
                //Serial.print("wet");
              } 
              else 
              {
                sensorA4_percentage = analog_rain_drop2.readPercentageHumidity();
                //Serial.print("dry");
              }
              Serial.print(")\n");
              delay(1000);

    //////////////////////////////////////////////////////////////////////switch 
  

    if(trigger_mode == 2)
    {
       //////////conditions
     /////////////////////////////////sana tama naiisip ko
    if(sensorA2_percentage < 45 && sensorA3_percentage < 45 && sensorA4_percentage < 45 && sensorA2_percentage < sensorA3_percentage && sensorA2_percentage < sensorA4_percentage )
    {
      
      ///sensor1
    
         digitalWrite(RELAY1, LOW);
      message = sensorA2_percentage;
      sentSMS += 1;
      
               if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
        
       
             delay(100);

    }
     else if(sensorA3_percentage < 45 && sensorA2_percentage < 45 && sensorA4_percentage < 45 && sensorA3_percentage < sensorA2_percentage && sensorA3_percentage < sensorA4_percentage )
    {
   
      digitalWrite(RELAY1, LOW);
      message = sensorA3_percentage;
      sentSMS += 1;
      
               if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
   

                     delay(100);
    }
     else if(sensorA4_percentage < 45 && sensorA3_percentage < 45 && sensorA2_percentage < 45 && sensorA4_percentage < sensorA3_percentage && sensorA4_percentage < sensorA2_percentage )
    {
      ///sensor2
   
        digitalWrite(RELAY1, LOW);
            
      message = sensorA4_percentage;
      sentSMS += 1;
      
               if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
  

       delay(100);
    }

      ///////////////////////////////////////////////////////////balik dito pag may mali
     else if(sensorA2_percentage < 45 && sensorA3_percentage < 45 && sensorA4_percentage < 45)
     {
        
             digitalWrite(RELAY1, LOW);
              if (sensorA2_percentage <  sensorA3_percentage && sensorA2_percentage <  sensorA4_percentage )
            {
              message = sensorA2_percentage;
              sentSMS += 1;
            }
              else if (sensorA3_percentage < sensorA2_percentage && sensorA3_percentage < sensorA4_percentage )
            {
              message = sensorA3_percentage;
              sentSMS += 1;
            }
              else if (sensorA4_percentage < sensorA2_percentage && sensorA4_percentage < sensorA3_percentage)
            {
              message = sensorA4_percentage;
              sentSMS += 1;
             }
                if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
     

       
 
     delay(100);
      }
      
      else if(sensorA2_percentage < 45 && sensorA3_percentage < 45 && sensorA4_percentage > 45)
     {

   
         digitalWrite(RELAY1, LOW);
        // Serial.print("sensor 1,2 dry sensor 3 wet");

         if(sensorA2_percentage < sensorA3_percentage)
       {
           message = sensorA2_percentage;
           sentSMS += 1;
        }
        else if(sensorA3_percentage < sensorA2_percentage)
        {
           message = sensorA3_percentage;
           sentSMS += 1;
        }
               if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
 

        delay(100);
      }
      else if(sensorA2_percentage < 45 && sensorA3_percentage > 45 && sensorA4_percentage < 45)
     {
            
               digitalWrite(RELAY1, LOW);
                   //Serial.print("sensor 1,3 dry sensor 2 wet");
                if(sensorA2_percentage < sensorA4_percentage)
               {
                  message = sensorA2_percentage;
                  sentSMS += 1;
                }
                else if(sensorA4_percentage < sensorA2_percentage)
               {
                  message = sensorA4_percentage;
                  sentSMS += 1;
                }
                       if (sentSMS == 1)
                          {
                             sendSMS();
                             sentSMS += 1;
                          }        
       

         
         delay(100);
      }
      else if(sensorA2_percentage > 70 && sensorA3_percentage > 70 && sensorA4_percentage < 45)
     {
      sentSMS = 0;
   
     ////////////////////////////////////////////////////////// trigger_mode = 0;
                  digitalWrite(RELAY1, HIGH);
                   delay(100);
      }
      else if(sensorA2_percentage < 45 && sensorA3_percentage > 70 && sensorA4_percentage > 70)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
          digitalWrite(RELAY1, HIGH);
                delay(100);
      }
      else if(sensorA2_percentage > 70 && sensorA3_percentage < 45 && sensorA4_percentage > 70)
     {
      sentSMS = 0;
   
     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);
                delay(100);
      }
           else if(sensorA2_percentage > 70 && sensorA3_percentage > 70 && sensorA4_percentage > 70)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);

                delay(100);
      }
      
      //sensor 2
      
      else if(sensorA3_percentage < 45 && sensorA2_percentage < 45 && sensorA4_percentage < 45)
     {
             
               digitalWrite(RELAY1, LOW);
               //Serial.print("sensor 2,1 and 3 dry");
                     if (sensorA3_percentage <  sensorA2_percentage && sensorA3_percentage <  sensorA4_percentage )
               {
                  message = sensorA3_percentage;
                  sentSMS += 1;
                }
                else if (sensorA4_percentage < sensorA2_percentage && sensorA4_percentage < sensorA3_percentage )
                {
                  message = sensorA4_percentage;
                  sentSMS += 1;
                }
                else if (sensorA2_percentage < sensorA3_percentage && sensorA2_percentage < sensorA4_percentage)
                {
                  message = sensorA2_percentage;
                  sentSMS += 1;
                  }
                if (sentSMS == 1)
              {
                 sendSMS();
                 sentSMS += 1;
              }
             


     delay(100);
      }
      else if(sensorA3_percentage < 45 && sensorA2_percentage < 45 && sensorA4_percentage > 45)
     {
            
                    digitalWrite(RELAY1, LOW);
                     //Serial.print("sensor 2,1 dry and 3 wet");
                     if(sensorA3_percentage < sensorA2_percentage)
                     {
                      message = sensorA3_percentage;
                       sentSMS += 1;
                      }
                      else if(sensorA2_percentage < sensorA3_percentage)
                      {
                        message = sensorA2_percentage;
                         sentSMS += 1;
                        }
                                   if (sentSMS == 1)
                    {
                       sendSMS();
                       sentSMS += 1;
                    }
           

     delay(100);
      }
            else if(sensorA3_percentage < 45 && sensorA2_percentage > 45 && sensorA4_percentage < 45)
     {

       
             digitalWrite(RELAY1, LOW);
               //Serial.print("sensor 2,3 and 1 wet");
               if(sensorA3_percentage < sensorA4_percentage )
               {
                  message = sensorA3_percentage;
                  sentSMS += 1;
                }
                else if(sensorA4_percentage < sensorA3_percentage )
                {
                  message = sensorA3_percentage;
                  sentSMS += 1;
                 }
                if (sentSMS == 1)
              {
                 sendSMS();
                 sentSMS += 1;
              }


     delay(100);
      }
      else if(sensorA3_percentage > 70 && sensorA2_percentage > 70 && sensorA4_percentage < 45)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);
                delay(100);
      }
      else if(sensorA3_percentage > 70 && sensorA2_percentage < 45 && sensorA4_percentage > 70)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);

                delay(100);
      }

      else if(sensorA3_percentage > 70 && sensorA2_percentage > 70 && sensorA4_percentage > 70)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);
                delay(100);
      }

      //sensor 3

            else if(sensorA4_percentage < 45 && sensorA2_percentage < 45 && sensorA3_percentage < 45)
     {
                  ///////////////////////////////////triggers
       
          digitalWrite(RELAY1, LOW);
            // Serial.print("sensor 3,1 and 2 dry");
              if(sensorA4_percentage < sensorA2_percentage && sensorA4_percentage < sensorA3_percentage)
              {
                message = sensorA4_percentage;
                 sentSMS += 1;
              }
               else if(sensorA3_percentage < sensorA2_percentage && sensorA3_percentage < sensorA2_percentage)
              {
                message = sensorA3_percentage;
                 sentSMS += 1;
                }
                        if(sensorA2_percentage < sensorA4_percentage && sensorA2_percentage < sensorA3_percentage)
              {
                message = sensorA2_percentage;
                 sentSMS += 1;
                }
             if (sentSMS == 1)
            {
               sendSMS();
               sentSMS += 1;
            }
       


    delay(100);
      }

      else if(sensorA4_percentage < 45 && sensorA2_percentage < 45 && sensorA3_percentage > 45)
     {
   
         digitalWrite(RELAY1, LOW);
             //Serial.print("sensor 3,1 dry and 2 wet");
             if(sensorA4_percentage < sensorA2_percentage)
             {
              message = sensorA4_percentage;
              sentSMS += 1;
              }
            else if(sensorA2_percentage < sensorA4_percentage)
             {
              message = sensorA2_percentage;
              sentSMS += 1;
              }
            if (sentSMS == 1)
            {
               sendSMS();
               sentSMS += 1;
            }

       
  delay(100);
      }
      
      else if(sensorA4_percentage < 45 && sensorA2_percentage > 45 && sensorA3_percentage < 45)
     {
      
 
        
      digitalWrite(RELAY1, LOW);
                 //Serial.print("sensor 3,2 dry and 1 wet");
                        if(sensorA4_percentage < sensorA2_percentage)
                       {
                        message = sensorA4_percentage;
                         sentSMS += 1;
                        }
                      else if(sensorA3_percentage < sensorA4_percentage)
                       {
                        message = sensorA3_percentage;
                         sentSMS += 1;
                        }
                 if (sentSMS == 1)
                {
                   sendSMS();
                   sentSMS += 1;
                }
       

delay(100);
      }
                
    
            else if(sensorA4_percentage > 70 && sensorA2_percentage > 70 && sensorA3_percentage < 45)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);

                delay(100);
      }
            else if(sensorA4_percentage > 70 && sensorA2_percentage < 45 && sensorA3_percentage > 70)
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);
                delay(100);
     }
            else if(sensorA4_percentage < 45 && sensorA2_percentage > 70 && sensorA3_percentage > 70)//
     {
      sentSMS = 0;

     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);

       delay(100);
      }
      else if(sensorA4_percentage > 70 && sensorA2_percentage > 70 && sensorA3_percentage > 70)
     {
      sentSMS = 0;
 
     ////////////////////////////////////////////////////////// trigger_mode = 0;
                 digitalWrite(RELAY1, HIGH);

        delay(100);
       ///Serial.print("sensor 3,1,2 wet");              
      }
     }
    else if(trigger_mode == 0)
    {
      digitalWrite(RELAY1, HIGH);
      delay(100);


     if(sensorA2_percentage < 45 && sensorA3_percentage < 45  && sensorA4_percentage > 45 )
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, LOW);
      delay(100);
      }
      else      if(sensorA3_percentage < 45 && sensorA2_percentage < 45  && sensorA4_percentage > 45 )
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, LOW);
      delay(100);
      }
            else      if(sensorA4_percentage < 45 && sensorA3_percentage < 45  && sensorA2_percentage > 45 )
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, LOW);
      delay(100);
      }
      else if(sensorA2_percentage > 70 && sensorA3_percentage > 70)
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      else if(sensorA3_percentage < 45 && sensorA2_percentage < 45 )
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, LOW);
      delay(100);
      }
      else if(sensorA4_percentage < 45 && sensorA2_percentage < 45 )
      {
         trigger_mode = 2;
               digitalWrite(RELAY1, LOW);
      delay(100);
      }
      
      
    }
    else if(trigger_mode == 1)
    {
      digitalWrite(RELAY1, LOW);
      delay(100);
       if(sensorA2_percentage > 70 && sensorA3_percentage > 70 && sensorA4_percentage < 45)
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      else        if(sensorA3_percentage > 70 && sensorA2_percentage > 70 && sensorA4_percentage < 45)
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, HIGH);
      delay(100);
      }
            else        if(sensorA4_percentage > 70 && sensorA3_percentage > 70 && sensorA2_percentage < 45)
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      else if(sensorA2_percentage > 70 && sensorA3_percentage > 70)
      {
        trigger_mode = 2;
              digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      
      else if(sensorA3_percentage > 70 && sensorA2_percentage > 70)
      {
         trigger_mode = 2;
               digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      else if(sensorA4_percentage > 70 && sensorA2_percentage > 70)
      {
         trigger_mode = 2;
               digitalWrite(RELAY1, HIGH);
      delay(100);
      }
      
     
      
    }


    
   recieveSMS();
    
         /////////////////////////////////////////////////////////////////// ///check status
   if( recSMS == 1)
   {
    /////////////wet
      if (sensorA2_percentage >= sensorA3_percentage && sensorA2_percentage >= sensorA4_percentage)
      {
        message_status = sensorA2_percentage;
      }
      else if(sensorA3_percentage >= sensorA2_percentage && sensorA3_percentage >= sensorA4_percentage)
      {
          message_status = sensorA3_percentage;
      }
      else if(sensorA4_percentage >= sensorA2_percentage && sensorA4_percentage >= sensorA3_percentage)
      {
          message_status = sensorA4_percentage;
      }
//      else       if (sensorA2_percentage <= sensorA3_percentage && sensorA2_percentage <= sensorA4_percentage)
//      {
//        message_status = sensorA2_percentage;
//      }
//      else if(sensorA3_percentage <= sensorA2_percentage && sensorA3_percentage <= sensorA4_percentage)
//      {
//          message_status = sensorA3_percentage;
//      }
//      else if(sensorA4_percentage <= sensorA2_percentage && sensorA4_percentage <= sensorA3_percentage)
//      {
//          message_status = sensorA4_percentage;
//      }
        sendSMS_status();
         recSMS = 0;
         delay(100);
   }

   
recieveSMS();
delay(100);
}

void sendSMS()
{
  SIM900.print("AT+CMGF=1\r"); 
  delay(100);
 SIM900.println("AT + CMGS = \"+639958406050\"");   //samsung phone
  //SIM900.println("AT + CMGS = \"+639773542865\""); //sony phone
  
  delay(100);
  
  SIM900.println(message); 
  delay(100);
  SIM900.println((char)26); 
  delay(100);
  SIM900.println();
  delay(2000); 
  }

void sendSMS_status()
{
  SIM900.print("AT+CMGF=1\r"); 
  delay(100);
 SIM900.println("AT + CMGS = \"+639958406050\"");   //samsung phone
  //SIM900.println("AT + CMGS = \"+639773542865\""); //sony phone
 
  delay(100);
  
  SIM900.println(message_status); 
  delay(100);
  SIM900.println((char)26); 
  delay(100);
  SIM900.println();
  delay(2000);
  }
  
void recieveSMS()
{
   if (SIM900.available()>0)
   {
   
        String k=SIM900.readString();
          if(k.indexOf("Check") > -1 || k.indexOf("CHECK") > -1 || k.indexOf("check") > -1)
        {
            recSMS = 1; 
             delay(100);
             SIM900.flush();
        }
        else if(k.indexOf("On") > -1 || k.indexOf("On") > -1 || k.indexOf("on") > -1 || k.indexOf("ON") > -1)
        {
            trigger_mode = 1;
            delay(100);
              SIM900.flush();
        }
        else if(k.indexOf("Off") > -1 || k.indexOf("off") > -1 || k.indexOf("OFF") > -1)
        {
            trigger_mode = 0;
             delay(100);
               SIM900.flush();
        }
    }
  }

