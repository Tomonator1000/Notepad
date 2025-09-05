# Notepad

A simple and lightweight note-taking Android application built for API level 34. This app allows users to create, edit, and manage multiple notes with an intuitive interface.

## Features

- **Create Notes**: Add new notes with custom titles and content
- **Edit Notes**: Modify existing notes with title and content editing
- **Delete Notes**: Remove notes that are no longer needed
- **Dynamic Note Management**: Automatic note organization with expandable storage
- **Persistent Storage**: Notes are maintained using a singleton database pattern
- **Clean UI**: Simple and user-friendly interface with styled buttons

## Requirements

- **Android Studio**: Latest version recommended
- **Android SDK**: API level 34 (Android 14)
- **Java**: JDK 8 or higher
- **Gradle**: 8.0 (included via wrapper)
- **Minimum SDK**: API level 34
- **Target SDK**: API level 34

## Dependencies

- AndroidX AppCompat 1.6.1
- Material Design Components 1.10.0
- ConstraintLayout 2.1.4
- JUnit 4.13.2 (for testing)
- Espresso 3.5.1 (for UI testing)

## Installation & Setup

### Prerequisites
1. Install [Android Studio](https://developer.android.com/studio)
2. Install Android SDK API 34 through the SDK Manager
3. Set up an Android Virtual Device (AVD) with API 34 or connect a physical device

### Building the Project
1. Clone the repository:
   ```bash
   git clone https://github.com/Tomonator1000/Notepad.git
   cd Notepad
   ```

2. Open the project in Android Studio:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory and select it

3. Build the project:
   ```bash
   ./gradlew build
   ```

4. Run the application:
   - Connect an Android device or start an AVD
   - Click the "Run" button in Android Studio, or use:
   ```bash
   ./gradlew installDebug
   ```

## Project Structure

```
app/src/main/java/com/example/notepad/
├── MainActivity.java          # Main activity - note list and creation
├── NotepadActivity.java       # Note editing activity
├── Notepad.java              # Note data model
└── NotepadDatabase.java      # Singleton database for note storage

app/src/main/res/
├── layout/                   # UI layout files
├── values/                   # Resources (strings, colors, styles)
└── mipmap/                   # App icons
```

## Usage

### Creating a New Note
1. Launch the app
2. Tap "CREATE NEW NOTE" button
3. A new note button will appear in the list
4. Tap the note button to open it for editing

### Editing a Note
1. Tap on any existing note button
2. Enter a title in the "Note Name" field
3. Add content in the "Note Content" field
4. Tap "SAVE" to save changes
5. Tap "CANCEL" to discard changes

### Deleting a Note
1. Open the note you want to delete
2. Tap the "DELETE" button
3. The note will be removed from the list

## Technical Details

### Architecture
- **Pattern**: Singleton pattern for data management
- **Activities**: Two main activities (MainActivity, NotepadActivity)
- **Data Storage**: In-memory array-based storage with dynamic expansion
- **UI**: Material Design components with custom styling

### Key Classes
- **MainActivity**: Handles note list display and new note creation
- **NotepadActivity**: Manages individual note editing
- **Notepad**: Simple data model for note title and content
- **NotepadDatabase**: Singleton class managing note storage and retrieval

### Memory Management
- Dynamic array expansion (increases by 50 slots when full)
- Automatic null slot reuse for deleted notes
- Efficient index management for note organization

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Author(s)

- [**Ethan Townsend (snxethan)**](www.ethantownsend.dev)
