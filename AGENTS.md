## Cursor Cloud specific instructions

This is **Sky Take-Out (苍穹外卖)**, a Java Spring Boot 2.7.3 food delivery backend (multi-module Maven project).

### System dependencies (pre-installed in snapshot)
- JDK 17 (`/usr/lib/jvm/java-17-openjdk-amd64`)
- Maven 3.8.7
- MySQL 8.0
- Redis 7.0

### Starting services before running the app

```bash
# Start MySQL
sudo mysqld --user=mysql --daemonize

# Start Redis
sudo redis-server --daemonize yes

# Verify
mysql -u root -proot -h 127.0.0.1 -e "SELECT 1;"
redis-cli ping
```

### Running the application

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
cd /workspace/sky-server
mvn spring-boot:run -DskipTests
```

App runs on port 8080. Swagger UI: http://localhost:8080/doc.html

### Build

```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
cd /workspace
mvn clean install -DskipTests
```

### Non-obvious gotchas

- `application-dev.yml` is git-ignored. It must exist at `sky-server/src/main/resources/application-dev.yml` with MySQL/Redis connection info plus Alibaba Cloud OSS placeholders.
- The `employee` table stores MD5-hashed passwords. The seed data from `sky.sql` has plaintext `123456`; you must update it to the MD5 hash `e10adc3949ba59abbe56e057f20f883e` for login to work.
- MySQL socket access may fail in this environment; use TCP (`-h 127.0.0.1`) instead.
- JAVA_HOME must point to JDK 17 (not 21) for Spring Boot 2.7.3 compatibility. It is set in `~/.bashrc`.
- WeChat Pay and Alibaba Cloud OSS are optional external integrations; placeholder config values are sufficient for local dev.
