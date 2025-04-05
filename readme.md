# 🏨 Hotel Management System

A simple Java console application for managing a hotel. Load data from a CSV file, view room availability, manage bookings, and interact with the system through numbered commands.

## 🚀 Getting Started

### ✅ Requirements
- Java JDK 8 or higher
- CSV file containing hotel data (e.g. `hotel_data.csv`)

### ▶️ How to Run

1. Compile and run the `Main` class:
   ```bash
   javac Main.java
   java Main
   ```

2. At startup, the program will prompt you to load a CSV file:
   ```
   hotel_data.csv
   ```

3. Follow the on-screen menu to execute commands.

## 💻 Console Commands

Interact with the system by typing the corresponding number for an action.

Example menu:
```
1. Check In - Use this command to check in a guest to a room.
2. Check Out - Use this command to check out a guest from a room.
3. Get from CSV - Load hotel data from a CSV file.
4. Save to CSV - Save hotel data to a CSV file.
5. List Rooms - Display a list of all rooms and their statuses.
6. Prices - View room pricing information.
7. View - View detailed information about a specific room.
8. Exit - Exit application.
```

## 🏢 Hotel Layout

- The **ground floor** (`floor 0`) is **reception only** — **no guest rooms** are located here.
- Guest rooms are available on **floors 1 and above**.

## 📄 CSV Format

Your hotel data CSV file should define the structure and current status of the hotel. Each row might represent a room, including fields like:

```
floor,room_number,room_type,status
1,101,Single,Available
2,202,Double,Occupied
...
```

Make sure your CSV matches the expected format by the application.

## 📚 Documentation

Javadoc documentation is available for all classes and methods.

To generate it:

```bash
javadoc -d docs src/*.java
```

Then open `docs/index.html` in your browser.

## ⚖️ License

This project is provided for educational purposes and is free to use, modify, and distribute.

