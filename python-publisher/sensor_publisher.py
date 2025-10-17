import paho.mqtt.client as mqtt
import time
import json
import random

# Mosquitto Broker Ayarları
BROKER_ADDRESS = "mosquitto"
PORT = 1883
TOPIC = "sensor/data"

# Client oluştur ve broker'a bağlan
client = mqtt.Client(mqtt.CallbackAPIVersion.VERSION1,"SensorSimulator")
client.connect(BROKER_ADDRESS, PORT)

print("Simülatör başlatıldı. Veri gönderiliyor...")

try:
    while True:
        # Rastgele sensör verileri üret
        temperature = round(random.uniform(20.0, 30.0), 2)
        humidity = round(random.uniform(40.0, 60.0), 2)
        Pressure = round(random.uniform(1000.0, 1025.0), 2)
        AirQuality = round(random.uniform(0.0, 500.0), 2)
        Co2 = round(random.uniform(300.0, 600.0), 2)
        
        # Veriyi JSON formatına çevir
        payload = {
            "deviceId": "sensor-001",
            "temperature": temperature,
            "humidity": humidity,
            "timestamp": time.time()
        }
        
        # JSON'ı string'e çevirerek yayınla
        client.publish(TOPIC, json.dumps(payload))
        
        print(f"Gönderildi -> {json.dumps(payload)}")
        
        # 5 saniye bekle
        time.sleep(5)

except KeyboardInterrupt:
    print("Simülasyon durduruldu.")
    client.disconnect()