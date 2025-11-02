## House Manager

Проект по CSCB525 Приложно програмиране с Java към НБУ.

### Описание

CLI приложение за управление на входни такси за жилищни сгради. Поддържат се следните функции:

- Управление на сгради
- Управление на апартаменти
- Управление на живущи и домашни любимци
- Управление на служители обслужващи сградите
- Изчисляване на месечни такси
- Генериране на отчети

### Технологии

- Java 21
- Gradle
- JUnit 5
- Micronaut + PicoCLI

### Инсталация

1. Клониране на текущото хранилище
2. Инсталиране на Gradle (ако не е инсталиран)
3. Навигиране до директорията на проекта
4. Изпълнение на `./gradlew assemble`

### Използване

> Изисква се първоначална "настройка" на фирмата оперираща сградите:
> ```bash
> java -jar build/libs/housemanager-0.1-all.jar company:setup --name="My Test Company" --address="123 Main St, City, Country" 
> ```

#### Примерни команди:

Добавяне на служител:

```bash
java -jar build/libs/housemanager-0.1-all.jar employees:create --firstName=John --lastName=Doe
```

### Demo

```bash
./gradlew build && \
java -jar build/libs/housemanager-0.1-all.jar company:setup --name="Demo Company" --address="456 Demo St, City, Country" && \
java -jar build/libs/housemanager-0.1-all.jar employees:create --firstName=Alice --lastName=Smith && \
java -jar build/libs/housemanager-0.1-all.jar employees:create --firstName=John --lastName=Doe && \
java -jar build/libs/housemanager-0.1-all.jar buildings:create --name="Sunset Apartments" --address="789 Sunset Blvd, City, Country" --employeeId=1 --commonArea=50.0 --feePerSqM=10 --feePerResident=12.50 --feePerPet=9.99 && \
java -jar build/libs/housemanager-0.1-all.jar buildings:list && \
java -jar build/libs/housemanager-0.1-all.jar buildings:update --id=1 --name="Sunrise Apartments" --address="789 Sunrise Blvd, City, Country" --employeeId=2 --commonArea=60.0  --feePerSqM=10 --feePerResident=12.50 --feePerPet=9.99 && \
java -jar build/libs/housemanager-0.1-all.jar buildings:list && \
java -jar build/libs/housemanager-0.1-all.jar apartments:create --buildingId=1 --name="1A" --floor=1 --area=79.88 --residents=2 --pets=1  && \
java -jar build/libs/housemanager-0.1-all.jar apartments:list --buildingId=1 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:update --id=1 --name="1B" --floor=2 --area=85.50 --residents=3 --pets=0 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:list --buildingId=1 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:delete --id=1 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:list --buildingId=1 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:create --buildingId=1 --name="1A" --floor=1 --area=79.88 --residents=2 --pets=1 && \
java -jar build/libs/housemanager-0.1-all.jar fees:issue --buildingId=1 --period="2025-10"

```