# Sử dụng image chính thức của Java
FROM eclipse-temurin:21-jdk-alpine

# Tạo thư mục làm việc trong container
WORKDIR /database_service

# Copy file JAR vào container (nhớ đổi tên file JAR nếu cần)
COPY target/databaseService-0.0.1-SNAPSHOT.jar databaseService.jar

# Expose port (nếu app chạy ở 8080)
EXPOSE 8080

# Lệnh để chạy ứng dụng
ENTRYPOINT ["java", "-jar", "databaseService.jar"]
