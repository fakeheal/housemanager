## House Manager

Проект по CSCB525 Приложно програмиране с Java към НБУ.

```bash
>_ HOUSE MANAGER
+-----------------------------+
| []  []  []  []  []  []  []  |
| []  []  []  []  []  []  []  |
| []  []  []  []  []  []  []  |
| []  []  []  []  []  []  []  |
+-----------------------------+
| Collect • Maintain • Manage |
+-----------------------------+
```

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
java -jar build/libs/housemanager-0.1-all.jar apartments:create --buildingId=1 --name="1B" --floor=1 --area=45.20 --residents=2 --pets=0 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:create --buildingId=1 --name="2A" --floor=2 --area=79.88 --residents=4 --pets=2 && \
java -jar build/libs/housemanager-0.1-all.jar apartments:create --buildingId=1 --name="2B" --floor=2 --area=25.48 --residents=1 --pets=3 && \
java -jar build/libs/housemanager-0.1-all.jar fees:issue --buildingId=1 --period="2025-10" && \
java -jar build/libs/housemanager-0.1-all.jar fees:list --buildingId=1 && \
 java -jar build/libs/housemanager-0.1-all.jar fees:pay --id=2 && \
java -jar build/libs/housemanager-0.1-all.jar fees:list --buildingId=1
```

Output:

```bash
Company 'Demo Company' at '456 Demo St, City, Country' has been set up successfully.
Employee created successfully!
Employee created successfully!
Building created successfully!
ID             Name                Address                                 Common Area    Managed By     Floors         Fee (per m²)   Fee (per Resident)  Fee (per Pet)  
1              Sunset Apartments   789 Sunset Blvd, City, Country          50.0 m²        Alice Smith    0              10.0 BGN       12.5 BGN            9.99 BGN       
Building updated successfully!
ID             Name                Address                                 Common Area    Managed By     Floors         Fee (per m²)   Fee (per Resident)  Fee (per Pet)  
1              Sunrise Apartments  789 Sunrise Blvd, City, Country         60.0 m²        John Doe       0              10.0 BGN       12.5 BGN            9.99 BGN       
Apartment created successfully!
ID             Name      Floor     Area           Residents      Pets           
1              1A        1         79.88 m²       2              1              
Apartment updated successfully!
ID             Name      Floor     Area           Residents      Pets           
1              1B        2         85.5 m²        3              0              
Apartment deleted successfully!
ID             Name      Floor     Area           Residents      Pets           
Apartment created successfully!
Apartment created successfully!
Apartment created successfully!
Apartment created successfully!
Fees issued successfully for building ID: 1
ID             Period    Apartment Status         Amount         Paid On        
1              2025-10   1-1A      PENDING        833.78 BGN     N/A            
2              2025-10   1-1B      PENDING        477.0 BGN      N/A            
3              2025-10   2-2A      PENDING        868.77 BGN     N/A            
4              2025-10   2-2B      PENDING        297.26 BGN     N/A            
Fee with ID 2 has been marked as paid.
ID             Period    Apartment Status         Amount         Paid On        
1              2025-10   1-1A      PENDING        833.78 BGN     N/A            
2              2025-10   1-1B      PAID           477.0 BGN      2025-11-02T16:29:23.752112Z
3              2025-10   2-2A      PENDING        868.77 BGN     N/A            
4              2025-10   2-2B      PENDING        297.26 BGN     N/A            

```