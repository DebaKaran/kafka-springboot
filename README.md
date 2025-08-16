# kafka-springboot
kafka-springboot

# Create a topic with name: test1 (Window OS)

cd <kafka_installation_folder>\bin\windows

Run below command

.\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
