# ApiAccess - Task CRUD Application

This project implements a full CRUD Android app that connects to a Spring Boot REST API backed by MySQL. The model used is **Task** (id, title, description).

## Project Structure

```
ApiAccess/
├── empl/                    # Spring Boot backend
│   ├── empldata/            # JPA entities, repositories, services
│   └── sbemplms/            # REST API, controllers
└── EmployeeApp/             # Android Kotlin app (Jetpack Compose)
```

## Prerequisites

1. **MySQL** - Ensure MySQL is running
2. **Java 17** - For Spring Boot
3. **Android Studio** - For the mobile app
4. **Device/Emulator** - Same network as your computer

## Backend Setup (empl)

1. Ensure MySQL is running and create database (or let the app create it):
   - Database: `empldb`
   - Username/password: See `empl/sbemplms/src/main/resources/application.yml`

2. From the `empl` folder:
   ```bash
   mvn install
   cd sbemplms
   mvn exec:java
   ```

3. The API runs at `http://localhost:8080`

### Task API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/task | List all tasks |
| GET | /api/task/{id} | Get task by ID |
| PUT | /api/task | Create task |
| POST | /api/task | Update task |
| DELETE | /api/task/{id} | Delete task |

## Android Setup (EmployeeApp)

1. **Configure server IP** in `EmployeeApp/app/src/main/java/com/gabriel/employeeapp/di/RetrofitClient.kt`:
   - Change `BASE_URL` to your computer's LAN IP (e.g. `http://192.168.1.100:8080/`)
   - Use `ipconfig` (Windows) or `ifconfig` (Mac/Linux) to find your IP
   - **Do not use `localhost`** - the emulator/phone cannot reach your PC via localhost

2. Open `EmployeeApp` in Android Studio

3. Build and run:
   ```bash
   .\gradlew.bat installDebug
   ```
   Or use Android Studio's Run button with an emulator/device connected.

## App Features

- **List** - View all tasks
- **Detail** - Tap a task to see full details
- **Create** - Use the + icon to add a new task
- **Update** - Tap Edit in the detail view to modify a task
- **Delete** - Tap Delete in the detail view to remove a task

## Troubleshooting

- **Can't connect to API**: Verify the BASE_URL IP matches your PC's IP. Emulator and phone must be on the same network.
- **MySQL connection failed**: Start MySQL service and check `application.yml` credentials.
- **Build fails**: Run `mvn install` from the root `empl` folder first for the backend.
