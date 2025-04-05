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
1 - Show available rooms
2 - Book a room
3 - Check out a guest
4 - View room details
0 - Exit the program
```

*(The exact list of commands may vary depending on the implementation.)*

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

