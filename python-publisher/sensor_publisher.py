import paho.mqtt.client as mqtt
import time
import json
import random
import os

BROKER_ADDRESS = "mosquitto"
PORT = 1883
TOPIC = "sensor/data"

# İstemci Ayarları
client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION2, "PythonSimulator")

def on_connect(client, userdata, flags, rc, properties=None):
    if rc == 0:
        print(f"✅ Broker'a bağlanıldı: {BROKER_ADDRESS}")
    else:
        print(f"❌ Bağlantı hatası! Kodu: {rc}")

client.on_connect = on_connect

print(f"Simülatör başlatılıyor... Hedef: {BROKER_ADDRESS}")

try:
    client.connect(BROKER_ADDRESS, PORT, 60)
    client.loop_start() # Arka planda ağ trafiğini yönet

    while True:
        # Rastgele bir sensör tipi seç (Polymorphism test etmek için)
        sensor_choice = random.choice(["AirQuality", "Distance", "Ldr"])
        payload = {}

        # 1. Hava Kalitesi Verisi
        if sensor_choice == "AirQuality":
            payload = {
                "sensorType": "AirQuality", # Spring'deki Switch buna bakıyor
                "sensorName": "Salon Hava Sensörü",
                "data": round(random.uniform(50.0, 500.0), 2)
            }
        
        # 2. Mesafe Verisi
        elif sensor_choice == "Distance":
            payload = {
                "sensorType": "Distance",
                "sensorName": "Garaj Mesafe Sensörü",
                "data": round(random.uniform(10.0, 300.0), 2) # cm cinsinden
            }

        # 3. Işık (LDR) Verisi
        elif sensor_choice == "Ldr":
            payload = {
                "sensorType": "Ldr",
                "sensorName": "Bahçe Işık Sensörü",
                "data": round(random.uniform(0.0, 1023.0), 2)
            }

        # JSON'a çevir ve gönder
        json_data = json.dumps(payload)
        client.publish(TOPIC, json_data)
        
        print(f"📤 Gönderildi: {json_data}")
        
        # 2 saniye bekle (Veri akışını görebilmek için)
        time.sleep(2)

except KeyboardInterrupt:
    print("\n🛑 Simülasyon durduruldu.")
    client.loop_stop()
    client.disconnect()
except Exception as e:
    print(f"Bir hata oluştu: {e}")