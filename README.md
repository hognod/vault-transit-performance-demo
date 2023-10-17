# vault-transit-performance-demo

## Vault 환경 구성

### Transit 활성화

```bash
vault secrets enable transit
```

### Transit 키 생성

```bash
vault write -f transit/keys/ds-poc type=aes256-gcm96
```

### Transit 생성된 키 확인

```bash
vault list transit/keys
```



## MySQL 환경 구성

### MySQL Connect

```bash
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
```

```bash
mysql -h $MYSQL_HOST -P $MYSQL_PORT -u root
```

### MySQL Database create

```mysql
CREATE DATABASE VaultData;
```

### MySQL User create

```mysql
CREATE USER app@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES
    ON VaultData.*
    TO app@'%';

FLUSH PRIVILEGES;
```

### MySQL Table create

```mysql
USE VaultData;

create table vault_data (
  id int unsigned auto_increment not null,
  data varchar(600) not null,
  date_created timestamp default now(),
  primary key (id)
);
```



## Java Run

### 환경변수 구성

```bash
export MYSQL_HOST=127.0.0.1
export MYSQL_PORT=3306
export MYSQL_DB_NAME=VaultData
export MYSQL_USERNAME=app
export MYSQL_USERPW=password
export VAULT_HOST=127.0.0.1
export VAULT_PORT=8200
export VAULT_TOKEN=root
export VAULT_TRANSIT_KEY_NAME=ds-poc
```

### Gradle build

```bash
cd application
```

```bash
gradle build
```

### 실행

```bash
cd build/libs
```

```bash
java -jar transit-1.0-SNAPSHOT.jar
```

```bash
1. App -> Vault -> DB
2. DB -> Vault -> App
```

* `1` 입력하여 Encrypt 데이터 DB 저장
* `2` 입력하여 Decrypt 데이터 출력
